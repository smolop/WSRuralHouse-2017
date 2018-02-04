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
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.Client;
import domain.Owner;
import domain.User;

public class ManageAccountInformation extends JFrame {
	private byte userType;
	private String username;

	Client client = null;
	Owner owner = null;

	private JPanel contentPane;
	private JPasswordField passwordJTextField;
	private JPasswordField newPasswordJTextField;
	private JPasswordField confirmNewPasswordJTextField;
	private JTextField profileImageJTextField;
	private JTextField nameJTextField;
	private JTextField lastNameJTextField;
	private JTextField emailJTextField;
	private JTextField phoneNumberJTextField;
	private JTextField addressJTextField;
	private JLabel usernameJLabel;
	private JLabel passwordJLabel;
	private JLabel newPasswordJLabel;
	private JLabel confirmNewPasswordJLabel;
	private JLabel nameJLabel;
	private JLabel lastNameJLabel;
	private JLabel emailJLabel;
	private JLabel phoneNumberJLabel;
	private JLabel addressJLabel;
	private JTextField usernameJTextField;
	private JButton browseJButton;
	private JLabel profileImageJLabel;
	private JLabel lblManageAccountInformation;
	private JButton btnUpdate;
	private JButton btnCancel;
	private JLabel feedbackJLabel;
	private JLabel profileImageViewJLabel;
	private BufferedImage img;
	private JInternalFrame updatedInternalFrame;
	private JLabel lblNewLabel;
	private JLabel imageJLabel;
	private InputStream in;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ManageAccountInformation frame = new ManageAccountInformation(User.USER_TYPE_USER, "Guest");
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
	public ManageAccountInformation(byte userType, String username) {
		this.userType = userType;
		this.username = username;
		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 764, 564);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		switch (userType) {
		case User.USER_TYPE_CLIENT:
			client = facade.getClient(username);
			break;
		case User.USER_TYPE_OWNER:
			owner = facade.getOwner(username);
			break;
		}

		updatedInternalFrame = new JInternalFrame("Rural House-Manage Account Information-Updated");
		updatedInternalFrame.getContentPane().setName("Updated");
		updatedInternalFrame.setBounds(138, 76, 471, 372);
		// if (updatedInternalFrame.getBounds().x <= 0) {
		// updatedInternalFrame.setBounds(0, updatedInternalFrame.getBounds().y,
		// 471, 372);
		// }
		// if (updatedInternalFrame.getBounds().y <= 0) {
		// updatedInternalFrame.setBounds(updatedInternalFrame.getBounds().x, 0,
		// 471, 372);
		// }
		contentPane.add(updatedInternalFrame);
		updatedInternalFrame.getContentPane().setLayout(null);

		lblNewLabel = new JLabel("Updated!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(193, 18, 68, 14);
		updatedInternalFrame.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String phoneNumber = phoneNumberJTextField.getText();
				if (userType == User.USER_TYPE_CLIENT) {
					closeWindow();
					MainGUI frame = new MainGUI(User.USER_TYPE_CLIENT, username, phoneNumber);
					frame.setVisible(true);
				} else if (userType == User.USER_TYPE_OWNER) {
					closeWindow();
					MainGUI frame = new MainGUI(User.USER_TYPE_OWNER, username, phoneNumber);
					frame.setVisible(true);
				}

			}
		});
		btnNewButton.setBounds(183, 308, 89, 23);
		updatedInternalFrame.getContentPane().add(btnNewButton);

		imageJLabel = new JLabel("Image");
		imageJLabel.setBounds(99, 43, 256, 256);
		updatedInternalFrame.getContentPane().add(imageJLabel);

		lblManageAccountInformation = new JLabel("Manage Account Information");
		lblManageAccountInformation.setFont(new Font("Tahoma", Font.PLAIN, 48));
		lblManageAccountInformation.setBounds(67, 45, 613, 58);
		contentPane.add(lblManageAccountInformation);

		usernameJLabel = new JLabel("Username:");
		usernameJLabel.setBounds(171, 143, 52, 14);
		contentPane.add(usernameJLabel);

		passwordJLabel = new JLabel("Password:");
		passwordJLabel.setBounds(171, 168, 52, 14);
		contentPane.add(passwordJLabel);

		newPasswordJLabel = new JLabel("New password:");
		newPasswordJLabel.setBounds(149, 193, 74, 14);
		contentPane.add(newPasswordJLabel);

		confirmNewPasswordJLabel = new JLabel("Confirm new password:");
		confirmNewPasswordJLabel.setBounds(110, 218, 113, 14);
		contentPane.add(confirmNewPasswordJLabel);

		profileImageJLabel = new JLabel("Profile image:");
		profileImageJLabel.setBounds(428, 143, 65, 14);
		contentPane.add(profileImageJLabel);

		nameJLabel = new JLabel("Name:");
		nameJLabel.setBounds(192, 243, 31, 14);
		contentPane.add(nameJLabel);

		lastNameJLabel = new JLabel("Last Name:");
		lastNameJLabel.setBounds(169, 268, 54, 14);
		contentPane.add(lastNameJLabel);

		emailJLabel = new JLabel("Email:");
		emailJLabel.setBounds(192, 293, 31, 14);
		contentPane.add(emailJLabel);

		phoneNumberJLabel = new JLabel("Phone number:");
		phoneNumberJLabel.setBounds(149, 318, 74, 14);
		contentPane.add(phoneNumberJLabel);

		addressJLabel = new JLabel("Address:");
		addressJLabel.setBounds(180, 343, 43, 14);
		contentPane.add(addressJLabel);

		usernameJTextField = new JTextField();
		usernameJTextField.setText(username);
		usernameJTextField.setBounds(233, 140, 128, 20);
		usernameJTextField.setEditable(false);
		contentPane.add(usernameJTextField);
		usernameJTextField.setColumns(10);

		passwordJTextField = new JPasswordField();
		passwordJTextField.setBounds(233, 165, 128, 20);
		contentPane.add(passwordJTextField);

		newPasswordJTextField = new JPasswordField();
		newPasswordJTextField.setBounds(233, 190, 128, 20);
		contentPane.add(newPasswordJTextField);

		confirmNewPasswordJTextField = new JPasswordField();
		confirmNewPasswordJTextField.setBounds(233, 215, 128, 20);
		contentPane.add(confirmNewPasswordJTextField);

		profileImageJTextField = new JTextField();
		profileImageJTextField.setBounds(428, 165, 190, 20);
		contentPane.add(profileImageJTextField);
		profileImageJTextField.setColumns(10);

		profileImageViewJLabel = new JLabel("");
		in = null;
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			in = new ByteArrayInputStream(client.getProfileImage());
			break;
		case User.USER_TYPE_OWNER:
			in = new ByteArrayInputStream(owner.getProfileImage());
			break;
		}

		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		profileImageViewJLabel.setIcon(new ImageIcon(img.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
		profileImageViewJLabel.setBounds(428, 193, 256, 256);
		contentPane.add(profileImageViewJLabel);

		nameJTextField = new JTextField();
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			nameJTextField.setText(client.getName());
			break;
		case User.USER_TYPE_OWNER:
			nameJTextField.setText(owner.getName());
			break;
		}
		nameJTextField.setBounds(233, 240, 128, 20);
		contentPane.add(nameJTextField);
		nameJTextField.setColumns(10);

		lastNameJTextField = new JTextField();
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			lastNameJTextField.setText(client.getLastName());
			break;
		case User.USER_TYPE_OWNER:
			lastNameJTextField.setText(owner.getLastName());
			break;
		}
		lastNameJTextField.setBounds(233, 265, 128, 20);
		contentPane.add(lastNameJTextField);
		lastNameJTextField.setColumns(10);

		emailJTextField = new JTextField();
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			emailJTextField.setText(client.getEmail());
			break;
		case User.USER_TYPE_OWNER:
			emailJTextField.setText(owner.getEmail());
			break;
		}
		emailJTextField.setBounds(233, 290, 128, 20);
		contentPane.add(emailJTextField);
		emailJTextField.setColumns(10);

		phoneNumberJTextField = new JTextField();
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			phoneNumberJTextField.setText(String.valueOf(client.getPhoneNumber()));
			break;
		case User.USER_TYPE_OWNER:
			phoneNumberJTextField.setText(String.valueOf(owner.getPhoneNumber()));
			break;
		}
		phoneNumberJTextField.setBounds(233, 315, 128, 20);
		contentPane.add(phoneNumberJTextField);
		phoneNumberJTextField.setColumns(10);

		addressJTextField = new JTextField();
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			addressJTextField.setText(client.getAddress());
			break;
		case User.USER_TYPE_OWNER:
			addressJTextField.setText(owner.getAddress());
			break;
		}
		addressJTextField.setBounds(233, 340, 128, 20);
		contentPane.add(addressJTextField);
		addressJTextField.setColumns(10);

		browseJButton = new JButton("Browse...");
		browseJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnValue = fc.showOpenDialog(getParent());
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						img = ImageIO.read(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					profileImageViewJLabel.setIcon(new ImageIcon(img.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
					profileImageJTextField.setText(file.getAbsolutePath());
				}
			}
		});
		browseJButton.setBounds(628, 164, 89, 23);
		contentPane.add(browseJButton);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String phoneNumber = phoneNumberJTextField.getText();
				new MainGUI(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		btnCancel.setBounds(649, 491, 89, 23);
		contentPane.add(btnCancel);

		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String newUsername = usernameJTextField.getText().toString();
				char[] password = passwordJTextField.getPassword();
				char[] newPassword = newPasswordJTextField.getPassword();
				char[] confirmNewPassword = confirmNewPasswordJTextField.getPassword();
				String newProfileImage = profileImageJTextField.getText().toString();
				String newName = nameJTextField.getText().toString();
				String newLastName = lastNameJTextField.getText().toString();
				String newEmail = emailJTextField.getText().toString();
				String newPhoneNumber = phoneNumberJTextField.getText().toString();
				String newAddress = addressJTextField.getText().toString();
				if (Arrays.equals(newPassword, confirmNewPassword)) {
					updatedInternalFrame.setVisible(true);
					switch (userType) {
					case User.USER_TYPE_CLIENT:
						facade.updateClient(client, feedbackJLabel, newUsername, password, newPassword,
								confirmNewPassword, newProfileImage, newName, newLastName, newEmail, newPhoneNumber,
								newAddress);
						imageJLabel.setIcon(new ImageIcon(img.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
						break;
					case User.USER_TYPE_OWNER:
						facade.updateOwner(owner, feedbackJLabel, newUsername, password, newPassword,
								confirmNewPassword, newProfileImage, newName, newLastName, newEmail, newPhoneNumber,
								newAddress);
						imageJLabel.setIcon(new ImageIcon(img.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
						break;
					}
				} else {
					JOptionPane.showMessageDialog(null, "Confrm new password isn't match", "Alert!!",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnUpdate.setBounds(550, 491, 89, 23);
		contentPane.add(btnUpdate);

		feedbackJLabel = new JLabel("");
		feedbackJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackJLabel.setForeground(Color.RED);
		feedbackJLabel.setBounds(10, 466, 728, 14);
		contentPane.add(feedbackJLabel);
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}