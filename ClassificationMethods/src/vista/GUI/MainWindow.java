package vista.GUI;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Lectura.ReadEjemplos;

import java.awt.CardLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPKMedias jpKMedias;
	private JPBayes jpBayes;
	private JPLloyd jpLloyd;
	private JMenuBar menuBar;
	private JMenu mnMtodos;
	private JMenuItem mntmBayes;
	private JMenuItem mntmKmedias;
	private JMenuItem mntmLloyd;
	
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
		super("Métodos de clasificación");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnMtodos = new JMenu("Archivo");
		mnMtodos.setForeground(Color.BLACK);
		mnMtodos.setBackground(Color.WHITE);
		mnMtodos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menuBar.add(mnMtodos);
		
		mntmBayes = new JMenuItem("Bayes");
		mntmBayes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CardLayout card = (CardLayout) contentPane.getLayout();
				card.show(contentPane, "jpBayes");
			}
		});
		
		mntmKmedias = new JMenuItem("K - Medias");
		mntmKmedias.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CardLayout card = (CardLayout) contentPane.getLayout();
				card.show(contentPane, "jpKMedias");
			}
		});
		mnMtodos.add(mntmKmedias);
		mnMtodos.add(mntmBayes);
		
		mntmLloyd = new JMenuItem("Lloyd");
		mntmLloyd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CardLayout card = (CardLayout) contentPane.getLayout();
				card.show(contentPane, "jpLloyd");
			}
		});
		mnMtodos.add(mntmLloyd);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		try {
			ReadEjemplos.read();
		} catch (IOException | NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Archivo no encontrado", "ERROR",
		            JOptionPane.ERROR_MESSAGE);
			
			System.exit(0);
		}
		contentPane.setLayout(new CardLayout(0, 0));
		
		jpKMedias = new JPKMedias();
		contentPane.add(jpKMedias, "jpKMedias");
		jpLloyd = new JPLloyd();
		contentPane.add(jpLloyd, "jpLloyd");
		jpBayes = new JPBayes();
		contentPane.add(jpBayes, "jpBayes");
	}
}