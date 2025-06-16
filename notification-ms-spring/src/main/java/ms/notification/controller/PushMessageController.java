package ms.notification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//https://medium.com/@javatechie/build-real-time-notifications-in-spring-boot-applications-websocket-1d5a452c528c

//@Controller
public class PushMessageController {
	

	private static final Logger log = LoggerFactory.getLogger(PushMessageController.class);
	 

	
//	
//	@MessageMapping("/sendMessage")  
//	@SendTo("/message/notifications")  
	public String push(String username, String message) {
		return message;
	}

}
