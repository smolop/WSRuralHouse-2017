package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class RuralHouse implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	@GeneratedValue
	private Integer houseNumber;
	private String city;
	private String description;
	private String shortDescription;
	@OneToMany(fetch = FetchType.EAGER)
	private List<byte[]> ruralHousesImages;
	private int bathroomQuantity;
	private int bedroomQuantity;
	private int dinningroomQuantity;
	private int kitchenQuantity;
	private int parkingPlaceQuantity;
	private int roomQuantity;
	@OneToMany(fetch = FetchType.EAGER)
	public Vector<Offer> offers;
	@ManyToOne(fetch = FetchType.EAGER)
	private Owner owner;

	public RuralHouse(String shortDescription, String description, String city, List<byte[]> ruralHousesImages,
			int bathroomQuantity, int bedroomQuantity, int dinningroomQuantity, int kitchenQuantity,
			int parkingPlaceQuantity, int roomQuantity, Owner owner) {
		this.shortDescription = shortDescription;
		this.description = description;
		this.city = city;
		this.ruralHousesImages = new ArrayList<>();
		this.ruralHousesImages = ruralHousesImages;
		this.bathroomQuantity = bathroomQuantity;
		this.bedroomQuantity = bedroomQuantity;
		this.dinningroomQuantity = dinningroomQuantity;
		this.kitchenQuantity = kitchenQuantity;
		this.parkingPlaceQuantity = parkingPlaceQuantity;
		this.roomQuantity = roomQuantity;
		offers = new Vector<Offer>();
		this.owner = owner;
		// this.ruralHouseImages.add(0, ruralHouseImage);
	}

	public List<byte[]> getRuralHouseImages() {
		return this.ruralHousesImages;
	}

	public void setRuralHouseImageIntoImagesList(int index, byte[] ruralHouseImage) {
		this.ruralHousesImages.set(index, ruralHouseImage);

	}

	public int getBathroomQuantity() {
		return bathroomQuantity;
	}

	public void setBathroomQuantity(int bathroomQuantity) {
		this.bathroomQuantity = bathroomQuantity;
	}

	public int getBedroomQuantity() {
		return bedroomQuantity;
	}

	public void setBedroomQuantity(int bedroomQuantity) {
		this.bedroomQuantity = bedroomQuantity;
	}

	public int getDinningroomQuantity() {
		return dinningroomQuantity;
	}

	public void setDinningroomQuantity(int dinningroomQuantity) {
		this.dinningroomQuantity = dinningroomQuantity;
	}

	public int getKitchenQuantity() {
		return kitchenQuantity;
	}

	public void setKitchenQuantity(int kitchenQuantity) {
		this.kitchenQuantity = kitchenQuantity;
	}

	public int getParkingPlaceQuantity() {
		return parkingPlaceQuantity;
	}

	public void setParkingPlaceQuantity(int parkingPlaceQuantity) {
		this.parkingPlaceQuantity = parkingPlaceQuantity;
	}

	public int getRoomQuantity() {
		return roomQuantity;
	}

	public void setRoomQuantity(int roomQuantity) {
		this.roomQuantity = roomQuantity;
	}

	public Integer getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * This method creates an offer with a house number, first day, last day and
	 * price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return None
	 */
	public Offer createOffer(Date firstDay, Date lastDay, float price) {
		System.out.println("LLAMADA RuralHouse createOffer, offerNumber=" + " firstDay=" + firstDay + " lastDay="
				+ lastDay + " price=" + price);
		Offer off = new Offer(firstDay, lastDay, price, this);
		offers.add(off);
		return off;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + houseNumber.hashCode();
		return result;
	}

	@Override
	public String toString() {
		// return this.houseNumber + ": " + this.city;
		return this.city;
	}

	@Override
	public boolean equals(Object obj) {
		RuralHouse other = (RuralHouse) obj;
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		// if (houseNumber != other.houseNumber) // NO COMPARAR AS� ya que
		// houseNumber NO ES "int" sino objeto de "java.lang.Integer"
		if (!houseNumber.equals(other.houseNumber)) {
			return false;
		}
		return true;
	}

	/**
	 * This method obtains available offers for a concrete house in a certain
	 * period
	 * 
	 * @param houseNumber
	 *            , the house number where the offers must be obtained
	 * @param firstDay
	 *            , first day in a period range
	 * @param lastDay
	 *            , last day in a period range
	 * @return a vector of offers(Offer class) available in this period
	 */
	public Vector<Offer> getOffers(Date firstDay, Date lastDay) {

		Vector<Offer> availableOffers = new Vector<Offer>();
		Iterator<Offer> e = offers.iterator();
		Offer offer;
		while (e.hasNext()) {
			offer = e.next();
			if (offer.getFirstDay().compareTo(firstDay) >= 0 && offer.getLastDay().compareTo(lastDay) <= 0) {
				availableOffers.add(offer);
			}
		}
		return availableOffers;

	}

	/**
	 * This method obtains the first offer that overlaps with the provided dates
	 * 
	 * @param firstDay
	 *            , first day in a period range
	 * @param lastDay
	 *            , last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there
	 *         is no overlapping offer
	 */

	public Offer overlapsWith(Date firstDay, Date lastDay) {

		Iterator<Offer> e = offers.iterator();
		Offer offer = null;
		while (e.hasNext()) {
			offer = e.next();
			if (offer.getFirstDay().compareTo(lastDay) < 0 && offer.getLastDay().compareTo(firstDay) > 0) {
				return offer;
			}
		}
		return null;

	}

	public Owner getOwner() {
		return owner;
	}

	public Vector<Offer> getAllOffers() {
		return this.offers;
	}

	public void deleteOffer(Offer offer) {
		this.offers.remove(offer);
	}

	public void bookOffer(Offer offer) {
		this.offers.remove(offer);
		offer.setBooked(true);
		this.offers.addElement(offer);
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Vector<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Vector<Offer> offers) {
		this.offers = offers;
	}

	public void setRuralHouseImages(List<byte[]> ruralHouseImages) {
		this.ruralHousesImages = ruralHouseImages;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public int getRuralHouseNumber() {
		return this.houseNumber;
	}
}
