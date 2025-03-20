package ms.notification.service;

import java.util.List;

import ms.notification.dynamo.repository.model.Email;
 

public interface MessageService {

	Email sendMessage(Email m) throws Exception;
	
	Email notify(String username, String message) throws Exception;

	Email sendEmail(Email m) throws Exception;

	List<Email> findRetryMessages();
	
	
	
}
