package ms.sync.service;

import java.io.File;

import ms.sync.dynamo.entity.SyncLog;
 

public interface SyncService {

	SyncLog createSyncLog(SyncLog syncLog) ;
	
	boolean uploadSyncLog(File data);
	
 

}
