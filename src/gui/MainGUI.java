package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;
/**
 * @author Software Engineering teachers
 */
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.Client;
import domain.Owner;
import domain.RuralHouse;
import domain.User;

public class MainGUI extends JFrame {
	private byte userType;
	private String username;

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPanel jContentPane = null;
	private JButton boton1 = null;
	private JButton boton2 = null;
	private JButton boton3 = null;

	private static ApplicationFacadeInterfaceWS appFacadeInterface;

	public static ApplicationFacadeInterfaceWS getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(ApplicationFacadeInterfaceWS afi) {
		appFacadeInterface = afi;
	}

	protected JLabel lblNewLabel;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton logInJButton;
	private JButton signUpJButton;
	private JLabel homeJLabel;
	private JLabel helloUsernameJLabel;
	private JButton queryAvailabilityJButton;
	private JButton logOutJButton;
	private JButton addRuralHouseJButton;
	private JButton setAvailabilityButton;
	private JButton deleteRuralHouseJButton;
	private JButton manageAccountInformationJButton;
	private JButton searchJButton;
	private JButton cancelBookedOfferJButton;
	private JLabel profileImageViewJTextLabel;
	private BufferedImage img;
	private JButton upgradeToClientJButton;
	private JButton followingJButton;
	private JButton chatListJButton;

	public MainGUI(byte userType, String username, String phoneNumber) {
		super();
		this.userType = userType;
		this.username = username;
		Client client = null;
		Owner owner = null;
		// getContentPane().setLayout(null);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		switch (userType) {
		case User.USER_TYPE_CLIENT:
			client = facade.getClient(username);
			System.out.println("soy cliente");
			break;
		case User.USER_TYPE_OWNER:
			owner = facade.getOwner(username);
			break;
		}

		helloUsernameJLabel = new JLabel("Hello, " + username);
		helloUsernameJLabel.setBounds(10, 15, 216, 14);
		helloUsernameJLabel.setForeground(Color.BLACK);
		contentPane.add(helloUsernameJLabel);

		logInJButton = new JButton("Log In");
		logInJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new LogInGUI(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		logInJButton.setBounds(335, 11, 89, 23);
		if (userType != User.USER_TYPE_USER) {
			logInJButton.setVisible(false);
		}
		contentPane.add(logInJButton);

		signUpJButton = new JButton("Sign Up");
		signUpJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUpGUI(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		signUpJButton.setBounds(236, 11, 89, 23);
		if (userType != User.USER_TYPE_USER) {
			signUpJButton.setVisible(false);
		}
		contentPane.add(signUpJButton);

		logOutJButton = new JButton("Log Out");
		logOutJButton.setBounds(335, 11, 89, 23);
		logOutJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MainGUI(User.USER_TYPE_USER, "Guest", null).setVisible(true);
				closeWindow();
			}
		});

		if (userType != User.USER_TYPE_CLIENT && userType != User.USER_TYPE_OWNER && userType != User.USER_TYPE_ADMIN) {
			logOutJButton.setVisible(false);
		}
		contentPane.add(logOutJButton);

		homeJLabel = new JLabel("Home");
		homeJLabel.setBounds(155, 40, 123, 58);
		homeJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		contentPane.add(homeJLabel);

		queryAvailabilityJButton = new JButton("Query availability");
		queryAvailabilityJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new QueryAvailabilityGUI(userType, username).setVisible(true);
				closeWindow();
			}
		});
		queryAvailabilityJButton.setBounds(134, 227, 123, 23);
		queryAvailabilityJButton.setVisible(false);
		contentPane.add(queryAvailabilityJButton);

		deleteRuralHouseJButton = new JButton("Delete rural house");
		deleteRuralHouseJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DeleteRuralHouseGUI(userType, username).setVisible(true);
				closeWindow();
			}
		});
		deleteRuralHouseJButton.setBounds(267, 159, 123, 23);
		if (userType != User.USER_TYPE_OWNER) {
			deleteRuralHouseJButton.setVisible(false);
		}
		contentPane.add(deleteRuralHouseJButton);

		JButton deleteOfferJButton = new JButton("Delete offer");
		deleteOfferJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DeleteOfferGUI(userType, username).setVisible(true);
				closeWindow();
			}
		});
		deleteOfferJButton.setBounds(267, 129, 123, 23);
		if (userType != User.USER_TYPE_OWNER) {
			deleteOfferJButton.setVisible(false);
		}
		contentPane.add(deleteOfferJButton);

		manageAccountInformationJButton = new JButton("Manage Account Information");
		manageAccountInformationJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ManageAccountInformation(userType, username).setVisible(true);
				closeWindow();
			}
		});
		manageAccountInformationJButton.setBounds(154, 11, 171, 23);
		if (userType != User.USER_TYPE_CLIENT && userType != User.USER_TYPE_OWNER) {
			manageAccountInformationJButton.setVisible(false);
		}
		contentPane.add(manageAccountInformationJButton);

		searchJButton = new JButton("Search");
		searchJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SearchGUI(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		searchJButton.setBounds(10, 227, 89, 23);
		contentPane.add(searchJButton);

		cancelBookedOfferJButton = new JButton("Cancel Booked Offer");
		cancelBookedOfferJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (userType == User.USER_TYPE_CLIENT) {
					new CancelBookedOfferGUI(userType, username).setVisible(true);
				}
				if (userType == User.USER_TYPE_USER && phoneNumber != null) {
					closeWindow();
					// String phoneNumber =
					// facade.getUser(phoneNumber).getPhoneNumber();
					System.out.println("PHONENUMBER: " + phoneNumber);
					new CancelBookedOfferGUI(userType, phoneNumber).setVisible(true);
				} else if (userType == User.USER_TYPE_USER && username == "Guest") {
					closeWindow();
					new CancelBookedOfferGUI(userType, username).setVisible(true);
				}
			}
		});
		cancelBookedOfferJButton.setBounds(288, 227, 136, 23);
		if (userType == User.USER_TYPE_OWNER || userType == User.USER_TYPE_ADMIN) {
			cancelBookedOfferJButton.setVisible(false);
		}
		contentPane.add(cancelBookedOfferJButton);

		profileImageViewJTextLabel = new JLabel("");
		profileImageViewJTextLabel.setBounds(10, 40, 64, 64);
		InputStream in = null;
		File file;
		switch (userType) {
		case User.USER_TYPE_USER:
			file = new File(".\\resources\\user.png");
			try {
				img = ImageIO.read(file);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			profileImageViewJTextLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			break;
		case User.USER_TYPE_CLIENT:
			System.out.println("soy el cliente " + client.toString());
			System.out.println("tengo el nombre " + client.getUsername());
			in = new ByteArrayInputStream(client.getProfileImage());
			try {
				img = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			profileImageViewJTextLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			break;
		case User.USER_TYPE_OWNER:
			in = new ByteArrayInputStream(owner.getProfileImage());
			try {
				img = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			profileImageViewJTextLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			break;
		case User.USER_TYPE_ADMIN:
			file = new File(".\\resources\\admin.png");
			try {
				img = ImageIO.read(file);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			profileImageViewJTextLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			break;
		}
		contentPane.add(profileImageViewJTextLabel);

		JButton quickLogOutJButton = new JButton("Forget");
		quickLogOutJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();
				MainGUI frame = new MainGUI(User.USER_TYPE_USER, "Guest", null);
				frame.setVisible(true);
			}
		});
		quickLogOutJButton.setBounds(7, 115, 70, 23);
		if (userType != User.USER_TYPE_USER || username == "Guest") {
			quickLogOutJButton.setVisible(false);
		}
		contentPane.add(quickLogOutJButton);

		followingJButton = new JButton("Following");
		followingJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ShowFollowingOffers(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		followingJButton.setBounds(10, 159, 89, 23);
		if (userType == User.USER_TYPE_USER) {
			followingJButton.setVisible(false);
		}
		contentPane.add(followingJButton);

		chatListJButton = new JButton("Chat list");
		chatListJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("mi userType es " + userType);
				new ChatListGUI(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		chatListJButton.setBounds(10, 193, 89, 23);
		if (userType == User.USER_TYPE_USER) {
			chatListJButton.setVisible(false);
		}
		contentPane.add(chatListJButton);

		JButton editAnythingJButton = new JButton("Edit Anything");
		editAnythingJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new EditAnythingShowEntitiesGUI(userType, username).setVisible(true);
				closeWindow();
			}
		});
		editAnythingJButton.setBounds(327, 45, 97, 23);
		if (userType != User.USER_TYPE_ADMIN) {
			editAnythingJButton.setVisible(false);
		}
		contentPane.add(editAnythingJButton);

		upgradeToClientJButton = new JButton("Upgrade to Client");
		upgradeToClientJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();
				UserToClientGUI frame = new UserToClientGUI(userType, username, phoneNumber);
				frame.setVisible(true);
			}
		});
		upgradeToClientJButton.setBounds(109, 11, 117, 23);
		if (userType == User.USER_TYPE_USER) {
			contentPane.add(upgradeToClientJButton);
		}

		setAvailabilityButton = new JButton("Set availability");
		setAvailabilityButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<RuralHouse> ruralHouses = facade.getOwnerRuralHouses(username);
				new SetAvailabilityGUI(userType, username, ruralHouses).setVisible(true);
				closeWindow();
			}
		});
		setAvailabilityButton.setBounds(134, 129, 123, 23);
		if (userType == User.USER_TYPE_OWNER) {
			contentPane.add(setAvailabilityButton);
		}

		addRuralHouseJButton = new JButton("Add rural house");
		addRuralHouseJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddRuralHouseGUI(userType, username).setVisible(true);
				closeWindow();
			}
		});
		addRuralHouseJButton.setBounds(134, 159, 123, 23);
		if (userType == User.USER_TYPE_OWNER) {
			contentPane.add(addRuralHouseJButton);
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();
				try {
					// if (ConfigXML.getInstance().isBusinessLogicLocal())
					// facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println(
							"Error: " + e1.toString() + " , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		// initialize();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(495, 290);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridLayout(4, 1, 0, 0));
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBoton3());
			jContentPane.add(getBoton2());
			jContentPane.add(getPanel());
		}
		return jContentPane;
	}

	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton2() {
		if (boton2 == null) {
			boton2 = new JButton();
			boton2.setText(ResourceBundle.getBundle("Etiquetas").getString("SetAvailability"));
			boton2.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();
					Vector<RuralHouse> ruralHouses = facade.getAllRuralHouses();
					new SetAvailabilityGUI(userType, username, ruralHouses).setVisible(true);
				}
			});
		}
		return boton2;
	}

	/**
	 * This method initializes boton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (boton3 == null) {
			boton3 = new JButton();
			boton3.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryAvailability"));
			boton3.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new QueryAvailabilityGUI(userType, username);

					a.setVisible(true);
				}
			});
		}
		return boton3;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setForeground(Color.BLACK);
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNewLabel;
	}

	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}

	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}

	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}

	private void redibujar() {
		lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		boton3.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryAvailability"));
		boton2.setText(ResourceBundle.getBundle("Etiquetas").getString("SetAvailability"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	private void closeWindow() {
		this.setVisible(false);
	}
} // @jve:decl-index=0:visual-constraint="0,0"