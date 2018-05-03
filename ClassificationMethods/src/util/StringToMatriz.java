package util;

public class StringToMatriz {
	public static double[][] metodoCutre(String s) {
		String[] filas = s.split("\n");
		double[][] matriz = new double[filas.length][];
		for (int i = 0; i < filas.length; i++) {
			String[] elementos = filas[i].split("\\s");
			matriz[i] = new double[elementos.length];
			for (int j = 0; j < elementos.length; j++) {
				matriz[i][j] = Double.parseDouble(elementos[j]);
			}
		}
		return matriz;
	}
}
