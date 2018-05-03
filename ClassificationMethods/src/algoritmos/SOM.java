package algoritmos;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.IntToDoubleFunction;
import java.util.stream.DoubleStream;

import Jama.Matrix;


public class SOM
{

	private String[] clases;
	private HashMap<String, Integer> idx_clases;
	private Matrix centros;
	
	private double alpha_inicial, alpha_final,
		tolerancia_vecindad;
	
	private int max_iteraciones, pasada;
	
	private IntToDoubleFunction funcionRazonAprendizaje;
	
	
	public SOM(double [][] _centros, String[] nombre_clases, double[][] entrenamiento, IntToDoubleFunction funcion, double _alpha_inicial, double _alpha_final, double tolerancia, double _vecindad, int _max_iteraciones)
	{
		clases = Arrays.copyOf(nombre_clases, nombre_clases.length);
		idx_clases = new HashMap<String, Integer>();
		for (int i = 0; i < clases.length; i++) {
			idx_clases.put(clases[i], i);
		}
		centros = new Matrix(_centros);
		
		funcionRazonAprendizaje = funcion;
		
		alpha_inicial = _alpha_inicial;
		alpha_final = _alpha_final;
		tolerancia_vecindad = _vecindad;
		
		max_iteraciones = _max_iteraciones;
		pasada = 0;
		
		Matrix estimulos = new Matrix(entrenamiento);
		int iteracion = 0;
		double cambio = Double.POSITIVE_INFINITY;
		Matrix centros_fin_iteracion;
		while (iteracion < max_iteraciones && cambio > tolerancia) {
			centros_fin_iteracion = iterar(estimulos);
			DoubleStream ds = DoubleStream.of(centros_fin_iteracion.minus(centros).getRowPackedCopy());
			cambio = ds.map(valor->Math.abs(valor)).max().getAsDouble();
			centros = centros_fin_iteracion;
			iteracion++;
		}
		
	}
	
	public String predecirClase (double[] _ejemplo) {
		Matrix ejemplo = new Matrix(_ejemplo, 1);
		return clases[centroMasCercano(ejemplo)];
	}
	

	private Matrix iterar(Matrix estimulos)
	{
		Matrix estimulo, centro;
		Matrix centros_finales = centros.copy();
		for (int i = 0; i < estimulos.getRowDimension(); i++) {
			pasada++;
			estimulo = estimulos.getMatrix(new int[]{i}, 0, estimulos.getColumnDimension()-1);
			for (int j = 0; j < centros_finales.getRowDimension(); j++) {
				centro = centros_finales.getMatrix(new int[]{j}, 0, centros_finales.getColumnDimension()-1);
				double vecindad = vecindad(centro, estimulo, pasada);
				if (vecindad > tolerancia_vecindad) {
					Matrix nuevo_centro = centro.plus(estimulo.minus(centro).times(funcionRazonAprendizaje.applyAsDouble(pasada)*vecindad));
					centros_finales.setMatrix(new int[]{j}, 0, centros_finales.getColumnDimension()-1, nuevo_centro);
				}
			}
		}
		return centros_finales;
	}
	
	private double vecindad(Matrix z1, Matrix z2, int iteracion) {
		Matrix dif = z1.minus(z2);
		DoubleStream ds = DoubleStream.of(dif.getArray()[0]).map((value)->Math.pow(value, 2));
		return Math.exp(-ds.sum()/(2*Math.pow(alpha_inicial*Math.pow(alpha_final/alpha_inicial, iteracion/max_iteraciones), 2)));
	}
	
	private int centroMasCercano(Matrix estimulo) {
		int mejor_centro = 0;
		double mejor_distancia = Double.POSITIVE_INFINITY, distancia;
		for (int idx_centro = 0; idx_centro < centros.getRowDimension(); idx_centro++)
		{
			distancia = 0;
			for (int j = 0; j < centros.getColumnDimension(); j++) {
				distancia += Math.pow(estimulo.get(0, j) - centros.get(idx_centro, j), 2);
			}
			distancia = Math.sqrt(distancia);
			
			if (distancia < mejor_distancia) {
				mejor_distancia = distancia;
				mejor_centro = idx_centro;
			}
		}
		return mejor_centro;
	}
	
	public static void main (String args[]) {
		double[][] datos = {
				{1, 3},
				{1, 5},
				{2, 2},
				{6, 3},
				{6, 4},
				{7, 3}
		};
		
		double[][] centros = {
				{1, 4},
				{7, 2}
		};
		
		String[] clases = {
				"c1",
				"c2"
		};
		
		SOM som = new SOM(centros, clases, datos, (pasada) -> (1.0/(10+pasada)), 1, 0.8, Math.pow(10, -6), 0.2, 5);
		
		double[] prueba = {6, 2};
		
		System.out.println(som.predecirClase(prueba));
	}
}
