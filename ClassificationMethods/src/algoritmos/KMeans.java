package algoritmos;

import java.util.Vector;

import util.MatrizToVectorVector;

public class KMeans {

	private double tolerancia = 0.01;
	private double b = 2;
	
	private Vector<Vector<Double>> v;
	private Vector<Vector<Double>> vanterior;
	private Vector<Vector<Double>> probabilidades;
	private Vector<Vector<Double>> datos;
	
	private String[] clases;
	
	public KMeans(double [][] _centros, String[] nombre_clases, double[][] entrenamiento, double _tolerancia, double peso_exponencial) {
		tolerancia = _tolerancia;
		b = peso_exponencial;
		
		clases = nombre_clases;
		v = MatrizToVectorVector.metodoCutre(_centros);
		datos = MatrizToVectorVector.metodoCutre(entrenamiento);
		
		ehecutar();
	}
	
	private Vector<Vector<Double>> ehecutar(){
		probabilidades = new Vector<Vector<Double>>();
		do{
			calcularProbabilidades(b);
			recalcularCentros();
		}while(!criterioDeFinalizacion());
		return v;
	}
	
	public String comprobarPunto(Vector<Double> punto){
		String resultado = new String();
		double distancia = Double.MAX_VALUE;
		for (int i = 0; i<v.size(); i++){
			if (distancia > distanciaEntre(punto,v.get(i))){
				distancia = distanciaEntre(punto,v.get(i));
				resultado = clases[i];
			}
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	private void recalcularCentros() {
		vanterior = v;
		Vector<Vector<Double>> nuevosCentros = new Vector<Vector<Double>> ();	
		Vector<Double> aux = new Vector<Double>();
		
		for(int j = 0; j < v.size(); j++){
			Vector<Double> centro = new Vector<Double>();
			aux = new Vector<Double>();
			double aux2 = 0.0;
			for (int k = 0; k <v.get(0).size();k++) centro.add(0.0);
			
			for(int i = 0; i < datos.size(); i++){	
				aux = (Vector<Double>) datos.get(i).clone();
				multiplicarVector(aux,Math.pow(probabilidades.get(i).get(j),b));
				aux2 = aux2 + (Math.pow(probabilidades.get(i).get(j), b));
				
				centro = sumaVectores(centro,aux);
			}
			multiplicarVector(centro, 1 / aux2);
			nuevosCentros.add(centro);
		}
		v = nuevosCentros;
	}

	private Vector<Double> sumaVectores(Vector<Double> v1,
			Vector<Double> v2) {
		Vector<Double> resultado = new Vector<Double>();
		for(int i = 0; i<v1.size();i++) resultado.add(v1.get(i) + v2.get(i));
		return resultado;
	}

	private void multiplicarVector(Vector<Double> vector, double valor) {
		for (int i = 0; i < vector.size(); i++)	vector.set(i, vector.get(i) * valor);
	}

	private boolean criterioDeFinalizacion() {
		boolean finalizacion = true;
		int i = 0;
		while(finalizacion && i<v.size()){
			if (distanciaEntre(v.get(i),vanterior.get(i)) > tolerancia){
				finalizacion = false;
			}
			i++;
		}
		return finalizacion;
	}

	private void calcularProbabilidades(double b2) {
		probabilidades = new Vector<Vector<Double>>();
		Vector<Double> probabilidadMuestra = null;
		for (int i=0; i<datos.size();i++){
			probabilidadMuestra = new Vector<Double>();
			for (int j=0; j<v.size();j++){
				Vector<Double> v1 = datos.get(i);
				probabilidadMuestra.add(calcularProbabilidad(v1,j));
			}
			probabilidades.add(probabilidadMuestra);
		}
	}

	private Double calcularProbabilidad(Vector<Double> v1, int j) {
		return Math.pow(1 / distanciaEntre(v1,v.get(j)), 1 / (b - 1)) / Math.pow(sumatorioDeClases(v1), 1 / (b - 1));
	}

	private double sumatorioDeClases(Vector<Double> v1) {
		double resultado = 0;
		for(int i = 0; i < v.size(); i++) resultado = resultado + 1 / (distanciaEntre(v1,v.get(i)));
		return resultado;
	}

	private double distanciaEntre(Vector<Double> v1, Vector<Double> v2) {
		double resultado = 0;
		if(v1.size() != v2.size()) resultado = -1;
		else{
			for (int i=0 ; i<v1.size(); i++){
				resultado = resultado + Math.pow(v1.get(i) - v2.get(i), 2);
			}
		}
		return Math.sqrt(resultado);
	}


}
 