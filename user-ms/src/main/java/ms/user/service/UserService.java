package ms.user.service;

import java.util.List;

import jakarta.json.JsonValue;
import ms.user.models.User;

public interface UserService {

	List<User> findUser(String username);

	void createUser(User newUser, String pwdHash);

	User readUser(int id);

	void updateUser(User prevUser);

	void deleteUser(User user);

	List<User> readAllUsers();

	 

}
