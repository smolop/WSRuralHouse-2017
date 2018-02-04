package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.FacadeImplementationWS;
import domain.User;

public class UserLoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField phoneNumberJTextField;
	private JPasswordField passwordJPassword;
	private JPasswordField confirmPasswordJPasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UserLoginGUI frame = new UserLoginGUI(User.USER_TYPE_USER, "Guest", 0, null);
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
	public UserLoginGUI(byte UserType, String username, int offerNumber, String operation) {

		FacadeImplementationWS facade = new FacadeImplementationWS();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 413, 259);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel logInJlabel = new JLabel("Log In");
		logInJlabel.setBounds(93, 26, 211, 45);
		logInJlabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		logInJlabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(logInJlabel);

		JLabel phoneNumberJLabel = new JLabel("Phone Number:");
		phoneNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phoneNumberJLabel.setBounds(52, 101, 104, 14);
		contentPane.add(phoneNumberJLabel);

		JLabel passwordJLabel = new JLabel("Password:");
		passwordJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordJLabel.setBounds(62, 126, 94, 14);
		contentPane.add(passwordJLabel);

		JLabel confirmPasswordJLabel = new JLabel("Confirm password:");
		confirmPasswordJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		confirmPasswordJLabel.setBounds(52, 151, 104, 14);
		confirmPasswordJLabel.setVisible(false);
		;
		contentPane.add(confirmPasswordJLabel);

		confirmPasswordJPasswordField = new JPasswordField();
		confirmPasswordJPasswordField.setBounds(166, 152, 138, 20);
		confirmPasswordJPasswordField.setVisible(false);

		phoneNumberJTextField = new JTextField();
		phoneNumberJTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!facade.userExists(phoneNumberJTextField.getText())) {
					confirmPasswordJLabel.setVisible(true);
					confirmPasswordJPasswordField.setVisible(true);
				} else {
					confirmPasswordJLabel.setVisible(false);
					confirmPasswordJPasswordField.setVisible(false);
				}
			}
		});
		phoneNumberJTextField.setBounds(166, 99, 138, 17);
		contentPane.add(phoneNumberJTextField);
		phoneNumberJTextField.setColumns(10);

		passwordJPassword = new JPasswordField();
		passwordJPassword.setBounds(166, 124, 138, 17);
		contentPane.add(passwordJPassword);

		JButton OkJButton = new JButton("OK");
		OkJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String phoneNumber = phoneNumberJTextField.getText();
				if (!facade.userExists(phoneNumberJTextField.getText())) {
					confirmPasswordJLabel.setVisible(true);
					confirmPasswordJPasswordField.setVisible(true);
					if (Arrays.equals(passwordJPassword.getPassword(), confirmPasswordJPasswordField.getPassword())) {
						facade.createUser(phoneNumber, passwordJPassword.getPassword());
						JOptionPane.showMessageDialog(null, "your user'd been created sucessfully", "User Up",
								JOptionPane.INFORMATION_MESSAGE);
						if (facade.userExists(phoneNumber) && operation == "SeeOfferDetails") {
							facade.userBookOffer(phoneNumber, offerNumber);
							new SeeOfferDetailsGUI(User.USER_TYPE_USER, "User", phoneNumber, offerNumber, false)
									.setVisible(true);
							closeWindow();
						}

					} else {
						if (confirmPasswordJPasswordField.getPassword().length != 0) {
							JOptionPane.showMessageDialog(null, "The password doesn't match", "Password Alert!",
									JOptionPane.WARNING_MESSAGE);
						}
					}
				} else if (facade.userExists(phoneNumberJTextField.getText()) && operation == "SeeOfferDetails") {
					facade.userBookOffer(phoneNumber, offerNumber);
					new SeeOfferDetailsGUI(User.USER_TYPE_USER, "User", phoneNumber, offerNumber, false)
							.setVisible(true);
					closeWindow();
				}

			}
		});
		OkJButton.setBounds(98, 186, 89, 23);
		contentPane.add(OkJButton);

		JButton cancelJButton = new JButton("Cancel");
		cancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();
			}
		});
		cancelJButton.setBounds(202, 186, 89, 23);
		contentPane.add(cancelJButton);

		contentPane.add(confirmPasswordJPasswordField);
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
