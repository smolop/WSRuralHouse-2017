package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.User;

public class EditAnythingShowEntitiesGUI extends JFrame {
	byte userType;
	String username;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					EditAnythingShowEntitiesGUI frame = new EditAnythingShowEntitiesGUI(User.USER_TYPE_USER, "Guest");
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
	public EditAnythingShowEntitiesGUI(byte userType, String username) {
		this.userType = userType;
		this.username = username;

		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		JLabel lblEditAnything = new JLabel("Edit Anything");
		lblEditAnything.setFont(new Font("Tahoma", Font.PLAIN, 48));
		lblEditAnything.setBounds(115, 45, 284, 58);
		contentPane.add(lblEditAnything);

		JButton btnUsers = new JButton("Users");
		btnUsers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowElementsGUI(userType, username, "User", facade.getAllUsers()).setVisible(true);
				closeWindow();
			}
		});
		btnUsers.setBounds(10, 148, 89, 23);
		contentPane.add(btnUsers);

		JButton btnClients = new JButton("Clients");
		btnClients.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowElementsGUI(userType, username, "Client", facade.getAllClients()).setVisible(true);
				closeWindow();
			}
		});
		btnClients.setBounds(109, 148, 89, 23);
		contentPane.add(btnClients);

		JButton btnOwners = new JButton("Owners");
		btnOwners.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowElementsGUI(userType, username, "Owner", facade.getAllOwners()).setVisible(true);
				closeWindow();
			}
		});
		btnOwners.setBounds(208, 148, 89, 23);
		contentPane.add(btnOwners);

		JButton btnOffers = new JButton("Offers");
		btnOffers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowElementsGUI(userType, username, "Offer", facade.getAllOffers()).setVisible(true);
				closeWindow();
			}
		});
		btnOffers.setBounds(307, 148, 89, 23);
		contentPane.add(btnOffers);

		JButton btnRuralHouses = new JButton("Rural Houses");
		btnRuralHouses.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditAnythingShowElementsGUI(userType, username, "RuralHouse",
						Arrays.asList(facade.getAllRuralHouses().toArray())).setVisible(true);
				closeWindow();
			}
		});
		btnRuralHouses.setBounds(406, 148, 95, 23);
		contentPane.add(btnRuralHouses);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MainGUI(userType, username, null).setVisible(true);
				closeWindow();
			}
		});
		btnCancel.setBounds(412, 227, 89, 23);
		contentPane.add(btnCancel);
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
