package vista.GUI;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

public class JPResultados extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JButton btnComprobar;
	
	/**
	 * Create the panel.
	 */
	public JPResultados() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane (textArea, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(new TitledBorder("Resultados: "));		
		textArea.setRows(20);
		add(scroll);		
		
		JPanel panel = new JPanel();
		add(panel);
		
		btnComprobar = new JButton("Comprobar");
		btnComprobar.setBackground(new Color(153, 204, 153));
		panel.add(btnComprobar);
	}
	
	public void setResultados(String results){
		textArea.setText(results);
		this.revalidate();
		this.repaint();
	}
	
	public JButton getButton(){
		return btnComprobar;
	}
	
	public void clear(){
		textArea.setText("");
	}

}
