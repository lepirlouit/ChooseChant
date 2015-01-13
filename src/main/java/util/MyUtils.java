package util;



import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class MyUtils {
	public static String readLine(InputStream is, String charsetName) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b;
		
		// On lit l'input caractère par caractère (pour ne pas empiéter sur l'image)
		// ==> Du coup il est préférable que ce soit un BufferedInputStream
		while ( (b=is.read()) >= 0) {
			baos.write(b);
			// Et on arrête lorsqu'on trouve un retour à la ligne :
			if (b=='\n') {
				break;
			}
		}
		return new String(baos.toString(charsetName));
	}
	public static String tovertical(String s) {
		StringBuffer b = new StringBuffer("<HTML><center>");
		for (int i = 0; i < s.length(); ++i)
			b.append(s.charAt(i)).append("<BR>");
		return b.append("</HTML>").toString();

	}
}
