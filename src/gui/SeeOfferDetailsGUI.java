package gui;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.FacadeImplementationWS;
import domain.Offer;
import domain.User;

public class SeeOfferDetailsGUI extends JFrame {

	private JPanel contentPane;
	private byte userType;
	private String username;
	private String phoneNumber;
	private Boolean isAdmin = false;
	protected int offerNumber;
	private JPanel logInContentPane;
	private JTextField phoneNumberJTextField;
	private JPasswordField passwordJPassword;

	ByteArrayInputStream inputStream;
	BufferedImage img;
	Image imgRuralHouse;
	Image imgOwner;
	Integer index;
	private JLabel ownerUsernameJLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					// SeeOfferDetailsGUI frame = new
					// SeeOfferDetailsGUI(User.USER_TYPE_CLIENT, "Yowner", null,
					// 369,
					// false);
					SeeOfferDetailsGUI frame = new SeeOfferDetailsGUI(User.USER_TYPE_USER, "Guest", null, 361, false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SeeOfferDetailsGUI(byte userType, String username, String phoneNumber, int offerNumber, boolean Admin) {
		setTitle("WSRuralHouse - SeeOfferDetails");

		this.username = username;
		this.userType = userType;
		this.phoneNumber = phoneNumber;
		this.offerNumber = offerNumber;
		this.isAdmin = isAdmin;

		FacadeImplementationWS facade = new FacadeImplementationWS();

		Offer offer = facade.getOffer(offerNumber);
		List<byte[]> imgList = offer.getRuralHouse().getRuralHouseImages();
		index = 0;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 942, 561);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel bathroomJLabel = new JLabel("Bathrooms:");
		bathroomJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		bathroomJLabel.setBounds(64, 374, 64, 14);
		contentPane.add(bathroomJLabel);

		JLabel bedroomsJLabel = new JLabel("Bedrooms:");
		bedroomsJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		bedroomsJLabel.setBounds(64, 399, 64, 14);
		contentPane.add(bedroomsJLabel);

		JLabel kitchenJLabel = new JLabel("Kitchen:");
		kitchenJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		kitchenJLabel.setBounds(64, 424, 64, 14);
		contentPane.add(kitchenJLabel);

		JLabel dinnigRoomsJLabel = new JLabel("Dinnig rooms:");
		dinnigRoomsJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dinnigRoomsJLabel.setBounds(183, 374, 97, 14);
		contentPane.add(dinnigRoomsJLabel);

		JLabel parkingPlacesJLabel = new JLabel("Parking places:");
		parkingPlacesJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		parkingPlacesJLabel.setBounds(183, 399, 97, 14);
		contentPane.add(parkingPlacesJLabel);

		JLabel roomsJLabel = new JLabel("Rooms:");
		roomsJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		roomsJLabel.setBounds(234, 424, 46, 14);
		contentPane.add(roomsJLabel);

		JLabel cityJLabel = new JLabel("City:");
		cityJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cityJLabel.setBounds(32, 70, 97, 14);
		contentPane.add(cityJLabel);

		JButton bookOfferJButton = new JButton("Book offer");

		switch (userType) {
		case User.USER_TYPE_USER:
			if (username != "Guest") {
				Vector<Offer> bookedOffers = facade.getUserBookedOffers(phoneNumber);
				for (Offer bookedoffer : bookedOffers) {
					if (bookedoffer.getOfferNumber() == offerNumber) {
						bookOfferJButton.setText("Booked");
						bookOfferJButton.setEnabled(false);
						break;
					}
				}
			}
			break;
		case User.USER_TYPE_CLIENT:
			Vector<Offer> bookedOffers = facade.getClientBookedOffers(username);
			for (Offer bookedOffer : bookedOffers) {
				if (bookedOffer.getOfferNumber() == offerNumber) {
					bookOfferJButton.setText("Booked");
					bookOfferJButton.setEnabled(false);
					break;
				}
			}
			break;
		}

		bookOfferJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (userType) {
				case User.USER_TYPE_USER:
					if (phoneNumber != null) {
						facade.userBookOffer(phoneNumber, offerNumber);
						bookOfferJButton.setEnabled(false);
					} else {
						UserLoginGUI frame = new UserLoginGUI(userType, username, offerNumber, "SeeOfferDetails");
						frame.setVisible(true);
						if (!frame.isActive()) {
							closeWindow();
						}
					}
					break;
				case User.USER_TYPE_CLIENT:
					facade.clientBookOffer(username, offerNumber);
					bookOfferJButton.setEnabled(false);
					break;
				}
			}
		});
		bookOfferJButton.setBounds(565, 343, 89, 23);
		if (userType == User.USER_TYPE_ADMIN || userType == User.USER_TYPE_OWNER) {
			bookOfferJButton.setVisible(false);
		}
		if (offer.isBooked()) {
			bookOfferJButton.setText("Booked");
			bookOfferJButton.setEnabled(false);
		}
		contentPane.add(bookOfferJButton);

		JButton followJButton = new JButton("Follow ");

		switch (userType) {
		case User.USER_TYPE_USER:
			followJButton.setVisible(false);
			break;
		case User.USER_TYPE_CLIENT:
			if (facade.isFollowingOffer(username, userType, offerNumber)) {
				followJButton.setText("Unfollow");
			} else {
				followJButton.setText("Follow");
			}
			break;
		case User.USER_TYPE_OWNER:
			if (facade.isFollowingOffer(username, userType, offerNumber)) {
				followJButton.setText("Unfollow");
			} else {
				followJButton.setText("Follow");
			}
			break;
		case User.USER_TYPE_ADMIN:
			if (facade.isFollowingOffer(username, userType, offerNumber)) {
				followJButton.setText("Unfollow");
			} else {
				followJButton.setText("Follow");
			}
			break;
		}

		followJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switch (userType) {
				case User.USER_TYPE_USER:
					if (followJButton.getText().equals("Follow")) {
						followJButton.setText("Unfollow");
						facade.followOffer(phoneNumber, userType, offerNumber);
					} else {
						followJButton.setText("Follow");
						facade.unfollowOffer(phoneNumber, userType, offerNumber);
					}
					break;
				case User.USER_TYPE_CLIENT:
					if (!facade.isFollowingOffer(username, userType, offerNumber)) {
						followJButton.setText("Unfollow");
						facade.followOffer(username, userType, offerNumber);
					} else {
						followJButton.setText("Follow");
						facade.unfollowOffer(username, userType, offerNumber);
					}
					break;
				case User.USER_TYPE_OWNER:
					if (!facade.isFollowingOffer(username, userType, offerNumber)) {
						followJButton.setText("Unfollow");
						facade.followOffer(username, userType, offerNumber);
					} else {
						followJButton.setText("Follow");
						facade.unfollowOffer(username, userType, offerNumber);
					}
					break;
				case User.USER_TYPE_ADMIN:
					if (!facade.isFollowingOffer(username, userType, offerNumber)) {
						followJButton.setText("Unfollow");
						facade.followOffer(username, userType, offerNumber);
					} else {
						followJButton.setText("Follow");
						facade.unfollowOffer(username, userType, offerNumber);
					}
					break;
				}
			}
		});
		followJButton.setBounds(466, 343, 89, 23);
		contentPane.add(followJButton);

		JButton chatJButton = new JButton("Chat");
		chatJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int conversationNumber = facade.getConversationNumber(userType, username,
						offer.getRuralHouse().getOwner().getUsername());

				if (conversationNumber == -1) {
					int generatedConversationNumber = facade.startConversation(userType, username,
							offer.getRuralHouse().getOwner().getUsername());
					new ChatGUI(userType, username, phoneNumber, generatedConversationNumber).setVisible(true);
					closeWindow();
				} else {
					new ChatGUI(userType, username, phoneNumber, conversationNumber).setVisible(true);
					closeWindow();
				}
			}
		});
		chatJButton.setBounds(826, 145, 89, 23);
		if (userType == User.USER_TYPE_USER) {
			chatJButton.setVisible(false);
		}
		if (userType == User.USER_TYPE_OWNER && username.equals(offer.getRuralHouse().getOwner().getUsername())) {
			chatJButton.setVisible(false);
		}
		contentPane.add(chatJButton);

		JLabel quantityOfBathroomsJLabel = new JLabel("QBathrooms");
		quantityOfBathroomsJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		quantityOfBathroomsJLabel.setBounds(138, 374, 46, 14);
		quantityOfBathroomsJLabel.setText(String.valueOf(offer.getRuralHouse().getBathroomQuantity()));
		contentPane.add(quantityOfBathroomsJLabel);

		JLabel quantityOfBedroomsJLabel = new JLabel("QBedrooms");
		quantityOfBedroomsJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		quantityOfBedroomsJLabel.setBounds(138, 399, 46, 14);
		quantityOfBedroomsJLabel.setText(String.valueOf(offer.getRuralHouse().getBedroomQuantity()));
		contentPane.add(quantityOfBedroomsJLabel);

		JLabel quantityKitchensJLabel = new JLabel("QKitchens");
		quantityKitchensJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		quantityKitchensJLabel.setBounds(138, 424, 46, 14);
		quantityKitchensJLabel.setText(String.valueOf(offer.getRuralHouse().getKitchenQuantity()));
		contentPane.add(quantityKitchensJLabel);

		JLabel quantityDinningRoomsJLabel = new JLabel("QDinningRooms");
		quantityDinningRoomsJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		quantityDinningRoomsJLabel.setBounds(290, 374, 46, 14);
		quantityDinningRoomsJLabel.setText(String.valueOf(offer.getRuralHouse().getDinningroomQuantity()));
		contentPane.add(quantityDinningRoomsJLabel);

		JLabel quantityParkingPlacesJLabel = new JLabel("QParkingPlaces");
		quantityParkingPlacesJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		quantityParkingPlacesJLabel.setBounds(290, 399, 46, 14);
		quantityParkingPlacesJLabel.setText(String.valueOf(offer.getRuralHouse().getParkingPlaceQuantity()));
		contentPane.add(quantityParkingPlacesJLabel);

		JLabel quantityRoomsJLabel = new JLabel("QRooms");
		quantityRoomsJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		quantityRoomsJLabel.setBounds(290, 424, 46, 14);
		quantityRoomsJLabel.setText(String.valueOf(offer.getRuralHouse().getRoomQuantity()));
		contentPane.add(quantityRoomsJLabel);

		JLabel whatCityJLabel = new JLabel("whatCity");
		whatCityJLabel.setBounds(139, 70, 187, 14);
		whatCityJLabel.setText(offer.getRuralHouse().getCity());
		contentPane.add(whatCityJLabel);

		JPanel imagesListJPanel = new JPanel();
		imagesListJPanel.setBounds(335, 32, 450, 300);
		contentPane.add(imagesListJPanel);
		imagesListJPanel.setLayout(null);

		JButton indexJButton = new JButton("|i/N|");
		indexJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		indexJButton.setBounds(0, 277, 450, 23);
		indexJButton.setEnabled(false);
		indexJButton.setText("1/" + imgList.size());

		imagesListJPanel.add(indexJButton);

		JLabel imagesListJLabel = new JLabel(" Images List");
		imagesListJLabel.setBounds(71, 0, 312, 277);
		imagesListJLabel.setText("");
		if (!offer.getRuralHouse().getRuralHouseImages().isEmpty()) {
			InputStream ruralHousesInputStream = new ByteArrayInputStream(imgList.get(0));
			indexJButton.setText(index + 1 + "/" + imgList.size());

			try {
				imgRuralHouse = ImageIO.read(ruralHousesInputStream);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error", "Data charging problems", JOptionPane.ERROR_MESSAGE);
				ex.getMessage();
			}
			imagesListJLabel.setIcon(new ImageIcon(imgRuralHouse.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
		} else {
			InputStream ruralHouseInputStream = new ByteArrayInputStream(
					offer.getRuralHouse().getRuralHouseImages().get(0));
			indexJButton.setText("1/1");
			try {
				imgRuralHouse = ImageIO.read(ruralHouseInputStream);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error", "Data charging problems", JOptionPane.ERROR_MESSAGE);
				e.getMessage();
			}
		}
		imagesListJLabel.setIcon(new ImageIcon(imgRuralHouse.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
		imagesListJPanel.add(imagesListJLabel);
		imagesListJLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JButton btnPreview = new JButton("<");
		btnPreview.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnPreview.setVerifyInputWhenFocusTarget(false);
		btnPreview.setBounds(0, 0, 71, 277);
		btnPreview.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (index == 0) {
					index = imgList.size() - 1;
				} else {
					--index;
				}
				InputStream ruralHousesInputStream = new ByteArrayInputStream(imgList.get(index));
				indexJButton.setText(index + 1 + "/" + imgList.size());

				try {
					imgRuralHouse = ImageIO.read(ruralHousesInputStream);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error", "Data charging problems", JOptionPane.ERROR_MESSAGE);
					ex.getMessage();
				}
				imagesListJLabel.setIcon(new ImageIcon(imgRuralHouse.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));

			}
		});
		btnPreview.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imagesListJPanel.add(btnPreview);

		JButton btnNext = new JButton(">");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnNext.setBounds(383, 0, 67, 277);
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				InputStream ruralHousesInputStream;
				if (index == imgList.size() - 1) {
					index = 0;
					ruralHousesInputStream = new ByteArrayInputStream(imgList.get(index));
				} else {
					ruralHousesInputStream = new ByteArrayInputStream(imgList.get(++index));
				}
				indexJButton.setText(index + 1 + "/" + imgList.size());
				try {
					imgRuralHouse = ImageIO.read(ruralHousesInputStream);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error", "Data charging problems", JOptionPane.ERROR_MESSAGE);
					ex.getMessage();
				}
				imagesListJLabel.setIcon(new ImageIcon(imgRuralHouse.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
			}
		});
		btnNext.setActionCommand("  Next  ");
		btnNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imagesListJPanel.add(btnNext);

		JTextPane descriptionJPane = new JTextPane();
		descriptionJPane.setEditable(false);
		descriptionJPane.setText("Description:");
		descriptionJPane.setBounds(369, 396, 447, 114);
		descriptionJPane.setText(offer.getRuralHouse().getDescription());
		contentPane.add(descriptionJPane);

		JTextPane shortDescriptionJPane = new JTextPane();
		shortDescriptionJPane.setEditable(false);
		shortDescriptionJPane.setText("Short Description:");
		shortDescriptionJPane.setBounds(32, 98, 288, 83);
		shortDescriptionJPane.setText(offer.getRuralHouse().getShortDescription());
		contentPane.add(shortDescriptionJPane);

		JLabel dataJLabel = new JLabel("Fecha:");
		dataJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataJLabel.setBounds(32, 248, 97, 14);
		contentPane.add(dataJLabel);

		JLabel priceJLabel = new JLabel("Precio:");
		priceJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		priceJLabel.setBounds(32, 318, 97, 14);
		contentPane.add(priceJLabel);

		JLabel ownerImgJLabel = new JLabel("Owner Img");
		ownerImgJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ownerImgJLabel.setBounds(826, 53, 89, 83);
		InputStream ownerInputStream = new ByteArrayInputStream(offer.getRuralHouse().getOwner().getProfileImage());
		try {
			imgOwner = ImageIO.read(ownerInputStream);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error", "Data charging problems", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		ownerImgJLabel.setText("");
		ownerImgJLabel.setIcon(new ImageIcon(imgOwner.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
		contentPane.add(ownerImgJLabel);

		JLabel startDateJLabel = new JLabel("startDate");
		startDateJLabel.setHorizontalAlignment(SwingConstants.LEFT);
		startDateJLabel.setBounds(139, 273, 173, 14);
		startDateJLabel.setText(offer.getFirstDay().toString());
		contentPane.add(startDateJLabel);

		JLabel endDateJLabel = new JLabel("endDate");
		endDateJLabel.setHorizontalAlignment(SwingConstants.LEFT);
		endDateJLabel.setBounds(139, 293, 173, 14);
		endDateJLabel.setText(offer.getLastDay().toString());
		contentPane.add(endDateJLabel);

		JLabel howManyJLabel = new JLabel("howMany");
		howManyJLabel.setHorizontalAlignment(SwingConstants.LEFT);
		howManyJLabel.setBounds(139, 318, 173, 14);
		BigDecimal finalPrice = new BigDecimal(offer.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
		howManyJLabel.setText(finalPrice.toString());

		contentPane.add(howManyJLabel);

		JLabel fromJLabel = new JLabel("From:");
		fromJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		fromJLabel.setBounds(83, 273, 46, 14);
		contentPane.add(fromJLabel);

		JLabel toJLabel = new JLabel("To:");
		toJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		toJLabel.setBounds(83, 293, 46, 14);
		contentPane.add(toJLabel);

		JButton cancelJButton = new JButton("Cancel");
		cancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MainGUI(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		cancelJButton.setBounds(826, 487, 89, 23);
		contentPane.add(cancelJButton);

		ownerUsernameJLabel = new JLabel(offer.getRuralHouse().getOwner().getUsername());
		ownerUsernameJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ownerUsernameJLabel.setBounds(826, 32, 89, 14);
		contentPane.add(ownerUsernameJLabel);

	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
