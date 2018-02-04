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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

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

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.Admin;
import domain.Client;
import domain.Conversation;
import domain.Owner;
import domain.User;

public class ChatListGUI extends JFrame {
	private byte userType;
	private String username;
	private String phoneNumber;

	private JTextArea outputJTextArea;
	private JScrollPane scroll;

	private JPanel contentPane;
	private JLabel chatListJLabel;
	private JButton cancelJButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ChatListGUI frame = new ChatListGUI(User.USER_TYPE_USER, "Guest", "943000000");
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
	public ChatListGUI(byte userType, String username, String phoneNumber) {
		this.userType = userType;
		this.username = username;
		this.phoneNumber = phoneNumber;

		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		User user = null;
		Client client = null;
		Owner owner = null;
		Admin admin = null;
		switch (userType) {
		case User.USER_TYPE_USER:
			if (phoneNumber != null) {
				user = facade.getUser(phoneNumber);
			} else {
				// TODO: mostrar login de user
			}
			break;
		case User.USER_TYPE_CLIENT:
			client = facade.getClient(username);
			break;
		case User.USER_TYPE_OWNER:
			owner = facade.getOwner(username);
			break;
		case User.USER_TYPE_ADMIN:
			admin = facade.getAdmin(username);
			break;
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		chatListJLabel = new JLabel("Chat List");
		chatListJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		chatListJLabel.setBounds(125, 45, 184, 58);
		contentPane.add(chatListJLabel);

		cancelJButton = new JButton("Cancel");
		cancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MainGUI(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		cancelJButton.setBounds(335, 452, 89, 23);
		contentPane.add(cancelJButton);

		outputJTextArea = new JTextArea();
		outputJTextArea.setEditable(false);
		outputJTextArea.setLocation(10, 150);
		outputJTextArea.setPreferredSize(new Dimension(384, 0));

		scroll = new JScrollPane(outputJTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setSize(414, 266);
		scroll.setLocation(10, 150);
		contentPane.add(scroll);

		List<Conversation> starterConversations = null;
		List<Conversation> listenerConversations = null;
		switch (userType) {
		case User.USER_TYPE_CLIENT:
			starterConversations = client.getStarterCoversations();
			listenerConversations = client.getListenerConversations();
			break;
		case User.USER_TYPE_OWNER:
			starterConversations = owner.getStarterCoversations();
			listenerConversations = owner.getListenerConversations();
			break;
		case User.USER_TYPE_ADMIN:
			starterConversations = admin.getStarterCoversations();
			listenerConversations = admin.getListenerConversations();
			break;
		}

		int xPos = 176;
		int yPos = 0;
		int conversationQuantity = 0;
		Vector<JButton> chats = new Vector<JButton>();
		for (int i = 0; i < starterConversations.size(); i++) {
			int conversationNumber = starterConversations.get(i).getConversationNumber();

			JButton newChat = new JButton();
			newChat.setBounds(0, yPos, 384, 128);
			outputJTextArea.add(newChat);

			JLabel newPartnerProfileImageJLabel = new JLabel("");
			InputStream in = null;
			BufferedImage img = null;
			Client clientPartner = null;
			Owner ownerPartner = null;
			Admin adminPartner = null;
			String usernameOther = null;
			String emailOther = null;
			switch (starterConversations.get(i).getListenerUserType()) {
			case User.USER_TYPE_CLIENT:
				clientPartner = starterConversations.get(i).getListenerClient();
				in = new ByteArrayInputStream(clientPartner.getProfileImage());
				usernameOther = clientPartner.getUsername();
				emailOther = client.getEmail();
				break;
			case User.USER_TYPE_OWNER:
				ownerPartner = starterConversations.get(i).getListenerOwner();
				in = new ByteArrayInputStream(ownerPartner.getProfileImage());
				usernameOther = ownerPartner.getUsername();
				emailOther = ownerPartner.getEmail();
				break;
			case User.USER_TYPE_ADMIN:
				adminPartner = starterConversations.get(i).getListenerAdmin();
				in = new ByteArrayInputStream(ownerPartner.getProfileImage());
				usernameOther = adminPartner.getUsername();
				emailOther = "ruralhouse@ruralhouse.com";
			}
			try {
				img = ImageIO.read(in);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			newPartnerProfileImageJLabel.setIcon(new ImageIcon(img.getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
			newPartnerProfileImageJLabel.setBounds(428, 193, 128, 128);
			newChat.add(newPartnerProfileImageJLabel);

			newChat.setIconTextGap(30);
			newChat.setVerticalAlignment(SwingConstants.TOP);
			newChat.setHorizontalAlignment(SwingConstants.TRAILING);
			StringBuilder sb = new StringBuilder();
			sb.append("<html>\n");
			sb.append("<body>\n");
			sb.append("<div align=\"right\">");
			sb.append("<b>Username:</b> " + usernameOther + "<br><br>");
			sb.append("<b>Email:</b> " + emailOther + "<br>");
			sb.append("</div>\n");
			sb.append("</body>\n");
			sb.append("</html>");
			newChat.setText(sb.toString());

			newChat.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new ChatGUI(userType, username, phoneNumber, conversationNumber);
					a.setVisible(true);
					closeWindow();
				}
			});

			yPos += 128;
			conversationQuantity++;
		}

		for (int i = 0; i < listenerConversations.size(); i++) {
			int conversationNumber = listenerConversations.get(i).getConversationNumber();

			JButton newChat = new JButton();
			newChat.setBounds(0, yPos, 384, 128);
			outputJTextArea.add(newChat);

			JLabel newPartnerProfileImageJLabel = new JLabel("");
			InputStream in = null;
			BufferedImage img = null;
			Client clientPartner = null;
			Owner ownerPartner = null;
			Admin adminPartner = null;
			String usernameOther = null;
			String emailOther = null;
			switch (listenerConversations.get(i).getStarterUseType()) {
			case User.USER_TYPE_CLIENT:
				clientPartner = listenerConversations.get(i).getStarterClient();
				in = new ByteArrayInputStream(clientPartner.getProfileImage());
				usernameOther = clientPartner.getUsername();
				emailOther = clientPartner.getEmail();
				break;
			case User.USER_TYPE_OWNER:
				ownerPartner = listenerConversations.get(i).getStarterOwner();
				in = new ByteArrayInputStream(ownerPartner.getProfileImage());
				usernameOther = ownerPartner.getUsername();
				emailOther = ownerPartner.getEmail();
				break;
			case User.USER_TYPE_ADMIN:
				adminPartner = listenerConversations.get(i).getStarterAdmin();
				in = new ByteArrayInputStream(adminPartner.getProfileImage());
				usernameOther = adminPartner.getUsername();
				emailOther = "ruralhouse@ruralhouse.com";
				break;
			}
			try {
				img = ImageIO.read(in);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			newPartnerProfileImageJLabel.setIcon(new ImageIcon(img.getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
			newPartnerProfileImageJLabel.setBounds(428, 193, 128, 128);
			newChat.add(newPartnerProfileImageJLabel);

			newChat.setIconTextGap(30);
			newChat.setVerticalAlignment(SwingConstants.TOP);
			newChat.setHorizontalAlignment(SwingConstants.TRAILING);
			StringBuilder sb = new StringBuilder();
			sb.append("<html>\n");
			sb.append("<body>\n");
			sb.append("<div align=\"right\">");
			sb.append("<b>Username:</b> " + usernameOther + "<br><br>");
			sb.append("<b>Email:</b> " + emailOther + "<br>");
			sb.append("</div>\n");
			sb.append("</body>\n");
			sb.append("</html>");
			newChat.setText(sb.toString());

			newChat.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new ChatGUI(userType, username, phoneNumber, conversationNumber);
					a.setVisible(true);
					closeWindow();
				}
			});

			yPos += 128;
			conversationQuantity++;
		}

		outputJTextArea.setPreferredSize(new Dimension(384, conversationQuantity * 128));
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
