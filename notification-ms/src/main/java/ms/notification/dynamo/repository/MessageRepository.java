package ms.notification.dynamo.repository;

import java.util.List;
import java.util.Optional;

//import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import ms.notification.dynamo.repository.model.Message;

//@EnableScan 
public interface MessageRepository extends  CrudRepository<Message, String> {
	
	Optional<Message> findById(String id);

	List<Message> findByStatus(String status);

}
