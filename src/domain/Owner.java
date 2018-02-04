package domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Owner implements Serializable {

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
	private String bankAccount;
	@OneToMany(fetch = FetchType.EAGER)
	private Vector<RuralHouse> ruralHouses;

	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Conversation> starterCoversations;
	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Conversation> listenerConversations;

	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Offer> followedOffers;

	public Owner(String username, char[] password, byte[] profileImage2,

			String name, String lastName, String dNI, String mail, String phoneNumber, String address) {
		super();
		this.username = username;
		this.password = password;
		this.profileImage = profileImage2;
		this.name = name;
		this.lastName = lastName;
		DNI = dNI;
		this.email = mail;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.ruralHouses = new Vector<>();
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

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
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

	public RuralHouse createRuralHouse(String shortDescription, String description, String city,
			List<byte[]> ruralHousesImages, int bathroomQuantity, int bedroomQuantity, int dinningroomQuantity,
			int kitchenQuantity, int parkingPlaceQuantity, int roomQuantity) {
		RuralHouse ruralHouse = new RuralHouse(shortDescription, description, city, ruralHousesImages, bathroomQuantity,
				bedroomQuantity, dinningroomQuantity, kitchenQuantity, parkingPlaceQuantity, roomQuantity, this);
		this.ruralHouses.add(ruralHouse);
		return ruralHouse;
	}

	public Vector<RuralHouse> getRuralHouses() {
		if (this.ruralHouses == null) {
			this.ruralHouses = new Vector<RuralHouse>();
		}
		return this.ruralHouses;
	}

	public void deleteRuralHouse(RuralHouse rh) {
		this.ruralHouses.remove(rh);
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

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
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

	public void setRuralHouses(Vector<RuralHouse> ruralHouses) {
		this.ruralHouses = ruralHouses;
	}

	public void setFollowedOffers(Vector<Offer> followedOffers) {
		this.followedOffers = followedOffers;
	}
}