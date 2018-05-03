package vista.GUI;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import Datos.Datos;
import algoritmos.KMeans;
import util.MatrizToVectorVector;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Component;

public class JPKMedias extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private JPEjemplos panelEjemplos;
	private JPResultados panelResultados;
	private JTextArea textField;
	private JTextArea textField_1;

	/**
	 * Create the panel.
	 */
	public JPKMedias() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel informacion = new JPanel();
		add(informacion);
		informacion.setLayout(new BoxLayout(informacion, BoxLayout.Y_AXIS));
		informacion.setBorder(new TitledBorder("Información: "));
		 ((javax.swing.border.TitledBorder) informacion.getBorder()).
	        setTitleFont(new Font("Arial", Font.BOLD, 18));
		 
		JPanel centros = new JPanel();
		centros.setBorder(new TitledBorder("Centros de las clases: "));
		 ((javax.swing.border.TitledBorder) centros.getBorder()).
	        setTitleFont(new Font("Arial", Font.BOLD, 14));
		informacion.add(centros);
		centros.setLayout(new BoxLayout(centros, BoxLayout.Y_AXIS));
		
		JPanel c1 = new JPanel();
		centros.add(c1);
		c1.setLayout(new BoxLayout(c1, BoxLayout.X_AXIS));
		
		textField = new JTextArea();
		textField.setEditable(false);
		textField.setBorder(new TitledBorder("Clase " + Datos.getClases().get(0) + ": "));
		textField.setText(Datos.getCentros1String());
		c1.add(textField);
		textField.setMaximumSize( 
			     new Dimension(200, textField.getPreferredSize().height) );
		textField.setPreferredSize( 
			     new Dimension(200, textField.getPreferredSize().height) );
		
		JPanel c2 = new JPanel();
		centros.add(c2);
		c2.setLayout(new BoxLayout(c2, BoxLayout.X_AXIS));
		
		textField_1 = new JTextArea();
		textField_1.setEditable(false);
		textField_1.setBorder(new TitledBorder("Clase " + Datos.getClases().get(1) + ": "));
		textField_1.setText(Datos.getCentros2String());
		c2.add(textField_1);
		textField_1.setMaximumSize( 
			     new Dimension(200, 50) );
		textField_1.setPreferredSize( 
			     new Dimension(200, textField.getPreferredSize().height) );
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Parámetros: "));
		 ((javax.swing.border.TitledBorder) panel.getBorder()).
	        setTitleFont(new Font("Arial", Font.BOLD, 14));
		informacion.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Tolerancia = 0.01");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblNewLabel);
		
		JLabel lblPesoExponencial = new JLabel("Peso exponencial = 2");
		lblPesoExponencial.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblPesoExponencial);
		panel.setPreferredSize(informacion.getPreferredSize());
		
		JPDatos datos = new JPDatos();
		datos.setBorder(new TitledBorder("Datos: "));
		((javax.swing.border.TitledBorder) datos.getBorder()).
        setTitleFont(new Font("Arial", Font.BOLD, 18));
		add(datos);
		
		panelEjemplos = new JPEjemplos();
		panelEjemplos.setBorder(new TitledBorder("Ejemplos: "));
		 ((javax.swing.border.TitledBorder) panelEjemplos.getBorder()).
	        setTitleFont(new Font("Arial", Font.BOLD, 18));
		add(panelEjemplos);
		
		panelResultados = new JPResultados();
		panelResultados.setBorder(new TitledBorder("Comprobación: "));
		 ((javax.swing.border.TitledBorder) panelResultados.getBorder()).
	        setTitleFont(new Font("Arial", Font.BOLD, 18));
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

				double tolerancia = 0.01,
						peso_exponencial = 2;

				KMeans kmeans = new KMeans(datos_centros, nombre_clases, datos_entrenamiento, tolerancia, peso_exponencial);
				
				String s = "";
				int i = 1;
				for (Vector<Double> ejemplo : MatrizToVectorVector.metodoCutre(datos_prueba)) {
					s += i + "º = " + kmeans.comprobarPunto(ejemplo);
					s += "\n";
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
