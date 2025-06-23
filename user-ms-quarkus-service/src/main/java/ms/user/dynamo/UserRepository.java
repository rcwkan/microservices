package ms.user.dynamo;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
 
import ms.user.dynamo.model.User;

@ApplicationScoped
public class UserRepository extends DynamoBaseRepository<User, String> {

	protected UserRepository() {
		super(User.class);
		// TODO Auto-generated constructor stub
	}

	private static final Logger log = Logger.getLogger(UserRepository.class);
  
	public User findById(String username) {
		User user = new User();
		user.setUsername(username);

		return load(user);
	}

}
