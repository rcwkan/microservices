package ms.notification.dynamo.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Repository;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import ms.notification.dynamo.repository.model.Message;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Repository
public class MessageRepository {

	@Autowired
	DynamoDbTemplate dynamoDbTemplate;
	
	public Message save(Message msg) {
	
		if(msg.getId() ==null) {
			//create
			msg.setId(UUID.randomUUID()); 
			msg.setCreateDate(LocalDate.now());
		}
		dynamoDbTemplate.save(msg);
		
		
		return  msg;
	}

	public Message findById(String id) {
		Key key = Key.builder().partitionValue(id).build();
		return dynamoDbTemplate.load(key, Message.class);
	}

	public List<Message> findByStatus(String status) {

		Map<String, AttributeValue> expressionValues = new HashMap<>();
		expressionValues.put(":val1", AttributeValue.fromS(status));

		Expression filterExpression = Expression.builder().expression("status = :val1")
				.expressionValues(expressionValues).build();

		ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder().filterExpression(filterExpression)
				.build();
		PageIterable<Message> returnedList = dynamoDbTemplate.scan(scanEnhancedRequest, Message.class);
		
		return returnedList.items().stream().toList();
	}

}
