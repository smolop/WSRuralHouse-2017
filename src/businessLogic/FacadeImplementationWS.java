package businessLogic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.swing.JLabel;

import configuration.ConfigXML;
import dataAccess.DataAccess;
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

//Service Implementation
@WebService(endpointInterface = "businessLogic.ApplicationFacadeInterfaceWS")
public class FacadeImplementationWS implements ApplicationFacadeInterfaceWS {
	public FacadeImplementationWS() {
		ConfigXML c = ConfigXML.getInstance();
		if (c.getDataBaseOpenMode().equals("initialize")) {
			DataAccess dbManager = new DataAccess();
			dbManager.initializeDB();
			dbManager.close();
		}
	}

	/**
	 * This method creates an offer with a house number, first day, last day and
	 * price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return the created offer, or null, or an exception
	 */
	@Override
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price)
			throws OverlappingOfferExists, BadDates {
		System.out.println(">> FacadeImplementationWS: createOffer=> ruralHouse= " + ruralHouse + " firstDay= "
				+ firstDay + " lastDay=" + lastDay + " price=" + price);

		DataAccess dbManager = new DataAccess();
		Offer o = null;
		if (firstDay.compareTo(lastDay) >= 0) {
			dbManager.close();
			throw new BadDates();
		}

		boolean b = dbManager.existsOverlappingOffer(ruralHouse, firstDay, lastDay);
		if (!b) {
			o = dbManager.createOffer(ruralHouse, firstDay, lastDay, price);
		}

		dbManager.close();
		System.out.println("<< FacadeImplementationWS: createOffer=> O= " + o);
		return o;
	}

	@Override
	public Vector<RuralHouse> getAllRuralHouses() {
		System.out.println(">> FacadeImplementationWS: getAllRuralHouses");

		DataAccess dbManager = new DataAccess();

		Vector<RuralHouse> ruralHouses = dbManager.getAllRuralHouses();
		dbManager.close();
		System.out.println("<< FacadeImplementationWS:: getAllRuralHouses");

		return ruralHouses;

	}

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
	@Override
	@WebMethod
	public Vector<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay) {

		DataAccess dbManager = new DataAccess();
		Vector<Offer> offers = new Vector<Offer>();
		offers = dbManager.getOffers(rh, firstDay, lastDay);
		dbManager.close();
		return offers;
	}

	public void close() {
		DataAccess dbManager = new DataAccess();
		dbManager.close();
	}

	@Override
	public void initializeBD() {
		DataAccess dbManager = new DataAccess();
		dbManager.initializeDB();
		dbManager.close();
	}

	@Override
	public byte scalatePrivileges(String username, char[] password) {
		DataAccess dbManager = new DataAccess();
		byte userType = dbManager.getUserType(username);
		boolean passwordMatch = dbManager.checkPassword(userType, username, password);
		dbManager.close();
		if (passwordMatch) {
			return userType;
		} else {
			return User.USER_TYPE_USER;
		}
	}

	@Override
	public byte getUserType(String username) {
		DataAccess dbManager = new DataAccess();
		byte userType = dbManager.getUserType(username);
		dbManager.close();
		return userType;
	}

	@Override
	public void createRuralHouse(String username, String shortDescription, String description,

			String city, List<byte[]> ruralHousesImages, int bathroomQuantity, int bedroomQuantity,
			int dinningroomQuantity, int kitchenQuantity, int parkingPlaceQuantity, int roomQuantity) {
		DataAccess dbManager = new DataAccess();
		dbManager.createRuralHouse(username, shortDescription, description, city, ruralHousesImages, bathroomQuantity,
				bedroomQuantity, dinningroomQuantity, kitchenQuantity, parkingPlaceQuantity, roomQuantity);
		dbManager.close();
	}

	@Override
	public User getUser(String phoneNumber) {
		DataAccess dbManager = new DataAccess();
		User user = dbManager.getUser(phoneNumber);
		dbManager.close();
		return user;
	}

	@Override
	public Client getClient(String username) {
		DataAccess dbManager = new DataAccess();
		Client client = dbManager.getClient(username);
		dbManager.close();
		return client;
	}

	@Override
	public Owner getOwner(String username) {
		DataAccess dbManager = new DataAccess();
		Owner owner = dbManager.getOwner(username);
		dbManager.close();
		return owner;
	}

	@Override
	public Vector<RuralHouse> getOwnerRuralHouses(String username) {
		DataAccess dbManager = new DataAccess();
		Vector<RuralHouse> ruralHouses = dbManager.getOwnerRuralHouses(username);
		dbManager.close();
		return ruralHouses;
	}

	@Override
	public void deleteRuralHouse(int ruralHouseNumber) {
		DataAccess dbManager = new DataAccess();
		dbManager.deleteRuralHouse(ruralHouseNumber);
		dbManager.close();
	}

	@Override
	public Vector<Offer> getRuralHouseOffers(int ruralHouseNumber) {
		DataAccess dbManager = new DataAccess();
		Vector<Offer> offers = dbManager.getRuralHouseOffers(ruralHouseNumber);
		dbManager.close();
		return offers;
	}

	@Override
	public void deleteOffer(int offerNumber) {
		DataAccess dbManager = new DataAccess();
		dbManager.deleteOffer(offerNumber);
		dbManager.close();
	}

	@Override
	public boolean isValidUsername(String username) {
		DataAccess dbManager = new DataAccess();
		boolean existsUser = dbManager.existUser(username);
		dbManager.close();
		if (existsUser) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isValidPassword(char[] password) {
		String pass = new String(password);
		System.out.println("LA CONTRASEÑA A VALIDAR ES " + pass);
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]*)(?=\\S+$).{8,}$";
		boolean match = Pattern.matches(regex, pass);
		System.out.println(pass + "< is a valid password ? : " + match);
		return match;
	}

	@Override
	public boolean isValidProfileImage(byte[] profileImage) {
		return true;
	}

	@Override
	public boolean isValidName(String name) {
		String regex = "^[A-Z]([a-z]+(\\s[a-zA-Z][a-z]+)*)$";
		boolean match = Pattern.matches(regex, name);
		System.out.println(name + "< is a valid namme ? : " + match);
		return match;
	}

	@Override
	public boolean isValidLastName(String lastName) {
		String regex = "^[A-Z]([a-z]+(\\s[a-zA-Z][a-z]+)*)$";
		boolean match = Pattern.matches(regex, lastName);
		System.out.println(lastName + "< is a valid lastnamme ? : " + match);
		return match;
	}

	@Override
	public boolean isValidDNI(String ID) {
		String DNI_REGEX = "^(\\d{8})([A-Z])$";
		String CIF_REGEX = "^([A-HJ-NP-W])(\\d{7})([0-9A-J])$";
		String NIE_REGEX = "^[XYZ]\\d{7,8}[A-Z]$";
		String regex = DNI_REGEX + "|" + CIF_REGEX + "|" + NIE_REGEX;
		boolean match = Pattern.matches(regex, ID);
		System.out.println(ID + "< is a valid ID ? : " + match);
		return match;
	}

	@Override
	public boolean isValidMail(String mail) {
		String regex = "^[_A-Za-z0-9-]+((\\.|\\+)[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z]{2,}){1}$)";
		// String regex = "^[a-zA-Z0-9._+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
		boolean match = Pattern.matches(regex, mail);
		System.out.println(mail + "< is a valid email ? : " + match);
		return match;
	}

	@Override
	public boolean isValidPhoneNumber(String phoneNumber) {
		String regex = "^(\\d{9})$";
		boolean match = Pattern.matches(regex, phoneNumber);
		System.out.println(phoneNumber + "< is a valid phone number ? : " + match);
		return match;
	}

	@Override
	public boolean isValidAddress(String address) {
		String regex = "^[a-zA-Z][a-z]+([\\s|-][a-zA-Z][a-z]*)*([\\s|-][0-9]*)*([\\s|-][a-zA-Z]*)*$";
		boolean match = Pattern.matches(regex, address);
		System.out.println(address + "< is a valid address ? : " + match);
		return match;
	}

	@Override
	public void createClient(String username, char[] password, byte[] profileImage, String name, String lastName,
			String dNI, String mail, String phoneNumber, String address) {
		DataAccess dbManager = new DataAccess();
		dbManager.createClient(username, password, profileImage, name, lastName, dNI, mail, phoneNumber, address);

	}

	@Override
	public void createOwner(String username, char[] password, byte[] profileImage, String name, String lastName,
			String dNI, String mail, String phoneNumber, String address) {
		DataAccess dbManager = new DataAccess();
		dbManager.createOwner(username, password, profileImage, name, lastName, dNI, mail, phoneNumber, address);

	}

	@Override
	public boolean isValidCity(String city) {
		if (city.length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isValidDescription(String description) {
		if (description.length() > 0) {
			return true;
		} else {
			return true;
		}
	}

	@Override
	public byte[] getUserProfileImage(byte userType, String username) {
		byte[] profileImage = null;
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			profileImage = getClientIcon(username);
			break;
		case User.USER_TYPE_OWNER:
			profileImage = getOwnerIcon(username);
			break;
		case User.USER_TYPE_ADMIN:
			profileImage = getAdminIcon(username);
			break;
		}
		return profileImage;
	}

	private byte[] getClientIcon(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getClientProfileImage(username);
	}

	private byte[] getOwnerIcon(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getOwnerProfileImage(username);
	}

	private byte[] getAdminIcon(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getAdminProfileImage(username);
	}

	@Override
	public String getUserName(byte userType, String username) {
		String name = null;
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			name = getClientName(username);
			break;
		case User.USER_TYPE_OWNER:
			name = getOwnerName(username);
			break;
		}
		return name;
	}

	private String getClientName(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getClientName(username);
	}

	private String getOwnerName(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getOwnerName(username);
	}

	@Override
	public String getUseLastName(byte userType, String username) {
		String lastName = null;
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			lastName = getClientLastName(username);
			break;
		case User.USER_TYPE_OWNER:
			lastName = getOwnerLastName(username);
			break;
		}
		return lastName;
	}

	private String getClientLastName(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getClientLastName(username);
	}

	private String getOwnerLastName(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getOwnerLastName(username);
	}

	@Override
	public String getUserEmail(byte userType, String username) {
		String email = null;
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			email = getClientEmail(username);
			break;
		case User.USER_TYPE_OWNER:
			email = getOwnerEmail(username);
			break;
		}
		return email;
	}

	private String getOwnerEmail(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getOwnerEmail(username);
	}

	private String getClientEmail(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getClientEmail(username);
	}

	@Override
	public String getUserPhoneNumber(byte userType, String username) {
		String phoneNumber = "0";
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			phoneNumber = getClientPhoneNumber(username);
			break;
		case User.USER_TYPE_OWNER:
			phoneNumber = getOwnerPhoneNumber(username);
			break;
		}
		return phoneNumber;
	}

	private String getOwnerPhoneNumber(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getOwnerPhoneNumber(username);
	}

	private String getClientPhoneNumber(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getClientPhoneNumber(username);
	}

	@Override
	public String getUserAddress(byte userType, String username) {
		String address = null;
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			address = getClientAddress(username);
			break;
		case User.USER_TYPE_OWNER:
			address = getOwnerAddress(username);
			break;
		}
		return address;
	}

	private String getOwnerAddress(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getOwnerAddress(username);
	}

	private String getClientAddress(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getClientAddress(username);
	}

	@Override
	public void updateClient(Client client, JLabel feedbackJLabel, String newUsername, char[] password,
			char[] newPassword, char[] confirmNewPassword, String newProfileImage, String newName, String newLastName,
			String newEmail, String newPhoneNumber, String newAddress) {
		DataAccess dbManager = new DataAccess();
		if (!client.getUsername().equals(newUsername)) {
			if (!dbManager.existClient(newUsername)) {
				if (isValidUsername(newUsername)) {
					client.setUsername(newUsername);
				} else {
					feedbackJLabel.setText("Invalid username");
					return;

				}
			} else {
				feedbackJLabel.setText("New username is already choosen");
				return;
			}
		}

		if (newPassword.length > 0) {
			if (client.checkPassword(password)) {
				if (Arrays.equals(newPassword, confirmNewPassword)) {
					if (isValidPassword(newPassword)) {
						client.setPassword(newPassword);
					} else {
						feedbackJLabel.setText("Invalid password");
						return;
					}
				} else {
					feedbackJLabel.setText("Passwords don't match");
					return;
				}
			}
		}

		byte[] profileImageInByte = null;
		if (newProfileImage.length() > 0) {
			File file = new File(newProfileImage);
			BufferedImage image = null;
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(image, "png", baos);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			profileImageInByte = baos.toByteArray();

			if (isValidProfileImage(profileImageInByte)) {
				client.setProfileImage(profileImageInByte);
			} else {
				feedbackJLabel.setText("Invalid profile image");
				return;
			}
		}

		if (isValidName(newName)) {
			client.setName(newName);
		} else {
			feedbackJLabel.setText("Invalid name");
			return;
		}

		if (isValidLastName(newLastName)) {
			client.setLastName(newLastName);
		} else {
			feedbackJLabel.setText("Invalid last name");
			return;
		}

		if (isValidMail(newEmail)) {
			client.setEmail(newEmail);
		} else {
			feedbackJLabel.setText("Invalid email");
			return;
		}

		if (isValidPhoneNumber(newPhoneNumber)) {
			client.setPhoneNumber(newPhoneNumber);
		} else {
			feedbackJLabel.setText("Is not a valid phone number");
			return;
		}

		if (isValidAddress(newAddress)) {
			client.setAddress(newAddress);
		} else {
			feedbackJLabel.setText("Is not a valid address");
			return;
		}

		dbManager.updateClient(client);
		feedbackJLabel.setText("Succesfully updated");
	}

	@Override
	public void updateOwner(Owner owner, JLabel feedbackJLabel, String newUsername, char[] password, char[] newPassword,
			char[] confirmNewPassword, String newProfileImage, String newName, String newLastName, String newEmail,
			String newPhoneNumber, String newAddress) {
		DataAccess dbManager = new DataAccess();
		if (!owner.getUsername().equals(newUsername)) {
			if (!dbManager.existClient(newUsername)) {
				if (isValidUsername(newUsername)) {
					owner.setUsername(newUsername);
				} else {
					feedbackJLabel.setText("Invalid username");
					return;

				}
			} else {
				feedbackJLabel.setText("New username is already choosen");
				return;
			}
		}

		if (newPassword.length > 0) {
			if (owner.checkPassword(password)) {
				if (Arrays.equals(newPassword, confirmNewPassword)) {
					if (isValidPassword(newPassword)) {
						owner.setPassword(newPassword);
					} else {
						feedbackJLabel.setText("Invalid password");
						return;
					}
				} else {
					feedbackJLabel.setText("Passwords don't match");
					return;
				}
			}
		}

		byte[] profileImageInByte = null;
		if (newProfileImage.length() > 0) {
			File file = new File(newProfileImage);
			BufferedImage image = null;
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(image, "png", baos);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			profileImageInByte = baos.toByteArray();

			if (isValidProfileImage(profileImageInByte)) {
				owner.setProfileImage(profileImageInByte);
			} else {
				feedbackJLabel.setText("Invalid profile image");
				return;
			}
		}

		if (isValidName(newName)) {
			owner.setName(newName);
		} else {
			feedbackJLabel.setText("Invalid name");
			return;
		}

		if (isValidLastName(newLastName)) {
			owner.setLastName(newLastName);
		} else {
			feedbackJLabel.setText("Invalid last name");
			return;
		}

		if (isValidMail(newEmail)) {
			owner.setEmail(newEmail);
		} else {
			feedbackJLabel.setText("Invalid email");
			return;
		}

		if (isValidPhoneNumber(newPhoneNumber)) {
			owner.setPhoneNumber(newPhoneNumber);
		} else {
			feedbackJLabel.setText("Is not a valid phone number");
			return;
		}

		if (isValidAddress(newAddress)) {
			owner.setAddress(newAddress);
		} else {
			feedbackJLabel.setText("Is not a valid address");
			return;
		}

		dbManager.updateOwner(owner);
		feedbackJLabel.setText("Succesfully updated");
	}

	@Override
	public Vector<RuralHouse> searchOffer(Vector<RuralHouse> ruralHouses, int ruralHouseNumber, int offerNumber,
			String city, int minimumPrice, int maximumPrice, Date firstDay, Date lastDay, int dayQuantity,
			String searchString) {
		Vector<RuralHouse> filteredRuralHouses = ruralHouses;

		if (ruralHouseNumber != -1) {
			filteredRuralHouses = filterByRuralHouseNumber(filteredRuralHouses, ruralHouseNumber);
		}

		if (offerNumber != -1) {
			filteredRuralHouses = filterByOfferNumber(filteredRuralHouses, offerNumber);
		}

		if (!city.equals("")) {
			filteredRuralHouses = filterByCity(filteredRuralHouses, city);
		}

		if (minimumPrice != -1) {
			filteredRuralHouses = filterByMinimumPrice(filteredRuralHouses, minimumPrice);
		}

		if (maximumPrice != -1) {
			filteredRuralHouses = filterByMaximumPrice(filteredRuralHouses, maximumPrice);
		}

		filteredRuralHouses = filterByFirstDay(filteredRuralHouses, firstDay);
		filteredRuralHouses = filterByLastDay(filteredRuralHouses, lastDay);

		if (dayQuantity != -1) {
			filteredRuralHouses = filterByDayQuantity(filteredRuralHouses, dayQuantity);
		}

		if (!searchString.equals("")) {
			filteredRuralHouses = filterBySearchString(filteredRuralHouses, searchString);
		}

		return filteredRuralHouses;
	}

	private Vector<RuralHouse> filterBySearchString(Vector<RuralHouse> ruralHouses, String searchString) {
		Vector<RuralHouse> filteredRuralHouses = ruralHouses;
		Iterator<RuralHouse> filteredRuralHousesIterator = filteredRuralHouses.iterator();
		String[] data = searchString.split(" ");
		ArrayList<String> commands = new ArrayList<String>();
		ArrayList<String> words = new ArrayList<String>();
		for (String str : data) {
			if (str.contains(":")) {
				commands.add(str);
			} else {
				words.add(str);
			}
		}
		Iterator<String> commandsIterator = commands.iterator();
		while (commandsIterator.hasNext()) {
			String command = commandsIterator.next();
			String[] parts = command.split(":");
			String name = parts[0];
			String value = parts[1];
			switch (name.toUpperCase()) {
			case "RURALHOUSENUMBER":
			case "RHN":
				filterByRuralHouseNumber(filteredRuralHouses, Integer.parseInt(value));
				break;
			case "OFFERNUMBER":
			case "ON":
				filterByOfferNumber(filteredRuralHouses, Integer.parseInt(value));
				break;
			case "PROVINCE":
			case "P":
				filterByCity(filteredRuralHouses, value);
				break;
			case "MINIMUMPRICE":
			case "MINP":
				filterByMaximumPrice(filteredRuralHouses, Integer.parseInt(value));
				break;
			case "MAXIMUMPRICE":
			case "MAXP":
				filterByMaximumPrice(filteredRuralHouses, Integer.parseInt(value));
				break;
			case "DAYQUANTITY":
			case "DQ":
				filterByDayQuantity(filteredRuralHouses, Integer.parseInt(value));
				break;
			case "FIRSTDAY":
			case "FDAY":
				DateFormat firstDayDateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Date firstDay = null;
				try {
					firstDay = firstDayDateFormat.parse(value);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				filterByFirstDay(filteredRuralHouses, firstDay);
				break;
			case "LASTDAY":
			case "LDAY":
				DateFormat lastDayDateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Date lastDay = null;
				try {
					lastDay = lastDayDateFormat.parse(value);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				filterByLastDay(filteredRuralHouses, lastDay);
				break;
			}
		}

		if (!words.isEmpty()) {
			filteredRuralHouses = filterByDescription(filteredRuralHouses, words);
		}
		return filteredRuralHouses;
	}

	private Vector<RuralHouse> filterByDescription(Vector<RuralHouse> ruralHouses, List<String> words) {
		Vector<RuralHouse> filteredRuralHouses = ruralHouses;
		Iterator<RuralHouse> filteredRuralHousesIterator = filteredRuralHouses.iterator();
		while (filteredRuralHousesIterator.hasNext()) {
			RuralHouse filteredRuralHouse = filteredRuralHousesIterator.next();
			boolean match = false;
			for (String word : words) {
				if (filteredRuralHouse.getDescription().toUpperCase().contains(word.toUpperCase())) {
					match = true;
					break;
				}
			}
			if (!match) {
				filteredRuralHousesIterator.remove();
			}
		}
		return filteredRuralHouses;
	}

	private Vector<RuralHouse> filterByDayQuantity(Vector<RuralHouse> ruralHouses, int dayQuantity) {
		Vector<RuralHouse> filteredRuralHouses = ruralHouses;
		Iterator<RuralHouse> filteredRuralHousesIterator = filteredRuralHouses.iterator();
		while (filteredRuralHousesIterator.hasNext()) {
			Vector<Offer> filteredOffers = filteredRuralHousesIterator.next().getAllOffers();
			Iterator<Offer> filteredOffersIterator = filteredOffers.iterator();
			while (filteredOffersIterator.hasNext()) {
				Offer filteredOffer = filteredOffersIterator.next();
				try {
					if (betweenDates(filteredOffer.getFirstDay(), filteredOffer.getLastDay()) < dayQuantity) {
						filteredOffersIterator.remove();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return filteredRuralHouses;
	}

	private Vector<RuralHouse> filterByLastDay(Vector<RuralHouse> ruralHouses, Date lastDay) {
		Vector<RuralHouse> filteredRuralHouses = ruralHouses;
		Iterator<RuralHouse> filteredRuralHousesIterator = filteredRuralHouses.iterator();
		while (filteredRuralHousesIterator.hasNext()) {
			Vector<Offer> filteredOffers = filteredRuralHousesIterator.next().getAllOffers();
			Iterator<Offer> filteredOffersIterator = filteredOffers.iterator();
			while (filteredOffersIterator.hasNext()) {
				if (filteredOffersIterator.next().getLastDay().after(lastDay)) {
					filteredOffersIterator.remove();
				}
			}
		}
		return filteredRuralHouses;
	}

	private Vector<RuralHouse> filterByFirstDay(Vector<RuralHouse> ruralHouses, Date firstDay) {
		Vector<RuralHouse> filteredRuralHouses = ruralHouses;
		Iterator<RuralHouse> filteredRuralHousesIterator = filteredRuralHouses.iterator();
		while (filteredRuralHousesIterator.hasNext()) {
			Vector<Offer> filteredOffers = filteredRuralHousesIterator.next().getAllOffers();
			Iterator<Offer> filteredOffersIterator = filteredOffers.iterator();
			while (filteredOffersIterator.hasNext()) {
				if (filteredOffersIterator.next().getFirstDay().before(firstDay)) {
					filteredOffersIterator.remove();
				}
			}
		}
		return filteredRuralHouses;
	}

	private Vector<RuralHouse> filterByMaximumPrice(Vector<RuralHouse> ruralHouses, int maximumPrice) {
		Vector<RuralHouse> filteredRuralHouses = ruralHouses;
		Iterator<RuralHouse> filteredRuralHousesIterator = filteredRuralHouses.iterator();
		while (filteredRuralHousesIterator.hasNext()) {
			Vector<Offer> filteredOffers = filteredRuralHousesIterator.next().getAllOffers();
			Iterator<Offer> filteredOffersIterator = filteredOffers.iterator();
			while (filteredOffersIterator.hasNext()) {
				if (filteredOffersIterator.next().getPrice() > maximumPrice) {
					filteredOffersIterator.remove();
				}
			}
		}
		return filteredRuralHouses;
	}

	private Vector<RuralHouse> filterByMinimumPrice(Vector<RuralHouse> ruralHouses, int minimumPrice) {
		Vector<RuralHouse> filteredRuralHouses = ruralHouses;
		Iterator<RuralHouse> filteredRuralHousesIterator = filteredRuralHouses.iterator();
		while (filteredRuralHousesIterator.hasNext()) {
			Vector<Offer> filteredOffers = filteredRuralHousesIterator.next().getAllOffers();
			Iterator<Offer> filteredOffersIterator = filteredOffers.iterator();
			while (filteredOffersIterator.hasNext()) {
				if (filteredOffersIterator.next().getPrice() < minimumPrice) {
					filteredOffersIterator.remove();
				}
			}
		}
		return filteredRuralHouses;
	}

	private Vector<RuralHouse> filterByCity(Vector<RuralHouse> ruralHouses, String city) {
		Vector<RuralHouse> filteredRuralHouses = ruralHouses;
		Iterator<RuralHouse> filteredRuralHousesIterator = filteredRuralHouses.iterator();
		while (filteredRuralHousesIterator.hasNext()) {
			if (!filteredRuralHousesIterator.next().getCity().toUpperCase().equals(city.toUpperCase())) {
				filteredRuralHousesIterator.remove();
			}
		}
		return filteredRuralHouses;
	}

	private Vector<RuralHouse> filterByOfferNumber(Vector<RuralHouse> ruralHouses, int offerNumber) {
		Vector<RuralHouse> filteredRuralHouses = ruralHouses;
		Iterator<RuralHouse> filteredRuralHousesIterator = filteredRuralHouses.iterator();
		while (filteredRuralHousesIterator.hasNext()) {
			RuralHouse filteredRuralHouse = filteredRuralHousesIterator.next();
			Vector<Offer> filteredOffers = filteredRuralHouse.getAllOffers();
			Iterator<Offer> filteredOffersIterator = filteredOffers.iterator();
			while (filteredOffersIterator.hasNext()) {
				Offer filteredOffer = filteredOffersIterator.next();
				if (filteredOffer.getOfferNumber() != offerNumber) {
					filteredOffersIterator.remove();
				}
			}
		}
		return filteredRuralHouses;
	}

	private Vector<RuralHouse> filterByRuralHouseNumber(Vector<RuralHouse> ruralHouses, int ruralHouseNumber) {
		Vector<RuralHouse> filteredRuralHouses = ruralHouses;
		Iterator<RuralHouse> filteredRuralHousesIterator = filteredRuralHouses.iterator();
		while (filteredRuralHousesIterator.hasNext()) {
			if (filteredRuralHousesIterator.next().getHouseNumber() != ruralHouseNumber) {
				filteredRuralHousesIterator.remove();
			}
		}
		return filteredRuralHouses;
	}

	public static long betweenDates(Date firstDate, Date secondDate) throws IOException {
		return ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
	}

	@Override
	public boolean isValidRuralHouseNumber(String ruralHouseNumber) {
		boolean isValid = true;
		if (!ruralHouseNumber.matches("\\d+")) {
			isValid = false;
		}
		return isValid;
	}

	@Override
	public boolean isValidOfferNumber(String offerNumber) {
		if (!offerNumber.matches("\\d+")) {
			return false;
		}
		if (Integer.parseInt(offerNumber) <= 0) {
			return false;
		}
		return true;
	}

	private boolean isValidPrice(String price) {
		if (!price.matches("[0-9]+([,.][0-9]{1,2})?")) {
			return false;
		}
		if (Float.parseFloat(price) < 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isValidMinimumPrice(String minimumPrice) {
		if (!minimumPrice.matches("\\d+")) {
			return false;
		}
		if (Integer.parseInt(minimumPrice) < 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isValidMaximumPrice(String maximumPrice) {
		if (!maximumPrice.matches("\\d+")) {
			return false;
		}
		if (Integer.parseInt(maximumPrice) < 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isValidDayQuantity(String dayQuantity) {
		if (!dayQuantity.matches("\\d+")) {
			return false;
		}
		if (Integer.parseInt(dayQuantity) < 1) {
			return false;
		}
		return true;
	}

	@Override
	public Offer getOffer(int offerNumber) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getOffer(offerNumber);
	}

	@Override
	public void clientBookOffer(String username, int offerNumber) {
		DataAccess dbManager = new DataAccess();
		dbManager.clientBookOffer(username, offerNumber);

	}

	@Override
	public void userBookOffer(String phoneNumber, int offerNumber) {
		DataAccess dbManager = new DataAccess();
		dbManager.userBookOffer(phoneNumber, offerNumber);
	}

	@Override
	public Vector<Offer> getClientBookedOffers(String username) {
		DataAccess dbManager = new DataAccess();
		Vector<Offer> bookedOffers = dbManager.getClientBookedOffers(username);
		dbManager.close();
		return bookedOffers;
	}

	@Override
	public void cancelClientBookedOffer(String username, Offer bookedOffer) {
		DataAccess dbManager = new DataAccess();
		int offerNumber = bookedOffer.getOfferNumber();
		dbManager.cancelClientBookedOffer(username, offerNumber);
		dbManager.close();
	}

	@Override
	public Vector<Offer> getUserBookedOffers(String phoneNumber) {
		DataAccess dbManager = new DataAccess();
		Vector<Offer> bookedOffers = dbManager.getUserBookedOffers(phoneNumber);
		System.out.println("PHONENUMBER: " + phoneNumber);
		dbManager.close();
		return bookedOffers;
	}

	@Override
	public void cancelUserBookedOffer(String phoneNumber, int offerNumber) {
		DataAccess dbManager = new DataAccess();
		dbManager.cancelUserBookedOffer(phoneNumber, offerNumber);
		dbManager.close();
	}

	@Override
	public boolean userExists(String phoneNumber) {
		DataAccess dbManager = new DataAccess();
		boolean existUser = dbManager.existsUser(phoneNumber);
		System.out.println(existUser);
		return existUser;
	}

	@Override
	public boolean clientExists(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.existClient(username);
	}

	@Override
	public void createUser(String phoneNumber, char[] password) {
		DataAccess dbManager = new DataAccess();
		if (!userExists(phoneNumber)) {
			dbManager.createUser(phoneNumber, password);
		}
		dbManager.close();
	}

	@Override
	public void createClientFromUser(String username, char[] password, byte[] profileImage, String name,
			String lastName, String dNI, String mail, String phoneNumber, String address) {
		DataAccess dbManager = new DataAccess();
		dbManager.createClientFromUser(username, password, profileImage, name, lastName, dNI, mail, phoneNumber,
				address);
	}

	@Override
	public void followOffer(String username, byte userType, int offerNumber) {
		DataAccess dbManager = new DataAccess();

		switch (userType) {

		// case User.USER_TYPE_USER:
		// dbManager.followOfferUser(username, offerNumber);
		// break;

		case User.USER_TYPE_CLIENT:
			dbManager.followOfferClient(username, offerNumber);
			break;

		case User.USER_TYPE_OWNER:
			dbManager.followOfferOwner(username, offerNumber);
			break;

		case User.USER_TYPE_ADMIN:
			dbManager.followOfferAdmin(username, offerNumber);
			break;
		}

		dbManager.close();
	}

	@Override
	public void unfollowOffer(String username, byte userType, int offerNumber) {
		DataAccess dbManager = new DataAccess();

		switch (userType) {

		// case User.USER_TYPE_USER:
		// dbManager.unfollowOfferUser(username, offerNumber);
		// break;

		case User.USER_TYPE_CLIENT:
			dbManager.unfollowOfferClient(username, offerNumber);
			break;

		case User.USER_TYPE_OWNER:
			dbManager.unfollowOfferOwner(username, offerNumber);
			break;

		case User.USER_TYPE_ADMIN:
			dbManager.unfollowOfferAdmin(username, offerNumber);
			break;
		}

		dbManager.close();

	}

	@Override
	public boolean isFollowingOffer(String username, byte userType, int offerNumber) {
		boolean result = false;
		DataAccess dbManager = new DataAccess();
		switch (userType) {

		// case User.USER_TYPE_USER:
		// result = dbManager.isFollowingOfferUser(username, offerNumber);
		// break;
		case User.USER_TYPE_CLIENT:
			result = dbManager.isFollowingOfferClient(username, offerNumber);
			break;
		case User.USER_TYPE_OWNER:
			result = dbManager.isFollowingOfferOwner(username, offerNumber);
			break;
		case User.USER_TYPE_ADMIN:
			result = dbManager.isFollowingOfferAdmin(username, offerNumber);
			break;
		}

		dbManager.close();
		return result;
	}

	@Override
	public Vector<Offer> getFollowingOffers(String username, byte userType) {
		DataAccess dbManager = new DataAccess();
		Vector<Offer> followingOffers = new Vector<Offer>();
		switch (userType) {
		// case User.USER_TYPE_USER:
		// followingOffers = dbManager.getFollowedOffersUser(username);
		// break;
		case User.USER_TYPE_CLIENT:
			followingOffers = dbManager.getFollowedOffersClient(username);
			break;
		case User.USER_TYPE_OWNER:
			followingOffers = dbManager.getFollowedOffersOwner(username);
			break;
		case User.USER_TYPE_ADMIN:
			followingOffers = dbManager.getFollowedOffersAdmin(username);
			break;

		default:
			break;
		}
		return followingOffers;
	}

	@Override
	public Conversation getConversation(Integer conversationNumber) {
		DataAccess dbManager = new DataAccess();
		Conversation conversation = dbManager.getConversation(conversationNumber);
		return conversation;
	}

	@Override
	public List<Object> getAllUsers() {
		DataAccess dbManager = new DataAccess();
		return dbManager.getAllUsers();
	}

	@Override
	public List<Object> getAllClients() {
		DataAccess dbManager = new DataAccess();
		return dbManager.getAllClients();
	}

	@Override
	public List<Object> getAllOwners() {
		DataAccess dbManager = new DataAccess();
		return dbManager.getAllOwners();
	}

	@Override
	public List<Object> getAllOffers() {
		DataAccess dbManager = new DataAccess();
		return dbManager.getAllOffers();
	}

	@Override
	public Client getClient(int elementID) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getClient(elementID);
	}

	@Override
	public Owner getOwner(int elementID) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getOwner(elementID);
	}

	@Override
	public RuralHouse getRuralHouse(int elementID) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getRuralHouse(elementID);
	}

	@Override
	public void updateUser(String phoneNumber, char[] newPassword) {
		DataAccess dbManager = new DataAccess();
		dbManager.updateUser(phoneNumber, newPassword);
	}

	@Override
	public void updateOffer(Offer offer, JLabel offerFeedback, Date firstDay, Date lastDay, String price) {
		DataAccess dbManager = new DataAccess();
		if (firstDay.before(lastDay)) {
			if (isValidPrice(price)) {
				offer.setFirstDay(firstDay);
				offer.setLastDay(lastDay);
				offer.setPrice(Float.valueOf(price));
			} else {
				offerFeedback.setText("This is not a valid price");
				return;
			}
		} else {
			offerFeedback.setText("Last day must be after first day");
			return;
		}
		dbManager.updateOffer(offer);
		offerFeedback.setText("Succesfully updated");
	}

	@Override
	public void updateRuralHouse(RuralHouse ruralHouse, JLabel ruralHouseFeedbackJLabel, String city,
			String shortDescription, String description, String bathrooms, String bedrooms, String dinningrooms,
			String kitchens, String parkingPlaces, String rooms, List<String> ruralHouseImagesPaths) {
		DataAccess dbManager = new DataAccess();
		if (isValidShortDescription(shortDescription)) {
			if (isValidDescription(description)) {
				if (Integer.parseInt(bathrooms) > 2) {
					if (Integer.parseInt(rooms) > 3) {
						if (Integer.parseInt(kitchens) > 1) {
							ruralHouse.setCity(city);
							ruralHouse.setShortDescription(shortDescription);
							ruralHouse.setDescription(description);
							ruralHouse.setBathroomQuantity(Integer.parseInt(bathrooms));
							ruralHouse.setBedroomQuantity(Integer.parseInt(bedrooms));
							ruralHouse.setDinningroomQuantity(Integer.parseInt(dinningrooms));
							ruralHouse.setKitchenQuantity(Integer.parseInt(kitchens));
							ruralHouse.setParkingPlaceQuantity(Integer.parseInt(parkingPlaces));
							ruralHouse.setRoomQuantity(Integer.parseInt(rooms));
						} else {
							ruralHouseFeedbackJLabel.setText("Kitchen quantity must be at least 1");
							return;
						}
					} else {
						ruralHouseFeedbackJLabel.setText("Room quantity must be at least 3");
						return;
					}
				} else {
					ruralHouseFeedbackJLabel.setText("Bathroom quantity must be at least 2");
					return;
				}
			} else {
				ruralHouseFeedbackJLabel.setText("Description must be more than 150 characters");
				return;
			}
		} else {
			ruralHouseFeedbackJLabel.setText("Short description must be at least 150 characters");
			return;
		}

		// ruralHouse.getRuralHouseImages().clear();
		// for (String path : ruralHouseImagesPaths) {
		// byte[] ruralHouseImageInByte = null;
		// if (path.length() > 0) {
		// File file = new File(path);
		// BufferedImage image = null;
		// try {
		// image = ImageIO.read(file);
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// try {
		// ImageIO.write(image, "png", baos);
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// ruralHouseImageInByte = baos.toByteArray();
		// } else {
		// File file = new File(".\\resources\\rural_house.png");
		// BufferedImage image = null;
		// try {
		// image = ImageIO.read(file);
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// try {
		// ImageIO.write(image, "png", baos);
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// ruralHouseImageInByte = baos.toByteArray();
		// ruralHouse.getRuralHouseImages().add(ruralHouseImageInByte);
		// }
		// }

		ruralHouseFeedbackJLabel.setText("Succesfully updated");
	}

	private boolean isValidShortDescription(String shortDescription) {
		if (shortDescription.length() > 150) {
			return false;
		}
		return true;
	}

	@Override
	public int getConversationNumber(byte userType, String username, String username2) {
		DataAccess dbManager = new DataAccess();
		User user;
		Client client;
		Owner owner;
		Admin admin;
		Vector<Conversation> startedConversations = null;
		Vector<Conversation> listenerConversations = null;
		switch (userType) {
		// case User.USER_TYPE_USER:
		// user = dbManager.getUser(username);
		// startedConversations = user.getStarterCoversations();
		// listenerConversations = user.getListenerConversations();
		// break;
		case User.USER_TYPE_CLIENT:
			client = dbManager.getClient(username);
			startedConversations = client.getStarterCoversations();
			listenerConversations = client.getListenerConversations();
			break;
		case User.USER_TYPE_OWNER:
			owner = dbManager.getOwner(username);
			startedConversations = owner.getStarterCoversations();
			listenerConversations = owner.getListenerConversations();
			break;
		case User.USER_TYPE_ADMIN:
			admin = dbManager.getAdmin(username);
			startedConversations = admin.getStarterCoversations();
			listenerConversations = admin.getListenerConversations();
			break;
		}

		for (Conversation startedConversation : startedConversations) {
			if (startedConversation.getListenerUsername().equals(username2)) {
				return startedConversation.getConversationNumber();
			}
		}
		for (Conversation listenerConversation : listenerConversations) {
			if (listenerConversation.getStarterUsername().equals(username2)) {
				return listenerConversation.getConversationNumber();
			}
		}

		return -1;
	}

	@Override
	public int startConversation(byte userType, String username, String username2) {
		DataAccess dbManager = new DataAccess();
		int generatedConversationNumber = dbManager.createConversation(userType, username, username2);
		return generatedConversationNumber;
	}

	@Override
	public void sendMessage(int conversationNumber, String username, String message) {
		DataAccess dbManager = new DataAccess();
		dbManager.sendMessage(conversationNumber, username, message);
	}

	@Override
	public void createAdmin(char[] password, byte[] image) {
		DataAccess dbManager = new DataAccess();
		dbManager.createAdmin(password, image);
	}

	@Override
	public Admin getAdmin(String username) {
		DataAccess dbManager = new DataAccess();
		return dbManager.getAdmin(username);
	}

	@Override
	public boolean isValidUserToClient(JLabel feedbackJLabel, char[] password, byte[] profileImageInByte, String name,
			String lastName, String dni, String mail, String phoneNumber, String address) {
		if (isValidPassword(password)) {
			if (isValidProfileImage(profileImageInByte)) {
				if (isValidName(name)) {
					if (isValidLastName(lastName)) {
						if (isValidDNI(dni)) {
							if (isValidMail(mail)) {
								if (isValidPhoneNumber(phoneNumber)) {
									if (isValidAddress(address)) {
										return true;
									} else {
										feedbackJLabel.setText("Invalid address");
										feedbackJLabel.setVisible(true);
									}
								} else {
									feedbackJLabel.setText("Invalid phone number");
									feedbackJLabel.setVisible(true);
								}
							} else {
								feedbackJLabel.setText("Invalid mail");
								feedbackJLabel.setVisible(true);
							}
						} else {
							feedbackJLabel.setText("Invalid dni");
							feedbackJLabel.setVisible(true);
						}
					} else {
						feedbackJLabel.setText("Invalid last name");
						feedbackJLabel.setVisible(true);
					}
				} else {
					feedbackJLabel.setText("Invalid name");
					feedbackJLabel.setVisible(true);
				}
			} else {
				feedbackJLabel.setText("Invalid profile image");
				feedbackJLabel.setVisible(true);
			}
		} else {
			feedbackJLabel.setText("Invalid password");
			feedbackJLabel.setVisible(true);
		}

		return false;
	}
}