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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import domain.Client;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.User;

public class EditAnythingShowElementsGUI extends JFrame {
	byte userType;
	String username;
	String elementsClass;
	List<Object> elements;

	private JTextArea outputJTextArea;
	private JScrollPane scroll;

	private JPanel contentPane;
	private JButton btnCancel;

	private InputStream in = null;
	private BufferedImage img = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					EditAnythingShowElementsGUI frame = new EditAnythingShowElementsGUI(User.USER_TYPE_USER, "Guest",
							null, null);
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
	public EditAnythingShowElementsGUI(byte userType, String username, String elementsClass, List<Object> elements) {
		this.userType = userType;
		this.username = username;
		this.elementsClass = elementsClass;
		this.elements = elements;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 541);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		JLabel lblEditAnything = new JLabel("Edit Anything");
		lblEditAnything.setFont(new Font("Tahoma", Font.PLAIN, 48));
		lblEditAnything.setBounds(75, 45, 284, 58);
		contentPane.add(lblEditAnything);

		JLabel lblElements = new JLabel("Elements:");
		lblElements.setBounds(10, 148, 414, 14);
		contentPane.add(lblElements);

		outputJTextArea = new JTextArea();
		outputJTextArea.setEditable(false);
		outputJTextArea.setLocation(10, 184);
		outputJTextArea.setPreferredSize(new Dimension(384, 0));

		scroll = new JScrollPane(outputJTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setSize(414, 266);
		scroll.setLocation(10, 184);
		contentPane.add(scroll);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowEntitiesGUI(userType, username).setVisible(true);
				closeWindow();
			}
		});
		btnCancel.setBounds(335, 468, 89, 23);
		contentPane.add(btnCancel);

		if (elementsClass.equals("User")) {
			int xPos = 176;
			int yPos = 0;

			Iterator it = elements.iterator();
			while (it.hasNext()) {
				User user = (User) it.next();

				JLabel newUserProfileImageJLabel = new JLabel("");
				JButton newClient = new JButton();
				newClient.setBounds(0, yPos, 394, 128);
				outputJTextArea.add(newClient);

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
				newUserProfileImageJLabel
						.setIcon(new ImageIcon(image.getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
				newUserProfileImageJLabel.setBounds(428, 193, 128, 128);
				newClient.add(newUserProfileImageJLabel);

				newClient.setIconTextGap(30);

				newClient.setVerticalAlignment(SwingConstants.TOP);
				newClient.setHorizontalAlignment(SwingConstants.TRAILING);

				StringBuilder sb = new StringBuilder();
				sb.append("<html>\n");
				sb.append("<body>\n");
				sb.append("<div align=\"right\">");
				sb.append("<b>Username:</b> " + user.getPhoneNumber() + "<br><br>");
				sb.append("</div>\n");
				sb.append("</body>\n");
				sb.append("</html>");
				newClient.setText(sb.toString());

				yPos += 128;

				newClient.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						new EditAnythingEditElementGUI(userType, username, "User", elements, user.getPhoneNumber())
								.setVisible(true);
						closeWindow();
					}
				});
			}

			outputJTextArea.setPreferredSize(new Dimension(384, elements.size() * 128));
		} else if (elementsClass.equals("Client")) {
			int xPos = 176;
			int yPos = 0;

			Iterator it = elements.iterator();
			while (it.hasNext()) {
				Client client = (Client) it.next();
				in = new ByteArrayInputStream(client.getProfileImage());

				JLabel newClientProfileImageJLabel = new JLabel("");
				JButton newClient = new JButton();
				newClient.setBounds(0, yPos, 394, 128);
				outputJTextArea.add(newClient);

				try {
					img = ImageIO.read(in);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				newClientProfileImageJLabel
						.setIcon(new ImageIcon(img.getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
				newClientProfileImageJLabel.setBounds(428, 193, 128, 128);
				newClient.add(newClientProfileImageJLabel);

				newClient.setIconTextGap(30);

				newClient.setVerticalAlignment(SwingConstants.TOP);
				newClient.setHorizontalAlignment(SwingConstants.TRAILING);

				StringBuilder sb = new StringBuilder();
				sb.append("<html>\n");
				sb.append("<body>\n");
				sb.append("<div align=\"right\">");
				sb.append("<b>Username:</b> " + client.getUsername() + "<br><br>");
				sb.append("<b>Email:</b> " + client.getEmail() + "<br>");
				sb.append("</div>\n");
				sb.append("</body>\n");
				sb.append("</html>");
				newClient.setText(sb.toString());

				yPos += 128;

				newClient.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						new EditAnythingEditElementGUI(userType, username, "Client", elements, client.getUsername())
								.setVisible(true);
						closeWindow();
					}
				});
			}

			outputJTextArea.setPreferredSize(new Dimension(384, elements.size() * 128));
		} else if (elementsClass.equals("Owner")) {
			int xPos = 176;
			int yPos = 0;

			Iterator it = elements.iterator();
			while (it.hasNext()) {
				Owner owner = (Owner) it.next();
				in = new ByteArrayInputStream(owner.getProfileImage());

				JLabel newClientProfileImageJLabel = new JLabel("");
				JButton newClient = new JButton();
				newClient.setBounds(0, yPos, 394, 128);
				outputJTextArea.add(newClient);

				try {
					img = ImageIO.read(in);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				newClientProfileImageJLabel
						.setIcon(new ImageIcon(img.getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
				newClientProfileImageJLabel.setBounds(428, 193, 128, 128);
				newClient.add(newClientProfileImageJLabel);

				newClient.setIconTextGap(30);

				newClient.setVerticalAlignment(SwingConstants.TOP);
				newClient.setHorizontalAlignment(SwingConstants.TRAILING);

				StringBuilder sb = new StringBuilder();
				sb.append("<html>\n");
				sb.append("<body>\n");
				sb.append("<div align=\"right\">");
				sb.append("<b>Username:</b> " + owner.getUsername() + "<br><br>");
				sb.append("<b>Email:</b> " + owner.getEmail() + "<br>");
				sb.append("</div>\n");
				sb.append("</body>\n");
				sb.append("</html>");
				newClient.setText(sb.toString());

				yPos += 128;

				newClient.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						new EditAnythingEditElementGUI(userType, username, "Owner", elements, owner.getUsername())
								.setVisible(true);
						closeWindow();
					}
				});
			}

			outputJTextArea.setPreferredSize(new Dimension(384, elements.size() * 128));

		} else if (elementsClass.equals("Offer")) {
			int xPos = 176;
			int yPos = 0;

			Iterator it = elements.iterator();
			while (it.hasNext()) {
				Offer offer = (Offer) it.next();
				in = new ByteArrayInputStream(offer.getRuralHouse().getRuralHouseImages().get(0));

				JLabel newOfferProfileImageJLabel = new JLabel("");
				JButton newOffer = new JButton();
				newOffer.setBounds(0, yPos, 394, 128);
				outputJTextArea.add(newOffer);

				try {
					img = ImageIO.read(in);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				newOfferProfileImageJLabel.setIcon(new ImageIcon(img.getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
				newOfferProfileImageJLabel.setBounds(428, 193, 128, 128);
				newOffer.add(newOfferProfileImageJLabel);

				newOffer.setIconTextGap(30);

				newOffer.setVerticalAlignment(SwingConstants.TOP);
				newOffer.setHorizontalAlignment(SwingConstants.TRAILING);

				StringBuilder sb = new StringBuilder();
				sb.append("<html>\n");
				sb.append("<body>\n");
				sb.append("<div align=\"right\">");
				sb.append("<b>Rural House ID:</b> " + offer.getRuralHouse().getRuralHouseNumber() + "<br>");
				sb.append("<b>Province:</b> " + offer.getRuralHouse().getCity() + "<br>");
				sb.append("<b>Description:</b><p style=\"width:250px;\">" + offer.getRuralHouse().getShortDescription()
						+ "</p>");
				sb.append("<b>Offer ID:</b> " + offer.getOfferNumber() + "<br>");
				sb.append("<b>First day:</b> " + offer.getFirstDay().toString() + "<br>");
				sb.append("<b>Last day:</b> " + offer.getLastDay().toString() + "<br>");
				sb.append("<b>Price:</b> " + offer.getPrice() + "€");
				sb.append("</div>\n");
				sb.append("</body>\n");
				sb.append("</html>");
				newOffer.setText(sb.toString());

				yPos += 128;

				newOffer.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						new EditAnythingEditElementGUI(userType, username, "Offer", elements,
								String.valueOf(offer.getOfferNumber())).setVisible(true);
						closeWindow();
					}
				});
			}

			outputJTextArea.setPreferredSize(new Dimension(384, elements.size() * 128));
		} else if (elementsClass.equals("RuralHouse")) {
			int xPos = 176;
			int yPos = 0;

			Iterator it = elements.iterator();
			while (it.hasNext()) {
				RuralHouse ruralHouse = (RuralHouse) it.next();
				in = new ByteArrayInputStream(ruralHouse.getRuralHouseImages().get(0));

				JLabel newRuralHouseProfileImageJLabel = new JLabel("");
				JButton newOffer = new JButton();
				newOffer.setBounds(0, yPos, 394, 128);
				outputJTextArea.add(newOffer);

				try {
					img = ImageIO.read(in);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				newRuralHouseProfileImageJLabel
						.setIcon(new ImageIcon(img.getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
				newRuralHouseProfileImageJLabel.setBounds(428, 193, 128, 128);
				newOffer.add(newRuralHouseProfileImageJLabel);

				newOffer.setIconTextGap(30);

				newOffer.setVerticalAlignment(SwingConstants.TOP);
				newOffer.setHorizontalAlignment(SwingConstants.TRAILING);

				StringBuilder sb = new StringBuilder();
				sb.append("<html>\n");
				sb.append("<body>\n");
				sb.append("<div align=\"right\">");
				sb.append("<b>Rural House ID:</b> " + ruralHouse.getRuralHouseNumber() + "<br>");
				sb.append("<b>Province:</b> " + ruralHouse.getCity() + "<br>");
				sb.append("<b>Description:</b><p style=\"width:250px;\">" + ruralHouse.getShortDescription() + "</p>");
				sb.append("</div>\n");
				sb.append("</body>\n");
				sb.append("</html>");
				newOffer.setText(sb.toString());

				yPos += 128;

				newOffer.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						new EditAnythingEditElementGUI(userType, username, "RuralHouse", elements,
								String.valueOf(ruralHouse.getRuralHouseNumber())).setVisible(true);
						closeWindow();
					}
				});
			}

			outputJTextArea.setPreferredSize(new Dimension(384, elements.size() * 128));
		}
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
