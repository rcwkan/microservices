package ms.sync.dynamo.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class SyncLog {
 
  
//	@Id
	public String username;
 
	public String syncType;

	public String status;

	public String fileId;
	
	private long createDate;
	

	public SyncLog() {
		
	}

	public SyncLog(String username) {
		this.username = username;
	}
 
	
	
	@DynamoDbPartitionKey
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

	@DynamoDbSortKey
	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

  

}
