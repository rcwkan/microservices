package ms.user.dynamo.model;

import java.util.Date;
import java.util.UUID;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;


//document base DB entityType = account
@Entity("account")
public class User {
 
 
	@Column
    private String userId;
 
 
    @Id 
	private String username;

	@Column
	private String displayName;

	@Column
	private String email;

	@Column
	private Boolean isActive;

	@Column
	private String pwdHash;

	@Column
	private Date createDate;

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
 
 
	

}
