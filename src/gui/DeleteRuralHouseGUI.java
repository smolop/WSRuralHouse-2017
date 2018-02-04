package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.RuralHouse;
import domain.User;

public class DeleteRuralHouseGUI extends JFrame {
	private byte userType;
	private String username;

	private JPanel contentPane;
	private JButton acceptJButton;
	private JComboBox ruralHouseJComboBox;
	private JLabel selectRuralHouseJLabel;
	private JLabel deleteRuralHouseJLabel;
	private JButton cancelJButton;
	private DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
	private JLabel feedbackJLabel;

	/**
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
					DeleteRuralHouseGUI frame = new DeleteRuralHouseGUI(User.USER_TYPE_USER, "Guest");
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
	public DeleteRuralHouseGUI(byte userType, String username) {
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

		deleteRuralHouseJLabel = new JLabel("Delete rural house");
		deleteRuralHouseJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		deleteRuralHouseJLabel.setBounds(23, 45, 388, 58);
		contentPane.add(deleteRuralHouseJLabel);

		cancelJButton = new JButton("Cancel");
		cancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MainGUI(userType, username, null).setVisible(true);
				closeWindow();
			}
		});
		cancelJButton.setBounds(322, 227, 89, 23);
		contentPane.add(cancelJButton);

		acceptJButton = new JButton("Accept");
		acceptJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ruralHouseJComboBox.getSelectedItem().getClass().equals(RuralHouse.class)) {
					RuralHouse ruralHouse = (RuralHouse) ruralHouseJComboBox.getSelectedItem();
					int ruralHouseNumber = ruralHouse.getHouseNumber();
					ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();
					facade.deleteRuralHouse(ruralHouseNumber);
					feedbackJLabel.setText("Rural house successfully deleted");
					feedbackJLabel.setVisible(true);
					ruralHouseJComboBox.removeItem(ruralHouse);
					if (ruralHouseJComboBox.getItemCount() == 0) {
						ruralHouseJComboBox.addItem("There are no rural houses");
						ruralHouseJComboBox.setEnabled(false);
					}
				} else {
					feedbackJLabel.setText("There are no rural houses to delete");
					feedbackJLabel.setVisible(true);
				}
			}
		});
		acceptJButton.setBounds(223, 227, 89, 23);
		contentPane.add(acceptJButton);

		ruralHouseJComboBox = new JComboBox();
		ruralHouseJComboBox.setBounds(153, 139, 200, 20);
		ruralHouseJComboBox.setModel(defaultComboBoxModel);
		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();
		Vector<RuralHouse> ruralHouses = facade.getOwnerRuralHouses(username);
		for (RuralHouse ruralHouse : ruralHouses) {
			ruralHouseJComboBox.addItem(ruralHouse);
		}
		if (ruralHouseJComboBox.getItemCount() == 0) {
			ruralHouseJComboBox.addItem("There are no rural houses");
			ruralHouseJComboBox.setEnabled(false);
		}
		contentPane.add(ruralHouseJComboBox);

		selectRuralHouseJLabel = new JLabel("Select rural house:");
		selectRuralHouseJLabel.setBounds(53, 142, 90, 14);
		contentPane.add(selectRuralHouseJLabel);

		feedbackJLabel = new JLabel("");
		feedbackJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackJLabel.setForeground(Color.RED);
		feedbackJLabel.setBounds(10, 202, 414, 14);
		feedbackJLabel.setVisible(false);
		contentPane.add(feedbackJLabel);
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
