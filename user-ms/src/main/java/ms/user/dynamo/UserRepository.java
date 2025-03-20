package ms.user.dynamo;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.jnosql.databases.dynamodb.mapping.DynamoDBRepository;
import org.eclipse.jnosql.databases.dynamodb.mapping.PartiQL;

import jakarta.data.repository.Param;
import jakarta.data.repository.Repository;
import ms.user.dynamo.model.User;


@Repository
public interface UserRepository  extends DynamoDBRepository<User, String> {
	
	
	@PartiQL("select * from \"user\"")
	Stream<User> findAll();

	@PartiQL("select * from \"user\" where username = ?")
	List<User> findUser(@Param("") String name);


}
