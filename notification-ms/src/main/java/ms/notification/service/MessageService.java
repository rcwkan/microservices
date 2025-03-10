package ms.notification.service;

import java.util.List;

import ms.notification.model.Message;

public interface MessageService {

	boolean sendMessage(Message m) throws Exception;
	
	boolean notify(String username, String message) throws Exception;

	boolean send(Message m);

	List<Message> findRetryMessages();
	
	
	
}
