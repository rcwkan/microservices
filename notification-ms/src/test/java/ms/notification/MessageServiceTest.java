package ms.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import ms.notification.adaptor.EmailAdaptor;
import ms.notification.model.Message;
import ms.notification.repository.MessageRepository;
import ms.notification.service.MessageService;
import ms.notification.service.impl.MessageServiceImpl;

@SpringBootTest
public class MessageServiceTest {

	@Mock
	private MessageRepository messageRepository;
	
	@Mock
	private EmailAdaptor emailAdaptor;

	@InjectMocks
	private MessageService messageService = new MessageServiceImpl();

	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void sendMessage() throws IOException {

		Message message = Message.builder().msgTo("tester01").content("Message").build();

		Mockito.when(this.messageRepository.save(message)).thenReturn(message); 
		Mockito.when(this.emailAdaptor.send(message.getMsgFrom(), message.getMsgTo(), message.getSubject(), message.getContent(), null)).thenReturn(true);

		try {
			
			boolean success = this.messageService.sendMessage(message);

			Mockito.verify(this.messageRepository, Mockito.times(2)).save(message);
			
			Mockito.verify(this.emailAdaptor, Mockito.times(1)).send(message.getMsgFrom(), message.getMsgTo(), message.getSubject(), message.getContent(), null);
			
			assertThat(success);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

}
