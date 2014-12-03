package entity;

import java.util.Date;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MESSAGE_ID")
	private int messageId;

	private String senderName;
	private String receiverName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date sendingDate;

	private String subject;
	private String body;
	private Boolean alreadyRead;
	private Box box;

	public int getmessageId() {
		return this.messageId;
	}

	public Message() {

	}

	public Message(String senderName, String receiverName, String subject,
			String body, Box box) {
		super();
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.sendingDate = new Date();
		this.subject = subject;
		this.body = body;
		this.alreadyRead = false;
		this.box = box;
	}

	@ManyToOne
	@JoinColumn(name = "BOX_ID", nullable = false)
	public Box getBox() {
		return box;
	}

	public String getsenderName() {
		return this.senderName;
	}

	public String getreceiverName() {
		return this.receiverName;
	}

	public Date getsendingDate() {
		return this.sendingDate;
	}

	public String getsubject() {
		return this.subject;
	}

	public String getbody() {
		return this.body;
	}

	public void setIsRead() {
		this.alreadyRead = true;
	}

	public Boolean getIsRead() {
		return alreadyRead;
	}

	// Methode SET

	public void setsenderName(String senderName) {
		this.senderName = senderName;
	}

	public void setreceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public void setsendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}

	public void setsubject(String subject) {
		this.subject = subject;
	}

	public void setbody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Id :" + messageId + "\n Sent by " + senderName
				+  "\n Received on "
				+ sendingDate + "\n Subject : " + subject + "\n Body : \n" + body + "\n\n";
	}
	
}
