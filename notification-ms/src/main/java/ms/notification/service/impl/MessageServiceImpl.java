package ms.notification.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.netty.util.internal.StringUtil;
import ms.notification.adaptor.EmailAdaptor;
import ms.notification.model.Message;
import ms.notification.repository.MessageRepository;
import ms.notification.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

	private static final String MSG_STATUS_FAILED = "F";

	private static final String MSG_STATUS_RETRY = "R";

	private static final String MSG_STATUS_SENT = "S";

	private static final String MSG_STATUS_PENDING = "P";

	@Autowired
	EmailAdaptor emailAdaptor;

	@Autowired
	MessageRepository messageRepository;

	@Override
	@Transactional
	public boolean notify(String username, String message) throws Exception {

		// validation
		if (StringUtils.isEmpty(username)) {
			throw new Exception("Missing username.");
		}
		if (StringUtils.isEmpty(message)) {
			throw new Exception("Missing message.");
		}
		
		
		Message msg = new Message(username, message);
		msg.setStatus(MSG_STATUS_PENDING);
		msg.setCreateDate(new Date());

		messageRepository.save(msg);

		return send(msg);

	}

	@Override
	@Transactional
	public boolean sendMessage(Message msg) throws Exception {

		// validation
		if (StringUtils.isEmpty(msg.getMsgTo())) {
			throw new Exception("Missing msgTo.");
		}
		if (StringUtils.isEmpty(msg.getContent())) {
			throw new Exception("Missing content.");
		}
		
		msg.setStatus(MSG_STATUS_PENDING);
		msg.setCreateDate(new Date());

		messageRepository.save(msg);

		return send(msg);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean send(Message message) {

		boolean success;
		try {
			success = emailAdaptor.send(null, null, null, null, null);
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
			success = false;
		}

		if (!success) {
			if (MSG_STATUS_PENDING.equals(message.getStatus())) {
				message.setStatus(MSG_STATUS_RETRY);
			} else if (MSG_STATUS_RETRY.equals(message.getStatus())) {
				message.setStatus(MSG_STATUS_FAILED);
			}

		} else {
			message.setStatus(MSG_STATUS_SENT);
		}
		messageRepository.save(message);

		return success;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Message> findRetryMessages() {
		return messageRepository.findByStatus(MSG_STATUS_RETRY);
	}

}
