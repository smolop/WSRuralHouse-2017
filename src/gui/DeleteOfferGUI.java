package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.Offer;
import domain.RuralHouse;
import domain.User;

public class DeleteOfferGUI extends JFrame {
	private byte userType;
	private String username;

	private JPanel contentPane;
	private JLabel deleteOfferJLabel;
	private JLabel selectRuralHouseJLabel;
	private JLabel selectOfferJLabel;
	private JComboBox ruralHouseJComboBox;
	private JComboBox offerJComboBox;
	private JButton acceptJButton;
	private JButton cancelJButton;

	private DefaultComboBoxModel ruralHousesDefaultComboBoxModel = new DefaultComboBoxModel();
	private DefaultComboBoxModel offersDefaultComboBoxModel = new DefaultComboBoxModel();
	private JLabel feedbackJLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					DeleteOfferGUI frame = new DeleteOfferGUI(User.USER_TYPE_USER, "Guest");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DeleteOfferGUI(byte userType, String username) {
		this.userType = userType;
		this.username = username;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		deleteOfferJLabel = new JLabel("Delete offer");
		deleteOfferJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		deleteOfferJLabel.setBounds(93, 45, 248, 58);
		contentPane.add(deleteOfferJLabel);

		cancelJButton = new JButton("Cancel");
		cancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MainGUI(userType, username, null).setVisible(true);
				closeWindow();
			}
		});
		cancelJButton.setBounds(335, 227, 89, 23);
		contentPane.add(cancelJButton);

		acceptJButton = new JButton("Accept");
		acceptJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (offerJComboBox.getSelectedItem().getClass().equals(Offer.class)) {
					Offer offer = (Offer) offerJComboBox.getSelectedItem();
					ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();
					facade.deleteOffer(offer.getOfferNumber());
					feedbackJLabel.setText("Offer successfully deleted");
					feedbackJLabel.setVisible(true);
					offerJComboBox.removeItem(offer);
					if (offerJComboBox.getItemCount() == 0) {
						offerJComboBox.addItem("There are no offers available for this rural house");
						offerJComboBox.setEnabled(false);
					}
				} else {
					feedbackJLabel.setText("There are no offers to delete");
					feedbackJLabel.setVisible(true);
				}
			}
		});
		acceptJButton.setBounds(236, 227, 89, 23);
		contentPane.add(acceptJButton);

		selectRuralHouseJLabel = new JLabel("Select rural house:");
		selectRuralHouseJLabel.setBounds(10, 114, 90, 14);
		contentPane.add(selectRuralHouseJLabel);

		selectOfferJLabel = new JLabel("Select offer:");
		selectOfferJLabel.setBounds(10, 170, 60, 14);
		contentPane.add(selectOfferJLabel);

		offerJComboBox = new JComboBox();
		offerJComboBox.setBounds(10, 195, 414, 20);
		offerJComboBox.setModel(offersDefaultComboBoxModel);
		contentPane.add(offerJComboBox);

		ruralHouseJComboBox = new JComboBox();
		ruralHouseJComboBox.setBounds(10, 139, 414, 20);
		ruralHouseJComboBox.setModel(ruralHousesDefaultComboBoxModel);
		ruralHouseJComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (ruralHouseJComboBox.getSelectedItem().getClass().equals(RuralHouse.class)) {
					RuralHouse ruralHouse = (RuralHouse) ruralHouseJComboBox.getSelectedItem();
					int ruralHouseNumber = ruralHouse.getHouseNumber();
					ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();
					Vector<Offer> offers = facade.getRuralHouseOffers(ruralHouseNumber);
					offerJComboBox.removeAllItems();
					offerJComboBox.setEnabled(true);
					for (Offer offer : offers) {
						offerJComboBox.addItem(offer);
					}
					if (offerJComboBox.getItemCount() == 0) {
						offerJComboBox.addItem("There are no offers available for this rural house");
						offerJComboBox.setEnabled(false);
					}
				} else {
					offerJComboBox.addItem("There are no offers available for this rural house");
					offerJComboBox.setEnabled(false);
				}
			}
		});
		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();
		Vector<RuralHouse> ruralHouses = facade.getOwnerRuralHouses(username);
		ruralHouseJComboBox.setEnabled(true);
		for (RuralHouse ruralHouse : ruralHouses) {
			ruralHouseJComboBox.addItem(ruralHouse);
		}
		if (ruralHouseJComboBox.getItemCount() == 0) {
			ruralHouseJComboBox.addItem("There are no rural houses");
			ruralHouseJComboBox.setEnabled(false);
		}
		contentPane.add(ruralHouseJComboBox);

		feedbackJLabel = new JLabel("");
		feedbackJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackJLabel.setForeground(Color.RED);
		feedbackJLabel.setBounds(10, 236, 216, 14);
		feedbackJLabel.setVisible(false);
		contentPane.add(feedbackJLabel);
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
