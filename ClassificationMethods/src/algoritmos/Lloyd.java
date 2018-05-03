package algoritmos;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.IntToDoubleFunction;
import java.util.stream.DoubleStream;

import Jama.Matrix;


public class Lloyd {
	
	private String[] clases;
	private HashMap<String, Integer> idx_clases;
	private Matrix centros;
	
	private int pasada;

	private IntToDoubleFunction funcionRazonAprendizaje;
	
	public Lloyd(double [][] _centros, String[] nombre_clases, double[][] entrenamiento, IntToDoubleFunction funcion, double tolerancia, int max_iteraciones) {
		clases = Arrays.copyOf(nombre_clases, nombre_clases.length);
		idx_clases = new HashMap<String, Integer>();
		for (int i = 0; i < clases.length; i++) {
			idx_clases.put(clases[i], i);
		}
		centros = new Matrix(_centros);
		
		funcionRazonAprendizaje = funcion;
		
		Matrix estimulos = new Matrix(entrenamiento);
		int iteracion = 0; pasada = 0;
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
	
	private Matrix iterar(Matrix estimulos)
	{
		Matrix estimulo;
		Matrix centros_finales = centros.copy();
		for (int i = 0; i < estimulos.getRowDimension(); i++) {
			pasada++;
			estimulo = estimulos.getMatrix(new int[]{i}, 0, estimulos.getColumnDimension()-1);
			
			int idx_centro_cercano = competir(estimulo);
			Matrix centro_cercano = centros.getMatrix(new int[]{idx_centro_cercano}, 0, centros.getColumnDimension()-1);
			Matrix nuevo_centro = centro_cercano.plus(estimulo.minus(centro_cercano).times(funcionRazonAprendizaje.applyAsDouble(pasada)));

			centros_finales.setMatrix(new int[]{idx_centro_cercano}, 0, centros_finales.getColumnDimension()-1, nuevo_centro);
		}
		return centros_finales;
	}
	
	// Devuelve el centro más cercano al estímulo
	private int competir(Matrix estimulo) {
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
	
	public String predecirClase (double[] _ejemplo) {
		Matrix ejemplo = new Matrix(_ejemplo, 1);
		return clases[competir(ejemplo)];
	}
	
	public static void main (String args[]) {
		
		// El ejercicio de las transparencias
		double[][] centros_iniciales = {
				{1,4},
				{7,2}
		};
		String[] clases = {
				"c1",
				"c2"
		};
		double[][] entrenamiento = new double[][] {
			{1,1},
			{1,3},
			{1,5},
			{2,2},
			{2,3},
			{6,3},
			{6,4},
			{7,1},
			{7,3},
			{7,5}
		};
		
		double[] ejemplo = new double[]{6,2};
		
		Lloyd clasificador = new Lloyd(centros_iniciales, clases, entrenamiento, (pasada) -> 0.1, Math.pow(10, -10), 10);
		System.out.println(clasificador.predecirClase(ejemplo));
	}
	
}
