package ms.notification.repository;

import org.springframework.data.repository.CrudRepository;

import ms.notification.model.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {

}
