package vista.GUI;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Datos.Datos;
import algoritmos.Bayes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;

public class JPBayes extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPResultados panelResultados;
	private JPEjemplos panelEjemplos;
	private JPanel panel;
	private JLabel lblNewLabel;
	/**
	 * Create the panel.
	 */
	public JPBayes() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panel = new JPanel();
		add(panel);
		
		lblNewLabel = new JLabel("BAYES");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblNewLabel);
		
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
				Bayes bayes = new Bayes();
				
				
				for (int i = 0; i < Datos.getClases().size(); i++) {
					bayes.aprenderClase(Datos.getDatosClases().get(i), Datos.getClases().get(i));
				}
				
				String s = "";
				int i = 1;
				for (double[] ejemplo : Datos.getEjemplos()) {
					s += i + ") " + bayes.predecirClase(ejemplo);
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
