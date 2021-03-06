package vista.GUI;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Datos.Datos;
import algoritmos.Lloyd;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.IntToDoubleFunction;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;

public class JPLloyd extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPEjemplos panelEjemplos;
	private JPResultados panelResultados;

	/**
	 * Create the panel.
	 */
	public JPLloyd() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		
		JPanel panel = new JPanel();
		add(panel);
		
		JLabel lblNewLabel = new JLabel("LLOYD");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblNewLabel);
		
		JPanel centros = new JPanel();
		centros.setPreferredSize(new Dimension(100, 100));
		centros.setBackground(Color.WHITE);
		centros.setLayout(new BoxLayout(centros, BoxLayout.Y_AXIS));

		centros.add(new JLabel("Centros de clases:"));
		centros.add(new JLabel("1. " + Datos.getClases().get(0)));
		centros.add(new JLabel("     " + Datos.getCentros1String()));	
		centros.add(new JLabel("2. " + Datos.getClases().get(0)));
		centros.add(new JLabel("     " + Datos.getCentros2String()));

		JPanel tolerancia = new JPanel();
		tolerancia.setPreferredSize(new Dimension(200, 100));
		tolerancia.setBackground(Color.WHITE);
		tolerancia.setLayout(new BoxLayout(tolerancia, BoxLayout.Y_AXIS));
		
		tolerancia.add(new JLabel("             Tolerancia: 0.0000000001"));
		tolerancia.add(new JLabel("             Raz�n de aprendizaje: 0.1"));
		tolerancia.add(new JLabel("             N�mero de iteraciones: 10"));

		JPanel info = new JPanel();
		info.setPreferredSize(new Dimension(100, 100));
		info.setBackground(Color.WHITE);
		info.setLayout(new BoxLayout(info, BoxLayout.X_AXIS));

		info.add(centros);
		info.add(tolerancia);
		
		add(info);
		//
		
		JPDatos datos = new JPDatos();
		add(datos);
		
		panelEjemplos = new JPEjemplos();
		panelEjemplos.setBorder(new TitledBorder("Ejemplos"));
		add(panelEjemplos);
		
		panelResultados = new JPResultados();
		add(panelResultados);
		
		JButton btnComprobar = panelResultados.getButton();
		
		for( ActionListener al : btnComprobar.getActionListeners() ) {
			btnComprobar.removeActionListener( al );
	    }
		
		btnComprobar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] nombre_clases = {Datos.getClases().get(0), Datos.getClases().get(1)};
				double[][] datos_centros = Datos.getCentros();
				
				double[][] datos_entrenamiento = new double[Datos.getDatosClases().get(0).length + Datos.getDatosClases().get(1).length][Datos.getDatosClases().get(0)[0].length]; 
						
				int pos = 0;
				for(int i = 0; i < Datos.getDatosClases().get(0).length; i++){
					datos_entrenamiento[pos+i] = Datos.getDatosClases().get(0)[i];
				}				
				pos = Datos.getDatosClases().get(0).length;
				for(int i = 0; i < Datos.getDatosClases().get(1).length; i++){
					datos_entrenamiento[pos+i] = Datos.getDatosClases().get(1)[i];
				}
				
				double[][] datos_prueba = new double[Datos.getEjemplos().size()][Datos.getEjemplos().get(0).length];
				for(int i = 0; i < Datos.getEjemplos().size(); i++){
					datos_prueba[i] = Datos.getEjemplos().get(i);
				}

				IntToDoubleFunction funcion = (i) -> 0.1;
				double tolerancia = 0.0000000001;
				int max_iteraciones = 10;
				
				Lloyd lloyd = new Lloyd(datos_centros, nombre_clases, datos_entrenamiento, funcion, tolerancia, max_iteraciones);
				String s = "";
				int i = 1;
				for (double[] prueba : datos_prueba) {
					s += i + ") " + lloyd.predecirClase(prueba);
					s+= "\n";
					i++;
				}
				
				panelResultados.setResultados(s);
			}
			
			
		});
		
	}
	
	public void refresh(){
		panelEjemplos.refresh();
		panelResultados.clear();
	}

}
