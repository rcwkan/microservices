package ms.user.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_cred")

public class UserCred implements Serializable {
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "user_cred_id")
	private long userCredId;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "pwd_hash")
	private String pwdHash;

	@Column(name = "create_date")
	private Date createDate;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	private User user;
	

	public UserCred() {
	}

	public UserCred(long userId, String pwdHash, boolean isActive, Date createDate) {

		this.userId = userId;
		this.pwdHash = pwdHash;
		this.isActive = isActive;
		this.createDate = createDate;
	}

	public long getUserCredId() {
		return userCredId;
	}

	public void setUserCredId(int userCredId) {
		this.userCredId = userCredId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActiver) {
		this.isActive = isActiver;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
