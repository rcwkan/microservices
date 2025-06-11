package ms.user.service.impl;

import java.io.File;
import java.net.URISyntaxException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.reflections.util.ClasspathHelper;

import app.core.jwt.JwtUtils;
import app.core.util.CoreUtils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ms.user.dynamo.UserRepository;
import ms.user.dynamo.model.User;
import ms.user.service.AuthService;

@Named("authService")
@ApplicationScoped
public class AuthServiceImpl implements AuthService {

	private static final Logger log = Logger.getLogger(AuthServiceImpl.class);

	@Inject
	UserRepository userRepository;

	@Inject
	@ConfigProperty(name = "ms.core.key.store", defaultValue = "key.p12")
	private String keystore;

	@Inject
	@ConfigProperty(name = "ms.core.key.pass", defaultValue = "password")
	private String keypass;

	@Inject
	@ConfigProperty(name = "ms.core.key.alias", defaultValue = "1")
	private String keyAlias;

	JwtUtils jwtUtils;

	@PostConstruct
	public void init() throws URISyntaxException {

		log.info("loadKeyPair: keystore: " + keystore);
		log.info("loadKeyPair: keystore: " 	+ ClasspathHelper.contextClassLoader().getResource(keystore).toURI().toString());
		File f = new File(ClasspathHelper.contextClassLoader().getResource(keystore).getFile());
 

		jwtUtils = JwtUtils.getInstance(f, keyAlias, keypass);
	}

	@Override
	public String authenticate(String username, String password) throws Exception {

		log.info("authenticate username:  " + username + "," + password);

		try {
			User user = userRepository.findById(username);

			if (user == null)
				throw new Exception("User not found. username:" + username);

			if (user.getPwdHash().equals(CoreUtils.hashPwd(user.getUserId(), password)))

				return jwtUtils.generateToken(user.getUsername());

		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new Exception("Login Failed.");

	}

}
