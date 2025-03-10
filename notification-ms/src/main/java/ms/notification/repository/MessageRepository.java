package ms.notification.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ms.notification.model.Message;
 

public interface MessageRepository extends CrudRepository<Message, Integer> {

  List<Message> findByStatus(String status);

}
