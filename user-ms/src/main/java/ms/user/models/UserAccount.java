 
package ms.user.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_account") 
@NamedQuery(name = "UserAccount.findAll", query = "SELECT e FROM UserAccount e")
@NamedQuery(name = "UserAccount.findUser", query = "SELECT e FROM UserAccount e WHERE " + "e.username = :username")
public class UserAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Id
	@Column(name = "user_id")
	private long userId;

	@Column(name = "username")
	private String username;

	@Column(name = "display_name")
	private String displayName;

	@Column(name = "email")
	private String email;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "update_date")
	private Date updateDate;

	@OrderBy("isActive desc")
	@OneToMany(mappedBy = "user") 
	private List<UserCred> creds;

	public UserAccount() {
	}

	public UserAccount(String username, String email, String displayName) {
		this.username = username;
		this.email = email;
		this.displayName = displayName;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<UserCred> getCreds() {
		return creds;
	}

	public void setCreds(List<UserCred> creds) {
		this.creds = creds;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserAccount other = (UserAccount) obj;

		return true;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", displayName=" + displayName + ", createDate=" + createDate + "]";
	}
}
