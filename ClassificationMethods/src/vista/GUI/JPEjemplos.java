package vista.GUI;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Datos.Datos;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class JPEjemplos extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	/**
	 * Create the panel.
	 */
	public JPEjemplos() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		textArea = new JTextArea();
		textArea.setText(Datos.getEjemplosString());
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane (textArea, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		add(scroll);
		
		JButton btnAnadirEjemplo = new JButton("A\u00F1adir Ejemplo");
		add(btnAnadirEjemplo);
		btnAnadirEjemplo.setBackground(Color.orange);
		btnAnadirEjemplo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnAnadirEjemplo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String seleccion = JOptionPane.showInputDialog(
						   null,
						   "Nuevo ejemplo:",
						   "Añadir ejemplo",
						   JOptionPane.PLAIN_MESSAGE);
				
				if(seleccion != null){
					String[] lineas = seleccion.split(",");
					ArrayList<String> aux = new ArrayList<>();
			    	for(String dato : lineas){		    		
			    		aux.add(dato);
			    	}	
				    try{
				    	double[] ejemplo = new double[aux.size()];
				   
					    for(int i = 0; i < aux.size(); i++){			
							ejemplo[i] = Double.parseDouble(aux.get(i));
						}			    
					    
					    if(ejemplo.length == Datos.getEjemplos().get(0).length){
					      	Datos.addEjemplo(ejemplo);
					      	refresh();
					    }
					    else
					    	JOptionPane.showMessageDialog(null, "Formato incorrecto", "ERROR",
					            JOptionPane.ERROR_MESSAGE);
				    } catch( NumberFormatException a){
				    	JOptionPane.showMessageDialog(null, "Formato incorrecto", "ERROR",
					            JOptionPane.ERROR_MESSAGE);
				    }
				}				
			}
		});

	}
	
	public void refresh(){
		textArea.setText(Datos.getEjemplosString());
      	revalidate();
      	repaint();
	}

}
