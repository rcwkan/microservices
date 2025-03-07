package ms.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import app.core.jwt.JwtVertxUtils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonValue;
import jakarta.transaction.Transactional;
import ms.user.dao.UserDao;
import ms.user.models.User;
import ms.user.models.UserCred;
import ms.user.service.UserService;

@ApplicationScoped
public class UserServiceImpl implements UserService {

	@Inject
	private UserDao userDao;

	@Override
	public List<User> findUser(String name) {
	 
		return userDao.findUser(name);
	}

	@Override
	@Transactional
	public void createUser(User newUser, String pwdHash) {

		newUser.setCreateDate(new Date());

		userDao.createUser(newUser);

		UserCred cred = new UserCred(newUser.getUserId(), pwdHash, true, new Date());

		newUser.setCreds(new ArrayList<>());
		newUser.getCreds().add(cred);
		userDao.updateUser(newUser);
	}

	@Override
	public User readUser(int id) {

		return userDao.readUser(id);
	}

	@Override
	public void updateUser(User prevUser) {

		prevUser.setUpdateDate(new Date());
		userDao.updateUser(prevUser);

	}

	@Override
	public void deleteUser(User user) {
		userDao.deleteUser(user);

	}

	@Override
	public List<User> readAllUsers() {

		return userDao.readAllUsers();
	}
  
}
