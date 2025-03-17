package ms.sync.service.impl;

import ms.sync.entity.SyncLog;
import ms.sync.resources.SyncResource;
import ms.sync.service.SyncService;

import java.io.File;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped 
public class SyncServiceImpl implements SyncService {
 

	private static final Logger log = Logger.getLogger(SyncServiceImpl.class);
	
	
	@Override
	@Transactional
	public SyncLog createSyncLog(SyncLog syncLog) {
		
		log.infov("createSyncLog : "+ syncLog.username);
		syncLog.persist();
		
		return syncLog;
	}

	@Override
	public boolean uploadSyncLog(File data) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
