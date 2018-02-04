package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.Offer;
import domain.User;

public class CancelBookedOfferGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private byte userType;
	private String username;
	private String phoneNumber;
	private Vector<Offer> bookedOffers;

	private JPanel contentPane;

	private DefaultComboBoxModel bookedOffersDefaultComboBoxModel = new DefaultComboBoxModel();
	private JLabel feedbackJLabel;
	private JButton cancelBookedOfferJButton;
	private JButton BackJButton;
	private JComboBox bookedOffersJComboBox;
	private JLabel cancelBookedOfferJLabel;
	private JPanel cancelBookedOfferJPanel;
	private JTextField phoneNumberJTextField;
	private JLabel phoneNumberJLabel;
	private JLabel feedback2JLabel;
	private JButton feedbackMainJButton;
	private JPanel feedbackJPanel;
	private JLabel passwordJLabel;
	private JPasswordField passwordJPasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CancelBookedOfferGUI frame = new CancelBookedOfferGUI(User.USER_TYPE_USER, "Guest");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param string
	 * @param userTypeUser
	 */
	public CancelBookedOfferGUI(byte userTypeUser, String username) {
		setTitle("Cancel booked offer - WSRuralHouse  ");

		this.username = username;
		this.userType = userTypeUser;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 506, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		JPanel userPhoneJPanel = new JPanel();
		userPhoneJPanel.setBounds(0, 0, 490, 128);
		if (userTypeUser != User.USER_TYPE_USER || username != "Guest") {
			userPhoneJPanel.setVisible(false);
		} else {
			setBounds(100, 100, 506, 157);
		}
		contentPane.add(userPhoneJPanel);
		userPhoneJPanel.setLayout(null);

		phoneNumberJTextField = new JTextField();
		phoneNumberJTextField.setColumns(10);
		phoneNumberJTextField.setBounds(216, 21, 186, 20);
		userPhoneJPanel.add(phoneNumberJTextField);

		phoneNumberJLabel = new JLabel("Telephone number:");
		phoneNumberJLabel.setBounds(102, 24, 93, 14);
		userPhoneJPanel.add(phoneNumberJLabel);

		JButton OkJButton = new JButton("OK");
		OkJButton.setBounds(214, 83, 89, 23);
		OkJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (userTypeUser == User.USER_TYPE_USER) {

					phoneNumber = phoneNumberJTextField.getText().toString();
					System.out.println("EL NUMERO INTRODUCIDO ES " + phoneNumber);
					if (facade.userExists(phoneNumber)) {
						User user = facade.getUser(phoneNumber);
						char[] matcherPassword = passwordJPasswordField.getPassword();
						if (user.passwordMatch(matcherPassword)) {
							bookedOffers = facade.getUserBookedOffers(phoneNumber);
							for (Offer bookedOffer : bookedOffers) {

								bookedOffersJComboBox.addItem(bookedOffer);

								System.out.println(bookedOffer);

							}
							if (bookedOffersJComboBox.getItemCount() == 0) {
								userPhoneJPanel.setVisible(false);
								setBounds(100, 100, 506, 332);
								cancelBookedOfferJPanel.setVisible(true);
								cancelBookedOfferJPanel.setEnabled(true);
								bookedOffersJComboBox.addItem("There aren't booked offers availables");
								feedbackJLabel.setText("There aren't booked offers to cancel");
								cancelBookedOfferJButton.setEnabled(false);
								bookedOffersJComboBox.setEnabled(false);
								feedbackJLabel.setVisible(true);
							} else {
								userPhoneJPanel.setVisible(false);
								setBounds(100, 100, 506, 332);
								cancelBookedOfferJPanel.setVisible(true);
								cancelBookedOfferJPanel.setEnabled(true);
							}

						} else {
							feedbackJPanel.setVisible(true);
							userPhoneJPanel.setVisible(false);
							feedback2JLabel.setText("Sorry, your phone number hadn't been found by our database");
							feedback2JLabel.setVisible(true);
						}
					}
				}

			}

		});
		userPhoneJPanel.add(OkJButton);

		JButton phoneBackJButton = new JButton("Back");
		phoneBackJButton.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {
				new MainGUI(userTypeUser, username, null).setVisible(true);
				closeWindow();
			}
		});
		phoneBackJButton.setBounds(313, 83, 89, 23);
		userPhoneJPanel.add(phoneBackJButton);

		passwordJLabel = new JLabel("Password:");
		passwordJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordJLabel.setBounds(102, 55, 93, 14);
		userPhoneJPanel.add(passwordJLabel);

		passwordJPasswordField = new JPasswordField();
		passwordJPasswordField.setBounds(216, 52, 186, 20);
		userPhoneJPanel.add(passwordJPasswordField);

		feedbackJPanel = new JPanel();
		feedbackJPanel.setBounds(0, 0, 490, 128);
		contentPane.add(feedbackJPanel);
		feedbackJPanel.setVisible(false);
		feedbackJPanel.setLayout(null);

		feedback2JLabel = new JLabel("feedbackText");
		feedback2JLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedback2JLabel.setForeground(Color.RED);
		feedback2JLabel.setBounds(85, 35, 320, 14);
		feedbackJPanel.add(feedback2JLabel);

		feedbackMainJButton = new JButton("Main");
		feedbackMainJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();
				MainGUI frame = new MainGUI(User.USER_TYPE_USER, "Guest", null);
				frame.setVisible(true);
			}
		});
		feedbackMainJButton.setBounds(89, 80, 101, 23);
		feedbackJPanel.add(feedbackMainJButton);

		JButton feedbackTryAgainJButton = new JButton("Try Again");
		feedbackTryAgainJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();
				CancelBookedOfferGUI frame = new CancelBookedOfferGUI(User.USER_TYPE_USER, "Guest");
				frame.setVisible(true);
			}
		});
		feedbackTryAgainJButton.setBounds(299, 80, 101, 23);
		feedbackJPanel.add(feedbackTryAgainJButton);

		cancelBookedOfferJPanel = new JPanel();
		cancelBookedOfferJPanel.setBounds(0, 0, 494, 301);
		contentPane.add(cancelBookedOfferJPanel);
		cancelBookedOfferJPanel.setLayout(null);

		cancelBookedOfferJLabel = new JLabel("Cancel Booked Offer");
		cancelBookedOfferJLabel.setBounds(31, 53, 435, 75);
		cancelBookedOfferJPanel.add(cancelBookedOfferJLabel);
		cancelBookedOfferJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));

		bookedOffersJComboBox = new JComboBox();
		bookedOffersJComboBox.setBounds(20, 148, 446, 20);
		cancelBookedOfferJPanel.add(bookedOffersJComboBox);
		bookedOffersJComboBox.setModel(bookedOffersDefaultComboBoxModel);

		cancelBookedOfferJButton = new JButton("Cancel booked offer");
		cancelBookedOfferJButton.setBounds(256, 258, 129, 23);
		cancelBookedOfferJPanel.add(cancelBookedOfferJButton);

		BackJButton = new JButton("Cancel");
		BackJButton.setBounds(395, 258, 89, 23);
		BackJButton.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {

				if (userTypeUser == User.USER_TYPE_CLIENT) {
					closeWindow();
					new MainGUI(userTypeUser, username, phoneNumber).setVisible(true);
				}
				if (userTypeUser == User.USER_TYPE_USER && username != "Guest") {
					closeWindow();
					new MainGUI(userTypeUser, username, phoneNumber).setVisible(true);
				}
				if (userTypeUser == User.USER_TYPE_USER && username == "Guest") {
					closeWindow();
					String phoneNumber = phoneNumberJTextField.getText().toString();
					new MainGUI(userTypeUser, username, phoneNumber).setVisible(true);
				}

			}
		});
		cancelBookedOfferJPanel.add(BackJButton);

		feedbackJLabel = new JLabel("");
		feedbackJLabel.setBounds(141, 188, 216, 14);
		cancelBookedOfferJPanel.add(feedbackJLabel);
		feedbackJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackJLabel.setForeground(Color.RED);
		feedbackJLabel.setVisible(false);

		cancelBookedOfferJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (bookedOffersJComboBox.getSelectedItem().getClass().equals(Offer.class)) {
						Offer bookedOffer = (Offer) bookedOffersJComboBox.getSelectedItem();
						if (userTypeUser == User.USER_TYPE_CLIENT) {
							int offerNumber = bookedOffer.getOfferNumber();
							facade.cancelClientBookedOffer(username, bookedOffer);
						} else if (userTypeUser == User.USER_TYPE_USER && username != "Guest") {
							int offerNumber = bookedOffer.getOfferNumber();
							facade.cancelUserBookedOffer(phoneNumber, offerNumber);
						} else if (userTypeUser == User.USER_TYPE_USER) {
							String phoneNumber = phoneNumberJTextField.getText().toString();
							int offerNumber = bookedOffer.getOfferNumber();
							facade.cancelUserBookedOffer(phoneNumber, offerNumber);
						}

						feedbackJLabel.setText("Booked Offer successfully deleted");
						feedbackJLabel.setVisible(true);
						bookedOffersJComboBox.removeItem(bookedOffer);
						if (bookedOffersJComboBox.getItemCount() == 0) {
							bookedOffersJComboBox.addItem("There aren't booked offers availables");
							bookedOffersJComboBox.setEnabled(false);
							cancelBookedOfferJButton.setEnabled(false);
						}
					}
				} catch (java.lang.NullPointerException crash) {
					bookedOffersJComboBox.addItem("There aren't booked offers availables");
					feedbackJLabel.setText("There aren't booked offers to cancel");
					bookedOffersJComboBox.setEnabled(false);
					feedbackJLabel.setVisible(true);
				}
			}
		});
		if (userTypeUser != User.USER_TYPE_CLIENT) {
			cancelBookedOfferJPanel.setVisible(false);
		}
		if (userTypeUser == User.USER_TYPE_CLIENT) {
			bookedOffers = facade.getClientBookedOffers(username);
			for (Offer bookedOffer : bookedOffers) {

				bookedOffersJComboBox.addItem(bookedOffer);

			}
			if (bookedOffersJComboBox.getItemCount() == 0) {
				bookedOffersJComboBox.addItem("There aren't booked offers availables");
				feedbackJLabel.setText("There aren't booked offers to cancel");
				bookedOffersJComboBox.setEnabled(false);
				cancelBookedOfferJButton.setEnabled(false);
			}
		}
		if (userTypeUser != User.USER_TYPE_USER) {
			phoneNumberJLabel.setVisible(false);
			feedbackJPanel.setVisible(false);
		}
		if (userTypeUser == User.USER_TYPE_USER && username != "Guest") {
			cancelBookedOfferJPanel.setVisible(true);
			this.phoneNumber = username;
			System.out.println("EL NUMERO INTRODUCIDO ES " + phoneNumber);
			if (facade.userExists(phoneNumber)) {

				bookedOffers = facade.getUserBookedOffers(phoneNumber);
				for (Offer bookedOffer : bookedOffers) {

					bookedOffersJComboBox.addItem(bookedOffer);

					System.out.println(bookedOffer);

				}
				if (bookedOffersJComboBox.getItemCount() == 0) {
					userPhoneJPanel.setVisible(false);
					setBounds(100, 100, 506, 332);
					cancelBookedOfferJPanel.setVisible(true);
					cancelBookedOfferJPanel.setEnabled(true);
					bookedOffersJComboBox.addItem("There aren't booked offers availables");
					feedbackJLabel.setText("There aren't booked offers to cancel");
					cancelBookedOfferJButton.setEnabled(false);
					bookedOffersJComboBox.setEnabled(false);
					feedbackJLabel.setVisible(true);
				} else {

					setBounds(100, 100, 506, 332);
					cancelBookedOfferJPanel.setVisible(true);
					cancelBookedOfferJPanel.setEnabled(true);
				}

			}

		}

	}

	private void closeWindow() {
		this.setVisible(false);
	}

	private void redimension(JPanel panel) {
		this.setBounds(panel.getBounds());
		setLocationRelativeTo(null);
	}
}