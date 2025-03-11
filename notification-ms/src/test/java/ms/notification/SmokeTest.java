package ms.notification;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ms.notification.controller.MessageController;

@SpringBootTest
public class SmokeTest {
	
	@Autowired
	private MessageController controller;

	
	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}
