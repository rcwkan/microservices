package ms.notification.controller;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ms.notification.jwt.JwtAuthenticationFilter;
import ms.notification.service.MessageService;

@RestController
@RequestMapping("notification")
public class NotificationController {

 
	private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
	 
	@Autowired
	MessageService messageService;
	
	
	@PostMapping("/notify")
	public String notify(String username, String message) {
		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 
		
//		LOGGER.info("authentication : {} {}", authentication.getName() ,authentication.getPrincipal().toString());
		
		messageService.sendMessage(username, message);

		return "";
	}
	
	
	@MessageMapping("/push")  
	@SendTo("/message/notifications")  
	public String push(String username, String message) {
		return message;
	}


}
