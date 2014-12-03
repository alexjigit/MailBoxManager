package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import static javax.persistence.CascadeType.*;

import javax.persistence.*;

@Entity
public class Box implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7604404813537390577L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BOX_ID")
	private int idBox;

	@Column(name = "BOX_NAME")
	private String boxName;

	private int userId;

	private String type = "Mail";

	@OneToMany(cascade = ALL, mappedBy = "box", targetEntity = Message.class)
	private Collection<Message> messages = new ArrayList<Message>();

	public Box() {
	}

	public Box(String boxName, int userId) {
		super();
		this.boxName = boxName;
		this.userId = userId;
	}

	public int getId() {
		return idBox;
	}

	public void setId(int id) {
		this.idBox = id;
	}

	public String getboxName() {
		return boxName;
	}

	public void setboxName(String boxName) {
		this.boxName = boxName;
	}

	public int getuserId() {
		return userId;
	}

	public void setuserId(int userId) {
		this.userId = userId;
	}

	public void setMessages(Collection<Message> newValue) {
		this.messages = newValue;
	}

	public Collection<Message> getMessages() {
		return messages;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
