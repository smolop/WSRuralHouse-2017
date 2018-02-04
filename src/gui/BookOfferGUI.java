package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXLabel.TextAlignment;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.Offer;
import domain.User;

public class BookOfferGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte userType;
	private String username;
	private int offerNumber;
	private Offer offer;
	private JPanel contentPane;
	private JLabel bookOfferJLabel;
	private JButton bookJButton;
	private JTextField phoneNumberJTextField;
	private JLabel phoneNumberJLabel;
	private JPasswordField passwordJPasswordField;
	private JLabel descriptionJLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					BookOfferGUI frame = new BookOfferGUI(User.USER_TYPE_USER, "Guest", 0);
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
	 * @param username
	 * @param userType
	 */
	public BookOfferGUI(byte userType, String username, int offerNumber) {
		this.username = username;
		this.userType = userType;
		this.offerNumber = offerNumber;
		BufferedImage img = null;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 335);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		bookOfferJLabel = new JLabel("Book Offer");
		bookOfferJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		bookOfferJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bookOfferJLabel.setBounds(104, 45, 225, 58);
		contentPane.add(bookOfferJLabel);
		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();
		this.offer = facade.getOffer(offerNumber);

		bookJButton = new JButton("Book");
		bookJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (userType == User.USER_TYPE_CLIENT) {
					facade.clientBookOffer(username, offerNumber);
					new MainGUI(userType, username, null).setVisible(true);
					closeWindow();
				} else if (userType == User.USER_TYPE_USER) {
					String phoneNumber = phoneNumberJTextField.getText().toString();
					char[] password = passwordJPasswordField.getPassword();
					if (!facade.userExists(phoneNumber)) {
						facade.createUser(phoneNumber, password);
					}
					facade.userBookOffer(phoneNumber, offerNumber);
					new MainGUI(userType, "User", phoneNumber).setVisible(true);
					closeWindow();
				}

			}
		});
		bookJButton.setBounds(240, 262, 89, 23);
		contentPane.add(bookJButton);

		phoneNumberJTextField = new JTextField();
		phoneNumberJTextField.setToolTipText("");
		phoneNumberJTextField.setBounds(153, 200, 127, 20);
		if (userType != User.USER_TYPE_USER) {
			phoneNumberJTextField.setVisible(false);
		} else if (username == "Guest") {
			phoneNumberJTextField.setBounds(153, 177, 127, 20);
		}

		contentPane.add(phoneNumberJTextField);
		phoneNumberJTextField.setColumns(10);

		JButton cancelJButton = new JButton("Cancel");
		cancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String phoneNumber = phoneNumberJTextField.getText();
				new MainGUI(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		cancelJButton.setBounds(340, 262, 89, 23);
		contentPane.add(cancelJButton);

		JLabel ruralHouseImageJLabel = new JLabel("");

		// InputStream in = new ByteArrayInputStream(offer.getRuralHouse()
		// .getRuralHouseImage());
		// try {
		// img = ImageIO.read(in);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// ruralHouseImageJLabel.setIcon(new
		// ImageIcon(img.getScaledInstance(256,
		// 256, Image.SCALE_DEFAULT)));

		ruralHouseImageJLabel.setBounds(26, 91, 89, 75);
		contentPane.add(ruralHouseImageJLabel);

		JXLabel ruralHouseDescriptionJLabel = new JXLabel(this.offer.getRuralHouse().getDescription());
		ruralHouseDescriptionJLabel.setTextAlignment(TextAlignment.CENTER);
		ruralHouseDescriptionJLabel.setLineWrap(true);
		ruralHouseDescriptionJLabel.setBounds(10, 114, 419, 75);
		contentPane.add(ruralHouseDescriptionJLabel);

		phoneNumberJLabel = new JLabel("Phone number:");
		phoneNumberJLabel.setBounds(70, 203, 73, 14);
		if (userType != User.USER_TYPE_USER) {
			phoneNumberJLabel.setVisible(false);
		} else {
			String phoneNumber = phoneNumberJTextField.getText().toString();
			if (username == "Guest") {
				phoneNumberJLabel.setBounds(70, 180, 73, 14);
			}
		}
		contentPane.add(phoneNumberJLabel);

		descriptionJLabel = new JLabel("Description:");
		descriptionJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		descriptionJLabel.setBounds(70, 145, 73, 14);
		contentPane.add(descriptionJLabel);

		JButton followOfferJButton;
		// userType != User.USER_TYPE_USER --> Es temporal para que se pueda
		// acceder a BookOffer
		if (userType != User.USER_TYPE_USER && facade.isFollowingOffer(username, userType, offerNumber)) {
			followOfferJButton = new JButton("Unfollow Offer");
		} else {
			followOfferJButton = new JButton("Follow Offer");
		}
		followOfferJButton.setBounds(54, 250, 101, 23);
		contentPane.add(followOfferJButton);

		followOfferJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (followOfferJButton.getText().equals("Follow Offer")) {
					facade.followOffer(username, userType, offerNumber);
					followOfferJButton.setText("Unfollow Offer");
				} else {
					facade.unfollowOffer(username, userType, offerNumber);
					followOfferJButton.setText("Follow Offer");
				}

			}
		});

		JLabel passwordJLabel = new JLabel("Password:");
		passwordJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordJLabel.setBounds(70, 220, 73, 14);
		if (userType == User.USER_TYPE_USER && username == "Guest") {
			contentPane.add(passwordJLabel);
			passwordJLabel.setVisible(true);
		}

		passwordJPasswordField = new JPasswordField();
		passwordJPasswordField.setBounds(153, 217, 127, 20);
		if (userType == User.USER_TYPE_USER && username == "Guest") {
			contentPane.add(passwordJPasswordField);
			passwordJLabel.setVisible(true);
		}

	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
