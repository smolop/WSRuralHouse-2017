package domain;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Conversation {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer conversationNumber;

	private byte starterUserType;
	private String starterUsername;
	private byte listenerUserType;
	private String listenerUsername;

	@ManyToOne(fetch = FetchType.EAGER)
	Admin starterAdmin;
	@ManyToOne(fetch = FetchType.EAGER)
	Admin listenerAdmin;
	@ManyToOne(fetch = FetchType.EAGER)
	Owner starterOwner;
	@ManyToOne(fetch = FetchType.EAGER)
	Owner listenerOwner;
	@ManyToOne(fetch = FetchType.EAGER)
	Client starterClient;
	@ManyToOne(fetch = FetchType.EAGER)
	Client listenerClient;

	@OneToMany(fetch = FetchType.EAGER)
	private Map<Date, String[]> messages = new TreeMap<Date, String[]>();

	public Conversation(byte starterUseType, byte listenerUserType) {
		super();
		this.starterUserType = starterUseType;
		this.listenerUserType = listenerUserType;
		this.messages = new TreeMap<Date, String[]>();
	}

	public Conversation(byte starterUseType, byte listenerUserType, Map<Date, String[]> messages) {
		super();
		this.starterUserType = starterUseType;
		this.listenerUserType = listenerUserType;
		this.messages = new TreeMap<Date, String[]>();
		this.messages = messages;
	}

	public int getConversationNumber() {
		return conversationNumber;
	}

	public byte getStarterUseType() {
		return starterUserType;
	}

	public byte getListenerUserType() {
		return listenerUserType;
	}

	public Map<Date, String[]> getMessages() {
		return messages;
	}

	public String getStarterUsername() {
		return starterUsername;
	}

	public void setStarterUsername(String starterUsername) {
		this.starterUsername = starterUsername;
	}

	public String getListenerUsername() {
		return listenerUsername;
	}

	public void setListenerUsername(String listenerUsername) {
		this.listenerUsername = listenerUsername;
	}

	public Admin getStarterAdmin() {
		return starterAdmin;
	}

	public void setStarterAdmin(Admin starterAdmin) {
		this.starterAdmin = starterAdmin;
	}

	public Admin getListenerAdmin() {
		return listenerAdmin;
	}

	public void setListenerAdmin(Admin listenerAdmin) {
		this.listenerAdmin = listenerAdmin;
	}

	public Owner getStarterOwner() {
		return starterOwner;
	}

	public void setStarterOwner(Owner starterOwner) {
		this.starterOwner = starterOwner;
	}

	public Owner getListenerOwner() {
		return listenerOwner;
	}

	public void setListenerOwner(Owner listenerOwner) {
		this.listenerOwner = listenerOwner;
	}

	public Client getStarterClient() {
		return starterClient;
	}

	public void setStarterClient(Client starterClient) {
		this.starterClient = starterClient;
	}

	public Client getListenerClient() {
		return listenerClient;
	}

	public void setListenerClient(Client listenerClient) {
		this.listenerClient = listenerClient;
	}

	public void setStarterUseType(byte starterUseType) {
		this.starterUserType = starterUseType;
	}

	public void setListenerUserType(byte listenerUserType) {
		this.listenerUserType = listenerUserType;
	}

	public void setMessages(Map<Date, String[]> messages) {
		this.messages = messages;
	}
}
