package vista.GUI;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import Datos.Datos;

public class JPDatos extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public JPDatos() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JTextArea textArea = new JTextArea();
		textArea.setText(Datos.getDatos1String());
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane (textArea, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(new TitledBorder("Clase " + Datos.getClases().get(0) + ": "));
		add(scroll);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
		   public void run() { 
		       scroll.getVerticalScrollBar().setValue(0);
		   }
		});
		
		JTextArea textArea2 = new JTextArea();
		textArea2.setText(Datos.getDatos2String());
		textArea2.setEditable(false);
		JScrollPane scroll2 = new JScrollPane (textArea2, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll2.setBorder(new TitledBorder("Clase " + Datos.getClases().get(1) + ": "));
		add(scroll2);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
		   public void run() { 
		       scroll2.getVerticalScrollBar().setValue(0);
		   }
		});
	}

}
