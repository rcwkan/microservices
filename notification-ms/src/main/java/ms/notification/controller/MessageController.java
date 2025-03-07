package ms.notification.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


//https://medium.com/@javatechie/build-real-time-notifications-in-spring-boot-applications-websocket-1d5a452c528c

@Controller
public class MessageController {
	

	@MessageMapping("/sendMessage")  
	@SendTo("/message/notifications")  
	public String push(String username, String message) {
		return message;
	}

}
