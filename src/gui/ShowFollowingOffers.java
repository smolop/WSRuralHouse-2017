package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.Admin;
import domain.Client;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.User;

public class ShowFollowingOffers extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordJPasswordField;
	private JLabel titleJLabel;
	private JScrollPane scroll;
	private JTextArea outputJTextArea;
	private JButton cancelJButton;
	private JTextField telephoneNumberJTextField;
	private JLabel feedbackJLabel;
	private JLabel TitleJLabel;
	private JLabel passwordJLabel;
	private JButton backJButton;
	private JButton okJButton;
	private JLabel telephoneNumberJLabel;
	private JPanel followingOffersJPanel = new JPanel();
	private JPanel userLogInJPanel = new JPanel();

	private String username;
	private byte userType;
	private String phoneNumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ShowFollowingOffers frame = new ShowFollowingOffers(User.USER_TYPE_USER, "Guest", null);
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
	public ShowFollowingOffers(byte userType, final String username, String phoneNumber) {
		setResizable(false);
		this.username = username;
		this.userType = userType;
		this.phoneNumber = phoneNumber;
		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		User user;
		Client client;
		Owner owner;
		Admin admin;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 609, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		followingOffersJPanel.setBounds(0, 0, 593, 561);
		contentPane.add(followingOffersJPanel);
		followingOffersJPanel.setLayout(null);

		titleJLabel = new JLabel("Your Following Offers");
		titleJLabel.setBounds(49, 51, 494, 58);
		followingOffersJPanel.add(titleJLabel);
		titleJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		outputJTextArea = new JTextArea();
		outputJTextArea.setFont(new Font("Monospaced", Font.PLAIN, 21));
		outputJTextArea.setEditable(false);

		scroll = new JScrollPane(outputJTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(10, 178, 573, 335);
		followingOffersJPanel.add(scroll);

		scroll.setViewportView(outputJTextArea);

		cancelJButton = new JButton("Cancel");
		cancelJButton.setBounds(494, 527, 89, 23);
		followingOffersJPanel.add(cancelJButton);
		cancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MainGUI(userType, getUsername(), phoneNumber).setVisible(true);
				closeWindow();
			}
		});

		userLogInJPanel.setBounds(0, 0, 321, 198);
		contentPane.add(userLogInJPanel);
		userLogInJPanel.setLayout(null);

		telephoneNumberJTextField = new JTextField();
		telephoneNumberJTextField.setColumns(10);
		telephoneNumberJTextField.setBounds(124, 102, 186, 20);
		userLogInJPanel.add(telephoneNumberJTextField);

		telephoneNumberJLabel = new JLabel("Telephone number:");
		telephoneNumberJLabel.setBounds(10, 105, 93, 14);
		userLogInJPanel.add(telephoneNumberJLabel);

		okJButton = new JButton("OK");
		okJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String phoneNumber;
				char[] password;

				phoneNumber = telephoneNumberJTextField.getText().toString();
				password = passwordJPasswordField.getPassword();
				if (facade.userExists(phoneNumber)) {
					User user = facade.getUser(phoneNumber);
					if (user.checkPassword(password)) {
						setUsername(phoneNumber);
						// showFollowingOffers(user.getFollowedOffers());
						followingOffersJPanel.setVisible(true);
						userLogInJPanel.setVisible(false);
						redimension(followingOffersJPanel);
					} else {
						feedbackJLabel.setText("Error: user or password incorrect");
					}
				} else {
					feedbackJLabel.setText("Error: user or password incorrect");
				}

			}
		});
		okJButton.setBounds(122, 164, 89, 23);
		userLogInJPanel.add(okJButton);

		backJButton = new JButton("Back");
		backJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MainGUI(userType, getUsername(), phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		backJButton.setBounds(221, 164, 89, 23);
		userLogInJPanel.add(backJButton);

		passwordJLabel = new JLabel("Password:");
		passwordJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordJLabel.setBounds(10, 136, 93, 14);
		userLogInJPanel.add(passwordJLabel);

		passwordJPasswordField = new JPasswordField();
		passwordJPasswordField.setBounds(124, 133, 186, 20);
		userLogInJPanel.add(passwordJPasswordField);

		TitleJLabel = new JLabel("Log In");
		TitleJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TitleJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		TitleJLabel.setBounds(0, 0, 321, 58);
		userLogInJPanel.add(TitleJLabel);

		feedbackJLabel = new JLabel("");
		feedbackJLabel.setForeground(Color.RED);
		feedbackJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackJLabel.setBounds(10, 80, 300, 14);
		userLogInJPanel.add(feedbackJLabel);

		// Comprueba si es user y se llama Guest
		if (this.userType == User.USER_TYPE_USER && phoneNumber == null) {
			followingOffersJPanel.setVisible(false);
			redimension(userLogInJPanel);
		} else {
			userLogInJPanel.setVisible(false);
			redimension(followingOffersJPanel);
		}
		Vector<Offer> followingOffers;
		if (userType != User.USER_TYPE_USER) {
			followingOffers = facade.getFollowingOffers(username, userType);
			showFollowingOffers(followingOffers);
		} else {
			followingOffers = facade.getFollowingOffers(phoneNumber, userType);
		}
	}

	private void closeWindow() {
		this.setVisible(false);
	}

	private void redimension(JPanel panel) {
		this.setSize(panel.getWidth() + 20, panel.getHeight() + 35);
		setLocationRelativeTo(null);
	}

	private void setUsername(String username) {
		this.username = username;
	}

	private String getUsername() {
		return this.username;
	}

	private void showFollowingOffers(Vector<Offer> followingOffers) {
		int yPos = 0;
		for (Offer followingOffer : followingOffers) {
			// / Empieza el mostrador
			RuralHouse ruralHouse = followingOffer.getRuralHouse();
			JButton newResult = new JButton();
			newResult.setBounds(0, yPos, 552, 128);
			outputJTextArea.add(newResult);

			JLabel newRuralHouseImageJLabel = new JLabel("");
			InputStream in = null;
			BufferedImage img = null;
			in = new ByteArrayInputStream(ruralHouse.getRuralHouseImages().get(0));
			try {
				img = ImageIO.read(in);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			newRuralHouseImageJLabel.setIcon(new ImageIcon(img.getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
			newRuralHouseImageJLabel.setBounds(428, 193, 256, 256);
			newResult.add(newRuralHouseImageJLabel);

			newResult.setIconTextGap(30);
			newResult.setVerticalAlignment(SwingConstants.TOP);
			newResult.setHorizontalAlignment(SwingConstants.TRAILING);

			StringBuilder sb = new StringBuilder();
			sb.append("<html>\n");
			sb.append("<body>\n");
			sb.append("<div align=\"right\">");
			// sb.append("<b>Rural House ID:</b> " +
			// filteredRuralHouses.get(i).getHouseNumber() + "<br><br>");
			sb.append("<b>Province:</b> " + ruralHouse.getCity() + "<br>");
			sb.append("<b>Description:</b><br><p style=\"width:250px;\">" + ruralHouse.getDescription() + "</p><br>");
			// sb.append("<b>Bathrooms:</b> " +
			// filteredRuralHouses.get(i).getBathroomQuantity() + "<br>");
			// sb.append("<b>Bedrooms:</b> " +
			// filteredRuralHouses.get(i).getBedroomQuantity() + "<br>");
			// sb.append(
			// "<b>Dinningrooms:</b> " +
			// filteredRuralHouses.get(i).getDinningroomQuantity() + "<br>");
			// sb.append("<b>Kitchens:</b> " +
			// filteredRuralHouses.get(i).getKitchenQuantity() + "<br>");
			// sb.append("<b>Parking places:</b> " +
			// filteredRuralHouses.get(i).getParkingPlaceQuantity()
			// + "<br>");
			// sb.append("<b>Rooms:</b> " +
			// filteredRuralHouses.get(i).getRoomQuantity() + "<br><br>");
			// sb.append("<b>Offer ID:</b> " +
			// filteredOffers.get(j).getOfferNumber() + "<br>");
			sb.append("<b>First day:</b> " + followingOffer.getFirstDay().toString() + "<br>");
			sb.append("<b>Last day:</b> " + followingOffer.getLastDay().toString() + "<br>");
			sb.append("<b>Price:</b> " + followingOffer.getPrice() + "€");
			sb.append("</div>\n");
			sb.append("</body>\n");
			sb.append("</html>");
			String phonenumber = null;
			newResult.setText(sb.toString());
			newResult.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new SeeOfferDetailsGUI(userType, username, phonenumber, followingOffer.getOfferNumber(),
							false);
					a.setVisible(true);

					closeWindow();
				}
			});

			yPos += 128;

		}
		outputJTextArea.setPreferredSize(new Dimension(500, followingOffers.size() * 128));
	}
}
