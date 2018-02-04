package domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	private char[] password;
	private byte[] profileImage;
	private String name;
	private String lastName;
	private String DNI;
	private String email;
	private String phoneNumber;
	private String address;
	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Offer> offers = new Vector<Offer>();

	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Conversation> starterCoversations;
	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Conversation> listenerConversations;

	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Offer> followedOffers;

	public static byte CLIENT_PROFILE_TYPE_LOCAL = 0;
	public static byte CLIENT_PROFILE_TYPE_SOCIAL = 1;

	public Client(String username, char[] password, byte[] profileImage,

			String name, String lastName, String dNI, String mail, String phoneNumber, String address) {
		super();
		this.username = username;
		this.password = password;
		this.profileImage = profileImage;
		this.name = name;
		this.lastName = lastName;
		DNI = dNI;
		this.email = mail;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.starterCoversations = new Vector<>();
		this.listenerConversations = new Vector<>();
		this.followedOffers = new Vector<>();
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String mail) {
		this.email = mail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String newPhoneNumber) {
		this.phoneNumber = newPhoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public boolean checkPassword(char[] password) {
		return Arrays.equals(this.password, password);
	}

	public void addOffer(Offer offer) {
		System.out.println(offer);
		offers.add(offer);
	}

	public Vector<Offer> getClientBookedOffers() {
		Vector<Offer> bookedOffers = new Vector<Offer>();
		for (Offer offer : this.offers) {

			bookedOffers.add(offer);

		}
		return bookedOffers;
	}

	public void cancelBookedOffer(Offer bookedOffer) {
		bookedOffer.setBooked(false);
		bookedOffer.removeClient();
		this.offers.remove(bookedOffer);
		for (Offer offer : this.offers) {
			System.out.println(offer);
		}
	}

	public void addToBookedOffers(Offer offer) {
		offers.add(offer);
	}

	public void followOffer(Offer offer) {
		followedOffers.addElement(offer);
	}

	public void unfollowOffer(Offer offer) {
		followedOffers.remove(offer);
	}

	public boolean isFollowingOffer(Offer offer) {

		return followedOffers.contains(offer);
	}

	public Vector<Offer> getFollowedOffers() {
		return followedOffers;
	}

	public Vector<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Vector<Offer> offers) {
		this.offers = offers;
	}

	public Vector<Conversation> getStarterCoversations() {
		return starterCoversations;
	}

	public void setStarterCoversations(Vector<Conversation> starterCoversations) {
		this.starterCoversations = starterCoversations;
	}

	public Vector<Conversation> getListenerConversations() {
		return listenerConversations;
	}

	public void setListenerConversations(Vector<Conversation> listenerConversations) {
		this.listenerConversations = listenerConversations;
	}

	public Vector<Offer> getBookedOffers() {
		return this.offers;
	}

	public void setBookedOffers(Vector<Offer> bookedOffers) {
		this.offers = bookedOffers;
	}

	public void setFollowedOffers(Vector<Offer> followedOffers) {
		this.followedOffers = followedOffers;
	}

	public void addToFollowedOffers(Offer followedOffer) {
		followedOffers.add(followedOffer);
	}
}