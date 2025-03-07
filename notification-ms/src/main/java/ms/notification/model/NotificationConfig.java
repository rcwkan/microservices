package ms.notification.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class NotificationConfig {

	@Id
	private String username;

	private String defaultNotiType;
	
	
	public NotificationConfig() {
		
	}
	
	public NotificationConfig(String username, String defaultNotiType) {
		this.username = username;
		this.defaultNotiType = defaultNotiType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDefaultNotiType() {
		return defaultNotiType;
	}

	public void setDefaultNotiType(String defaultNotiType) {
		this.defaultNotiType = defaultNotiType;
	}

}
	