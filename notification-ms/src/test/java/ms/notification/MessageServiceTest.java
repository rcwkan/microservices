package ms.notification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import ms.notification.adaptor.EmailAdaptor;
import ms.notification.dynamo.repository.EmailRepository;
import ms.notification.dynamo.repository.model.Email;
import ms.notification.model.Message;
 
import ms.notification.service.MessageService;
import ms.notification.service.impl.MessageServiceImpl;

@SpringBootTest
public class MessageServiceTest {

	@Mock
	private EmailRepository messageRepository;

	@Mock
	private EmailAdaptor emailAdaptor;

	@InjectMocks
	private MessageService messageService = new MessageServiceImpl();

	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void notifyMessage() throws Exception {

		Email message = Email.builder().to("tester01").content("Message").build();

		Mockito.when(this.messageRepository.save(message)).thenReturn(message);
		Mockito.when(this.emailAdaptor.send(message.getFrom(), message.getTo(), message.getSubject(),
				message.getContent(), null)).thenReturn(true);

		try {
			Email successMsg = this.messageService.notify(message.getTo(), message.getContent());

			Mockito.verify(this.messageRepository, Mockito.times(2)).save(any(Email.class));

			Mockito.verify(this.emailAdaptor, Mockito.times(1)).send(message.getFrom(), message.getTo(),
					message.getSubject(), message.getContent(), null);

			assertNotNull(successMsg);
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void sendMessage() throws Exception {

		Email message = Email.builder().to("tester01").content("Message").build();

		Mockito.when(this.messageRepository.save(message)).thenReturn(message);
		Mockito.when(this.emailAdaptor.send(message.getFrom(), message.getTo(), message.getSubject(),
				message.getContent(), null)).thenReturn(true);

		try {

			Email successMsg = this.messageService.sendMessage(message);

			Mockito.verify(this.messageRepository, Mockito.times(2)).save(message);

			Mockito.verify(this.emailAdaptor, Mockito.times(1)).send(message.getFrom(), message.getTo(),
					message.getSubject(), message.getContent(), null);

			assertNotNull(successMsg);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void sendMessage_missing_username() {

		Email message = Email.builder().content("Message").build();

		try {

			this.messageService.sendMessage(message);

			fail("validation not implemented");

		} catch (Exception e) {

			assertTrue("Missing msgTo.".equals(e.getMessage()));
		}
	}

	@Test
	public void sendMessage_missing_content() {

		Email message = Email.builder().to("tester01").build();

		try {

			this.messageService.sendMessage(message);

			fail("validation not implemented");

		} catch (Exception e) {
			assertTrue("Missing content.".equals(e.getMessage()));
		}
	}

}
