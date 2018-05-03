package util;

import java.util.Vector;

public class MatrizToVectorVector {
	public static Vector<Vector<Double>> metodoCutre(double[][] matriz)
	{
		Vector<Vector<Double>> vv = new Vector<Vector<Double>>();
		for (double[] fila : matriz) {
			Vector<Double> aux = new Vector<Double>();
			for (double elemento :fila) {
				aux.add(elemento);
			}
			vv.add(aux);
		}
		return vv;
	}
}
