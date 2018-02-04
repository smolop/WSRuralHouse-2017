package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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

import businessLogic.ApplicationFacadeInterfaceWS;
import businessLogic.FacadeImplementationWS;
import domain.User;

public class SignUpGUI extends JFrame {
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
	private JTextField imageJTtextField;
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
	private JLabel imageJLabel;
	private JButton browseJButton;
	private BufferedImage img;
	private JLabel imageViewJLabel;
	private JLabel lblImg;

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
					SignUpGUI frame = new SignUpGUI(User.USER_TYPE_USER, "Guest", null);
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
	public SignUpGUI(byte userType, String username, String phoneNumber) {
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
		setBounds(100, 100, 617, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		JInternalFrame signedUpInternalJFrame = new JInternalFrame("Signed Up");
		signedUpInternalJFrame.setEnabled(false);
		signedUpInternalJFrame.setBounds(84, 22, 463, 394);
		contentPane.add(signedUpInternalJFrame);
		signedUpInternalJFrame.getContentPane().setLayout(null);

		JButton okJButton = new JButton("Ok");
		okJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
				new MainGUI(User.USER_TYPE_USER, username, null).setVisible(true);
			}
		});
		okJButton.setBounds(348, 330, 89, 23);
		signedUpInternalJFrame.getContentPane().add(okJButton);

		JLabel signedUpJLabel = new JLabel("Successfully signed up!");
		signedUpJLabel.setBounds(168, 25, 111, 14);
		signedUpInternalJFrame.getContentPane().add(signedUpJLabel);

		lblImg = new JLabel("IMg");
		lblImg.setBounds(95, 50, 256, 256);
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
				String name;
				String lastName;
				String DNI;
				String mail;
				String phoneNumber;
				String address;

				username = usernameJTextField.getText().toString();
				ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();
				if (facade.isValidUsername(username)) {
					password = passwordJPasswordField.getPassword();
					confirmPassword = confirmPasswordJPasswordField.getPassword();
					String profileImage = imageJTtextField.getText().toString();
					byte[] profileImageInByte = null;
					if (profileImage.length() > 0) {
						File file = new File(profileImage);
						BufferedImage image = null;
						try {
							image = ImageIO.read(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						try {
							ImageIO.write(image, "png", baos);
						} catch (IOException e) {
							e.printStackTrace();
						}
						profileImageInByte = baos.toByteArray();
					} else {
						File file = new File(".\\resources\\user.png");
						BufferedImage image = null;
						try {
							image = ImageIO.read(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						try {
							ImageIO.write(image, "png", baos);
						} catch (IOException e) {
							e.printStackTrace();
						}
						profileImageInByte = baos.toByteArray();
					}
					name = nameJTextField.getText().toString();
					lastName = lastNameJTextField.getText().toString();
					DNI = DNIJTextField.getText().toString();
					mail = mailJTextField.getText().toString();
					try {
						phoneNumber = phoneNumberJTextField.getText().toString();
						address = addressJTextField.getText().toString();

						if (Arrays.equals(password, confirmPassword)) {
							if (facade.isValidPassword(password)) {
								if (facade.isValidProfileImage(profileImageInByte)) {
									if (facade.isValidName(name)) {
										if (facade.isValidLastName(lastName)) {
											if (facade.isValidDNI(DNI)) {
												if (facade.isValidMail(mail)) {
													if (facade.isValidPhoneNumber(phoneNumber)) {
														if (facade.isValidAddress(address)) {
															facade.createClient(username, password, profileImageInByte,
																	name, lastName, DNI, mail, phoneNumber, address);

															lblImg.setIcon(new ImageIcon(img.getScaledInstance(256, 256,
																	Image.SCALE_DEFAULT)));
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
		signUpJButton.setBounds(399, 457, 89, 23);
		contentPane.add(signUpJButton);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MainGUI(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		btnCancel.setBounds(498, 457, 89, 23);
		contentPane.add(btnCancel);

		imageJTtextField = new JTextField();
		imageJTtextField.setBounds(301, 117, 190, 20);
		contentPane.add(imageJTtextField);
		imageJTtextField.setColumns(10);

		imageJLabel = new JLabel("Image:");
		imageJLabel.setBounds(301, 92, 34, 14);
		contentPane.add(imageJLabel);

		nameJTextField = new JTextField();
		nameJTextField.setBounds(174, 182, 86, 20);
		contentPane.add(nameJTextField);
		nameJTextField.setColumns(10);

		nameJLabel = new JLabel("Name:");
		nameJLabel.setBounds(133, 185, 31, 14);
		contentPane.add(nameJLabel);

		lastNameJTextField = new JTextField();
		lastNameJTextField.setBounds(174, 213, 86, 20);
		contentPane.add(lastNameJTextField);
		lastNameJTextField.setColumns(10);

		lastNameJLabel = new JLabel("Last Name: ");
		lastNameJLabel.setBounds(107, 216, 57, 14);
		contentPane.add(lastNameJLabel);

		DNIJTextField = new JTextField();
		DNIJTextField.setBounds(174, 244, 86, 20);
		contentPane.add(DNIJTextField);
		DNIJTextField.setColumns(10);

		DNIJLabel = new JLabel("DNI: ");
		DNIJLabel.setBounds(139, 247, 25, 14);
		contentPane.add(DNIJLabel);

		mailJTextField = new JTextField();
		mailJTextField.setBounds(174, 275, 86, 20);
		contentPane.add(mailJTextField);
		mailJTextField.setColumns(10);

		mailJLabel = new JLabel("Mail: ");
		mailJLabel.setBounds(139, 278, 25, 14);
		contentPane.add(mailJLabel);

		phoneNumberJTextField = new JTextField();
		phoneNumberJTextField.setBounds(174, 309, 86, 20);
		contentPane.add(phoneNumberJTextField);
		phoneNumberJTextField.setColumns(10);

		phoneNumberJLabel = new JLabel("Phone Number:");
		phoneNumberJLabel.setBounds(90, 312, 74, 14);
		contentPane.add(phoneNumberJLabel);

		addressJTextField = new JTextField();
		addressJTextField.setBounds(174, 340, 86, 20);
		contentPane.add(addressJTextField);
		addressJTextField.setColumns(10);

		addressJLabel = new JLabel("Address: ");
		addressJLabel.setBounds(118, 343, 46, 14);
		contentPane.add(addressJLabel);

		browseJButton = new JButton("Browse");
		browseJButton.addActionListener(new ActionListener() {
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
					imageViewJLabel.setIcon(new ImageIcon(img.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
					imageJTtextField.setText(file.getAbsolutePath());
				}
			}
		});
		browseJButton.setBounds(501, 116, 86, 23);
		contentPane.add(browseJButton);

		imageViewJLabel = new JLabel("");
		imageViewJLabel.setBounds(311, 148, 115, 256);
		File defaultRuralHouseImageFile = new File(".\\resources\\user.png");
		try {
			img = ImageIO.read(defaultRuralHouseImageFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		imageViewJLabel.setIcon(new ImageIcon(img.getScaledInstance(256, 256, Image.SCALE_DEFAULT)));
		imageViewJLabel.setBounds(311, 148, 256, 256);
		contentPane.add(imageViewJLabel);

		JButton IamAlreadyUserJButton = new JButton("I'm already an user");
		IamAlreadyUserJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();
				new UserToClientGUI(userType, username, phoneNumber).setVisible(true);
			}
		});
		IamAlreadyUserJButton.setBounds(215, 457, 175, 23);
		contentPane.add(IamAlreadyUserJButton);
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
