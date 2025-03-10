package ms.notification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ms.notification.jwt.JwtAuthenticationFilter;
import ms.notification.model.Message;
import ms.notification.service.MessageService;

@RestController
@RequestMapping("message")
public class MessageController {

	private static final Logger log = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	MessageService messageService;

	@Operation(summary = "Send Message")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Message is pending to send out.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input provided") }) 
	@PostMapping("/send")
	public ResponseEntity<String> sendMesssage(@RequestBody Message message) {

		messageService.sendMessage(message);

		return ResponseEntity.ok("success");
	}
	

	@Operation(summary = "Send Notification")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Message is pending to send out.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input provided") }) 
	@PostMapping("/notify")
	public ResponseEntity<String> notify(String username, String content) {

		messageService.notify(username, content);

		return ResponseEntity.ok("success");
	}
	
	 
	
 
//	
//	@MessageMapping("/push")  
//	@SendTo("/message/notifications")  
//	public String push(String username, String message) {
//		return message;
//	}

}
