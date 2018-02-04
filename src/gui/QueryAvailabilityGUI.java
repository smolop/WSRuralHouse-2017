package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.Offer;
import domain.RuralHouse;

public class QueryAvailabilityGUI extends JFrame {
	byte userType;
	String username;

	private static final long serialVersionUID = 1L;

	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();
	private JTextField jTextField2 = new JTextField();
	private JLabel jLabel3 = new JLabel();
	private JTextField jTextField3 = new JTextField();
	private JButton jButton1 = new JButton();
	private JButton jButton2 = new JButton();

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private JLabel jLabel4 = new JLabel();
	private JScrollPane scrollPane = new JScrollPane();
	private JComboBox<Object> comboBox;
	private JTable table;
	private DefaultTableModel tableModel;
	private final JLabel labelNoOffers = new JLabel("");
	private String[] columnNames = new String[] { ResourceBundle.getBundle("Etiquetas").getString("OfferN"),
			ResourceBundle.getBundle("Etiquetas").getString("RuralHouse"),
			ResourceBundle.getBundle("Etiquetas").getString("FirstDay"),
			ResourceBundle.getBundle("Etiquetas").getString("LastDay"),
			ResourceBundle.getBundle("Etiquetas").getString("Price") };

	// private static configuration.ConfigXML c;

	public QueryAvailabilityGUI(byte userType, String username) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				new MainGUI(userType, username, null).setVisible(true);
				closeWindow();
			}
		});
		this.userType = userType;
		this.username = username;

		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void jbInit() throws Exception {

		ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

		Vector<RuralHouse> rhs = facade.getAllRuralHouses();

		comboBox = new JComboBox(rhs);
		// comboBox.setModel(new DefaultComboBoxModel(rhs));

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(433, 548));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryAvailability"));
		jLabel1.setText(ResourceBundle.getBundle("Etiquetas").getString("RuralHouse"));
		jLabel1.setBounds(new Rectangle(40, 20, 145, 25));
		jLabel2.setText(ResourceBundle.getBundle("Etiquetas").getString("FirstDay"));
		jLabel2.setBounds(new Rectangle(40, 55, 140, 25));
		jTextField2.setBounds(new Rectangle(190, 210, 155, 25));
		jTextField2.setEditable(false);
		jLabel3.setText(ResourceBundle.getBundle("Etiquetas").getString("NumNights"));
		jLabel3.setBounds(new Rectangle(40, 250, 115, 25));
		jTextField3.setBounds(new Rectangle(190, 250, 155, 25));
		jTextField3.setText("0");
		jButton1.setText(ResourceBundle.getBundle("Etiquetas").getString("Accept"));
		jButton1.setBounds(new Rectangle(55, 455, 130, 30));
		jButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jButton1_actionPerformed(e);
			}
		});
		jButton2.setText(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButton2.setBounds(new Rectangle(230, 455, 130, 30));

		jTextField3.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				jTextField3_focusLost();
			}
		});
		jButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});
		jLabel4.setBounds(new Rectangle(55, 300, 305, 30));
		jLabel4.setForeground(Color.red);
		jCalendar1.setBounds(new Rectangle(190, 60, 225, 150));
		scrollPane.setBounds(new Rectangle(45, 305, 320, 116));

		this.getContentPane().add(scrollPane, null);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// int i=table.getSelectedRow();
				// int houseNumber = (int) tableModel.getValueAt(i,1);
				// Date firstDate=new
				// Date(((java.util.Date)tableModel.getValueAt(i,2)).getTime());
				// Date lastDate=new
				// Date(((java.util.Date)tableModel.getValueAt(i,3)).getTime());

				// BookRuralHouseGUI b=new
				// BookRuralHouseGUI(houseNumber,firstDate,lastDate);
				// b.setVisible(true);
			}
		});

		scrollPane.setViewportView(table);
		tableModel = new DefaultTableModel(null, columnNames);

		table.setModel(tableModel);
		this.getContentPane().add(jCalendar1, null);
		this.getContentPane().add(jLabel4, null);
		this.getContentPane().add(jButton2, null);
		this.getContentPane().add(jButton1, null);
		this.getContentPane().add(jTextField3, null);
		this.getContentPane().add(jLabel3, null);
		this.getContentPane().add(jTextField2, null);
		this.getContentPane().add(jLabel2, null);
		this.getContentPane().add(jLabel1, null);
		comboBox.setBounds(new Rectangle(245, 22, 115, 20));
		comboBox.setBounds(189, 22, 115, 20);

		getContentPane().add(comboBox);
		labelNoOffers.setBounds(73, 432, 265, 14);

		getContentPane().add(labelNoOffers);

		// Codigo para el JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
					DateFormat dateformat = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					jTextField2.setText(dateformat.format(calendarMio.getTime()));
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					jTextField2.setText(dateformat1.format(calendarMio.getTime()));
					jCalendar1.setCalendar(calendarMio);
				}
			}
		});

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		new MainGUI(userType, username, null).setVisible(true);
		this.setVisible(false);
	}

	private void jTextField3_focusLost() {
		try {
			new Integer(jTextField3.getText());
			jLabel4.setText("");
		} catch (NumberFormatException ex) {
			jLabel4.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
		}
	}

	private Date trim(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return calendar.getTime();
	}

	private void jButton1_actionPerformed(ActionEvent e) {

		Calendar calendar = Calendar.getInstance();

		// House object
		RuralHouse rh = (RuralHouse) comboBox.getSelectedItem();
		// First day

		// Remove the hour:minute:second:ms from the date
		Date firstDay = trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

		calendar.setTime(firstDay);

		int numDias = Integer.parseInt(jTextField3.getText());
		calendar.add(Calendar.DAY_OF_YEAR, numDias);

		// Last day
		Date lastDay = calendar.getTime();

		// System.out.println("firstDay= "+firstDay+" lastDay= "+lastDay);

		try {
			ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

			Vector<Offer> v = facade.getOffers(rh, firstDay, lastDay);
			// Vector<Offer> v=rh.getOffers(firstDay, lastDay);

			Enumeration<Offer> en = v.elements();
			Offer of;
			tableModel.setDataVector(null, columnNames);
			if (!en.hasMoreElements()) {
				labelNoOffers.setText(ResourceBundle.getBundle("Etiquetas").getString("NoOffers"));
			} else {
				labelNoOffers.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOffer"));

				while (en.hasMoreElements()) {
					of = en.nextElement();
					System.out.println("Offer retrieved: " + of.toString());
					Vector<Object> row = new Vector<Object>();
					row.add(of.getOfferNumber());

					// row.add(of.getRuralHouse().getHouseNumber()); // It does
					// not contain the rural house, when access through web
					// services
					row.add(rh.getHouseNumber()); // Rural houses are not
													// serialized with offers

					// Dates are stored in db4o as java.util.Date objects
					// instead of java.sql.Date objects
					// They have to be converted into java.sql.Date objects
					// before
					java.sql.Date firstDaySqlDate = new java.sql.Date(of.getFirstDay().getTime());
					java.sql.Date lastDaySqlDate = new java.sql.Date(of.getLastDay().getTime());
					row.add(firstDaySqlDate);
					row.add(lastDaySqlDate);
					row.add(of.getPrice());

					tableModel.addRow(row);
				}
			}

		} catch (Exception e1) {

			labelNoOffers.setText(e1.getMessage());
		}
	}

	private void closeWindow() {
		this.setVisible(false);
	}
}