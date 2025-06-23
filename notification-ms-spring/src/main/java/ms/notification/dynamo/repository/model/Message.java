package ms.notification.dynamo.repository.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDbBean
public class Message {

	public static final String TYPE_EMAIL = "email";

	private UUID id;
	
	//private String uuid;

	private String entityType;

	private String from;

	private String to;

	private String content;

	private String subject;

	private String status;

	private LocalDateTime createDate;

	private LocalDateTime sentDate;

	@DynamoDbPartitionKey
	public UUID getId() {
		return id;
	}

 
 
 
/*
	@DynamoDbPartitionKey
	@DynamoDbAttribute("id")
	public String getId() {
		return uuid;
	}

	public void setId(String id) {
		this.uuid = id;
	}
*/
	
	
	
}
