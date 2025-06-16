package ms.notification.dynamo.repository.model;

import java.util.Date;
import java.util.UUID;
 
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

 
@DynamoDbBean
public class Message {
	
	public static final String TYPE_EMAIL = "email";
	
 
	private UUID id;
	
 
	private String entityType;

 
	private String from;

 
	private String to;

 
	private String content;

 
	private String subject;

 
	private String status;

 
	private Date createDate;

 
	private Date sentDate;


	@DynamoDbPartitionKey
	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public String getEntityType() {
		return entityType;
	}


	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getSentDate() {
		return sentDate;
	}


	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
 

	
	
}
