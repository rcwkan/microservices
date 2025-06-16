package ms.sync.entity;

import java.util.Date;

//import io.quarkus.hibernate.orm.panache.PanacheEntity;
//import jakarta.persistence.Cacheable;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Lob;

//@Entity(name = "sync_log")
//@Cacheable
public class SyncLog2  /*extends PanacheEntity*/ {

//	@Column(name = "username")
	public String username;

//	@Column(name = "sync_date")
	public Date syncDate;

//	@Column(name = "sync_type")
	public String syncType;

//	@Column(name = "status")
	public String status;

//	@Lob
//	@Column(name = "zipped_data", columnDefinition = "bytea")
	private byte[] zippedData;

	public SyncLog2() {

	}

	public SyncLog2(String username) {
		this.username = username;
	}

}
