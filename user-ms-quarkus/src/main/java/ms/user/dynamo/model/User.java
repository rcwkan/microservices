package ms.user.dynamo.model;

import java.time.Instant;
import java.util.UUID;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class User {
 
 
    private String userId;
  
	private String username;

	private String displayName;
 
	private String email;
 
	private Boolean isActive;
 
	private String pwdHash;
 
	private Instant createDate;

	public User() { 
	 
		this.isActive = true;
//		this.userId = UUID.randomUUID().toString();
	}

	public User(String username, String email, String displayName) {
	 
		this.username = username;
		this.email = email;
		this.displayName = displayName;
 
		this.isActive = true; 
		this.userId = UUID.randomUUID().toString();
	}

	@DynamoDbPartitionKey
	public String getUsername() {
		return username;

	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getPwdHash() {
		return pwdHash;
	}

	public void setPwdHash(String pwdHash) {
		this.pwdHash = pwdHash;
	}
  

	public Instant getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Instant createDate) {
		this.createDate = createDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	 
 
 
	

}
