package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class FicheroToString {
	public static String metodoCutre(String filename) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while (br.ready()) {
				sb.append((char)br.read());
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
