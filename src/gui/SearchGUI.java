package gui;

import java.awt.Dimension;
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
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.Offer;
import domain.RuralHouse;
import domain.User;

public class SearchGUI extends JFrame {

	private byte userType;
	private String username;

	private JPanel contentPane;
	private JTextField searchJTextField;
	private JTextField minimumPriceJTextField;
	private JTextField maximumPriceJTextField;
	private JTextField dayQuantityJTextField;
	private JTextField ruralHouseNumberJTextField;
	private JTextField offerNumberJTextField;
	private JLabel searchTitleJLabel;
	private JButton searchJButton;
	private JComboBox citiesJComboBox;
	private JButton cancelJButton;

	private Vector<RuralHouse> ruralHouses;
	private JXDatePicker firstDayJXDatePicker;
	private JXDatePicker lastDayJXDatePicker;
	private JLabel ruralHouseNumberJLabel;
	private JLabel offerNumberJLabel;
	private JLabel citiesJLabel;
	private JLabel minimumPriceJLabel;
	private JLabel maximumPriceJLabel;
	private JLabel dayQuantityJLabel;
	private JLabel firstDayJLabel;
	private JLabel lastDayJLabel;
	private JTextArea outputJTextArea;
	private JScrollPane scroll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					SearchGUI frame = new SearchGUI(User.USER_TYPE_USER, "Guest", null);
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
	public SearchGUI(byte userType, String username, String phoneNumber) {
		this.userType = userType;
		this.username = username;
		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		cancelJButton = new JButton("Cancel");
		cancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MainGUI(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		cancelJButton.setBounds(885, 677, 89, 23);
		contentPane.add(cancelJButton);

		searchTitleJLabel = new JLabel("Search");
		searchTitleJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		searchTitleJLabel.setBounds(420, 45, 143, 58);
		contentPane.add(searchTitleJLabel);

		searchJTextField = new JTextField();
		searchJTextField.setBounds(176, 151, 533, 20);
		contentPane.add(searchJTextField);
		searchJTextField.setColumns(10);

		searchJButton = new JButton("Search");
		searchJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				outputJTextArea.removeAll();
				outputJTextArea.repaint();
				outputJTextArea.setPreferredSize(new Dimension(500, 0));

				ruralHouses = new Vector<RuralHouse>();
				ruralHouses = facade.getAllRuralHouses();

				int ruralHouseNumber = 0;
				String ruralHouseNumberInput = ruralHouseNumberJTextField.getText().toString();
				if (!ruralHouseNumberInput.equals("") && facade.isValidRuralHouseNumber(ruralHouseNumberInput)) {
					ruralHouseNumber = Integer.parseInt(ruralHouseNumberInput);
				} else {
					ruralHouseNumber = -1;
				}

				int offerNumber = 0;
				String offerNumberInput = offerNumberJTextField.getText().toString();
				if (!offerNumberInput.equals("") && facade.isValidOfferNumber(offerNumberInput)) {
					offerNumber = Integer.parseInt(offerNumberInput);
				} else {
					offerNumber = -1;
				}

				String city = (String) citiesJComboBox.getSelectedItem();

				int minimumPrice = 0;
				String minimumPriceInput = minimumPriceJTextField.getText().toString();
				if (!minimumPriceInput.equals("") && facade.isValidMinimumPrice(minimumPriceInput)) {
					minimumPrice = Integer.parseInt(minimumPriceInput);
				} else {
					minimumPrice = -1;
				}

				int maximumPrice = 0;
				String maximumPriceInput = maximumPriceJTextField.getText().toString();
				if (!maximumPriceInput.equals("") && facade.isValidMaximumPrice(maximumPriceInput)) {
					maximumPrice = Integer.parseInt(maximumPriceInput);
				} else {
					maximumPrice = -1;
				}

				Date firstDay = firstDayJXDatePicker.getDate();
				Date lastDay = lastDayJXDatePicker.getDate();

				int dayQuantity = 0;
				String dayQuantityInput = dayQuantityJTextField.getText().toString();
				if (!dayQuantityInput.equals("") && facade.isValidDayQuantity(dayQuantityInput)) {
					dayQuantity = Integer.parseInt(dayQuantityInput);
				} else {
					dayQuantity = -1;
				}

				String searchString = searchJTextField.getText().toString();

				Vector<RuralHouse> filteredRuralHouses;

				String cityInput = (String) citiesJComboBox.getSelectedItem();
				if (cityInput == null) {
					cityInput = "";
				}

				System.out.println("ANTES DE FILTRAR -- " + ruralHouses.size());

				filteredRuralHouses = facade.searchOffer(ruralHouses, ruralHouseNumber, offerNumber, cityInput,
						minimumPrice, maximumPrice, firstDay, lastDay, dayQuantity, searchString);

				System.out.println("DESPUES DE FILTRAR -- " + filteredRuralHouses.size());

				int xPos = 176;
				int yPos = 0;
				int offerQuantity = 0;
				Vector<JButton> results = new Vector<JButton>();
				for (int i = 0; i < filteredRuralHouses.size(); i++) {
					System.out.println("ESTOY MOSTRANDO LA CASA --" + filteredRuralHouses.get(i).getHouseNumber());
					Vector<Offer> filteredOffers = filteredRuralHouses.get(i).getAllOffers();
					for (int j = 0; j < filteredOffers.size(); j++) {
						if (filteredOffers.get(j).isBooked()) {
							continue;
						}

						System.out.println("ESTOY MOSTRANDO LA OFERTA --" + filteredOffers.get(j).getOfferNumber());

						JButton newResult = new JButton();
						newResult.setBounds(0, yPos, 610, 128);
						outputJTextArea.add(newResult);

						JLabel newRuralHouseImageJLabel = new JLabel("");
						InputStream in = null;
						BufferedImage img = null;
						System.out.println("LA casa en cuestion es " + filteredRuralHouses.get(i).toString());
						System.out.println("El vector se imagenes es "
								+ filteredRuralHouses.get(i).getRuralHouseImages().toString());
						System.out.println(
								"El tamaño al traerlo es " + filteredRuralHouses.get(i).getRuralHouseImages().size());
						in = new ByteArrayInputStream(filteredRuralHouses.get(i).getRuralHouseImages().get(0));
						try {
							img = ImageIO.read(in);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						newRuralHouseImageJLabel
								.setIcon(new ImageIcon(img.getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
						newRuralHouseImageJLabel.setBounds(428, 193, 128, 128);
						newResult.add(newRuralHouseImageJLabel);

						newResult.setIconTextGap(30);
						newResult.setVerticalAlignment(SwingConstants.TOP);
						newResult.setHorizontalAlignment(SwingConstants.TRAILING);
						StringBuilder sb = new StringBuilder();
						sb.append("<html>\n");
						sb.append("<body>\n");
						sb.append("<div align=\"right\">");
						sb.append("<b>Rural House ID:</b> " + filteredRuralHouses.get(i).getHouseNumber() + "<br>");
						sb.append("<b>Province:</b> " + filteredRuralHouses.get(i).getCity() + "<br>");
						sb.append("<b>Short description:</b><p style=\"width:250px;\">"
								+ filteredRuralHouses.get(i).getShortDescription() + "</p>");
						sb.append("<b>Offer ID:</b> " + filteredOffers.get(j).getOfferNumber() + "<br>");
						sb.append("<b>First day:</b> " + filteredOffers.get(j).getFirstDay().toString() + "<br>");
						sb.append("<b>Last day:</b> " + filteredOffers.get(j).getLastDay().toString() + "<br>");
						sb.append("<b>Price:</b> " + filteredOffers.get(j).getPrice() + "€");
						sb.append("</div>\n");
						sb.append("</body>\n");
						sb.append("</html>");
						newResult.setText(sb.toString());

						final int finalJ = j;
						newResult.addActionListener(new java.awt.event.ActionListener() {
							@Override
							public void actionPerformed(java.awt.event.ActionEvent e) {
								JFrame a = new SeeOfferDetailsGUI(userType, username, phoneNumber,
										filteredOffers.get(finalJ).getOfferNumber(), false);
								a.setVisible(true);

								closeWindow();
							}
						});

						yPos += 128;
						offerQuantity++;

					}
				}

				outputJTextArea.setPreferredSize(new Dimension(500, offerQuantity * 128));
			}
		});
		searchJButton.setBounds(719, 150, 89, 23);
		contentPane.add(searchJButton);

		citiesJComboBox = new JComboBox();
		citiesJComboBox.setBounds(10, 339, 100, 20);
		citiesJComboBox.addItem("");
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
		contentPane.add(citiesJComboBox);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = null;
		try {
			d = sdf.parse("01/01/1990");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		firstDayJXDatePicker = new JXDatePicker();
		firstDayJXDatePicker.setDate(d);
		firstDayJXDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		firstDayJXDatePicker.setBounds(10, 563, 100, 19);
		contentPane.add(firstDayJXDatePicker);

		try {
			d = sdf.parse("01/01/2100");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		lastDayJXDatePicker = new JXDatePicker();
		lastDayJXDatePicker.setDate(d);
		lastDayJXDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		lastDayJXDatePicker.setBounds(10, 618, 100, 19);
		contentPane.add(lastDayJXDatePicker);

		minimumPriceJTextField = new JTextField();
		minimumPriceJTextField.setBounds(10, 395, 100, 20);
		minimumPriceJTextField.setColumns(10);
		contentPane.add(minimumPriceJTextField);

		maximumPriceJTextField = new JTextField();
		maximumPriceJTextField.setBounds(10, 451, 100, 20);
		maximumPriceJTextField.setColumns(10);
		contentPane.add(maximumPriceJTextField);

		dayQuantityJTextField = new JTextField();
		dayQuantityJTextField.setBounds(10, 507, 100, 20);
		contentPane.add(dayQuantityJTextField);
		dayQuantityJTextField.setColumns(10);

		ruralHouseNumberJTextField = new JTextField();
		ruralHouseNumberJTextField.setBounds(10, 227, 100, 20);
		contentPane.add(ruralHouseNumberJTextField);
		ruralHouseNumberJTextField.setColumns(10);

		offerNumberJTextField = new JTextField();
		offerNumberJTextField.setBounds(10, 283, 100, 20);
		contentPane.add(offerNumberJTextField);
		offerNumberJTextField.setColumns(10);

		ruralHouseNumberJLabel = new JLabel("Rural house number:");
		ruralHouseNumberJLabel.setBounds(10, 202, 100, 14);
		contentPane.add(ruralHouseNumberJLabel);

		offerNumberJLabel = new JLabel("Offer number:");
		offerNumberJLabel.setBounds(10, 258, 71, 14);
		contentPane.add(offerNumberJLabel);

		citiesJLabel = new JLabel("Cities:");
		citiesJLabel.setBounds(10, 314, 30, 14);
		contentPane.add(citiesJLabel);

		minimumPriceJLabel = new JLabel("Minimum price:");
		minimumPriceJLabel.setBounds(10, 370, 71, 14);
		contentPane.add(minimumPriceJLabel);

		maximumPriceJLabel = new JLabel("Maximum price:");
		maximumPriceJLabel.setBounds(10, 426, 74, 14);
		contentPane.add(maximumPriceJLabel);

		dayQuantityJLabel = new JLabel("Day quantity:");
		dayQuantityJLabel.setBounds(10, 482, 66, 14);
		contentPane.add(dayQuantityJLabel);

		firstDayJLabel = new JLabel("First day:");
		firstDayJLabel.setBounds(10, 538, 46, 14);
		contentPane.add(firstDayJLabel);

		lastDayJLabel = new JLabel("Last day:");
		lastDayJLabel.setBounds(10, 593, 46, 14);
		contentPane.add(lastDayJLabel);

		outputJTextArea = new JTextArea();
		outputJTextArea.setEditable(false);
		outputJTextArea.setLocation(176, 202);
		outputJTextArea.setPreferredSize(new Dimension(500, 0));

		scroll = new JScrollPane(outputJTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setSize(630, 435);
		scroll.setLocation(176, 202);
		contentPane.add(scroll);

	}

	private void closeWindow() {
		this.setVisible(false);
	}
}