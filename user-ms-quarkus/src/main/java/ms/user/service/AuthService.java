package ms.user.service;

public interface AuthService {

	String authenticate(String username, String password) throws Exception;

	String register(String username, String email, String displayName, String password);

}
