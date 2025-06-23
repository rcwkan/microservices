package ms.sync.dynamo;

 

import jakarta.enterprise.context.ApplicationScoped;
import ms.sync.dynamo.entity.SyncLog;
 
 
@ApplicationScoped
public class SyncLogRepository extends DynamoBaseRepository<SyncLog, String>{
	

	public SyncLogRepository() {
		super(SyncLog.class);
	}

}
