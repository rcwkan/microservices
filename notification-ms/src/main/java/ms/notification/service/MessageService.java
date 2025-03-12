package ms.notification.service;

import java.util.List;

import ms.notification.model.Message;

public interface MessageService {

	Message sendMessage(Message m) throws Exception;
	
	Message notify(String username, String message) throws Exception;

	Message send(Message m);

	List<Message> findRetryMessages();
	
	
	
}
