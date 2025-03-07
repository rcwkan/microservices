package ms.notification.service.impl;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.notification.adaptor.EmailAdaptor;
 
import ms.notification.repository.MessageRepository;
import ms.notification.service.MessageService;


@Service
public class MessageServiceImpl implements MessageService {
 
	private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);
	 
	
	@Autowired 
	EmailAdaptor emailAdaptor;
	
	
	@Autowired
	MessageRepository messageRepository;


	@Override
	public void sendMessage(String username, String message) {
		// TODO Auto-generated method stub
		
	}
	
	

}
