package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.Admin;
import domain.Client;
import domain.Owner;
import domain.User;

public class LogInGUI extends JFrame {
	private byte userType;
	private String username;

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPasswordField passwordJPasswordField;
	private JTextField usernameJField;
	private JLabel signInJLabel;
	private JButton signInJButton;
	private JLabel usernameJLabel;
	private JLabel passwordJLabel;
	private JLabel feedbackJLabel;
	private JButton cancelJButton;

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
					LogInGUI frame = new LogInGUI(User.USER_TYPE_USER, "Guest", null);
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
	public LogInGUI(byte userType, String username, String phoneNumber) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.userType = userType;
		this.username = username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		signInJLabel = new JLabel("Log In");
		signInJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		signInJLabel.setBounds(141, 11, 152, 58);
		contentPane.add(signInJLabel);

		signInJButton = new JButton("Log In");
		signInJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String username;
				char[] password;

				username = usernameJField.getText().toString();
				password = passwordJPasswordField.getPassword();
				ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();
				switch (facade.getUserType(username)) {
				case User.USER_TYPE_UNKNOWN:
					feedbackJLabel.setText("Incorrect username");
					feedbackJLabel.setVisible(true);
					break;
				case User.USER_TYPE_CLIENT:
					Client client = facade.getClient(username);
					if (client.checkPassword(password)) {
						new MainGUI(User.USER_TYPE_CLIENT, client.getUsername(), null).setVisible(true);
						closeWindow();
					} else {
						feedbackJLabel.setText("Incorrect password");
						feedbackJLabel.setVisible(true);
					}
					break;
				case User.USER_TYPE_OWNER:
					Owner owner = facade.getOwner(username);
					if (owner.checkPassword(password)) {
						new MainGUI(User.USER_TYPE_OWNER, owner.getUsername(), null).setVisible(true);
						closeWindow();
					} else {
						feedbackJLabel.setText("Incorrect password");
						feedbackJLabel.setVisible(true);
					}
					break;
				case User.USER_TYPE_ADMIN:
					Admin admin = facade.getAdmin(username);
					if (admin.checkPassword(password)) {
						new MainGUI(User.USER_TYPE_ADMIN, admin.getUsername(), null).setVisible(true);
						closeWindow();
					} else {
						feedbackJLabel.setText("Incorrect password");
						feedbackJLabel.setVisible(true);
					}
				}
			}
		});
		signInJButton.setBounds(236, 227, 89, 23);
		contentPane.add(signInJButton);

		passwordJPasswordField = new JPasswordField();
		passwordJPasswordField.setBounds(174, 142, 86, 20);
		contentPane.add(passwordJPasswordField);

		usernameJField = new JTextField();
		usernameJField.setBounds(174, 111, 86, 20);
		contentPane.add(usernameJField);
		usernameJField.setColumns(10);

		usernameJLabel = new JLabel("Username:");
		usernameJLabel.setBounds(112, 114, 52, 14);
		contentPane.add(usernameJLabel);

		passwordJLabel = new JLabel("Password:");
		passwordJLabel.setBounds(112, 145, 52, 14);
		contentPane.add(passwordJLabel);

		feedbackJLabel = new JLabel("");
		feedbackJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackJLabel.setForeground(Color.RED);
		feedbackJLabel.setBounds(10, 173, 414, 14);
		feedbackJLabel.setVisible(false);
		contentPane.add(feedbackJLabel);

		cancelJButton = new JButton("Cancel");
		cancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MainGUI(userType, username, phoneNumber);
				a.setVisible(true);
				closeWindow();
			}
		});
		cancelJButton.setBounds(335, 227, 89, 23);
		contentPane.add(cancelJButton);
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
