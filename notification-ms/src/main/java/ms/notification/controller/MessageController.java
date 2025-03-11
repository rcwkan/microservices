package ms.notification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

		boolean success;
		try {
			success = messageService.sendMessage(message);

		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		if (success) {
			return ResponseEntity.ok("success");
		}
		return ResponseEntity.badRequest().body("fail");

	}

	@Operation(summary = "Send Notification")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Message is pending to send out.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input provided") })
	@PostMapping("/notify")
	public ResponseEntity<String> notify(@RequestParam String username, @RequestParam String content) {

		boolean success;
		try {
			success = messageService.notify(username, content);
		 
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		if (success) {
			return ResponseEntity.ok("success");
		}
		return ResponseEntity.badRequest().body("fail");

	}

//	
//	@MessageMapping("/push")  
//	@SendTo("/message/notifications")  
//	public String push(String username, String message) {
//		return message;
//	}

}
