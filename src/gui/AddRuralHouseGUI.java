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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import businessLogic.ApplicationFacadeInterfaceWS;
import businessLogic.FacadeImplementationWS;
import domain.User;

public class AddRuralHouseGUI extends JFrame {
	private byte userType;
	private String username;

	private FacadeImplementationWS facade = new FacadeImplementationWS();

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JLabel addRuralHouseJLabel;
	private JLabel cityJLabel;
	private JLabel feedbackJLabel;
	private JLabel descriptionJLabel;
	private JEditorPane descriptionJEditorPane;
	private JButton addRuralHouseJButton;
	private JButton cancelJButton;
	private JLabel imageJLabel;
	private JTextField bathroomsJTextField;
	private JTextField bedroomsJTextField;
	private JTextField dinningroomsJTextField;
	private JTextField kitchensJTextField;
	private JTextField parkingPlacesJTextFields;
	private JTextField roomsJTextFields;
	private JComboBox cityJComboBox;
	private DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
	private JLabel bathroomsJLabel;
	private JLabel bedroomsJLabel;
	private JLabel dinningroomsJLabel;
	private JLabel kitchensJLabel;
	private JLabel parkingPlacesJLabel;
	private JLabel roomsJLabel;
	private JButton browseJButton;
	private BufferedImage img;
	private List<String> paths = new ArrayList<>();
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
	private JEditorPane shortDescriptionJEditorPane;

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
					AddRuralHouseGUI frame = new AddRuralHouseGUI(User.USER_TYPE_USER, "Guest");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AddRuralHouseGUI(byte userType, String username) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		this.userType = userType;
		this.username = username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 749, 625);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		addRuralHouseJLabel = new JLabel("Add rural house");
		addRuralHouseJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		addRuralHouseJLabel.setBounds(49, 11, 336, 58);
		contentPane.add(addRuralHouseJLabel);

		cityJLabel = new JLabel("Province:");
		cityJLabel.setBounds(49, 90, 51, 14);
		contentPane.add(cityJLabel);

		descriptionJEditorPane = new JEditorPane();
		descriptionJEditorPane.setBounds(49, 277, 256, 128);
		contentPane.add(descriptionJEditorPane);

		descriptionJLabel = new JLabel("Description:");
		descriptionJLabel.setBounds(49, 252, 57, 14);
		contentPane.add(descriptionJLabel);

		feedbackJLabel = new JLabel("");
		feedbackJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackJLabel.setForeground(Color.RED);
		feedbackJLabel.setBounds(10, 527, 713, 14);
		feedbackJLabel.setVisible(false);
		contentPane.add(feedbackJLabel);

		addRuralHouseJButton = new JButton("Add rural house");
		addRuralHouseJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String city = (String) cityJComboBox.getSelectedItem();
				String shortDescription = shortDescriptionJEditorPane.getText();
				String description = descriptionJEditorPane.getText().toString();
				for (String path : paths) {
					byte[] ruralHouseImageInByte = null;
					if (path.length() > 0) {
						File file = new File(path);
						BufferedImage image = null;
						try {
							image = ImageIO.read(file);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						try {
							ImageIO.write(image, "png", baos);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						ruralHouseImageInByte = baos.toByteArray();
					} else {
						File file = new File(".\\resources\\rural_house.png");
						BufferedImage image = null;
						try {
							image = ImageIO.read(file);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						try {
							ImageIO.write(image, "png", baos);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						ruralHouseImageInByte = baos.toByteArray();
					}
					ruralHouseImagesInBytes.add(ruralHouseImageInByte);
				}
				int bathroomQuantity = Integer.parseInt(bathroomsJTextField.getText().toString());
				int bedroomQuantity = Integer.parseInt(bedroomsJTextField.getText().toString());
				int dinningroomQuantity = Integer.parseInt(dinningroomsJTextField.getText().toString());
				int kitchenQuantity = Integer.parseInt(kitchensJTextField.getText().toString());
				int parkingPlaceQuantity = Integer.parseInt(parkingPlacesJTextFields.getText().toString());
				int roomQuantity = Integer.parseInt(roomsJTextFields.getText().toString());

				if (facade.isValidCity(city)) {
					if (facade.isValidDescription(description)) {
						if (kitchenQuantity >= 1) {
							if (roomQuantity >= 3) {
								if (bathroomQuantity >= 2) {
									ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();
									System.out.println("Imagenes añadidas: " + ruralHouseImagesInBytes.size());
									facade.createRuralHouse(username, shortDescription, description, city,
											ruralHouseImagesInBytes, bathroomQuantity, bedroomQuantity,
											dinningroomQuantity, kitchenQuantity, parkingPlaceQuantity, roomQuantity);
									feedbackJLabel.setText("Rural house added successfully");
									feedbackJLabel.setVisible(true);
									shortDescriptionJEditorPane.setText("");
									descriptionJEditorPane.setText("");
									bathroomsJTextField.setText("");
									bedroomsJTextField.setText("");
									dinningroomsJTextField.setText("");
									kitchensJTextField.setText("");
									parkingPlacesJTextFields.setText("");
									roomsJTextFields.setText("");
									cityJComboBox.setSelectedIndex(0);
									for (JLabel imageView : imageViewJLabelList) {
										try {
											img = ImageIO.read(defaultRuralHouseImageFile);
										} catch (IOException e1) {
											e1.printStackTrace();
										}
										imageView.setIcon(
												new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
										imageView.setVisible(false);
									}
									for (JButton button : imageViewJButtonList) {
										button.setVisible(false);
									}
									nextImageIndex = 0;
								} else {
									feedbackJLabel.setText("Bathroom quantity must be at least 2");
									feedbackJLabel.setVisible(true);
								}
							} else {
								feedbackJLabel.setText("Room quantity must be at least 3");
								feedbackJLabel.setVisible(true);
							}
						} else {
							feedbackJLabel.setText("Kitchen quantity must be at least 1");
							feedbackJLabel.setVisible(true);
						}
					} else {
						feedbackJLabel.setText("Insert the description");
						feedbackJLabel.setVisible(true);
					}
				} else {
					feedbackJLabel.setText("Please, insert the city");
					feedbackJLabel.setVisible(true);
				}
			}
		});
		addRuralHouseJButton.setBounds(515, 552, 109, 23);
		contentPane.add(addRuralHouseJButton);

		cancelJButton = new JButton("Cancel");
		cancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MainGUI(userType, username, null).setVisible(true);
				closeWindow();
			}
		});
		cancelJButton.setBounds(634, 552, 89, 23);
		contentPane.add(cancelJButton);

		cityJComboBox = new JComboBox();
		cityJComboBox.setBounds(49, 116, 128, 20);
		cityJComboBox.setModel(defaultComboBoxModel);
		File file = new File("resources/provincias.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		while (sc.hasNextLine()) {
			cityJComboBox.addItem(sc.nextLine());
		}
		sc.close();
		contentPane.add(cityJComboBox);
		File defaultRuralHouseImageFile = new File(".\\resources\\rural_house.png");
		try {
			img = ImageIO.read(defaultRuralHouseImageFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		imageJLabel = new JLabel("Images:");
		imageJLabel.setBounds(395, 124, 125, 14);
		contentPane.add(imageJLabel);

		browseJButton = new JButton("Browse...");
		browseJButton.addActionListener(new ActionListener() {

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
					feedbackJLabel.setText("Maximum image quantity is 6");
					feedbackJLabel.setVisible(true);
				}
			}
		});
		browseJButton.setBounds(571, 115, 89, 23);
		contentPane.add(browseJButton);

		imageView1JLabel = new JLabel("");
		imageView1JLabel.setBounds(395, 172, 64, 64);
		imageView1JLabel.setVisible(false);
		contentPane.add(imageView1JLabel);

		imageView2JLabel = new JLabel("");
		imageView2JLabel.setBounds(490, 172, 64, 64);
		imageView2JLabel.setVisible(false);
		contentPane.add(imageView2JLabel);

		imageView3JLabel = new JLabel("");
		imageView3JLabel.setBounds(580, 172, 64, 64);
		imageView3JLabel.setVisible(false);
		contentPane.add(imageView3JLabel);

		imageView4JLabel = new JLabel("");
		imageView4JLabel.setBounds(395, 252, 64, 64);
		imageView4JLabel.setVisible(false);
		contentPane.add(imageView4JLabel);

		imageView5JLabel = new JLabel("");
		imageView5JLabel.setBounds(490, 252, 64, 64);
		imageView5JLabel.setVisible(false);
		contentPane.add(imageView5JLabel);

		imageView6JLabel = new JLabel("");
		imageView6JLabel.setBounds(580, 252, 64, 64);
		imageView6JLabel.setVisible(false);
		contentPane.add(imageView6JLabel);

		imageView1JButton = new JButton("");
		imageView1JButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		imageView1JButton.setBackground(Color.RED);
		imageView1JButton.setContentAreaFilled(false);
		imageView1JButton.setOpaque(true);
		imageView1JButton.setBounds(459, 172, 16, 16);
		imageView1JButton.setVisible(false);
		contentPane.add(imageView1JButton);

		imageView2JButton = new JButton("");
		imageView2JButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		imageView2JButton.setBackground(Color.RED);
		imageView2JButton.setContentAreaFilled(false);
		imageView2JButton.setOpaque(true);
		imageView2JButton.setBounds(554, 172, 16, 16);
		imageView2JButton.setVisible(false);
		contentPane.add(imageView2JButton);

		imageView3JButton = new JButton("");
		imageView3JButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		imageView3JButton.setBackground(Color.RED);
		imageView3JButton.setContentAreaFilled(false);
		imageView3JButton.setOpaque(true);
		imageView3JButton.setBounds(644, 172, 16, 16);
		imageView3JButton.setVisible(false);
		contentPane.add(imageView3JButton);

		imageView4JButton = new JButton("");
		imageView4JButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		imageView4JButton.setBackground(Color.RED);
		imageView4JButton.setContentAreaFilled(false);
		imageView4JButton.setOpaque(true);
		imageView4JButton.setBounds(459, 252, 16, 16);
		imageView4JButton.setVisible(false);
		contentPane.add(imageView4JButton);

		imageView5JButton = new JButton("");
		imageView5JButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		imageView5JButton.setBackground(Color.RED);
		imageView5JButton.setContentAreaFilled(false);
		imageView5JButton.setOpaque(true);
		imageView5JButton.setBounds(554, 252, 16, 16);
		imageView5JButton.setVisible(false);
		contentPane.add(imageView5JButton);

		imageView6JButton = new JButton("");
		imageView6JButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		imageView6JButton.setBackground(Color.RED);
		imageView6JButton.setContentAreaFilled(false);
		imageView6JButton.setOpaque(true);
		imageView6JButton.setBounds(644, 252, 16, 16);
		imageView6JButton.setVisible(false);
		contentPane.add(imageView6JButton);

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
					paths.remove(finalI);
					System.out.println(">>>>> Eliminada la dirección a pasar de la casa de índice " + finalI);
					nextImageIndex--;
					System.out.println("Ahora nextImageIndex vale " + nextImageIndex);
				}
			});
		}

		for (JLabel imageView : imageViewJLabelList) {
			try {
				img = ImageIO.read(defaultRuralHouseImageFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			imageView.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
		}

		bathroomsJLabel = new JLabel("Bathrooms:");
		bathroomsJLabel.setBounds(81, 443, 57, 14);
		contentPane.add(bathroomsJLabel);

		bathroomsJTextField = new JTextField();
		bathroomsJTextField.setColumns(10);
		bathroomsJTextField.setBounds(148, 440, 23, 20);
		contentPane.add(bathroomsJTextField);

		bedroomsJLabel = new JLabel("Bedrooms:");
		bedroomsJLabel.setBounds(87, 474, 51, 14);
		contentPane.add(bedroomsJLabel);

		bedroomsJTextField = new JTextField();
		bedroomsJTextField.setColumns(10);
		bedroomsJTextField.setBounds(148, 468, 23, 20);
		contentPane.add(bedroomsJTextField);

		dinningroomsJLabel = new JLabel("Dinningrooms:");
		dinningroomsJLabel.setBounds(70, 499, 68, 14);
		contentPane.add(dinningroomsJLabel);

		dinningroomsJTextField = new JTextField();
		dinningroomsJTextField.setColumns(10);
		dinningroomsJTextField.setBounds(148, 496, 23, 20);
		contentPane.add(dinningroomsJTextField);

		kitchensJLabel = new JLabel("Kitchens:");
		kitchensJLabel.setBounds(228, 443, 44, 14);
		contentPane.add(kitchensJLabel);

		kitchensJTextField = new JTextField();
		kitchensJTextField.setColumns(10);
		kitchensJTextField.setBounds(282, 440, 23, 20);
		contentPane.add(kitchensJTextField);

		parkingPlacesJLabel = new JLabel("Parking places:");
		parkingPlacesJLabel.setBounds(200, 474, 72, 14);
		contentPane.add(parkingPlacesJLabel);

		parkingPlacesJTextFields = new JTextField();
		parkingPlacesJTextFields.setColumns(10);
		parkingPlacesJTextFields.setBounds(282, 471, 23, 20);
		contentPane.add(parkingPlacesJTextFields);

		roomsJLabel = new JLabel("Rooms:");
		roomsJLabel.setBounds(233, 499, 39, 14);
		contentPane.add(roomsJLabel);

		roomsJTextFields = new JTextField();
		roomsJTextFields.setColumns(10);
		roomsJTextFields.setBounds(282, 496, 23, 20);
		contentPane.add(roomsJTextFields);

		JLabel shortDescriptionJLabel = new JLabel("Short description:");
		shortDescriptionJLabel.setBounds(49, 147, 89, 14);
		contentPane.add(shortDescriptionJLabel);

		shortDescriptionJEditorPane = new JEditorPane();
		shortDescriptionJEditorPane.setBounds(49, 172, 256, 64);
		contentPane.add(shortDescriptionJEditorPane);
	}

	public void closeWindow() {
		this.setVisible(false);
	}
}
