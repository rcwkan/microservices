package ms.notification.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ms.notification.adaptor.EmailAdaptor;
import ms.notification.dynamo.repository.MessageRepository;
import ms.notification.dynamo.repository.model.Message;
 
import ms.notification.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

	private static final String MSG_STATUS_FAILED = "F";

	private static final String MSG_STATUS_RETRY = "R";

	public static final String MSG_STATUS_SENT = "S";

	public static final String MSG_STATUS_PENDING = "P";

	@Autowired
	EmailAdaptor emailAdaptor;

	// @Autowired
	// MessageRepository messageRepository;

	@Autowired
	MessageRepository messageRepository;

	@Override
	@Transactional
	public Message notify(String username, String message) throws Exception {

		// validation
		if (StringUtils.isEmpty(username)) {
			throw new Exception("Missing username.");
		}
		if (StringUtils.isEmpty(message)) {
			throw new Exception("Missing message.");
		}

//		Message msg = new Message(username, message); 
//		msg.setStatus(MSG_STATUS_PENDING);
//		msg.setCreateDate(new Date());

//		Message msg = Message.builder().msgTo(username).content(message).status(MSG_STATUS_PENDING)
//				.createDate(new Date()).build();
//		messageRepository.save(msg);

		Message email = Message.builder().entityType(Message.TYPE_EMAIL).to(username).content(message).status(MSG_STATUS_PENDING).createDate(new Date())
				.build();
		messageRepository.save(email);

		return sendEmail(email);

	}

	@Override
	@Transactional
	public Message sendMessage(Message email) throws Exception {

		// validation
		if (StringUtils.isEmpty(email.getTo())) {
			throw new Exception("Missing msgTo.");
		}
		if (StringUtils.isEmpty(email.getContent())) {
			throw new Exception("Missing content.");
		}
		
		
		email.setEntityType(Message.TYPE_EMAIL);
		email.setStatus(MSG_STATUS_PENDING);
		email.setCreateDate(new Date());

		messageRepository.save(email);

		return sendEmail(email);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Message sendEmail(Message m) throws Exception {

//		try {
		boolean success = emailAdaptor.send(m.getFrom(), m.getTo(), m.getSubject(), m.getContent(), null);
//		} catch (IOException e) {
//			log.warn(e.getMessage(), e);
//			success = false;
//		}

		if (!success) {
			if (MSG_STATUS_PENDING.equals(m.getStatus())) {
				m.setStatus(MSG_STATUS_RETRY);
			} else if (MSG_STATUS_RETRY.equals(m.getStatus())) {
				m.setStatus(MSG_STATUS_FAILED);
			}

		} else {
			m.setStatus(MSG_STATUS_SENT);
		}

		messageRepository.save(m);

		return m;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Message> findRetryMessages() {
		return messageRepository.findByStatus(MSG_STATUS_RETRY);
	}

}
