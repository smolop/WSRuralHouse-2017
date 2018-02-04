package gui;

import java.awt.Color;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businessLogic.ApplicationFacadeInterfaceWS;
import businessLogic.FacadeImplementationWS;
import configuration.ConfigXML;
import domain.User;

public class ApplicationLauncher {

	public static void main(String[] args) {

		ConfigXML c = ConfigXML.getInstance();

		System.out.println(c.getLocale());

		Locale.setDefault(new Locale(c.getLocale()));

		System.out.println("Locale: " + Locale.getDefault());

		MainGUI a = null;

		try {

			ApplicationFacadeInterfaceWS appFacadeInterface;
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			a = new MainGUI(User.USER_TYPE_USER, "Guest", null);
			a.setVisible(true);

			if (c.isBusinessLogicLocal()) {
				appFacadeInterface = new FacadeImplementationWS();
			}

			else { // Si es remoto
					// String
					// serviceName="http://localhost:9999/ws/ruralHouses?wsdl";
				String serviceName = "http://" + c.getBusinessLogicNode() + ":" + c.getBusinessLogicPort() + "/ws/"
						+ c.getBusinessLogicName() + "?wsdl";

				// URL url = new
				// URL("http://localhost:9999/ws/ruralHouses?wsdl");
				URL url = new URL(serviceName);

				// 1st argument refers to wsdl document above
				// 2nd argument is service name, refer to wsdl document above
				QName qname = new QName("http://businessLogic/", "FacadeImplementationWSService");

				Service service = Service.create(url, qname);

				appFacadeInterface = service.getPort(ApplicationFacadeInterfaceWS.class);
			}
			/*
			 * if (c.getDataBaseOpenMode().equals("initialize"))
			 * appFacadeInterface.initializeBD();
			 */
			MainGUI.setBussinessLogic(appFacadeInterface);

		} catch (Exception e) {
			a.lblNewLabel.setText("Error: " + e.toString());
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}
		// a.pack();
	}
}
