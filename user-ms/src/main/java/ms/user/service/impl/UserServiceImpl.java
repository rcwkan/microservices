package ms.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ms.user.dao.UserDao;
import ms.user.dynamo.UserRepository;
import ms.user.dynamo.model.User;
import ms.user.models.UserAccount;
import ms.user.models.UserCred;
import ms.user.service.UserService;

@ApplicationScoped
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	 

	@Inject
	private UserRepository userDao;

	@Override
	public List<User> findUser(String name) {
	 
		return userDao.findUser(name);
	}

	@Override
	@Transactional
	public void createUser(User newUser) {

		newUser.setCreateDate(new Date());


		userDao.save(newUser);
		
		
//		newUser.setPwdHash(pwdHash);
//		UserCred cred = new UserCred(newUser.getUserId(), pwdHash, true, new Date());

//		newUser.setCreds(new ArrayList<>());
//		newUser.getCreds().add(cred);
//		userDao.updateUser(newUser);
	}

	@Override
	public User readUser(String id) {

		return userDao.findById(id).get();
	}

	@Override
	public void updateUser(User prevUser) {

		//prevUser.setUpdateDate(new Date());
		userDao.save(prevUser);

	}

	@Override
	public void deleteUser(User user) {
		userDao.delete(user);

	}

	@Override
	public List<User> readAllUsers() {

		return userDao.findAll().collect(Collectors.toList());  
	}
  
}
