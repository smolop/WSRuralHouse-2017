package auxiliar;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import businessLogic.FacadeImplementationWS;
import domain.User;
import gui.MainGUI;

public class OwnerSignUpGUI extends JFrame {
	private byte userType;
	private String username;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameJTextField;
	private JPasswordField passwordJPasswordField;
	private JPasswordField confirmPasswordJPasswordField;
	private JLabel signUpJLabel;
	private JLabel usernameJLabel;
	private JLabel passwordJLabel;
	private JLabel confirmPasswordJLabel;
	private JLabel feedbackJLabel;
	private JButton signUpJButton;
	private JButton btnCancel;
	private JTextField insertImageJTtextField;
	private JLabel lastNameJLabel;
	private JTextField DNIJTextField;
	private JLabel addressJLabel;
	private JTextField addressJTextField;
	private JLabel phoneNumberJLabel;
	private JTextField phoneNumberJTextField;
	private JLabel mailJLabel;
	private JTextField mailJTextField;
	private JLabel DNIJLabel;
	private JTextField lastNameJTextField;
	private JLabel nameJLabel;
	private JTextField nameJTextField;
	private JLabel insertImageJLabel;
	private JButton browseJButton;
	private byte[] imgP = null;

	/**
	 * 
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
					OwnerSignUpGUI frame = new OwnerSignUpGUI(User.USER_TYPE_USER, "Guest");
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
	public OwnerSignUpGUI(byte userType, String username) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.userType = userType;
		this.username = username;
		FacadeImplementationWS facade = new FacadeImplementationWS();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		JInternalFrame signedUpInternalJFrame = new JInternalFrame("Signed Up");
		signedUpInternalJFrame.setEnabled(false);
		signedUpInternalJFrame.setBounds(84, 80, 269, 259);
		contentPane.add(signedUpInternalJFrame);
		signedUpInternalJFrame.getContentPane().setLayout(null);

		JButton okJButton = new JButton("Ok");
		okJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
				new MainGUI(User.USER_TYPE_USER, "Guest", null).setVisible(true);
			}
		});
		okJButton.setBounds(75, 63, 89, 23);
		signedUpInternalJFrame.getContentPane().add(okJButton);

		JLabel signedUpJLabel = new JLabel("Client successfully signed up!");
		signedUpJLabel.setBounds(42, 25, 172, 14);
		signedUpInternalJFrame.getContentPane().add(signedUpJLabel);

		JLabel lblImg = new JLabel("IMg");
		lblImg.setBounds(75, 126, 46, 14);
		signedUpInternalJFrame.getContentPane().add(lblImg);

		signUpJLabel = new JLabel("Sign Up");
		signUpJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		signUpJLabel.setBounds(134, 11, 165, 58);
		contentPane.add(signUpJLabel);

		usernameJLabel = new JLabel("Username:");
		usernameJLabel.setBounds(112, 92, 52, 14);
		contentPane.add(usernameJLabel);

		usernameJTextField = new JTextField();
		usernameJTextField.setBounds(174, 89, 86, 20);
		contentPane.add(usernameJTextField);
		usernameJTextField.setColumns(10);

		passwordJLabel = new JLabel("Password:");
		passwordJLabel.setBounds(112, 123, 52, 14);
		contentPane.add(passwordJLabel);

		passwordJPasswordField = new JPasswordField();
		passwordJPasswordField.setBounds(174, 120, 86, 20);
		contentPane.add(passwordJPasswordField);

		confirmPasswordJLabel = new JLabel("Confirm password:");
		confirmPasswordJLabel.setBounds(74, 154, 90, 14);
		contentPane.add(confirmPasswordJLabel);

		confirmPasswordJPasswordField = new JPasswordField();
		confirmPasswordJPasswordField.setBounds(174, 151, 86, 20);
		contentPane.add(confirmPasswordJPasswordField);

		feedbackJLabel = new JLabel("");
		feedbackJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackJLabel.setForeground(Color.RED);
		feedbackJLabel.setBounds(21, 392, 414, 14);
		feedbackJLabel.setVisible(false);
		contentPane.add(feedbackJLabel);

		signUpJButton = new JButton("Sign Up");
		signUpJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String username;
				char[] password;
				char[] confirmPassword;
				byte[] profileImage = imgP;
				String name;
				String lastName;
				String DNI;
				String mail;
				String phoneNumber;
				String address;

				username = usernameJTextField.getText().toString();
				if (facade.isValidUsername(username)) {
					password = passwordJPasswordField.getPassword();
					confirmPassword = confirmPasswordJPasswordField.getPassword();
					name = nameJTextField.getText().toString();
					lastName = lastNameJTextField.getText().toString();
					DNI = DNIJTextField.getText().toString();
					mail = mailJTextField.getText().toString();
					try {
						phoneNumber = phoneNumberJTextField.getText().toString();
						address = addressJTextField.getText().toString();

						if (Arrays.equals(password, confirmPassword)) {
							if (facade.isValidPassword(password)) {
								if (facade.isValidProfileImage(profileImage)) {
									if (facade.isValidName(name)) {
										if (facade.isValidLastName(lastName)) {
											if (facade.isValidDNI(DNI)) {
												if (facade.isValidMail(mail)) {
													if (facade.isValidPhoneNumber(phoneNumber)) {
														if (facade.isValidAddress(address)) {
															facade.createOwner(username, password, profileImage, name,
																	lastName, DNI, mail, phoneNumber, address);

															signedUpInternalJFrame.setVisible(true);

														} else {
															feedbackJLabel.setText("Invalid Address");
															feedbackJLabel.setVisible(true);
														}
													} else {
														feedbackJLabel.setText("Invalid Phone Number");
														feedbackJLabel.setVisible(true);
													}
												} else {
													feedbackJLabel.setText("Invalid Mail");
													feedbackJLabel.setVisible(true);
												}
											} else {
												feedbackJLabel.setText("Invalid DNI");
												feedbackJLabel.setVisible(true);
											}
										} else {
											feedbackJLabel.setText("Invalid Last Name");
											feedbackJLabel.setVisible(true);
										}
									} else {
										feedbackJLabel.setText("Invalid Name");
										feedbackJLabel.setVisible(true);
									}
								} else {
									feedbackJLabel.setText("Invalid Profile Image");
									feedbackJLabel.setVisible(true);
								}
							} else {
								feedbackJLabel.setText("Invalid Password");
								feedbackJLabel.setVisible(true);
							}
						} else {
							feedbackJLabel.setText("Passwords don't match");
							feedbackJLabel.setVisible(true);
						}
					} catch (NumberFormatException e) {
						feedbackJLabel.setText("Invalid Phone Number");
						feedbackJLabel.setVisible(true);
					}
				} else {
					feedbackJLabel.setText("This username is in use");
					feedbackJLabel.setVisible(true);
				}
			}
		});
		signUpJButton.setBounds(236, 417, 89, 23);
		contentPane.add(signUpJButton);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String phoneNumber = phoneNumberJLabel.getText();
				new MainGUI(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		btnCancel.setBounds(335, 417, 89, 23);
		contentPane.add(btnCancel);

		insertImageJTtextField = new JTextField();
		insertImageJTtextField.setBounds(174, 182, 86, 20);
		contentPane.add(insertImageJTtextField);
		insertImageJTtextField.setColumns(10);

		insertImageJLabel = new JLabel("Insert Image:");
		insertImageJLabel.setBounds(98, 185, 66, 14);
		contentPane.add(insertImageJLabel);

		nameJTextField = new JTextField();
		nameJTextField.setBounds(174, 213, 86, 20);
		contentPane.add(nameJTextField);
		nameJTextField.setColumns(10);

		nameJLabel = new JLabel("Name:");
		nameJLabel.setBounds(133, 216, 31, 14);
		contentPane.add(nameJLabel);

		lastNameJTextField = new JTextField();
		lastNameJTextField.setBounds(174, 244, 86, 20);
		contentPane.add(lastNameJTextField);
		lastNameJTextField.setColumns(10);

		lastNameJLabel = new JLabel("Last Name: ");
		lastNameJLabel.setBounds(107, 247, 57, 14);
		contentPane.add(lastNameJLabel);

		DNIJTextField = new JTextField();
		DNIJTextField.setBounds(174, 275, 86, 20);
		contentPane.add(DNIJTextField);
		DNIJTextField.setColumns(10);

		DNIJLabel = new JLabel("DNI: ");
		DNIJLabel.setBounds(139, 278, 25, 14);
		contentPane.add(DNIJLabel);

		mailJTextField = new JTextField();
		mailJTextField.setBounds(174, 306, 86, 20);
		contentPane.add(mailJTextField);
		mailJTextField.setColumns(10);

		mailJLabel = new JLabel("Mail: ");
		mailJLabel.setBounds(139, 309, 25, 14);
		contentPane.add(mailJLabel);

		phoneNumberJTextField = new JTextField();
		phoneNumberJTextField.setBounds(174, 337, 86, 20);
		contentPane.add(phoneNumberJTextField);
		phoneNumberJTextField.setColumns(10);

		phoneNumberJLabel = new JLabel("Phone Number:");
		phoneNumberJLabel.setBounds(90, 340, 74, 14);
		contentPane.add(phoneNumberJLabel);

		addressJTextField = new JTextField();
		addressJTextField.setBounds(174, 368, 86, 20);
		contentPane.add(addressJTextField);
		addressJTextField.setColumns(10);

		addressJLabel = new JLabel("Address: ");
		addressJLabel.setBounds(118, 371, 46, 14);
		contentPane.add(addressJLabel);

		browseJButton = new JButton("Browse");
		browseJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int retVal = fileChooser.showOpenDialog(OwnerSignUpGUI.this);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					File img = fileChooser.getSelectedFile();
					try {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						BufferedImage prof = ImageIO.read(img);
						ImageIO.write(prof, "png", baos);
						imgP = baos.toByteArray();
						InputStream in = new ByteArrayInputStream(imgP);
						BufferedImage bImageFromConvert = ImageIO.read(in);
						lblImg.setIcon(new ImageIcon(bImageFromConvert));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		browseJButton.setBounds(267, 181, 86, 23);
		contentPane.add(browseJButton);
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
