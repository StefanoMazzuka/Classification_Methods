package vista.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Lectura.ReadEjemplos;

import javax.swing.JTabbedPane;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPKMedias jpKMedias;
	private JPBayes jpBayes;
	private JPLloyd jpLloyd;
	private JPSom jpSom;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setSize(600, 600);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		super("Métodos de clasificación - Juan José Prieto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		try {
			ReadEjemplos.read();
		} catch (IOException | NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Falta algun archivo o el formato es incorrecto", "ERROR",
		            JOptionPane.ERROR_MESSAGE);
			
			System.exit(0);
		}
		
		jpKMedias = new JPKMedias();
		jpBayes = new JPBayes();
		jpLloyd = new JPLloyd();
		jpSom = new JPSom();
		
		JTabbedPane tabbedPane = new JTabbedPane();		
		tabbedPane.add("K-Medias", jpKMedias);
		tabbedPane.add("Bayes", jpBayes);
		tabbedPane.add("Lloyd", jpLloyd);
		tabbedPane.add("SOM", jpSom);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.addChangeListener(new ChangeListener() {
	        @Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
	        	jpKMedias.refresh();
	        	jpBayes.refresh();
	        	jpLloyd.refresh();
	        	jpSom.refresh();
			}
	    });
	}

}
