package ms.notification.job;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import ms.notification.dynamo.repository.model.Message;
import ms.notification.model.Message2;
import ms.notification.service.MessageService;

@Service
public class MessageJob {
	
	
	private static final Logger log = LoggerFactory.getLogger(MessageJob.class);
	
	
	@Autowired
	MessageService messageService;
	

	@Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
	//@Transactional
	public void scheduleSendRetryMessages() {
		
		log.info("scheduleSendRetryMessages ");

		// resend retry message

		// get List of message to retry
		List<Message> messages = messageService.findRetryMessages();

		for (Message m : messages) {
			try {
				messageService.sendEmail(m);
			} catch (Exception e) {
				log.info("scheduleSendRetryMessages");
			}
		}
	}

}
