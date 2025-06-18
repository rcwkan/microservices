package ms.notification;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ms.notification.dynamo.repository.model.Message;
import ms.notification.model.JwtUserDetails;
import ms.notification.service.MessageService;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTests {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean  
	private MessageService messageService;
	
	private JwtUserDetails user =  new JwtUserDetails("tester01", "", Collections.emptyList())  ;

	@Test
	void sendTest() throws Exception {
		
		Message message = Message.builder().to("tester01").content("Message").status("S").build();

		Mockito.when(messageService.sendMessage(any(Message.class))).thenReturn(message);
	 
		

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(message);
		

		mockMvc.perform(post("/message/send")
				//.with(SecurityMockMvcRequestPostProcessors.jwt().authorities(new SimpleGrantedAuthority("USER")))
				.with(SecurityMockMvcRequestPostProcessors.user(user))
				.contentType(MediaType.APPLICATION_JSON).content(json))//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("success"));

	}
	
	
	@Test
	void sendTest_missing_to() throws Exception {

		Message message = Message.builder().content("Message").build();

		Mockito.when(messageService.sendMessage(any(Message.class))).thenThrow(new Exception("Missing msgTo."));

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(message);

		mockMvc.perform(post("/message/send")
				.with(SecurityMockMvcRequestPostProcessors.user(user))
				.contentType(MediaType.APPLICATION_JSON).content(json))//.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Missing msgTo.")); 
	}
	
	@Test
	void sendTest_missing_content() throws Exception {

		Message message = Message.builder().to("tester01").build();

		Mockito.when(messageService.sendMessage(any(Message.class))).thenThrow(new Exception("Missing content."));

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(message);

		mockMvc.perform(post("/message/send")
				.with(SecurityMockMvcRequestPostProcessors.user(user))
				.contentType(MediaType.APPLICATION_JSON).content(json))//.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Missing content.")); 
	}
	
	@Test
	void sendTest_internal_error() throws Exception {

		Message message = Message.builder().to("tester01").content("Message").status("P").build();

		Mockito.when(messageService.sendMessage(any(Message.class))).thenReturn(message);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(message);

		mockMvc.perform(post("/message/send")
				.with(SecurityMockMvcRequestPostProcessors.user(user))
				.contentType(MediaType.APPLICATION_JSON).content(json))//.andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("fail")); 
	}

	@Test
	void notifyTest() throws Exception {
		
		Message message = Message.builder().to("tester01").content("Message").status("S").build();


		Mockito.when(messageService.notify("tester01","Message" )).thenReturn(message);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(message);


		mockMvc.perform(post("/message/notify")
				.with(SecurityMockMvcRequestPostProcessors.user(user))
				.contentType(MediaType.APPLICATION_JSON).content(json))//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("success"));

	}

}
