package ms.sync.dynamo.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.json.bind.annotation.JsonbTransient;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class SyncLog {

//	@Id
	public UUID id;

	public String username;

	public String syncType;

	public String status;

	public String fileId;

	private LocalDateTime createDate;

	public SyncLog() {

	}

	public SyncLog(String uuid) {
		this.id = UUID.fromString(uuid);
	}
	
	public SyncLog(UUID id, String username) {
		this.username = username;

		this.id = id;
	}

	public SyncLog(String uuid, String username) {
		this.username = username;

		this.id = UUID.fromString(uuid);
	}
 

	@DynamoDbPartitionKey
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@DynamoDbSortKey
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSyncType() {
		return syncType;
	}

	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
  
	

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
