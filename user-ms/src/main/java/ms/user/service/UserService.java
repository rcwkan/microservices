package ms.user.service;

import java.util.List;

import ms.user.models.UserAccount;

public interface UserService {

	List<UserAccount> findUser(String username);

	void createUser(UserAccount newUser, String pwdHash);

	UserAccount readUser(int id);

	void updateUser(UserAccount prevUser);

	void deleteUser(UserAccount user);

	List<UserAccount> readAllUsers();

	 

}
