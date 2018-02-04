package businessLogic;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.swing.JLabel;

import domain.Admin;
import domain.Client;
import domain.Conversation;
//import domain.Booking;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.User;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;

@WebService
public interface ApplicationFacadeInterfaceWS {

	/**
	 * This method creates an offer with a house number, first day, last day and
	 * price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return None
	 */

	@WebMethod
	Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price)
			throws OverlappingOfferExists, BadDates;

	/**
	 * This method creates a book with a corresponding parameters
	 * 
	 * @param First
	 *            day, last day, house number and telephone
	 * @return a book
	 */

	/**
	 * This method retrieves the existing rural houses
	 * 
	 * @return a Set of rural houses
	 */
	@WebMethod
	public Vector<RuralHouse> getAllRuralHouses();

	/**
	 * This method obtains the offers of a ruralHouse in the provided dates
	 * 
	 * @param ruralHouse
	 *            , the ruralHouse to inspect
	 * @param firstDay
	 *            , first day in a period range
	 * @param lastDay
	 *            , last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there
	 *         is no overlapping offer
	 */

	@WebMethod
	public Vector<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay);

	@WebMethod
	public void initializeBD();

	public byte scalatePrivileges(String username, char[] password);

	public byte getUserType(String username);

	public void createRuralHouse(String username, String shortDescription, String description, String city,
			List<byte[]> ruralHouseImagesInBytes, int bathroomQuantity, int bedroomQuantity, int dinningroomQuantity,
			int kitchenQuantity, int parkingPlaceQuantity, int roomQuantity);

	public User getUser(String phoneNumber);

	public Client getClient(String username);

	public Owner getOwner(String username);

	public Vector<RuralHouse> getOwnerRuralHouses(String username);

	public void deleteRuralHouse(int ruralHouseNumber);

	public Vector<Offer> getRuralHouseOffers(int ruralHouseNumber);

	public void deleteOffer(int offerNumber);

	public boolean isValidUsername(String username);

	public boolean isValidPassword(char[] password);

	public boolean isValidProfileImage(byte[] profileImage);

	public boolean isValidName(String name);

	public boolean isValidLastName(String lastName);

	public boolean isValidDNI(String dNI);

	public boolean isValidMail(String mail);

	public boolean isValidPhoneNumber(String phoneNumber);

	public boolean isValidAddress(String address);

	public void createClient(String username, char[] password, byte[] profileImage, String name, String lastName,
			String dNI, String mail, String phoneNumber, String address);

	void createOwner(String username, char[] password, byte[] profileImage, String name, String lastName, String dNI,
			String mail, String phoneNumber, String address);

	public boolean isValidCity(String city);

	public boolean isValidDescription(String description);

	byte[] getUserProfileImage(byte userType, String username);

	String getUserName(byte userType, String username);

	String getUseLastName(byte userType, String username);

	String getUserEmail(byte userType, String username);

	public String getUserPhoneNumber(byte userType, String username);

	String getUserAddress(byte userType, String username);

	void updateClient(Client client, JLabel feedbackJLabel, String newUsername, char[] password, char[] newPassword,
			char[] confirmNewPassword, String newProfileImage, String newName, String newLastName, String newEmail,
			String newPhoneNumber, String newAddress);

	void updateOwner(Owner owner, JLabel feedbackJLabel, String newUsername, char[] password, char[] newPassword,
			char[] confirmNewPassword, String newProfileImage, String newName, String newLastName, String newEmail,
			String newPhoneNumber, String newAddress);

	public Vector<RuralHouse> searchOffer(Vector<RuralHouse> ruralHouses, int ruralHouseNumber, int offerNumber,
			String city, int minimumPrice, int maximumPrice, Date firstDay, Date lastDay, int dayQuantity,
			String searchString);

	boolean isValidRuralHouseNumber(String ruralHouseNumber);

	boolean isValidOfferNumber(String offerNumber);

	boolean isValidMinimumPrice(String minimumPrice);

	boolean isValidMaximumPrice(String maximumPrice);

	boolean isValidDayQuantity(String dayQuantity);

	Offer getOffer(int offerNumber);

	void clientBookOffer(String username, int offerNumber);

	void userBookOffer(String phoneNumber, int offerNumber);

	public void cancelClientBookedOffer(String username, Offer bookedOffer);

	public void cancelUserBookedOffer(String phoneNumber, int offerNumber);

	public Vector<Offer> getClientBookedOffers(String username);

	public boolean userExists(String phoneNumber);

	public boolean clientExists(String username);

	public Vector<Offer> getUserBookedOffers(String phoneNumber);

	public void createClientFromUser(String username, char[] password, byte[] profileImage, String name,
			String lastName, String dNI, String mail, String phoneNumber, String address);

	public void createUser(String phoneNumber, char[] password);

	public void followOffer(String username, byte userType, int offerNumber);

	public void unfollowOffer(String username, byte userType, int offerNumber);

	boolean isFollowingOffer(String username, byte userType, int offerNumber);

	Vector<Offer> getFollowingOffers(String username, byte userType);

	Conversation getConversation(Integer conversationNumber);

	List<Object> getAllUsers();

	List<Object> getAllClients();

	List<Object> getAllOwners();

	List<Object> getAllOffers();

	Client getClient(int elementID);

	Owner getOwner(int elementID);

	RuralHouse getRuralHouse(int elementID);

	void updateUser(String phoneNumber, char[] newPassword);

	void updateOffer(Offer offer, JLabel offerFeedback, Date firstDay, Date lastDay, String price);

	void updateRuralHouse(RuralHouse ruralHouse, JLabel ruralHouseFeedbackJLabel, String city, String shortDescription,
			String description, String bathrooms, String bedrooms, String dinningrooms, String kitchens,
			String parkingPlaces, String rooms, List<String> image);

	int getConversationNumber(byte userType, String username, String username2);

	public int startConversation(byte userType, String username, String username2);

	void sendMessage(int conversationNumber, String username, String message);

	void createAdmin(char[] password, byte[] image);

	Admin getAdmin(String username);

	boolean isValidUserToClient(JLabel feedbackJLabel, char[] password, byte[] profileImageInByte, String name,
			String lastName, String dni, String mail, String phoneNumber, String address);
}