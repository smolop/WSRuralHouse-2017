package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.Client;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.User;

public class EditAnythingEditElementGUI extends JFrame {
	private byte userType;
	private String username;
	private String elementClass;
	private List<Object> elements;
	private String elementID;

	private JPanel editUserJPanel;
	private JPanel editClientJPanel;
	private JPanel editOwnerJPanel;
	private JPanel editOfferJPanel;
	private JPanel editRuralHouseJPanel;
	private JTextField userPhoneNumberJTextField;
	private JLabel editAnythingJLabel;
	private JLabel userPhoneNumberJLabel;
	private JLabel userNewPasswordJLabel;
	private JLabel userConfirmPasswordJLabel;
	private JButton userBookedOffersJButton;
	private JButton cancelJButton;
	private JTextField clientUsernameJTextField;
	private JTextField clientNameJTextField;
	private JTextField clientLastNameJTextField;
	private JTextField clientDNIJTextField;
	private JTextField clientEmailJTextField;
	private JTextField clientPhoneNumberJTextField;
	private JTextField clientAddressJTextField;
	private JTextField clientProfileImageJTextField;
	private JLabel clientEditAnythingJLabel;
	private JLabel clientNumberJLabel;
	private JLabel clientUsernameJLabel;
	private JLabel clientNewPasswordJLabel;
	private JLabel clientNameJLabel;
	private JLabel clientLastNameJLabel;
	private JLabel clientDNIJLabel;
	private JLabel clientEmailJLabel;
	private JLabel clientPhoneNumberJLabel;
	private JLabel clientAddressJLabel;
	private JLabel clientProfileImageJLabel;
	private JButton clientBrowseJButton;
	private JLabel clientProfileImageViewJLabel;
	private JButton clientBookedOffersJButton;
	private JButton clientFollowedOffersJButton;
	private JButton clientCancelJButton;
	private JTextField ownerUsernameJTextField;
	private JTextField ownerNameJTextField;
	private JTextField ownerPhoneNumberJTextField;
	private JTextField ownerLastNameJTextField;
	private JTextField ownerEmailJTextField;
	private JTextField ownerDNIJTextField;
	private JTextField ownerAddressJTextField;
	private JTextField ownerBankAccountJTextField;
	private JTextField ownerProfileImageJTextField;
	private JLabel ownerNumberJLabel;
	private JLabel ownerUsernameJLabel;
	private JLabel ownerNewPasswordJLabel;
	private JLabel ownerConfirmPasswordJLabel;
	private JLabel ownerNameJLabel;
	private JLabel ownerLastNameJLabel;
	private JLabel ownerDNIJLabel;
	private JLabel ownerEmailJLabel;
	private JLabel ownerPhoneNumberJLabel;
	private JLabel ownerAddressJLabel;
	private JLabel ownerBankAccountJLabel;
	private JButton ownerRuralHousesJButton;
	private JButton ownerFollowedOffersJButton;
	private JLabel ownerProfileImageJLabel;
	private JButton ownerBrowseJButton;
	private JLabel ownerProfileImageViewJLabel;
	private JButton ownerCancelJButton;
	private JLabel ownerEditAnythingJLabel;
	private JTextField offerFirstDayJTextField;
	private JTextField offerLastDayJTextField;
	private JTextField offerPriceJTextField;
	private JButton offerFollowingClientsJButton;
	private JButton offerFollowingOwnersJButton;
	private JButton offerRuralHouseJButton;
	private JButton offerCancelJButton;
	private JLabel offerFirstDayJLabel;
	private JLabel offerNumberJLabel;
	private JLabel offerEditAnythingJLabel;
	private JLabel offerLastDayJLabel;
	private JLabel offerPriceJLabel;
	private JTextField ruralHouseBathroomsJTextField;
	private JTextField ruralHouseBedroomsJTextField;
	private JTextField ruralHouseDinningroomsJTextField;
	private JTextField ruralHouseKitchensJTextField;
	private JTextField ruralHouseParkingPlacesJTextField;
	private JTextField ruralHouseRoomsJTextField;
	private JLabel ruralHouseCityJLabel;
	private JLabel ruralHouseNumber;
	private JLabel ruralHouseEditAnythingJLabel;
	private JLabel ruralHouseShortDescriptionJLabel;
	private JLabel ruralHouseDescriptionJLabel;
	private JLabel ruralHouseBathroomsJLabel;
	private JLabel ruraHouseBedroomsJLabel;
	private JLabel ruralHouseDinningroomsJLabel;
	private JLabel ruralHouseKitchensJLabel;
	private JLabel ruralHouseParkingPlacesJLabel;
	private JLabel ruralHouseRoomsJLabel;
	private JLabel ruralHouseImageJLabel;
	private JButton ruralHouseBrowseJButton;
	private JButton ruralHouseCancelJButton;
	private BufferedImage img;
	private InputStream in;
	private JButton clientUpdateJButton;
	private JButton ownerUpdateJButton;
	private JPasswordField clientNewPasswordJPasswordField;
	private JPasswordField clientConfirmPasswordJPasswordField;
	private JPasswordField ownerNewPasswordJPasswordField;
	private JPasswordField ownerConfirmPasswordJPasswordField;
	private JButton ruralHouseUpdateJButton;
	private JLabel userUserJLabel;
	private JLabel userFeedbackJLabel;
	private JLabel ownerOwnerJLabel;
	private JLabel ownerFeedbackJLabel;
	private JLabel offerOfferJLabel;
	private JLabel offerFeedback;
	private JLabel ruralHouseRuralHouseJLabel;
	private JLabel ruralHouseFeedbackJLabel;
	private JButton btnUpdate;
	private JPasswordField userNewPasswordJPasswordField;
	private JPasswordField userConfirmPasswordJPasswordField;
	private JLabel clientFeedbackJLabel;
	private JXDatePicker firstDayJXDatePicker;
	private JXDatePicker lastDayJXDatePicker;
	private JButton offerUpdateJButton;
	private JComboBox citiesJComboBox;
	private List<String> paths;
	private JButton imageView1JButton;
	private JButton imageView2JButton;
	private JButton imageView3JButton;
	private JButton imageView4JButton;
	private JButton imageView5JButton;
	private JButton imageView6JButton;
	private JLabel imageView1JLabel;
	private JLabel imageView2JLabel;
	private JLabel imageView3JLabel;
	private JLabel imageView4JLabel;
	private JLabel imageView5JLabel;
	private JLabel imageView6JLabel;
	private int nextImageIndex = 0;
	private List<JLabel> imageViewJLabelList;
	private List<JButton> imageViewJButtonList;
	private File defaultRuralHouseImageFile = new File(".\\resources\\rural_house.png");
	private List<byte[]> ruralHouseImagesInBytes = new ArrayList<>();
	private JEditorPane ruralHouseDescriptionJEditorPanel;
	private JEditorPane ruralHouseShortDescriptionJEditorPanel;
	private JButton ruralHouseOwnerJButton;
	private JButton ruralHouseOffersJButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					EditAnythingEditElementGUI frame = new EditAnythingEditElementGUI(User.USER_TYPE_USER, "Guest",
							null, null, "");
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
	public EditAnythingEditElementGUI(byte userType, String username, String elementClass, List<Object> elements,
			String elementID) {
		this.userType = userType;
		this.username = username;
		this.elementClass = elementClass;
		this.elements = elements;
		this.elementID = elementID;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		editUserJPanel = new JPanel();
		editUserJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		editClientJPanel = new JPanel();
		editClientJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		editOwnerJPanel = new JPanel();
		editOwnerJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		editOfferJPanel = new JPanel();
		editOfferJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		editRuralHouseJPanel = new JPanel();
		editRuralHouseJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		switch (elementClass) {
		case "User":
			showUserGUI();
			break;
		case "Client":
			showClientGUI();
			break;
		case "Owner":
			showOwnerGUI();
			break;
		case "Offer":
			showOfferGUI();
			break;
		case "RuralHouse":
			showRuralHouseGUI();
			break;
		}

		// showUserGUI();
		// showClientGUI();
		// showOwnerGUI();
		// showOfferGUI();
		// showRuralHouseGUI();
	}

	public void showUserGUI() {
		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		User user = facade.getUser(elementID);

		setBounds(100, 100, 452, 403);

		setContentPane(editUserJPanel);
		editUserJPanel.setLayout(null);
		editUserJPanel.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		editAnythingJLabel = new JLabel("Edit Anything");
		editAnythingJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		editAnythingJLabel.setBounds(76, 45, 284, 58);
		editUserJPanel.add(editAnythingJLabel);

		userPhoneNumberJLabel = new JLabel("Phone number:");
		userPhoneNumberJLabel.setBounds(45, 210, 73, 14);
		editUserJPanel.add(userPhoneNumberJLabel);

		userNewPasswordJLabel = new JLabel("New password:");
		userNewPasswordJLabel.setBounds(44, 241, 74, 14);
		editUserJPanel.add(userNewPasswordJLabel);

		userConfirmPasswordJLabel = new JLabel("Confirm password:");
		userConfirmPasswordJLabel.setBounds(28, 272, 90, 14);
		editUserJPanel.add(userConfirmPasswordJLabel);

		userPhoneNumberJTextField = new JTextField(user.getPhoneNumber());
		userPhoneNumberJTextField.setEditable(false);
		userPhoneNumberJTextField.setBounds(128, 207, 86, 20);
		editUserJPanel.add(userPhoneNumberJTextField);
		userPhoneNumberJTextField.setColumns(10);

		userBookedOffersJButton = new JButton("Booked offers");
		userBookedOffersJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new EditAnythingShowElementsGUI(userType, username, "Offer",
						Arrays.asList(user.getBookedOffers().toArray())).setVisible(true);
				closeWindow();
			}
		});
		userBookedOffersJButton.setBounds(250, 206, 99, 23);
		editUserJPanel.add(userBookedOffersJButton);

		cancelJButton = new JButton("Cancel");
		cancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new EditAnythingShowEntitiesGUI(userType, username).setVisible(true);
				closeWindow();
			}
		});
		cancelJButton.setBounds(335, 330, 89, 23);
		editUserJPanel.add(cancelJButton);

		userUserJLabel = new JLabel("User");
		userUserJLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userUserJLabel.setBounds(33, 148, 369, 20);
		editUserJPanel.add(userUserJLabel);

		userFeedbackJLabel = new JLabel("");
		userFeedbackJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userFeedbackJLabel.setForeground(Color.RED);
		userFeedbackJLabel.setBounds(10, 334, 216, 14);
		userFeedbackJLabel.setVisible(false);
		editUserJPanel.add(userFeedbackJLabel);

		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String phoneNumber = userPhoneNumberJTextField.getText();
				char[] newPassword = userNewPasswordJPasswordField.getPassword();
				char[] confirmPassword = userConfirmPasswordJPasswordField.getPassword();

				if (Arrays.equals(newPassword, confirmPassword)) {
					facade.updateUser(phoneNumber, newPassword);
					userFeedbackJLabel.setText("Password changed succesfully");
				} else {
					userFeedbackJLabel.setText("Passwords don't match");
				}
				userFeedbackJLabel.setVisible(true);
				userNewPasswordJPasswordField.setText("");
				userConfirmPasswordJPasswordField.setText("");
			}
		});
		btnUpdate.setBounds(236, 330, 89, 23);
		editUserJPanel.add(btnUpdate);

		userNewPasswordJPasswordField = new JPasswordField();
		userNewPasswordJPasswordField.setBounds(128, 238, 86, 20);
		editUserJPanel.add(userNewPasswordJPasswordField);

		userConfirmPasswordJPasswordField = new JPasswordField();
		userConfirmPasswordJPasswordField.setBounds(128, 269, 86, 20);
		editUserJPanel.add(userConfirmPasswordJPasswordField);
	}

	private void showClientGUI() {
		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		Client client = facade.getClient(elementID);
		setBounds(100, 100, 600, 724);

		setContentPane(editClientJPanel);
		editClientJPanel.setLayout(null);
		editClientJPanel.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		clientEditAnythingJLabel = new JLabel("Edit Anything");
		clientEditAnythingJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		clientEditAnythingJLabel.setBounds(153, 45, 284, 58);
		editClientJPanel.add(clientEditAnythingJLabel);

		clientUsernameJLabel = new JLabel("Username:");
		clientUsernameJLabel.setBounds(73, 210, 53, 14);
		editClientJPanel.add(clientUsernameJLabel);

		clientNewPasswordJLabel = new JLabel("Password:");
		clientNewPasswordJLabel.setBounds(73, 241, 53, 14);
		editClientJPanel.add(clientNewPasswordJLabel);

		clientNameJLabel = new JLabel("Name:");
		clientNameJLabel.setBounds(95, 303, 31, 14);
		editClientJPanel.add(clientNameJLabel);

		clientLastNameJLabel = new JLabel("Last name:");
		clientLastNameJLabel.setBounds(73, 334, 53, 14);
		editClientJPanel.add(clientLastNameJLabel);

		clientDNIJLabel = new JLabel("DNI:");
		clientDNIJLabel.setBounds(104, 365, 22, 14);
		editClientJPanel.add(clientDNIJLabel);

		clientEmailJLabel = new JLabel("Email:");
		clientEmailJLabel.setBounds(95, 396, 31, 14);
		editClientJPanel.add(clientEmailJLabel);

		clientPhoneNumberJLabel = new JLabel("Phone number:");
		clientPhoneNumberJLabel.setBounds(53, 430, 73, 14);
		editClientJPanel.add(clientPhoneNumberJLabel);

		clientAddressJLabel = new JLabel("Address:");
		clientAddressJLabel.setBounds(83, 458, 43, 14);
		editClientJPanel.add(clientAddressJLabel);

		clientUsernameJTextField = new JTextField(client.getUsername());
		clientUsernameJTextField.setEditable(false);
		clientUsernameJTextField.setBounds(136, 207, 86, 20);
		editClientJPanel.add(clientUsernameJTextField);
		clientUsernameJTextField.setColumns(10);

		clientNameJTextField = new JTextField(client.getName());
		clientNameJTextField.setBounds(136, 300, 86, 20);
		editClientJPanel.add(clientNameJTextField);
		clientNameJTextField.setColumns(10);

		clientLastNameJTextField = new JTextField(client.getLastName());
		clientLastNameJTextField.setBounds(136, 331, 86, 20);
		editClientJPanel.add(clientLastNameJTextField);
		clientLastNameJTextField.setColumns(10);

		clientDNIJTextField = new JTextField(client.getDNI());
		clientDNIJTextField.setBounds(136, 362, 86, 20);
		editClientJPanel.add(clientDNIJTextField);
		clientDNIJTextField.setColumns(10);

		clientEmailJTextField = new JTextField(client.getEmail());
		clientEmailJTextField.setBounds(136, 393, 86, 20);
		editClientJPanel.add(clientEmailJTextField);
		clientEmailJTextField.setColumns(10);

		clientPhoneNumberJTextField = new JTextField(client.getPhoneNumber());
		clientPhoneNumberJTextField.setBounds(136, 424, 86, 20);
		editClientJPanel.add(clientPhoneNumberJTextField);
		clientPhoneNumberJTextField.setColumns(10);

		clientAddressJTextField = new JTextField(client.getAddress());
		clientAddressJTextField.setBounds(136, 455, 86, 20);
		editClientJPanel.add(clientAddressJTextField);
		clientAddressJTextField.setColumns(10);

		clientBookedOffersJButton = new JButton("Booked offers");
		clientBookedOffersJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new EditAnythingShowElementsGUI(userType, username, "Offer",
						Arrays.asList(client.getOffers().toArray())).setVisible(true);
				closeWindow();
			}
		});
		clientBookedOffersJButton.setBounds(95, 516, 128, 23);
		editClientJPanel.add(clientBookedOffersJButton);

		clientFollowedOffersJButton = new JButton("Followed offers");
		clientFollowedOffersJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowElementsGUI(userType, username, "Offer",
						Arrays.asList(client.getFollowedOffers().toArray())).setVisible(true);
				closeWindow();
			}
		});
		clientFollowedOffersJButton.setBounds(95, 550, 128, 23);
		editClientJPanel.add(clientFollowedOffersJButton);

		clientProfileImageJLabel = new JLabel("Profile image:");
		clientProfileImageJLabel.setBounds(258, 210, 65, 14);
		editClientJPanel.add(clientProfileImageJLabel);

		clientProfileImageJTextField = new JTextField();
		clientProfileImageJTextField.setBounds(258, 235, 190, 20);
		editClientJPanel.add(clientProfileImageJTextField);
		clientProfileImageJTextField.setColumns(10);

		clientBrowseJButton = new JButton("Browse...");
		clientBrowseJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnValue = fc.showOpenDialog(getParent());
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						img = ImageIO.read(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					clientProfileImageViewJLabel
							.setIcon(new ImageIcon(img.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
					clientProfileImageJTextField.setText(file.getAbsolutePath());
				}
			}
		});
		clientBrowseJButton.setBounds(458, 232, 89, 23);
		editClientJPanel.add(clientBrowseJButton);

		clientProfileImageViewJLabel = new JLabel("New label");
		in = new ByteArrayInputStream(client.getProfileImage());
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		clientProfileImageViewJLabel.setIcon(new ImageIcon(img.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
		clientProfileImageViewJLabel.setBounds(258, 272, 256, 256);
		editClientJPanel.add(clientProfileImageViewJLabel);

		clientCancelJButton = new JButton("Cancel");
		clientCancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new EditAnythingShowEntitiesGUI(userType, username).setVisible(true);
				closeWindow();
			}
		});
		clientCancelJButton.setBounds(485, 651, 89, 23);
		editClientJPanel.add(clientCancelJButton);

		clientUpdateJButton = new JButton("Update");
		clientUpdateJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = client.getUsername();
				char[] password = client.getPassword();
				char[] newPassword = clientNewPasswordJPasswordField.getPassword();
				char[] confirmPassword = clientConfirmPasswordJPasswordField.getPassword();
				String profileImage = clientProfileImageJTextField.getText();
				String name = clientNameJTextField.getText();
				String lastName = clientLastNameJTextField.getText();
				String dni = clientDNIJTextField.getText();
				String email = clientEmailJTextField.getText();
				String phoneNumber = clientPhoneNumberJTextField.getText();
				String address = clientAddressJTextField.getText();

				facade.updateClient(client, clientFeedbackJLabel, username, password, newPassword, confirmPassword,
						profileImage, name, lastName, email, phoneNumber, address);

				clientNewPasswordJPasswordField.setText("");
				clientConfirmPasswordJPasswordField.setText("");
			}
		});
		clientUpdateJButton.setBounds(386, 651, 89, 23);
		editClientJPanel.add(clientUpdateJButton);

		clientNewPasswordJPasswordField = new JPasswordField();
		clientNewPasswordJPasswordField.setBounds(136, 238, 86, 20);
		editClientJPanel.add(clientNewPasswordJPasswordField);

		clientConfirmPasswordJPasswordField = new JPasswordField();
		clientConfirmPasswordJPasswordField.setBounds(136, 269, 86, 20);
		editClientJPanel.add(clientConfirmPasswordJPasswordField);

		JLabel clientConfirmPasswordJLabel = new JLabel("New password:");
		clientConfirmPasswordJLabel.setBounds(52, 272, 74, 14);
		editClientJPanel.add(clientConfirmPasswordJLabel);

		clientFeedbackJLabel = new JLabel("feedback");
		clientFeedbackJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		clientFeedbackJLabel.setForeground(Color.RED);
		clientFeedbackJLabel.setBounds(10, 655, 366, 14);
		clientFeedbackJLabel.setVisible(false);
		editClientJPanel.add(clientFeedbackJLabel);

		JLabel clientClientJLabel = new JLabel("Client");
		clientClientJLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		clientClientJLabel.setBounds(32, 148, 522, 29);
		editClientJPanel.add(clientClientJLabel);
	}

	private void showOwnerGUI() {
		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		Owner owner = facade.getOwner(elementID);

		setBounds(100, 100, 600, 740);

		setContentPane(editOwnerJPanel);
		editOwnerJPanel.setLayout(null);
		editOwnerJPanel.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		ownerEditAnythingJLabel = new JLabel("Edit Anything");
		ownerEditAnythingJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		ownerEditAnythingJLabel.setBounds(150, 45, 284, 58);
		editOwnerJPanel.add(ownerEditAnythingJLabel);

		ownerUsernameJTextField = new JTextField(owner.getUsername());
		ownerUsernameJTextField.setEditable(false);
		ownerUsernameJTextField.setBounds(128, 207, 86, 20);
		editOwnerJPanel.add(ownerUsernameJTextField);
		ownerUsernameJTextField.setColumns(10);

		ownerNameJTextField = new JTextField(owner.getName());
		ownerNameJTextField.setBounds(128, 300, 86, 20);
		editOwnerJPanel.add(ownerNameJTextField);
		ownerNameJTextField.setColumns(10);

		ownerPhoneNumberJTextField = new JTextField(owner.getPhoneNumber());
		ownerPhoneNumberJTextField.setBounds(128, 424, 86, 20);
		editOwnerJPanel.add(ownerPhoneNumberJTextField);
		ownerPhoneNumberJTextField.setColumns(10);

		ownerLastNameJTextField = new JTextField(owner.getLastName());
		ownerLastNameJTextField.setBounds(128, 331, 86, 20);
		editOwnerJPanel.add(ownerLastNameJTextField);
		ownerLastNameJTextField.setColumns(10);

		ownerEmailJTextField = new JTextField(owner.getEmail());
		ownerEmailJTextField.setBounds(128, 393, 86, 20);
		editOwnerJPanel.add(ownerEmailJTextField);
		ownerEmailJTextField.setColumns(10);

		ownerDNIJTextField = new JTextField(owner.getDNI());
		ownerDNIJTextField.setBounds(128, 362, 86, 20);
		editOwnerJPanel.add(ownerDNIJTextField);
		ownerDNIJTextField.setColumns(10);

		ownerUsernameJLabel = new JLabel("Username:");
		ownerUsernameJLabel.setBounds(66, 210, 52, 14);
		editOwnerJPanel.add(ownerUsernameJLabel);

		ownerNewPasswordJLabel = new JLabel("New password:");
		ownerNewPasswordJLabel.setBounds(44, 241, 74, 14);
		editOwnerJPanel.add(ownerNewPasswordJLabel);

		ownerConfirmPasswordJLabel = new JLabel("Confirm password:");
		ownerConfirmPasswordJLabel.setBounds(28, 272, 90, 14);
		editOwnerJPanel.add(ownerConfirmPasswordJLabel);

		ownerNameJLabel = new JLabel("Name:");
		ownerNameJLabel.setBounds(87, 303, 31, 14);
		editOwnerJPanel.add(ownerNameJLabel);

		ownerLastNameJLabel = new JLabel("Last name:");
		ownerLastNameJLabel.setBounds(65, 334, 53, 14);
		editOwnerJPanel.add(ownerLastNameJLabel);

		ownerDNIJLabel = new JLabel("DNI:");
		ownerDNIJLabel.setBounds(96, 365, 22, 14);
		editOwnerJPanel.add(ownerDNIJLabel);

		ownerEmailJLabel = new JLabel("Email:");
		ownerEmailJLabel.setBounds(87, 396, 31, 14);
		editOwnerJPanel.add(ownerEmailJLabel);

		ownerPhoneNumberJLabel = new JLabel("Phone number:");
		ownerPhoneNumberJLabel.setBounds(44, 427, 74, 14);
		editOwnerJPanel.add(ownerPhoneNumberJLabel);

		ownerAddressJLabel = new JLabel("Address:");
		ownerAddressJLabel.setBounds(72, 458, 46, 14);
		editOwnerJPanel.add(ownerAddressJLabel);

		ownerBankAccountJLabel = new JLabel("Bank account:");
		ownerBankAccountJLabel.setBounds(50, 489, 68, 14);
		editOwnerJPanel.add(ownerBankAccountJLabel);

		ownerAddressJTextField = new JTextField(owner.getAddress());
		ownerAddressJTextField.setBounds(128, 455, 86, 20);
		editOwnerJPanel.add(ownerAddressJTextField);
		ownerAddressJTextField.setColumns(10);

		ownerBankAccountJTextField = new JTextField(owner.getBankAccount());
		ownerBankAccountJTextField.setBounds(128, 486, 86, 20);
		editOwnerJPanel.add(ownerBankAccountJTextField);
		ownerBankAccountJTextField.setColumns(10);

		ownerRuralHousesJButton = new JButton("Rural houses");
		ownerRuralHousesJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowElementsGUI(userType, username, "RuralHouse",
						Arrays.asList(owner.getRuralHouses().toArray())).setVisible(true);
				closeWindow();
			}
		});
		ownerRuralHousesJButton.setBounds(87, 548, 128, 23);
		editOwnerJPanel.add(ownerRuralHousesJButton);

		ownerFollowedOffersJButton = new JButton("Followed offers");
		ownerFollowedOffersJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new EditAnythingShowElementsGUI(userType, username, "Offer",
						Arrays.asList(owner.getFollowedOffers().toArray())).setVisible(true);
				closeWindow();
			}
		});
		ownerFollowedOffersJButton.setBounds(87, 582, 128, 23);
		editOwnerJPanel.add(ownerFollowedOffersJButton);

		ownerProfileImageJLabel = new JLabel("Profile image:");
		ownerProfileImageJLabel.setBounds(264, 210, 68, 14);
		editOwnerJPanel.add(ownerProfileImageJLabel);

		ownerProfileImageJTextField = new JTextField();
		ownerProfileImageJTextField.setBounds(264, 238, 190, 20);
		editOwnerJPanel.add(ownerProfileImageJTextField);
		ownerProfileImageJTextField.setColumns(10);

		ownerBrowseJButton = new JButton("Browse...");
		ownerBrowseJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnValue = fc.showOpenDialog(getParent());
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						img = ImageIO.read(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					ownerProfileImageViewJLabel
							.setIcon(new ImageIcon(img.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
					ownerProfileImageJTextField.setText(file.getAbsolutePath());
				}
			}
		});
		ownerBrowseJButton.setBounds(464, 237, 86, 23);
		editOwnerJPanel.add(ownerBrowseJButton);

		ownerProfileImageViewJLabel = new JLabel("New label");
		in = new ByteArrayInputStream(owner.getProfileImage());
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ownerProfileImageViewJLabel.setIcon(new ImageIcon(img.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
		ownerProfileImageViewJLabel.setBounds(264, 272, 256, 256);
		editOwnerJPanel.add(ownerProfileImageViewJLabel);

		ownerCancelJButton = new JButton("Cancel");
		ownerCancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowEntitiesGUI(userType, username).setVisible(true);
				closeWindow();
			}
		});
		ownerCancelJButton.setBounds(485, 667, 89, 23);
		editOwnerJPanel.add(ownerCancelJButton);

		ownerUpdateJButton = new JButton("Update");
		ownerUpdateJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = owner.getUsername();
				char[] password = owner.getPassword();
				char[] newPassword = ownerNewPasswordJPasswordField.getPassword();
				char[] confirmPassword = ownerConfirmPasswordJPasswordField.getPassword();
				String profileImage = ownerProfileImageJTextField.getText();
				String name = ownerNameJTextField.getText();
				String lastName = ownerLastNameJTextField.getText();
				String dni = ownerDNIJTextField.getText();
				String phoneNumber = ownerPhoneNumberJTextField.getText();
				String email = ownerEmailJTextField.getText();
				String address = ownerAddressJTextField.getText();
				String bankAccount = ownerBankAccountJTextField.getText();

				facade.updateOwner(owner, ownerFeedbackJLabel, username, password, newPassword, confirmPassword,
						profileImage, name, lastName, email, phoneNumber, address);
				ownerNewPasswordJPasswordField.setText("");
				ownerConfirmPasswordJPasswordField.setText("");
			}
		});
		ownerUpdateJButton.setBounds(386, 667, 89, 23);
		editOwnerJPanel.add(ownerUpdateJButton);

		ownerNewPasswordJPasswordField = new JPasswordField();
		ownerNewPasswordJPasswordField.setBounds(128, 238, 86, 20);
		editOwnerJPanel.add(ownerNewPasswordJPasswordField);

		ownerConfirmPasswordJPasswordField = new JPasswordField();
		ownerConfirmPasswordJPasswordField.setBounds(128, 269, 86, 20);
		editOwnerJPanel.add(ownerConfirmPasswordJPasswordField);

		ownerOwnerJLabel = new JLabel("Owner");
		ownerOwnerJLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ownerOwnerJLabel.setBounds(32, 148, 58, 20);
		editOwnerJPanel.add(ownerOwnerJLabel);

		ownerFeedbackJLabel = new JLabel("feedback");
		ownerFeedbackJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ownerFeedbackJLabel.setForeground(Color.RED);
		ownerFeedbackJLabel.setBounds(10, 671, 366, 14);
		ownerFeedbackJLabel.setVisible(false);
		editOwnerJPanel.add(ownerFeedbackJLabel);
	}

	private void showOfferGUI() {
		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		Offer offer = facade.getOffer(Integer.parseInt(elementID));

		setBounds(100, 100, 475, 545);

		setContentPane(editOfferJPanel);
		editOfferJPanel.setLayout(null);
		editOfferJPanel.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		offerEditAnythingJLabel = new JLabel("Edit Anything");
		offerEditAnythingJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		offerEditAnythingJLabel.setBounds(87, 45, 284, 58);
		editOfferJPanel.add(offerEditAnythingJLabel);

		System.out.println("offer = " + offer.toString());
		offerNumberJLabel = new JLabel("Offer ID: " + offer.getOfferNumber());
		offerNumberJLabel.setBounds(72, 213, 142, 14);
		editOfferJPanel.add(offerNumberJLabel);

		firstDayJXDatePicker = new JXDatePicker();
		firstDayJXDatePicker.setDate(offer.getFirstDay());
		firstDayJXDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		firstDayJXDatePicker.setBounds(128, 238, 100, 19);
		editOfferJPanel.add(firstDayJXDatePicker);

		lastDayJXDatePicker = new JXDatePicker();
		lastDayJXDatePicker.setDate(offer.getLastDay());
		lastDayJXDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		lastDayJXDatePicker.setBounds(128, 269, 100, 19);
		editOfferJPanel.add(lastDayJXDatePicker);

		offerPriceJTextField = new JTextField(String.valueOf(offer.getPrice()));
		offerPriceJTextField.setBounds(128, 300, 86, 20);
		editOfferJPanel.add(offerPriceJTextField);
		offerPriceJTextField.setColumns(10);

		offerFirstDayJLabel = new JLabel("First day:");
		offerFirstDayJLabel.setBounds(72, 241, 46, 14);
		editOfferJPanel.add(offerFirstDayJLabel);

		offerLastDayJLabel = new JLabel("Last day:");
		offerLastDayJLabel.setBounds(72, 272, 46, 14);
		editOfferJPanel.add(offerLastDayJLabel);

		offerPriceJLabel = new JLabel("Price:");
		offerPriceJLabel.setBounds(91, 303, 27, 14);
		editOfferJPanel.add(offerPriceJLabel);

		offerFollowingClientsJButton = new JButton("Following clients");
		offerFollowingClientsJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowElementsGUI(userType, username, "Client",
						Arrays.asList(offer.getFollowingClients().toArray())).setVisible(true);
				closeWindow();
			}
		});
		offerFollowingClientsJButton.setBounds(261, 269, 128, 23);
		editOfferJPanel.add(offerFollowingClientsJButton);

		offerFollowingOwnersJButton = new JButton("Following owners");
		offerFollowingOwnersJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowElementsGUI(userType, username, "Owner",
						Arrays.asList(offer.getFollowingOwners().toArray())).setVisible(true);
				closeWindow();
			}
		});
		offerFollowingOwnersJButton.setBounds(261, 303, 128, 23);
		editOfferJPanel.add(offerFollowingOwnersJButton);

		offerRuralHouseJButton = new JButton("Rural house");
		offerRuralHouseJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Llendo a una RuralHouse");
				new EditAnythingEditElementGUI(userType, username, "RuralHouse", elements,
						String.valueOf(offer.getRuralHouse().getRuralHouseNumber())).setVisible(true);
				closeWindow();
			}
		});
		offerRuralHouseJButton.setBounds(261, 238, 128, 23);
		editOfferJPanel.add(offerRuralHouseJButton);

		offerCancelJButton = new JButton("Cancel");
		offerCancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowEntitiesGUI(userType, username).setVisible(true);
				closeWindow();
			}
		});
		offerCancelJButton.setBounds(360, 472, 89, 23);
		editOfferJPanel.add(offerCancelJButton);

		offerOfferJLabel = new JLabel("Offer");
		offerOfferJLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		offerOfferJLabel.setBounds(32, 148, 46, 25);
		editOfferJPanel.add(offerOfferJLabel);

		offerFeedback = new JLabel("feedback");
		offerFeedback.setHorizontalAlignment(SwingConstants.CENTER);
		offerFeedback.setForeground(Color.RED);
		offerFeedback.setBounds(10, 476, 241, 14);
		offerFeedback.setVisible(false);
		editOfferJPanel.add(offerFeedback);

		offerUpdateJButton = new JButton("Update");
		offerUpdateJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date firstDay = firstDayJXDatePicker.getDate();
				Date lastDay = lastDayJXDatePicker.getDate();
				String price = offerPriceJTextField.getText();

				facade.updateOffer(offer, offerFeedback, firstDay, lastDay, price);
				offerFeedback.setVisible(true);
			}
		});
		offerUpdateJButton.setBounds(261, 472, 89, 23);
		editOfferJPanel.add(offerUpdateJButton);
	}

	private void showRuralHouseGUI() {
		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		RuralHouse ruralHouse = facade.getRuralHouse(Integer.parseInt(elementID));

		nextImageIndex = ruralHouse.getRuralHouseImages().size();

		setBounds(100, 100, 767, 750);

		setContentPane(editRuralHouseJPanel);
		editRuralHouseJPanel.setLayout(null);
		editRuralHouseJPanel.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		ruralHouseEditAnythingJLabel = new JLabel("Edit Anything");
		ruralHouseEditAnythingJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		ruralHouseEditAnythingJLabel.setBounds(233, 45, 284, 58);
		editRuralHouseJPanel.add(ruralHouseEditAnythingJLabel);

		ruralHouseNumber = new JLabel("Rural house ID: 342");
		ruralHouseNumber.setBounds(32, 210, 182, 14);
		editRuralHouseJPanel.add(ruralHouseNumber);

		ruralHouseCityJLabel = new JLabel("Province:");
		ruralHouseCityJLabel.setBounds(32, 238, 51, 14);
		editRuralHouseJPanel.add(ruralHouseCityJLabel);

		ruralHouseShortDescriptionJLabel = new JLabel("Short description:");
		ruralHouseShortDescriptionJLabel.setBounds(32, 294, 86, 14);
		editRuralHouseJPanel.add(ruralHouseShortDescriptionJLabel);

		ruralHouseDescriptionJLabel = new JLabel("Description:");
		ruralHouseDescriptionJLabel.setBounds(32, 394, 57, 14);
		editRuralHouseJPanel.add(ruralHouseDescriptionJLabel);

		ruralHouseBathroomsJTextField = new JTextField(String.valueOf(ruralHouse.getBathroomQuantity()));
		ruralHouseBathroomsJTextField.setBounds(128, 568, 23, 20);
		editRuralHouseJPanel.add(ruralHouseBathroomsJTextField);
		ruralHouseBathroomsJTextField.setColumns(10);

		ruralHouseBedroomsJTextField = new JTextField(String.valueOf(ruralHouse.getBedroomQuantity()));
		ruralHouseBedroomsJTextField.setBounds(128, 599, 23, 20);
		editRuralHouseJPanel.add(ruralHouseBedroomsJTextField);
		ruralHouseBedroomsJTextField.setColumns(10);

		ruralHouseDinningroomsJTextField = new JTextField(String.valueOf(ruralHouse.getDinningroomQuantity()));
		ruralHouseDinningroomsJTextField.setBounds(128, 630, 23, 20);
		editRuralHouseJPanel.add(ruralHouseDinningroomsJTextField);
		ruralHouseDinningroomsJTextField.setColumns(10);

		ruralHouseKitchensJTextField = new JTextField(String.valueOf(ruralHouse.getKitchenQuantity()));
		ruralHouseKitchensJTextField.setBounds(287, 568, 23, 20);
		editRuralHouseJPanel.add(ruralHouseKitchensJTextField);
		ruralHouseKitchensJTextField.setColumns(10);

		ruralHouseParkingPlacesJTextField = new JTextField(String.valueOf(ruralHouse.getParkingPlaceQuantity()));
		ruralHouseParkingPlacesJTextField.setBounds(287, 599, 23, 20);
		editRuralHouseJPanel.add(ruralHouseParkingPlacesJTextField);
		ruralHouseParkingPlacesJTextField.setColumns(10);

		ruralHouseBathroomsJLabel = new JLabel("Bathrooms:");
		ruralHouseBathroomsJLabel.setBounds(61, 571, 57, 14);
		editRuralHouseJPanel.add(ruralHouseBathroomsJLabel);

		ruralHouseKitchensJLabel = new JLabel("Kitchens:");
		ruralHouseKitchensJLabel.setBounds(233, 571, 44, 14);
		editRuralHouseJPanel.add(ruralHouseKitchensJLabel);

		ruraHouseBedroomsJLabel = new JLabel("Bedrooms:");
		ruraHouseBedroomsJLabel.setBounds(67, 602, 51, 14);
		editRuralHouseJPanel.add(ruraHouseBedroomsJLabel);

		ruralHouseDinningroomsJLabel = new JLabel("Dinningrooms:");
		ruralHouseDinningroomsJLabel.setBounds(50, 633, 68, 14);
		editRuralHouseJPanel.add(ruralHouseDinningroomsJLabel);

		ruralHouseRoomsJTextField = new JTextField(String.valueOf(ruralHouse.getRoomQuantity()));
		ruralHouseRoomsJTextField.setBounds(287, 630, 23, 20);
		editRuralHouseJPanel.add(ruralHouseRoomsJTextField);
		ruralHouseRoomsJTextField.setColumns(10);

		ruralHouseParkingPlacesJLabel = new JLabel("Parking places:");
		ruralHouseParkingPlacesJLabel.setBounds(205, 602, 72, 14);
		editRuralHouseJPanel.add(ruralHouseParkingPlacesJLabel);

		ruralHouseRoomsJLabel = new JLabel("Rooms:");
		ruralHouseRoomsJLabel.setBounds(241, 633, 36, 14);
		editRuralHouseJPanel.add(ruralHouseRoomsJLabel);

		ruralHouseImageJLabel = new JLabel("Images:");
		ruralHouseImageJLabel.setBounds(428, 238, 190, 14);
		editRuralHouseJPanel.add(ruralHouseImageJLabel);

		ruralHouseBrowseJButton = new JButton("Browse...");
		ruralHouseBrowseJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (nextImageIndex >= 0 && nextImageIndex < 6) {
					JFileChooser fc = new JFileChooser();
					int returnValue = fc.showOpenDialog(getParent());
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						try {
							img = ImageIO.read(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
						imageViewJLabelList.get(nextImageIndex)
								.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
						paths.add(nextImageIndex, file.getAbsolutePath());
						System.out.println(paths.get(nextImageIndex));
						System.out.println("Ahora paths tiene " + paths.size());

						imageViewJLabelList.get(nextImageIndex).setVisible(true);
						imageViewJButtonList.get(nextImageIndex).setVisible(true);
						nextImageIndex++;
					}
				} else {
					ruralHouseFeedbackJLabel.setText("Maximum image quantity is 6");
					ruralHouseFeedbackJLabel.setVisible(true);
				}
			}
		});
		ruralHouseBrowseJButton.setBounds(628, 234, 89, 23);
		editRuralHouseJPanel.add(ruralHouseBrowseJButton);
		in = new ByteArrayInputStream(ruralHouse.getRuralHouseImages().get(0));
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ruralHouseCancelJButton = new JButton("Cancel");
		ruralHouseCancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowEntitiesGUI(userType, username).setVisible(true);
				closeWindow();
			}
		});
		ruralHouseCancelJButton.setBounds(652, 677, 89, 23);
		editRuralHouseJPanel.add(ruralHouseCancelJButton);

		ruralHouseUpdateJButton = new JButton("Update");
		ruralHouseUpdateJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String city = (String) citiesJComboBox.getSelectedItem();
				String shortDescription = ruralHouseShortDescriptionJEditorPanel.getText();
				String description = ruralHouseDescriptionJEditorPanel.getText();
				String bathrooms = ruralHouseBathroomsJTextField.getText();
				String bedrooms = ruralHouseBedroomsJTextField.getText();
				String dinningrooms = ruralHouseDinningroomsJTextField.getText();
				String kitchens = ruralHouseKitchensJTextField.getText();
				String parkingPlaces = ruralHouseParkingPlacesJTextField.getText();
				String rooms = ruralHouseRoomsJTextField.getText();

				facade.updateRuralHouse(ruralHouse, ruralHouseFeedbackJLabel, city, shortDescription, description,
						bathrooms, bedrooms, dinningrooms, kitchens, parkingPlaces, rooms, paths);
				ruralHouseFeedbackJLabel.setVisible(true);
			}
		});
		ruralHouseUpdateJButton.setBounds(553, 677, 89, 23);
		editRuralHouseJPanel.add(ruralHouseUpdateJButton);

		ruralHouseRuralHouseJLabel = new JLabel("Rural House");
		ruralHouseRuralHouseJLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ruralHouseRuralHouseJLabel.setBounds(32, 148, 108, 20);
		editRuralHouseJPanel.add(ruralHouseRuralHouseJLabel);

		ruralHouseFeedbackJLabel = new JLabel("feedback");
		ruralHouseFeedbackJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ruralHouseFeedbackJLabel.setForeground(Color.RED);
		ruralHouseFeedbackJLabel.setBounds(10, 681, 533, 14);
		ruralHouseFeedbackJLabel.setVisible(false);
		editRuralHouseJPanel.add(ruralHouseFeedbackJLabel);

		citiesJComboBox = new JComboBox();
		citiesJComboBox.setBounds(32, 263, 100, 20);
		citiesJComboBox.addItem(ruralHouse.getCity());
		File file = new File(".\\resources\\provincias.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		while (sc.hasNextLine()) {
			citiesJComboBox.addItem(sc.nextLine());
		}
		sc.close();
		editRuralHouseJPanel.add(citiesJComboBox);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = null;
		try {
			d = sdf.parse("01/01/1990");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		imageView1JLabel = new JLabel("");
		imageView1JLabel.setBounds(428, 274, 64, 64);
		if (ruralHouse.getRuralHouseImages().size() >= 1) {
			in = new ByteArrayInputStream(ruralHouse.getRuralHouseImages().get(0));
			try {
				img = ImageIO.read(in);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			imageView1JLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			imageView1JLabel.setVisible(true);
		} else {
			imageView1JLabel.setVisible(false);
		}
		editRuralHouseJPanel.add(imageView1JLabel);

		imageView2JLabel = new JLabel("");
		imageView2JLabel.setBounds(528, 274, 64, 64);
		if (ruralHouse.getRuralHouseImages().size() >= 2) {
			in = new ByteArrayInputStream(ruralHouse.getRuralHouseImages().get(1));
			try {
				img = ImageIO.read(in);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			imageView2JLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			imageView2JLabel.setVisible(true);
		} else {
			imageView2JLabel.setVisible(false);
		}
		editRuralHouseJPanel.add(imageView2JLabel);

		imageView3JLabel = new JLabel("");
		imageView3JLabel.setBounds(628, 274, 64, 64);
		if (ruralHouse.getRuralHouseImages().size() >= 3) {
			in = new ByteArrayInputStream(ruralHouse.getRuralHouseImages().get(2));
			try {
				img = ImageIO.read(in);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			imageView3JLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			imageView3JLabel.setVisible(true);
		} else {
			imageView3JLabel.setVisible(false);
		}
		editRuralHouseJPanel.add(imageView3JLabel);

		imageView4JLabel = new JLabel("");
		imageView4JLabel.setBounds(428, 354, 64, 64);
		if (ruralHouse.getRuralHouseImages().size() >= 4) {
			in = new ByteArrayInputStream(ruralHouse.getRuralHouseImages().get(3));
			try {
				img = ImageIO.read(in);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			imageView4JLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			imageView4JLabel.setVisible(true);
		} else {
			imageView4JLabel.setVisible(false);
		}
		editRuralHouseJPanel.add(imageView4JLabel);

		imageView5JLabel = new JLabel("");
		imageView5JLabel.setBounds(528, 354, 64, 64);
		if (ruralHouse.getRuralHouseImages().size() >= 5) {
			in = new ByteArrayInputStream(ruralHouse.getRuralHouseImages().get(4));
			try {
				img = ImageIO.read(in);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			imageView5JLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			imageView5JLabel.setVisible(true);
		} else {
			imageView5JLabel.setVisible(false);
		}
		editRuralHouseJPanel.add(imageView5JLabel);

		imageView6JLabel = new JLabel("");
		imageView6JLabel.setBounds(628, 354, 64, 64);
		if (ruralHouse.getRuralHouseImages().size() >= 6) {
			in = new ByteArrayInputStream(ruralHouse.getRuralHouseImages().get(5));
			try {
				img = ImageIO.read(in);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			imageView6JLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			imageView6JLabel.setVisible(true);
		} else {
			imageView6JLabel.setVisible(false);
		}
		editRuralHouseJPanel.add(imageView6JLabel);

		imageView1JButton = new JButton("");
		imageView1JButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		imageView1JButton.setBackground(Color.RED);
		imageView1JButton.setContentAreaFilled(false);
		imageView1JButton.setOpaque(true);
		imageView1JButton.setBounds(492, 274, 16, 16);
		if (ruralHouse.getRuralHouseImages().size() >= 1) {
			imageView1JButton.setVisible(true);
		} else {
			imageView1JButton.setVisible(false);
		}
		editRuralHouseJPanel.add(imageView1JButton);

		imageView2JButton = new JButton("");
		imageView2JButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		imageView2JButton.setBackground(Color.RED);
		imageView2JButton.setContentAreaFilled(false);
		imageView2JButton.setOpaque(true);
		imageView2JButton.setBounds(592, 274, 16, 16);
		if (ruralHouse.getRuralHouseImages().size() >= 2) {
			imageView2JButton.setVisible(true);
		} else {
			imageView2JButton.setVisible(false);
		}
		editRuralHouseJPanel.add(imageView2JButton);

		imageView3JButton = new JButton("");
		imageView3JButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		imageView3JButton.setBackground(Color.RED);
		imageView3JButton.setContentAreaFilled(false);
		imageView3JButton.setOpaque(true);
		imageView3JButton.setBounds(692, 274, 16, 16);
		if (ruralHouse.getRuralHouseImages().size() >= 3) {
			imageView3JButton.setVisible(true);
		} else {
			imageView3JButton.setVisible(false);
		}
		editRuralHouseJPanel.add(imageView3JButton);

		imageView4JButton = new JButton("");
		imageView4JButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		imageView4JButton.setBackground(Color.RED);
		imageView4JButton.setContentAreaFilled(false);
		imageView4JButton.setOpaque(true);
		imageView4JButton.setBounds(492, 354, 16, 16);
		if (ruralHouse.getRuralHouseImages().size() >= 4) {
			imageView4JButton.setVisible(true);
		} else {
			imageView4JButton.setVisible(false);
		}
		editRuralHouseJPanel.add(imageView4JButton);

		imageView5JButton = new JButton("");
		imageView5JButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		imageView5JButton.setBackground(Color.RED);
		imageView5JButton.setContentAreaFilled(false);
		imageView5JButton.setOpaque(true);
		imageView5JButton.setBounds(592, 354, 16, 16);
		if (ruralHouse.getRuralHouseImages().size() >= 5) {
			imageView5JButton.setVisible(true);
		} else {
			imageView5JButton.setVisible(false);
		}
		editRuralHouseJPanel.add(imageView5JButton);

		imageView6JButton = new JButton("");
		imageView6JButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		imageView6JButton.setBackground(Color.RED);
		imageView6JButton.setContentAreaFilled(false);
		imageView6JButton.setOpaque(true);
		imageView6JButton.setBounds(692, 354, 16, 16);
		if (ruralHouse.getRuralHouseImages().size() >= 6) {
			imageView6JButton.setVisible(true);
		} else {
			imageView6JButton.setVisible(false);
		}
		editRuralHouseJPanel.add(imageView6JButton);

		imageViewJLabelList = new ArrayList<>();
		imageViewJLabelList.add(imageView1JLabel);
		imageViewJLabelList.add(imageView2JLabel);
		imageViewJLabelList.add(imageView3JLabel);
		imageViewJLabelList.add(imageView4JLabel);
		imageViewJLabelList.add(imageView5JLabel);
		imageViewJLabelList.add(imageView6JLabel);

		imageViewJButtonList = new ArrayList<>();
		imageViewJButtonList.add(imageView1JButton);
		imageViewJButtonList.add(imageView2JButton);
		imageViewJButtonList.add(imageView3JButton);
		imageViewJButtonList.add(imageView4JButton);
		imageViewJButtonList.add(imageView5JButton);
		imageViewJButtonList.add(imageView6JButton);

		ruralHouseShortDescriptionJEditorPanel = new JEditorPane();
		ruralHouseShortDescriptionJEditorPanel.setText(ruralHouse.getShortDescription());
		ruralHouseShortDescriptionJEditorPanel.setBounds(32, 319, 256, 64);
		editRuralHouseJPanel.add(ruralHouseShortDescriptionJEditorPanel);

		ruralHouseDescriptionJEditorPanel = new JEditorPane();
		ruralHouseDescriptionJEditorPanel.setText(ruralHouse.getDescription());
		ruralHouseDescriptionJEditorPanel.setBounds(32, 419, 256, 128);
		editRuralHouseJPanel.add(ruralHouseDescriptionJEditorPanel);

		ruralHouseOwnerJButton = new JButton("Owner");
		ruralHouseOwnerJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new EditAnythingEditElementGUI(userType, username, "Owner", elements,
						ruralHouse.getOwner().getUsername()).setVisible(true);
				closeWindow();
			}
		});
		ruralHouseOwnerJButton.setBounds(553, 490, 89, 23);
		editRuralHouseJPanel.add(ruralHouseOwnerJButton);

		ruralHouseOffersJButton = new JButton("Offers");
		ruralHouseOffersJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowElementsGUI(userType, username, "Offer",
						Arrays.asList(ruralHouse.getOffers().toArray())).setVisible(true);
				closeWindow();
			}
		});
		ruralHouseOffersJButton.setBounds(553, 524, 89, 23);
		editRuralHouseJPanel.add(ruralHouseOffersJButton);

		for (int i = 0; i < imageViewJButtonList.size(); i++) {
			final int finalI = i;
			imageViewJButtonList.get(finalI).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					for (int i = finalI; i < 5; i++) {
						imageViewJLabelList.get(i).setIcon(imageViewJLabelList.get(i + 1).getIcon());
					}
					try {
						img = ImageIO.read(defaultRuralHouseImageFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					imageViewJLabelList.get(nextImageIndex - 1)
							.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
					imageViewJLabelList.get(nextImageIndex - 1).setVisible(false);
					imageViewJButtonList.get(nextImageIndex - 1).setVisible(false);
					System.out.println(">>>>> Invisibilizada la de índice " + (nextImageIndex - 1));
					System.out.println(">>>>> Eliminada la dirección a pasar de la casa de índice " + finalI);
					nextImageIndex--;
					System.out.println("Ahora nextImageIndex vale " + nextImageIndex);
				}
			});
		}

		// for (JLabel imageView : imageViewJLabelList) {
		// try {
		// img = ImageIO.read(defaultRuralHouseImageFile);
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// imageView.setIcon(new ImageIcon(img.getScaledInstance(64, 64,
		// Image.SCALE_DEFAULT)));
		// }
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
