package domain;

import java.util.Arrays;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Admin {
	@Id
	private String username = "Admin";

	private byte[] profileImage;

	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Conversation> starterCoversations;
	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Conversation> listenerConversations;
	@OneToMany(fetch = FetchType.EAGER)
	private Vector<Offer> followedOffers;

	private char[] password;

	public Admin(char[] password, byte[] image) {
		this.password = password;
		this.profileImage = image;
		this.starterCoversations = new Vector<>();
		this.listenerConversations = new Vector<>();
		this.followedOffers = new Vector<>();
	}

	public boolean checkPassword(char[] password) {
		return Arrays.equals(this.password, password);
	}

	public String getUsername() {
		return username;
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

	public void setFollowedOffers(Vector<Offer> followedOffers) {
		this.followedOffers = followedOffers;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}
}