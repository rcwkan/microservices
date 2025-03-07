package ms.notification.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ms.notification.jwt.JwtAuthenticationFilter;

@RestController
@RequestMapping("notification")
public class NotificationController {

	private static final Logger LOGGER = LogManager.getLogger(NotificationController.class);
	
	
	@PostMapping("/notify")
	public String notify(String username, String message) {

		return "";
	}
	
	
	@MessageMapping("/push")  
	@SendTo("/message/notifications")  
	public String push(String username, String message) {
		return message;
	}


}
