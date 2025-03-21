package ms.user.service;

import java.util.List;

import ms.user.dynamo.model.User;
 

public interface UserService {

	List<User> findUser(String username);

	void createUser(User newUser);

	User readUser(String id);

	void updateUser(User prevUser);

	void deleteUser(User user);

	List<User> readAllUsers();

	 

}
