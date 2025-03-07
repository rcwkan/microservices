package ms.notification.repository;

import org.springframework.data.repository.CrudRepository;
 
import ms.notification.model.NotificationConfig;

public interface NotificationConfigRepository  extends CrudRepository<NotificationConfig, String> {

}
