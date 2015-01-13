package util;



import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class MyUtils {
	public static String readLine(InputStream is, String charsetName) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b;
		
		// On lit l'input caract�re par caract�re (pour ne pas empi�ter sur l'image)
		// ==> Du coup il est pr�f�rable que ce soit un BufferedInputStream
		while ( (b=is.read()) >= 0) {
			baos.write(b);
			// Et on arr�te lorsqu'on trouve un retour � la ligne :
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
