package Lectura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Datos.Datos;

public final class ReadEjemplos{

	public static void read() throws FileNotFoundException, IOException, NumberFormatException{
		File file = new File("Iris2Clases.txt");

		ArrayList<ArrayList<String>> clase1 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> clase2 = new ArrayList<ArrayList<String>>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				// process the line.
				String[] lineas = line.split(",");
				ArrayList<String> aux = new ArrayList<>();
				for(String dato : lineas){		    		
					aux.add(dato);
				}
				if(i == 0){
					clase1.add(aux);
				} else if (!clase1.get(0).get(clase1.get(0).size()-1).equals(aux.get(aux.size()-1))){
					clase2.add(aux);
				}
				else {
					clase1.add(aux);
				}
				i++;
			}
		} catch (IOException e) {
			throw e;
		}

		ArrayList<String> nombres = new ArrayList<String>();
		nombres.add(clase1.get(0).get(clase1.get(0).size()-1));
		nombres.add(clase2.get(0).get(clase2.get(0).size()-1));
		Datos.setClases(nombres);

		double[][] datosClase1 = new double[clase1.size()][clase1.get(0).size()-1];
		int i = 0;
		for(ArrayList<String> datos: clase1){
			for(int j = 0; j < clase1.get(0).size()-1; j++){
				datosClase1[i][j] = Double.parseDouble(datos.get(j));
			}
			i++;
		}
		Datos.addDatosClases(datosClase1);

		double[][] datosClase2 = new double[clase2.size()][clase2.get(0).size()-1];
		i = 0;
		for(ArrayList<String> datos: clase2){			
			for(int j = 0; j < clase2.get(0).size()-1; j++){
				datosClase2[i][j] = Double.parseDouble(datos.get(j));
			}
			i++;
		}
		Datos.addDatosClases(datosClase2);

		// Ejemplos
		File file2 = new File("TestIris01.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(file2))) {
			String line;
			i = 0;
			ArrayList<String> aux = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				// process the line.
				String[] lineas = line.split(",");
				for(String dato : lineas){		    		
					aux.add(dato);
				}		    	
				i++;
			}
			double[] ejemplo = new double[aux.size()-1];
			for(int h = 0; h < aux.size()-1; h++){			
				ejemplo[h] = Double.parseDouble(aux.get(h));
			}
			Datos.addEjemplo(ejemplo);
		} catch (IOException e) {
			throw e;
		}

		File file3 = new File("TestIris02.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(file3))) {
			String line;
			i = 0;
			ArrayList<String> aux = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				// process the line.
				String[] lineas = line.split(",");
				for(String dato : lineas){		    		
					aux.add(dato);
				}		    	
				i++;
			}
			double[] ejemplo = new double[aux.size()-1];
			for(int h = 0; h < aux.size()-1; h++){			
				ejemplo[h] = Double.parseDouble(aux.get(h));
			}
			Datos.addEjemplo(ejemplo);
		} catch (IOException e) {
			throw e;
		}

		File file4 = new File("TestIris03.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(file4))) {
			String line;
			i = 0;
			ArrayList<String> aux = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				// process the line.
				String[] lineas = line.split(",");
				for(String dato : lineas){		    		
					aux.add(dato);
				}		    	
				i++;
			}
			double[] ejemplo = new double[aux.size()-1];
			for(int h = 0; h < aux.size()-1; h++){			
				ejemplo[h] = Double.parseDouble(aux.get(h));
			}
			Datos.addEjemplo(ejemplo);
		} catch (IOException e) {
			throw e;
		}
	}
}
