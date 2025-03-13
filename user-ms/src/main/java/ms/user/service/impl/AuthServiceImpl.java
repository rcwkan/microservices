package ms.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.core.jwt.JwtUtils;
import app.core.jwt.JwtVertxUtils;
//import app.core.jwt.JwtBuilder;
import app.core.util.CoreUtils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ms.user.dao.UserDao;
import ms.user.models.User;
import ms.user.models.UserCred;
import ms.user.resources.UserResource;
import ms.user.service.AuthService;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {
	
	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
	 

	@Inject
	private UserDao userDao;

	@Inject
	@ConfigProperty(name = "ms.core.key.store", defaultValue = "key.p12")
	private String keystore;

	@Inject
	@ConfigProperty(name = "ms.core.key.pass", defaultValue = "")
	private String keypass;

	@Inject
	@ConfigProperty(name = "ms.core.key.alias", defaultValue = "1")
	private String keyAlias;

	JwtUtils jwtUtils;
	
//	JwtVertxUtils jwtUtils;

	@PostConstruct
	public void init() {
		jwtUtils = JwtUtils.getInstance(keystore, keyAlias, keypass);
	}

	@Override
	@Transactional
	public String authenticate(String username, String pwd) throws Exception {
		
		log.info("authenticate: {}",username);

		List<User> users = userDao.findUser(username);
		;
		if (users.isEmpty() || users.size() != 1) {
			throw new Exception("User not found. username:" + username);
		}

		List<UserCred> creds = users.get(0).getCreds().stream().filter(c -> c.isActive()).collect(Collectors.toList());

		if (creds.isEmpty() || creds.size() != 1) {
			throw new Exception("Password expired.");
		}

		if (creds.get(0).getPwdHash().equals(CoreUtils.hashString(pwd))) {
			return jwtUtils.generateToken(username);
		}

		throw new Exception("Login Failed.");

	}

}
