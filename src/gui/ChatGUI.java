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
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
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

public class ChatGUI extends JFrame {
	byte userType;
	String username;
	Integer conversationNumber;

	private JTextArea outputJTextArea;
	private JScrollPane scroll;

	private JPanel contentPane;
	private JButton cancelJButton;
	private JLabel usernameMeJLabel;
	private JLabel usernameOtherJLabel;
	private JTextArea messageJTextArea;
	private JButton sendJButton;
	private JLabel profileImageMeJLabel;
	private JLabel profileImageOtherJLabel;
	private JLabel chatJLabel;
	private BufferedImage img;
	InputStream in = null;
	private Map<Date, String[]> messages = new TreeMap<Date, String[]>();
	private Conversation conversation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ChatGUI frame = new ChatGUI(User.USER_TYPE_USER, "Guest", null, 1);
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
	public ChatGUI(byte userType, String username, String phoneNumber, Integer conversationNumber) {
		this.userType = userType;
		this.username = username;
		this.conversationNumber = conversationNumber;

		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		Client client = null;
		Owner owner = null;
		Admin admin = null;

		conversation = facade.getConversation(conversationNumber);

		System.out.println("antes de meterle ---> " + messages.toString());
		System.out.println("la conversation es ---> " + conversation.toString());
		messages = conversation.getMessages();
		System.out.println("antes de meterle ---> " + messages.toString());

		String usernameOther;
		if (conversation.getStarterUsername().equals(username)) {
			usernameOther = conversation.getListenerUsername();
		} else {
			usernameOther = conversation.getStarterUsername();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

		chatJLabel = new JLabel("Chat");
		chatJLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
		chatJLabel.setBounds(168, 45, 97, 58);
		contentPane.add(chatJLabel);

		profileImageOtherJLabel = new JLabel("New label");
		profileImageOtherJLabel.setBounds(10, 114, 64, 64);
		in = null;
		if (conversation.getStarterUsername().equals(username)) {
			in = new ByteArrayInputStream(
					facade.getUserProfileImage(conversation.getListenerUserType(), conversation.getListenerUsername()));
			try {
				img = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			profileImageOtherJLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
		} else {
			in = new ByteArrayInputStream(
					facade.getUserProfileImage(conversation.getStarterUseType(), conversation.getStarterUsername()));
			try {
				img = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			profileImageOtherJLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
		}
		contentPane.add(profileImageOtherJLabel);

		profileImageMeJLabel = new JLabel("New label");
		profileImageMeJLabel.setBounds(360, 114, 64, 64);
		in = null;
		File file;
		switch (userType) {
		case User.USER_TYPE_USER:
			file = new File(".\\resources\\user.png");
			try {
				img = ImageIO.read(file);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			profileImageMeJLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			break;
		case User.USER_TYPE_CLIENT:
			client = facade.getClient(username);
			in = new ByteArrayInputStream(client.getProfileImage());
			try {
				img = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			profileImageMeJLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			break;
		case User.USER_TYPE_OWNER:
			owner = facade.getOwner(username);
			in = new ByteArrayInputStream(owner.getProfileImage());
			try {
				img = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			profileImageMeJLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			break;
		case User.USER_TYPE_ADMIN:
			file = new File(".\\resources\\admin.png");
			try {
				img = ImageIO.read(file);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			profileImageMeJLabel.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			break;
		}
		contentPane.add(profileImageMeJLabel);

		usernameMeJLabel = new JLabel(username);
		usernameMeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		usernameMeJLabel.setBounds(286, 189, 128, 14);
		contentPane.add(usernameMeJLabel);

		usernameOtherJLabel = new JLabel(usernameOther);
		usernameOtherJLabel.setBounds(20, 189, 128, 14);
		contentPane.add(usernameOtherJLabel);

		cancelJButton = new JButton("Cancel");
		cancelJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// String phoneNumber = null;
				// if (userType == User.USER_TYPE_USER) {
				// phoneNumber = facade.getUserPhoneNumber(userType, username);
				// }
				// new ChatListGUI(userType, username, phoneNumber);
				// closeWindow();
				new MainGUI(userType, username, phoneNumber).setVisible(true);
				closeWindow();
			}
		});
		cancelJButton.setBounds(334, 677, 89, 23);
		contentPane.add(cancelJButton);

		outputJTextArea = new JTextArea();
		outputJTextArea.setEditable(false);
		outputJTextArea.setLocation(10, 214);
		outputJTextArea.setPreferredSize(new Dimension(384, 0));

		scroll = new JScrollPane(outputJTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setSize(414, 381);
		scroll.setLocation(10, 214);
		contentPane.add(scroll);

		messageJTextArea = new JTextArea();
		messageJTextArea.setBounds(9, 611, 315, 55);
		contentPane.add(messageJTextArea);

		sendJButton = new JButton("Send");
		sendJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = messageJTextArea.getText();
				facade.sendMessage(conversation.getConversationNumber(), username, message);
				messageJTextArea.setText("");

				conversation = facade.getConversation(conversationNumber);
				messages = conversation.getMessages();

				outputJTextArea.removeAll();
				outputJTextArea.repaint();
				outputJTextArea.setPreferredSize(new Dimension(384, 0));

				int xPos = 176;
				int yPos = 0;
				int messageQuantity = 0;
				Iterator it = messages.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry<Date, String[]> pair = (Map.Entry<Date, String[]>) it.next();

					JButton newMessage = new JButton();
					newMessage.setBounds(0, yPos, 384, 128);
					outputJTextArea.add(newMessage);

					newMessage.setVerticalAlignment(SwingConstants.TOP);
					if (pair.getValue()[0].equals(username)) {
						newMessage.setHorizontalAlignment(SwingConstants.RIGHT);
					} else {
						newMessage.setHorizontalAlignment(SwingConstants.LEFT);
					}
					StringBuilder sb = new StringBuilder();
					sb.append("<html>\n");
					sb.append("<body>\n");
					if (pair.getValue()[0].equals(username)) {
						sb.append("<div align=\"right\">");
					} else {
						sb.append("<div align=\"left\">");
					}
					sb.append("<b>" + pair.getKey() + "</b><br><br>");
					sb.append(pair.getValue()[1] + "<br>");
					sb.append("</div>\n");
					sb.append("</body>\n");
					sb.append("</html>");
					newMessage.setText(sb.toString());

					yPos += 128;
					messageQuantity++;
				}

				outputJTextArea.setPreferredSize(new Dimension(384, messageQuantity * 128));
				JScrollBar vertical = scroll.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
			}
		});
		sendJButton.setBounds(334, 611, 89, 55);
		contentPane.add(sendJButton);

		int xPos = 176;
		int yPos = 0;
		int messageQuantity = 0;
		System.out.println("messages ---> " + messages.toString());
		System.out.println("messages.entrySet() ---> " + messages.entrySet().toString());
		System.out.println("messages.entrySet().iterator() ---> " + messages.entrySet().iterator().toString());
		Iterator it = messages.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Date, String[]> pair = (Map.Entry<Date, String[]>) it.next();

			JButton newMessage = new JButton();
			newMessage.setBounds(0, yPos, 384, 128);
			outputJTextArea.add(newMessage);

			newMessage.setVerticalAlignment(SwingConstants.TOP);
			if (pair.getValue()[0].equals(username)) {
				newMessage.setHorizontalAlignment(SwingConstants.RIGHT);
			} else {
				newMessage.setHorizontalAlignment(SwingConstants.LEFT);
			}
			StringBuilder sb = new StringBuilder();
			sb.append("<html>\n");
			sb.append("<body>\n");
			if (pair.getValue()[0].equals(username)) {
				sb.append("<div align=\"right\">");
			} else {
				sb.append("<div align=\"left\">");
			}
			sb.append("<b>" + pair.getKey() + "</b><br><br>");
			sb.append(pair.getValue()[1] + "<br>");
			sb.append("</div>\n");
			sb.append("</body>\n");
			sb.append("</html>");
			newMessage.setText(sb.toString());

			yPos += 128;
			messageQuantity++;
		}

		outputJTextArea.setPreferredSize(new Dimension(384, messageQuantity * 128));
		JScrollBar vertical = scroll.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}
