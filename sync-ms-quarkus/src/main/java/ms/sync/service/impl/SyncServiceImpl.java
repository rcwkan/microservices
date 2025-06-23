package ms.sync.service.impl;

import java.io.File;
import java.time.LocalDateTime;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import ms.sync.dynamo.SyncLogRepository;
import ms.sync.dynamo.entity.SyncLog;
import ms.sync.service.SyncService;

@ApplicationScoped
public class SyncServiceImpl implements SyncService {

	private static final Logger log = Logger.getLogger(SyncServiceImpl.class);

	@Inject
	SyncLogRepository syncLogRepository;

	@Override
	public SyncLog createSyncLog(SyncLog syncLog) {

		log.infov("createSyncLog : " + syncLog.username);

		syncLog.setCreateDate(LocalDateTime.now());
		syncLogRepository.save(syncLog);

		return syncLog;
	}

	@Override
	public boolean uploadSyncLog(File data) {
		// TODO Auto-generated method stub
		return false;
	}

}
