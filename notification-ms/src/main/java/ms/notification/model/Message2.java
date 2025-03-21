package ms.notification.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class Message2 {

	@JsonIgnore
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String msgFrom;

	@JsonProperty(required = true)
	private String msgTo;

	@JsonProperty(required = true)
	private String content;
 
	private String subject;
	
	private String type;

	@JsonIgnore
	private String status;

	@JsonIgnore
	private Date createDate;

	@JsonIgnore
	private Date sentDate;

//	public Message(String msgTo, String content) {
//		this.msgTo = msgTo;
//		this.content = content;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public Date getCreateDate() {
//		return createDate;
//	}
//
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}
//
//	public Date getSentDate() {
//		return sentDate;
//	}
//
//	public void setSentDate(Date sentDate) {
//		this.sentDate = sentDate;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public String getMsgFrom() {
//		return msgFrom;
//	}
//
//	public void setMsgFrom(String msgFrom) {
//		this.msgFrom = msgFrom;
//	}
//
//	public String getMsgTo() {
//		return msgTo;
//	}
//
//	public void setMsgTo(String msgTo) {
//		this.msgTo = msgTo;
//	}

}
