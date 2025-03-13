package ms.notification.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ms.notification.model.NotificationConfig;
import ms.notification.repository.NotificationConfigRepository;
import ms.notification.service.NotificationConfigService;

@Service
public class NotificationConfigServiceImpl implements NotificationConfigService {

	private static final Logger log = LoggerFactory.getLogger(NotificationConfigService.class);
	 
	private static final String DEFAULT_NOTIFICATION_TYPE = "EMAIL";
	
	@Autowired
	NotificationConfigRepository notificationConfigRepository;

	@Transactional//(readOnly = true)
	public NotificationConfig getNotificationConfig(String username) {

		Optional<NotificationConfig> oConfig = notificationConfigRepository.findById(username);

		if (oConfig.isPresent()) {
			return oConfig.get();
		}
		
		NotificationConfig nConfig = new NotificationConfig(username, DEFAULT_NOTIFICATION_TYPE);
		notificationConfigRepository.save(nConfig);
		return nConfig;

	}

}
