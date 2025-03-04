package ms.user.service.impl;
 
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import app.core.jwt.JwtBuilder;
//import app.core.jwt.JwtBuilder;
import app.core.util.CoreUtils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ms.user.dao.UserDao;
import ms.user.models.User;
import ms.user.models.UserCred;
import ms.user.service.AuthService;
 
@ApplicationScoped
public class AuthServiceImpl implements AuthService {

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

	@PostConstruct
	public void init() { 
		JwtBuilder.init(keystore,keyAlias, keypass);
	}
 

	@Override 
	public String authenticate(String username, String pwd) throws Exception{

		List<User> users = userDao.findUser(username);;
		if (users.isEmpty() || users.size() != 1) {
			throw new Exception("User not found.");
		}

		List<UserCred> creds = users.get(0).getCreds().stream().filter(c -> c.isActive()).collect(Collectors.toList());

		if (creds.isEmpty() || creds.size() != 1) {
			throw new Exception("Password expired.");
		}

		if( creds.get(0).getPwdHash().equals(CoreUtils.hashString(pwd))) { 
			return JwtBuilder.createUserJwt(username);  
		} 
		
		throw new Exception("Login Failed.");

	}

}
