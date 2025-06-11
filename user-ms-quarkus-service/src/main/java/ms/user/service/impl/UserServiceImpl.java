package ms.user.service.impl;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.jboss.logging.Logger;

import app.core.util.CoreUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ms.user.dynamo.UserRepository;
import ms.user.dynamo.model.User;
import ms.user.service.UserService;

@Named("userService")
@ApplicationScoped
public class UserServiceImpl implements UserService {

	private static final Logger log = Logger.getLogger(UserServiceImpl.class);

	@Inject
	UserRepository userRepository;

	@Override
	public User register(String username, String email, String displayName, String password) throws Exception {

		if (findUser(username) !=null ) {
			throw new Exception("User already exists");
		}

		User newUser = new User(username, email, displayName);
		newUser.setCreateDate(Instant.now());
		newUser.setPwdHash(CoreUtils.hashPwd(newUser.getUserId(), password));

		userRepository.save(newUser);

		return newUser;
	}

	@Override
	public List<User> findUser(String username) {
		
		  
		
		return Collections.EMPTY_LIST;
	}

	@Override
	public void createUser(User newUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public User readUser(String id) {
		 
		return 	userRepository.findById(id);
	}

	@Override
	public void updateUser(User prevUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> readAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
