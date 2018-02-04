package dataAccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Admin;
import domain.Client;
import domain.Conversation;
//import domain.Booking;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.User;
import exceptions.OverlappingOfferExists;

public class DataAccess {

	public static String fileName;
	protected static EntityManagerFactory emf;
	protected static EntityManager db;

	ConfigXML c;

	public DataAccess() {

		c = ConfigXML.getInstance();

		System.out.println("Creating objectdb instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String filename = c.getDbFilename();

		if (c.isDatabaseLocal()) {

			emf = Persistence.createEntityManagerFactory(c.getDbFilename());
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());
			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + c.getDbFilename(),

					properties);
			db = emf.createEntityManager();
		}
	}

	public void initializeDB() {
		// db.getTransaction().begin();
		// try {
		//
		// TypedQuery<RuralHouse> query = db.createQuery("SELECT c FROM
		// RuralHouse
		// c", RuralHouse.class);
		// List<RuralHouse> results = query.getResultList();
		//
		// Iterator<RuralHouse> itr = results.iterator();
		//
		// while (itr.hasNext()) {
		// RuralHouse rh = itr.next();
		// db.remove(rh);
		// }
		//
		// RuralHouse rh1 = new RuralHouse("Ezkioko etxea", "Ezkio");
		// RuralHouse rh2 = new RuralHouse("Etxetxikia", "Iruna");
		// RuralHouse rh3 = new RuralHouse("Udaletxea", "Bilbo");
		// RuralHouse rh4 = new RuralHouse("Gaztetxea", "Renteria");
		//
		// db.persist(rh1);
		// db.persist(rh2);
		// db.persist(rh3);
		// db.persist(rh4);
		//
		// db.getTransaction().commit();
		// System.out.println("Db initialized");
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) {
		System.out.println(">> DataAccess: createOffer=> ruralHouse= " + ruralHouse + " firstDay= " + firstDay
				+ " lastDay=" + lastDay + " price=" + price);

		try {
			RuralHouse rh = db.find(RuralHouse.class, ruralHouse.getHouseNumber());

			db.getTransaction().begin();
			Offer o = rh.createOffer(firstDay, lastDay, price);
			db.persist(o);
			// System.out.println(rh.offers);
			db.getTransaction().commit();
			return o;

		} catch (Exception e) {
			System.out.println("Offer not created: " + e.toString());
			return null;
		}
	}

	public Vector<RuralHouse> getAllRuralHouses() {
		System.out.println(">> DataAccess: getAllRuralHouses");
		Vector<RuralHouse> res = new Vector<>();

		TypedQuery<RuralHouse> query = db.createQuery("SELECT c FROM RuralHouse c", RuralHouse.class);
		List<RuralHouse> results = query.getResultList();

		Iterator<RuralHouse> itr = results.iterator();

		while (itr.hasNext()) {
			res.add(itr.next());
		}

		return res;

	}

	public Vector<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay) {
		System.out.println(">> DataAccess: getOffers");
		Vector<Offer> res = new Vector<>();
		RuralHouse rhn = db.find(RuralHouse.class, rh.getHouseNumber());
		res = rhn.getOffers(firstDay, lastDay);
		return res;
	}

	public boolean existsOverlappingOffer(RuralHouse rh, Date firstDay, Date lastDay) throws OverlappingOfferExists {
		try {
			RuralHouse rhn = db.find(RuralHouse.class, rh.getHouseNumber());
			if (rhn.overlapsWith(firstDay, lastDay) != null) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
			return true;
		}
		return false;
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean existClient(String username) {
		Client client = db.find(Client.class, username);
		return client != null;
	}

	public void createClient(String username, char[] password, byte[] profileImage, String name, String lastName,
			String dNI, String mail, String phoneNumber, String address) {
		Client client = new Client(username, password, profileImage, name, lastName, dNI, mail, phoneNumber, address);
		db.getTransaction().begin();
		db.persist(client);
		db.getTransaction().commit();
	}

	public boolean existOwner(String username) {
		Owner owner = db.find(Owner.class, username);
		return owner != null;
	}

	public void createOwner(String username, char[] password, byte[] profileImage, String name, String lastName,
			String dNI, String mail, String phoneNumber, String address) {
		Owner owner = new Owner(username, password, profileImage, name, lastName, dNI, mail, phoneNumber, address);
		db.getTransaction().begin();
		db.persist(owner);
		db.getTransaction().commit();
	}

	public byte getUserType(String username) {
		byte userType = User.USER_TYPE_UNKNOWN;
		if (existClient(username)) {
			return User.USER_TYPE_CLIENT;
		} else if (existOwner(username)) {
			userType = User.USER_TYPE_OWNER;
		} else if (existAdmin(username)) {
			userType = User.USER_TYPE_ADMIN;
		}
		return userType;
	}

	private boolean existAdmin(String username) {
		db.getTransaction().begin();
		Admin admin = db.find(Admin.class, username);
		db.getTransaction().commit();
		return admin != null;
	}

	public boolean checkPassword(byte userType, String username, char[] password) {
		boolean passwordMatch = false;
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			Client client = db.find(Client.class, username);
			passwordMatch = client.checkPassword(password);
			break;
		case User.USER_TYPE_OWNER:
			Owner owner = db.find(Owner.class, username);
			passwordMatch = owner.checkPassword(password);
			break;
		}
		return passwordMatch;
	}

	public void createRuralHouse(String username, String shortDescription, String description, String city,
			List<byte[]> ruralHousesImages, int bathroomQuantity, int bedroomQuantity, int dinningroomQuantity,
			int kitchenQuantity, int parkingPlaceQuantity, int roomQuantity) {
		Owner owner = db.find(Owner.class, username);
		db.getTransaction().begin();
		RuralHouse ruralHouse = owner.createRuralHouse(shortDescription, description, city, ruralHousesImages,
				bathroomQuantity, bedroomQuantity, dinningroomQuantity, kitchenQuantity, parkingPlaceQuantity,
				roomQuantity);
		db.persist(owner);
		db.persist(ruralHouse);
		db.getTransaction().commit();
	}

	public User getUser(String phoneNumber) {
		User user = db.find(User.class, phoneNumber);
		return user;
	}

	public Client getClient(String username) {
		Client client = db.find(Client.class, username);
		return client;
	}

	public Owner getOwner(String username) {
		Owner owner = db.find(Owner.class, username);
		return owner;
	}

	public boolean existUser(String username) {
		if (existClient(username) || existOwner(username)) {
			return true;
		} else {
			return false;
		}
	}

	public Vector<RuralHouse> getOwnerRuralHouses(String username) {
		Owner owner = db.find(Owner.class, username);
		return owner.getRuralHouses();
	}

	public void deleteRuralHouse(int ruralHouseNumber) {
		RuralHouse ruralHouse = db.find(RuralHouse.class, ruralHouseNumber);
		Owner owner = ruralHouse.getOwner();
		Vector<Offer> offers = ruralHouse.getAllOffers();
		db.getTransaction().begin();
		for (Offer offer : offers) {
			offer.unfollowAll();
			db.remove(offer);
		}
		db.remove(ruralHouse);
		owner.deleteRuralHouse(ruralHouse);
		db.merge(owner);
		db.getTransaction().commit();
	}

	public Vector<Offer> getRuralHouseOffers(int ruralHouseNumber) {
		RuralHouse ruralHouse = db.find(RuralHouse.class, ruralHouseNumber);
		return ruralHouse.getAllOffers();
	}

	public void deleteOffer(int offerNumber) {
		Offer offer = db.find(Offer.class, offerNumber);
		RuralHouse ruralHouse = offer.getRuralHouse();
		db.getTransaction().begin();
		offer.unfollowAll();
		db.remove(offer);
		ruralHouse.deleteOffer(offer);
		db.merge(ruralHouse);
		db.getTransaction().commit();
	}

	public byte[] getClientProfileImage(String username) {
		Client client = db.find(Client.class, username);
		return client.getProfileImage();
	}

	public byte[] getOwnerProfileImage(String username) {
		Owner owner = db.find(Owner.class, username);
		return owner.getProfileImage();
	}

	public String getClientName(String username) {
		Client client = db.find(Client.class, username);
		return client.getName();
	}

	public String getOwnerName(String username) {
		Owner owner = db.find(Owner.class, username);
		return owner.getName();
	}

	public String getClientLastName(String username) {
		Client client = db.find(Client.class, username);
		return client.getLastName();
	}

	public String getOwnerLastName(String username) {
		Owner owner = db.find(Owner.class, username);
		return owner.getLastName();
	}

	public String getClientEmail(String username) {
		Client client = db.find(Client.class, username);
		return client.getEmail();
	}

	public String getOwnerEmail(String username) {
		Owner owner = db.find(Owner.class, username);
		return owner.getEmail();
	}

	public String getClientPhoneNumber(String username) {
		Client client = db.find(Client.class, username);
		return client.getPhoneNumber();
	}

	public String getOwnerPhoneNumber(String username) {
		Owner owner = db.find(Owner.class, username);
		return owner.getPhoneNumber();
	}

	public String getClientAddress(String username) {
		Client client = db.find(Client.class, username);
		return client.getAddress();
	}

	public String getOwnerAddress(String username) {
		Owner owner = db.find(Owner.class, username);
		return owner.getAddress();
	}

	public void updateClient(Client client) {
		db.getTransaction().begin();
		db.merge(client);
		db.getTransaction().commit();
	}

	public void updateOwner(Owner owner) {
		db.getTransaction().begin();
		db.merge(owner);
		db.getTransaction().commit();
	}

	public Offer getOffer(int offerNumber) {
		Offer offer = db.find(Offer.class, offerNumber);
		return offer;
	}

	public void clientBookOffer(String username, int offerNumber) {
		Client client = db.find(Client.class, username);
		Offer offer = db.find(Offer.class, offerNumber);
		client.addOffer(offer);
		RuralHouse ruralHouse = offer.getRuralHouse();
		ruralHouse.bookOffer(offer);
		db.getTransaction().begin();
		db.merge(offer);
		db.merge(client);
		db.merge(ruralHouse);
		db.getTransaction().commit();

	}

	public boolean existsUser(String phoneNumber) {
		User user = db.find(User.class, phoneNumber);
		System.out.println("EXISTE EL USER " + phoneNumber + "-- " + (user != null));
		return user != null;
	}

	public void createUser(String phoneNumber, char[] password) {
		User user = new User(phoneNumber, password);
		db.getTransaction().begin();
		db.persist(user);
		db.getTransaction().commit();
	}

	public void userBookOffer(String phoneNumber, int offerNumber) {
		User user = db.find(User.class, phoneNumber);
		Offer offer = db.find(Offer.class, offerNumber);
		System.out.println("USER: " + user.getPhoneNumber() + " OFFER: " + offer.getOfferNumber());
		db.getTransaction().begin();
		user.addOffer(offer);
		offer.setUser(user);
		RuralHouse ruralHouse = offer.getRuralHouse();
		ruralHouse.bookOffer(offer);
		db.merge(offer);
		db.merge(user);
		db.merge(ruralHouse);
		db.getTransaction().commit();
	}

	public Vector<Offer> getClientBookedOffers(String username) {
		Client client = db.find(Client.class, username);
		return client.getClientBookedOffers();
	}

	public void cancelClientBookedOffer(String username, int offerNumber) {
		Client client = db.find(Client.class, username);
		Offer offer = db.find(Offer.class, offerNumber);
		db.getTransaction().begin();
		client.cancelBookedOffer(offer);
		db.merge(client);
		db.merge(offer);
		db.getTransaction().commit();
	}

	public void cancelUserBookedOffer(String phoneNumber, int offerNumber) {
		User user = db.find(User.class, phoneNumber);
		Offer offer = db.find(Offer.class, offerNumber);
		db.getTransaction().begin();
		user.cancelBookedOffer(offer);
		db.merge(user);
		db.merge(offer);
		db.getTransaction().commit();
	}

	public Vector<Offer> getUserBookedOffers(String phoneNumber) {
		User user = db.find(User.class, phoneNumber);
		System.out.println("User: " + user.getPhoneNumber());
		return user.getBookedOffers();
	}

	public void createClientFromUser(String username, char[] password, byte[] profileImage, String name,
			String lastName, String dNI, String mail, String phoneNumber, String address) {
		Client client = new Client(username, password, profileImage, name, lastName, dNI, mail, phoneNumber, address);
		User user = db.find(User.class, phoneNumber);
		Vector<Offer> bookedOffers = user.getBookedOffers();
		// Vector<Offer> followedOffers = user.getFollowedOffers();
		for (Offer offer : bookedOffers) {
			client.addToBookedOffers(offer);
		}
		// for (Offer followedOffer : followedOffers) {
		// client.addToFollowedOffers(followedOffer);
		// }
		db.getTransaction().begin();
		db.remove(user);
		db.persist(client);
		db.getTransaction().commit();
	}

	// public void followOfferUser(String phoneNumber, int offerNumber) {
	// db.getTransaction().begin();
	//
	// User user = db.find(User.class, phoneNumber);
	// Offer offer = db.find(Offer.class, offerNumber);
	// // user.followOffer(offer);
	// offer.followedByUser(user);
	//
	// db.persist(user);
	// db.persist(offer);
	//
	// db.getTransaction().commit();
	// }
	//
	// public void unfollowOfferUser(String phoneNumber, int offerNumber) {
	// db.getTransaction().begin();
	//
	// User user = db.find(User.class, phoneNumber);
	// Offer offer = db.find(Offer.class, offerNumber);
	// // user.unfollowOffer(offer);
	// offer.unfollowedByUser(user);
	//
	// db.persist(user);
	// db.persist(offer);
	//
	// db.getTransaction().commit();
	// }

	public void followOfferClient(String username, int offerNumber) {
		db.getTransaction().begin();

		Client client = db.find(Client.class, username);
		Offer offer = db.find(Offer.class, offerNumber);
		client.followOffer(offer);
		offer.followedByClient(client);

		db.persist(client);
		db.persist(offer);

		db.getTransaction().commit();
	}

	public void unfollowOfferClient(String username, int offerNumber) {
		db.getTransaction().begin();

		Client client = db.find(Client.class, username);
		Offer offer = db.find(Offer.class, offerNumber);
		client.unfollowOffer(offer);
		offer.unfollowedByClient(client);

		db.persist(client);
		db.persist(offer);

		db.getTransaction().commit();
	}

	public void followOfferOwner(String username, int offerNumber) {
		db.getTransaction().begin();

		Owner owner = db.find(Owner.class, username);
		Offer offer = db.find(Offer.class, offerNumber);
		owner.followOffer(offer);
		offer.followedByOwner(owner);

		db.persist(owner);
		db.persist(offer);

		db.getTransaction().commit();
	}

	public void unfollowOfferOwner(String username, int offerNumber) {
		db.getTransaction().begin();

		Owner owner = db.find(Owner.class, username);
		Offer offer = db.find(Offer.class, offerNumber);
		owner.unfollowOffer(offer);
		offer.unfollowedByOwner(owner);

		db.persist(owner);
		db.persist(offer);

		db.getTransaction().commit();
	}

	public void followOfferAdmin(String username, int offerNumber) {
		db.getTransaction().begin();

		Admin admin = db.find(Admin.class, username);
		Offer offer = db.find(Offer.class, offerNumber);
		admin.followOffer(offer);
		offer.followedByAdmin(admin);

		db.persist(admin);
		db.persist(offer);

		db.getTransaction().commit();
	}

	public void unfollowOfferAdmin(String username, int offerNumber) {
		db.getTransaction().begin();

		Admin admin = db.find(Admin.class, username);
		Offer offer = db.find(Offer.class, offerNumber);
		admin.unfollowOffer(offer);
		offer.unfollowedByAdmin(admin);

		db.persist(admin);
		db.persist(offer);

		db.getTransaction().commit();
	}

	// public boolean isFollowingOfferUser(String phoneNumber, int offerNumber)
	// {
	// boolean result;
	// db.getTransaction().begin();
	//
	// User user = db.find(User.class, phoneNumber);
	// Offer offer = db.find(Offer.class, offerNumber);
	// // result = user.isFollowingOffer(offer);
	//
	// db.getTransaction().commit();
	// return result;
	// }

	public boolean isFollowingOfferClient(String username, int offerNumber) {
		boolean result;
		db.getTransaction().begin();

		Client client = db.find(Client.class, username);
		Offer offer = db.find(Offer.class, offerNumber);
		result = client.isFollowingOffer(offer);

		db.getTransaction().commit();
		return result;
	}

	public boolean isFollowingOfferOwner(String username, int offerNumber) {
		boolean result;
		db.getTransaction().begin();

		Owner owner = db.find(Owner.class, username);
		Offer offer = db.find(Offer.class, offerNumber);
		result = owner.isFollowingOffer(offer);

		db.getTransaction().commit();
		return result;

	}

	public boolean isFollowingOfferAdmin(String username, int offerNumber) {
		boolean result;
		db.getTransaction().begin();

		Admin admin = db.find(Admin.class, username);
		Offer offer = db.find(Offer.class, offerNumber);
		result = admin.isFollowingOffer(offer);

		db.getTransaction().commit();
		return result;

	}

	// public Vector<Offer> getFollowedOffersUser(String username) {
	// User user = db.find(User.class, username);
	// return user.getFollowedOffers();
	// }

	public Vector<Offer> getFollowedOffersClient(String username) {
		Client client = db.find(Client.class, username);
		return client.getFollowedOffers();
	}

	public Vector<Offer> getFollowedOffersOwner(String username) {
		Owner owner = db.find(Owner.class, username);
		return owner.getFollowedOffers();
	}

	public Vector<Offer> getFollowedOffersAdmin(String username) {
		Admin admin = db.find(Admin.class, username);
		return admin.getFollowedOffers();
	}

	public Conversation getConversation(Integer conversationNumber) {
		Conversation conversation = db.find(Conversation.class, conversationNumber);
		return conversation;
	}

	public List<Object> getAllUsers() {
		List<Object> users = new ArrayList<>();
		TypedQuery<User> query = db.createQuery("SELECT user FROM User user", User.class);
		List<User> results = query.getResultList();
		for (User user : results) {
			users.add(user);
		}
		return users;
	}

	public List<Object> getAllClients() {
		List<Object> clients = new ArrayList<>();
		TypedQuery<Client> query = db.createQuery("SELECT client FROM Client client", Client.class);
		List<Client> results = query.getResultList();
		for (Client client : results) {
			clients.add(client);
			System.out.println("sacado el client " + client.getUsername());
		}
		return clients;
	}

	public List<Object> getAllOwners() {
		List<Object> owners = new ArrayList<>();
		TypedQuery<Owner> query = db.createQuery("SELECT owner FROM Owner owner", Owner.class);
		List<Owner> results = query.getResultList();
		for (Owner owner : results) {
			owners.add(owner);
		}
		return owners;
	}

	public List<Object> getAllOffers() {
		List<Object> offers = new ArrayList<>();
		TypedQuery<Offer> query = db.createQuery("SELECT offer FROM Offer offer", Offer.class);
		List<Offer> results = query.getResultList();
		for (Offer offer : results) {
			offers.add(offer);
		}
		return offers;
	}

	public Client getClient(int elementID) {
		Client client = db.find(Client.class, elementID);
		return client;
	}

	public Owner getOwner(int elementID) {
		Owner owner = db.find(Owner.class, elementID);
		return owner;
	}

	public RuralHouse getRuralHouse(int elementID) {
		RuralHouse ruralHouse = db.find(RuralHouse.class, elementID);
		return ruralHouse;
	}

	public void updateUser(String phoneNumber, char[] newPassword) {
		db.getTransaction().begin();
		User user = db.find(User.class, phoneNumber);
		user.setPassword(newPassword);
		db.persist(user);
		db.getTransaction().commit();
	}

	public void updateOffer(Offer offer) {
		db.getTransaction().begin();
		db.merge(offer);
		db.getTransaction().commit();
	}

	public Admin getAdmin() {
		db.getTransaction().begin();
		Admin admin = db.find(Admin.class, "admin");
		db.getTransaction().commit();
		return admin;
	}

	public int createConversation(byte userType, String username, String username2) {
		db.getTransaction().begin();
		Owner chatingOwner = db.find(Owner.class, username2);
		User user;
		Client client;
		Owner owner;
		Admin admin;
		Conversation conversation = new Conversation(userType, User.USER_TYPE_OWNER);
		conversation.setStarterUsername(username);
		conversation.setListenerUsername(username2);
		conversation.setListenerOwner(chatingOwner);
		switch (userType) {
		// case User.USER_TYPE_USER:
		// user = db.find(User.class, username);
		// conversation.setStarterUser(user);
		// user.getStarterCoversations().add(conversation);
		// db.persist(user);
		// break;
		case User.USER_TYPE_CLIENT:
			client = db.find(Client.class, username);
			conversation.setStarterClient(client);
			client.getStarterCoversations().add(conversation);
			db.persist(client);
			break;
		case User.USER_TYPE_OWNER:
			owner = db.find(Owner.class, username);
			conversation.setStarterOwner(owner);
			owner.getStarterCoversations().add(conversation);
			db.persist(owner);
			break;
		case User.USER_TYPE_ADMIN:
			admin = db.find(Admin.class, username);
			conversation.setStarterAdmin(admin);
			admin.getStarterCoversations().add(conversation);
			db.persist(admin);
		}
		chatingOwner.getListenerConversations().add(conversation);
		db.persist(chatingOwner);
		db.persist(conversation);
		db.getTransaction().commit();
		return conversation.getConversationNumber();
	}

	public void sendMessage(int conversationNumber, String username, String message) {
		db.getTransaction().begin();
		Conversation conversation = db.find(Conversation.class, conversationNumber);
		Date date = new Date();
		String[] datos = { username, message };
		conversation.getMessages().put(date, datos);
		db.persist(conversation);
		db.getTransaction().commit();
	}

	public void createAdmin(char[] password, byte[] image) {
		db.getTransaction().begin();
		Admin admin = new Admin(password, image);
		System.out.println(admin.getUsername());
		db.persist(admin);
		db.getTransaction().commit();
	}

	public Admin getAdmin(String username) {
		db.getTransaction().begin();
		Admin admin = db.find(Admin.class, username);
		db.getTransaction().commit();
		return admin;
	}

	public byte[] getAdminProfileImage(String username) {
		db.getTransaction().begin();
		Admin admin = db.find(Admin.class, username);
		db.getTransaction().commit();
		return admin.getProfileImage();
	}

}