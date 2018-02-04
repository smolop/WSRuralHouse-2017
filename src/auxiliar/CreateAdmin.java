package auxiliar;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import businessLogic.FacadeImplementationWS;

public class CreateAdmin {
	public static void main(String[] args) {
		FacadeImplementationWS facade = new FacadeImplementationWS();

		byte[] imgP;
		File img = new File(".//resources/admin.png");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedImage prof = null;
		try {
			prof = ImageIO.read(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ImageIO.write(prof, "png", baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imgP = baos.toByteArray();
		InputStream in = new ByteArrayInputStream(imgP);
		try {
			BufferedImage bImageFromConvert = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		char[] password = { 'a', 'b', 'c', 'A', 'B', 'C', '1', '2', '3' };
		facade.createAdmin(password, imgP);
		System.out.println(">>>>> Admin created");
	}

}
