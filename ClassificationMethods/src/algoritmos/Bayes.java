package algoritmos;
import java.util.HashMap;

import Jama.Matrix;

public class Bayes {

	private HashMap<String, Matrix> emes;
	private HashMap<String, Matrix> ces;
	
	public Bayes() {
		emes = new HashMap<String, Matrix>();
		ces = new HashMap<String, Matrix>();
	}
	
	// Intentaremos asociar ejemplos parecidos a los de "x" con la clase "c"
	public void aprenderClase(double[][] x, String c) {
		Matrix ejemplos = new Matrix(x);
		
		// Cálculo de m
		
		Matrix m = ejemplos.getMatrix(new int[]{0}, 0, ejemplos.getColumnDimension()-1);
		for (int i = 1; i < ejemplos.getRowDimension(); i++) {
			m.plusEquals(ejemplos.getMatrix(new int[]{i}, 0, ejemplos.getColumnDimension()-1));
		}
		m.timesEquals(1.0/ejemplos.getRowDimension());
		
		// Cálculo de C
		
		Matrix C = new Matrix(ejemplos.getColumnDimension(), ejemplos.getColumnDimension());
		Matrix dxm;
		for (int i = 0; i < ejemplos.getRowDimension(); i++) {
			dxm = ejemplos.getMatrix(new int[]{i}, 0, ejemplos.getColumnDimension()-1).minus(m);
			C.plusEquals(dxm.transpose().times(dxm));
		}
		
		emes.put(c, m);
		ces.put(c, C);
	}
	
	public double funcionParaClase(double[] ejemplo, String clase) {
		Matrix e = new Matrix(ejemplo, 1);
		if (!ces.containsKey(clase))
			return Double.NaN;
		return calcularF(e, emes.get(clase), ces.get(clase));
	}
	
	public String predecirClase(double[] ejemplo) {
		String[] clases = getClases();
		String mejor_clase = null;
		double mejor_funcion = Double.NEGATIVE_INFINITY;
		double funcion;
		for (String clase : clases) {
			funcion = funcionParaClase(ejemplo, clase);
			if (funcion > mejor_funcion) {
				mejor_funcion = funcion;
				mejor_clase = clase;
			}
		}
		return mejor_clase;
	}
	
	public String[] getClases() {
		return ces.keySet().toArray(new String[0]);
	}
	
	public double[][] getM(String clase) {
		if (!ces.containsKey(clase))
			return null;
		return emes.get(clase).getArrayCopy();
	}
	
	public double[][] getC(String clase) {
		if (!ces.containsKey(clase))
			return null;
		return ces.get(clase).getArrayCopy();
	}
	
	private static double calcularF(Matrix ejemplo, Matrix m, Matrix c) {
		int d = ejemplo.getColumnDimension();
		Matrix aux = ejemplo.minus(m).times(c.inverse()).times(ejemplo.minus(m).transpose());
		double exponente = -0.5*aux.get(0, 0);
		double base = 1.0/(Math.pow(2*Math.PI,d/2)*Math.pow(c.det(), 0.5));
		return base*Math.exp(exponente);
	}
	
	public static void main (String[] args) {
		Bayes v = new Bayes();
		double setosa[][] = new double[][]{
				{5.1,3.5,1.4,0.2},
				{4.9,3.0,1.4,0.2},
				{4.7,3.2,1.3,0.2},
				{4.6,3.1,1.5,0.2},
				{5.0,3.6,1.4,0.2},
				{5.4,3.9,1.7,0.4},
				{4.6,3.4,1.4,0.3},
				{5.0,3.4,1.5,0.2},
				{4.4,2.9,1.4,0.2},
				{4.9,3.1,1.5,0.1},
				{5.4,3.7,1.5,0.2},
				{4.8,3.4,1.6,0.2},
				{4.8,3.0,1.4,0.1},
				{4.3,3.0,1.1,0.1},
				{5.8,4.0,1.2,0.2},
				{5.7,4.4,1.5,0.4},
				{5.4,3.9,1.3,0.4},
				{5.1,3.5,1.4,0.3},
				{5.7,3.8,1.7,0.3},
				{5.1,3.8,1.5,0.3},
				{5.4,3.4,1.7,0.2},
				{5.1,3.7,1.5,0.4},
				{4.6,3.6,1.0,0.2},
				{5.1,3.3,1.7,0.5},
				{4.8,3.4,1.9,0.2},
				{5.0,3.0,1.6,0.2},
				{5.0,3.4,1.6,0.4},
				{5.2,3.5,1.5,0.2},
				{5.2,3.4,1.4,0.2},
				{4.7,3.2,1.6,0.2},
				{4.8,3.1,1.6,0.2},
				{5.4,3.4,1.5,0.4},
				{5.2,4.1,1.5,0.1},
				{5.5,4.2,1.4,0.2},
				{4.9,3.1,1.5,0.1},
				{5.0,3.2,1.2,0.2},
				{5.5,3.5,1.3,0.2},
				{4.9,3.1,1.5,0.1},
				{4.4,3.0,1.3,0.2},
				{5.1,3.4,1.5,0.2},
				{5.0,3.5,1.3,0.3},
				{4.5,2.3,1.3,0.3},
				{4.4,3.2,1.3,0.2},
				{5.0,3.5,1.6,0.6},
				{5.1,3.8,1.9,0.4},
				{4.8,3.0,1.4,0.3},
				{5.1,3.8,1.6,0.2},
				{4.6,3.2,1.4,0.2},
				{5.3,3.7,1.5,0.2},
				{5.0,3.3,1.4,0.2}
		};
		double[][] versicolor = new double[][]{
				{7.0,3.2,4.7,1.4},
				{6.4,3.2,4.5,1.5},
				{6.9,3.1,4.9,1.5},
				{5.5,2.3,4.0,1.3},
				{6.5,2.8,4.6,1.5},
				{5.7,2.8,4.5,1.3},
				{6.3,3.3,4.7,1.6},
				{4.9,2.4,3.3,1.0},
				{6.6,2.9,4.6,1.3},
				{5.2,2.7,3.9,1.4},
				{5.0,2.0,3.5,1.0},
				{5.9,3.0,4.2,1.5},
				{6.0,2.2,4.0,1.0},
				{6.1,2.9,4.7,1.4},
				{5.6,2.9,3.6,1.3},
				{6.7,3.1,4.4,1.4},
				{5.6,3.0,4.5,1.5},
				{5.8,2.7,4.1,1.0},
				{6.2,2.2,4.5,1.5},
				{5.6,2.5,3.9,1.1},
				{5.9,3.2,4.8,1.8},
				{6.1,2.8,4.0,1.3},
				{6.3,2.5,4.9,1.5},
				{6.1,2.8,4.7,1.2},
				{6.4,2.9,4.3,1.3},
				{6.6,3.0,4.4,1.4},
				{6.8,2.8,4.8,1.4},
				{6.7,3.0,5.0,1.7},
				{6.0,2.9,4.5,1.5},
				{5.7,2.6,3.5,1.0},
				{5.5,2.4,3.8,1.1},
				{5.5,2.4,3.7,1.0},
				{5.8,2.7,3.9,1.2},
				{6.0,2.7,5.1,1.6},
				{5.4,3.0,4.5,1.5},
				{6.0,3.4,4.5,1.6},
				{6.7,3.1,4.7,1.5},
				{6.3,2.3,4.4,1.3},
				{5.6,3.0,4.1,1.3},
				{5.5,2.5,4.0,1.3},
				{5.5,2.6,4.4,1.2},
				{6.1,3.0,4.6,1.4},
				{5.8,2.6,4.0,1.2},
				{5.0,2.3,3.3,1.0},
				{5.6,2.7,4.2,1.3},
				{5.7,3.0,4.2,1.2},
				{5.7,2.9,4.2,1.3},
				{6.2,2.9,4.3,1.3},
				{5.1,2.5,3.0,1.1},
				{5.7,2.8,4.1,1.3}
		};
		v.aprenderClase(versicolor, "Iris-versicolor");
		v.aprenderClase(setosa, "Iris-setosa");

		System.out.println(v.predecirClase(new double[]{5.1,3.5,1.4,0.2})); // setosa
		System.out.println(v.predecirClase(new double[]{6.9,3.1,4.9,1.5})); // versicolor
		System.out.println(v.predecirClase(new double[]{5.0,3.4,1.5,0.2})); // setosa
	}
	
}
