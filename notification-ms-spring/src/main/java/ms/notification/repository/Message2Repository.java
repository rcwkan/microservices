package ms.notification.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ms.notification.model.Message2;
 

public interface Message2Repository extends CrudRepository<Message2, Integer> {

  List<Message2> findByStatus(String status);

}
