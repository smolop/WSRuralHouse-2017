package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Offer implements Serializable {

	@Id
	@GeneratedValue
	private Integer offerNumber;
	private Date firstDay; // Dates are stored as java.util.Date objects instead
	// of java.sql.Date objects
	private Date lastDay; // because, they are not well stored in db4o as
	// java.util.Date objects
	private float price; // This is coherent because objects of java.sql.Date
	// are objects of java.util.Date
	@XmlIDREF
	@ManyToOne(fetch = FetchType.EAGER)
	private RuralHouse ruralHouse;
	@ManyToOne(fetch = FetchType.EAGER)
	Client client;
	@ManyToOne(fetch = FetchType.EAGER)
	User user;
	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Client> followingClients = new Vector<Client>();
	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Owner> followingOwners = new Vector<Owner>();
	private Admin followingAdmin;

	private Boolean booked = false;

	public Offer() {
	}

	public Offer(Date firstDay, Date lastDay, float price, RuralHouse ruralHouse) {
		this.firstDay = firstDay;
		this.lastDay = lastDay;
		this.price = price;
		this.ruralHouse = ruralHouse;
	}

	public void setOfferNumber(Integer offerNumber) {
		this.offerNumber = offerNumber;
	}

	/**
	 * Get the house number of the offer
	 * 
	 * @return the house number
	 */
	public RuralHouse getRuralHouse() {
		return this.ruralHouse;
	}

	/**
	 * Set the house number to a offer
	 * 
	 * @param house
	 *            number
	 */
	public void setRuralHouse(RuralHouse ruralHouse) {
		this.ruralHouse = ruralHouse;
	}

	/**
	 * Get the offer number
	 * 
	 * @return offer number
	 */
	public int getOfferNumber() {
		return this.offerNumber;
	}

	/**
	 * Get the first day of the offer
	 * 
	 * @return the first day
	 */
	public Date getFirstDay() {
		return this.firstDay;
	}

	/**
	 * Set the first day of the offer
	 * 
	 * @param firstDay
	 *            The first day
	 */
	public void setFirstDay(Date firstDay) {
		this.firstDay = firstDay;
	}

	/**
	 * Get the last day of the offer
	 * 
	 * @return the last day
	 */
	public Date getLastDay() {
		return this.lastDay;
	}

	/**
	 * Set the last day of the offer
	 * 
	 * @param lastDay
	 *            The last day
	 */
	public void setLastDay(Date lastDay) {
		this.lastDay = lastDay;
	}

	/**
	 * Get the price
	 * 
	 * @return price
	 */
	public float getPrice() {
		return this.price;
	}

	/**
	 * Set the price
	 * 
	 * @param price
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		// return offerNumber + ";" + firstDay.toString() + ";" +
		// lastDay.toString() + ";" + price;
		return firstDay.toString() + " - " + lastDay.toString() + " (" + price + "€)";
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return this.client;
	}

	public void removeClient() {
		this.client = null;
	}

	// public void followedByUser(User user) {
	// followingUsers.addElement(user);
	// }
	//
	// public void unfollowedByUser(User user) {
	// followingUsers.remove(user);
	// }

	public void followedByClient(Client client) {
		followingClients.addElement(client);
	}

	public void unfollowedByClient(Client client) {
		followingClients.remove(client);
	}

	public void followedByOwner(Owner owner) {
		followingOwners.addElement(owner);
	}

	public void unfollowedByOwner(Owner owner) {
		followingOwners.remove(owner);
	}

	public void followedByAdmin(Admin admin) {
		followingAdmin = admin;
	}

	public void unfollowedByAdmin(Admin admin) {
		followingAdmin = null;
	}

	public void unfollowAll() {
		// for (User user : followingUsers) {
		// user.unfollowOffer(this);
		// }
		for (Client client : followingClients) {
			client.unfollowOffer(this);
		}
		for (Owner owner : followingOwners) {
			owner.unfollowOffer(this);
		}
		if (followingAdmin != null) {
			followingAdmin.unfollowOffer(this);
		}
	}

	// public Vector<User> getFollowingUsers() {
	// return followingUsers;
	// }
	//
	// public void setFollowingUsers(Vector<User> followingUsers) {
	// this.followingUsers = followingUsers;
	// }

	public Vector<Client> getFollowingClients() {
		return followingClients;
	}

	public void setFollowingClients(Vector<Client> followingClients) {
		this.followingClients = followingClients;
	}

	public Vector<Owner> getFollowingOwners() {
		return followingOwners;
	}

	public void setFollowingOwners(Vector<Owner> followingOwners) {
		this.followingOwners = followingOwners;
	}

	public Admin getFollowingAdmin() {
		return followingAdmin;
	}

	public void setFollowingAdmin(Admin followingAdmin) {
		this.followingAdmin = followingAdmin;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean isBooked() {
		return this.booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}

}