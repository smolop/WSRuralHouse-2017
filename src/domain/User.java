package domain;

import java.util.Arrays;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	public static final byte USER_TYPE_UNKNOWN = -1;
	public static final byte USER_TYPE_USER = 0;
	public static final byte USER_TYPE_CLIENT = 1;
	public static final byte USER_TYPE_OWNER = 2;
	public static final byte USER_TYPE_ADMIN = 3;

	@Id
	private String phoneNumber;
	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Offer> offers;

	private char[] password;

	public User(String phoneNumber, char[] password) {
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.offers = new Vector<Offer>();
	}

	public String getPhoneNumber() {
		return phoneNumber;

	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void addOffer(Offer offer) {
		offers.add(offer);
	}

	public void cancelBookedOffer(Offer offer) {
		offer.setBooked(false);
		this.offers.remove(offer);
	}

	public Vector<Offer> getBookedOffers() {
		Vector<Offer> bookedOffers = new Vector<Offer>();
		for (Offer offer : this.offers) {
			bookedOffers.addElement(offer);
			System.out.println("OFFER: " + offer.getOfferNumber());
		}
		return bookedOffers;
	}

	public boolean passwordMatch(char[] matcherPassword) {
		return Arrays.equals(this.password, matcherPassword);
	}

	public void addToBookedOffers(Offer offer) {
		this.offers.add(offer);
	}

	public Vector<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Vector<Offer> offers) {
		this.offers = offers;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public void setBookedOffers(Vector<Offer> bookedOffers) {
		this.offers = bookedOffers;
	}

	public boolean checkPassword(char[] password2) {
		return Arrays.equals(password, password2);

	}

	public void updateUser(char[] password) {
		this.password = password;
	}
}
