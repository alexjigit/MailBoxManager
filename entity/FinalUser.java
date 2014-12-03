package entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class FinalUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private int idFinalUser;

	private String userName;

	// private MailBox mailbox;
	private boolean readAccess;
	private boolean writeAccess;
	private String password;

	public FinalUser() {

	}

	public FinalUser(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;

	}

	public int getId() {
		return idFinalUser;
	}

	public void setId(int id) {
		this.idFinalUser = id;
	}

	public String getuserName() {
		return userName;
	}

	public void setuserName(String userName) {
		this.userName = userName;
	}

	public boolean getReadAccess() {
		return this.readAccess;
	}

	public void setReadAccess(boolean readRight) {
		this.readAccess = readRight;
	}

	public void unsetReadAccess() {
		this.readAccess = false;
	}

	public boolean getWriteAccess() {
		return this.writeAccess;
	}

	public void setWriteAccess(boolean writeRight) {
		this.writeAccess = writeRight;
	}

	public void unsetWriteAccess() {
		this.writeAccess = false;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Id : " + idFinalUser + "\t userName : "
				+ userName + "\n";
	}
	
	

	/*
	 * @OneToOne
	 * 
	 * @JoinColumn(name="BOX_ID", nullable=false) public MailBox getMailBox () {
	 * return this.mailbox; }
	 * 
	 * public void setMailBox (MailBox mailBox) { this.mailbox = mailBox; }
	 */

}
