package ms.notification.dynamo.repository;

import java.util.List;
import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import ms.notification.dynamo.repository.model.Email;
import ms.notification.model.Message;

@EnableScan 
public interface EmailRepository extends  CrudRepository<Email, String> {
	
	Optional<Email> findById(String id);

	List<Email> findByStatus(String status);

}
