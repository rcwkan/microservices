package ms.notification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
import ms.notification.dynamo.repository.model.Message;
import ms.notification.model.JwtUserDetails;
import ms.notification.service.MessageService;
import ms.user.client.api.DefaultApi;

@RestController
@RequestMapping("message")
public class MessageController {

	private static final Logger log = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	DefaultApi userApi;

	@Autowired
	MessageService messageService;

	@Operation(summary = "Send Message")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Message is pending to send out.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input provided"),
			@ApiResponse(responseCode = "500", description = "System Error.") })

	@PostMapping("/send")
	public ResponseEntity<String> sendMesssage(@RequestBody Message message) {

		log.info("sendMesssage: {}", message);

		boolean success;

		try {
			
//			JwtUserDetails jUSer = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
//					.getPrincipal();
//			userApi.getApiClient().addDefaultHeader("Authorization", "Bearer " + jUSer.getJwt());
//
//			log.info("sendMesssage jUSer.getJwt() {}", jUSer.getJwt());
//			Object obj = userApi.usersMeGet();
//
//			log.info("userApi.usersMeGet():" + obj);
			

			Message sMessage = messageService.sendMessage(message);

			success = "S".equals(sMessage.getStatus());

		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		if (success) {
			return ResponseEntity.ok("success");
		}
		
		return ResponseEntity.internalServerError().body("fail");

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
			Message sMessage = messageService.notify(username, content);

			success = "S".equals(sMessage.getStatus());

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
