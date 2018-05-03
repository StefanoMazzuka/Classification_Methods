package vista.GUI;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Datos.Datos;
import algoritmos.Bayes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class JPBayes extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPResultados panelResultados;
	private JPEjemplos panelEjemplos;
	/**
	 * Create the panel.
	 */
	public JPBayes() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
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
				Bayes bayes = new Bayes();
				
				
				for (int i = 0; i < Datos.getClases().size(); i++) {
					bayes.aprenderClase(Datos.getDatosClases().get(i), Datos.getClases().get(i));
				}
				
				String s = "";
				int i = 1;
				for (double[] ejemplo : Datos.getEjemplos()) {
					s += i + "º = " + bayes.predecirClase(ejemplo);
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
