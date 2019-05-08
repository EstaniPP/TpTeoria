import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class gui extends JPanel{

	private JFrame frame;
	private JTextField textField;
	private JLabel lblNewLabel;
	private static String destination = "";
	private JFileChooser chooser;
	private File image;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public gui(){
		initialize();
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	} 
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		lblNewLabel = new JLabel("");
		
		frame = new JFrame();
		frame.setBounds(20, 20, 600, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSeleccioneImagen = new JLabel("Seleccione imagen");
		lblSeleccioneImagen.setBounds(16, 0, 137, 16);
		frame.getContentPane().add(lblSeleccioneImagen);
		
		textField = new JTextField();
		textField.setBounds(6, 26, 296, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSeleccionar = new JButton("SELECCIONAR");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int seleccion = fileChooser.showOpenDialog(gui.this.lblNewLabel);
				if(seleccion == JFileChooser.APPROVE_OPTION) {
					image = fileChooser.getSelectedFile();
					ImageParser p = new ImageParser(image);
					BufferedImage bi = p.getBI();
					//p = p.getBlock(3, 0);
					Graphics2D g = (Graphics2D) bi.getGraphics();
					g.setPaint(Color.red);
					g.setFont(new Font("Arial", Font.BOLD, 40));
					//g.drawString("Bloque mayor entropia", 200, 200);
					//g.setStroke(new BasicStroke(20));
					//g.draw(new Rectangle(0, 0, 500, 500));
					g.drawImage(bi, null, 0, 0);
					gui.this.textField.setText(image.getPath());
					gui.this.lblNewLabel.setIcon(new ImageIcon(gui.this.resize(bi, 500, 625)));
				}
			}
		});
		btnSeleccionar.setBounds(314, 24, 128, 29);
		frame.getContentPane().add(btnSeleccionar);
		
		
		lblNewLabel.setBounds(6, 59, 600, 625);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnCrearArchivos = new JButton("Crear archivos");
		btnCrearArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {                                       

			    chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle(destination);
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);    
			    if (chooser.showOpenDialog(gui.this) == JFileChooser.APPROVE_OPTION) { 
			      destination = chooser.getSelectedFile().toString();
			      ImageParser p = new ImageParser(image);
			      calcular(p, chooser.getSelectedFile().toString());
			    }
				
			}
		});
		btnCrearArchivos.setBounds(457, 697, 137, 25);
		frame.getContentPane().add(btnCrearArchivos);
		
	}
	
	public void calcular(ImageParser p, String path) {
		ImageParser bloqueMayorE = null;
		ImageParser bloqueMenorE= null;
		ImageParser bloquePromedioE= null;
		double mayorentropia = Double.MIN_VALUE;
		double menorentropia = Double.MAX_VALUE;
		double promedioentropia = 0;
		
		double[][] entropiaSMemoria = new double[4][5];
		double[][] entropiaCMemoria = new double[4][5];
	    
		// ejercicio A
		SaveHTML eja = new SaveHTML();
		eja.addText("Entropias con y sin memoria: ");
		eja.addBreak();
		
		for(int j=0; j<5; j++) {
			for(int i =0; i<4; i++) {
				entropiaSMemoria[i][j] = Utilities.getEntropiaSMemoria(p.getBlock(i, j));
				entropiaCMemoria[i][j] = Utilities.getEntropiaCMemoria(p.getBlock(i, j));
	   		  	promedioentropia += entropiaCMemoria[i][j];
	   		  	
	   		  	// ejercicio A
				eja.addText("Bloque (" + String.valueOf(i) + "," + String.valueOf(j) + "). Con memoria: " + String.valueOf(entropiaCMemoria[i][j]) + " Sin memoria: " + String.valueOf(entropiaSMemoria[i][j]));
				eja.addBreak();
	   	  	}
	    }
		promedioentropia = promedioentropia/20;
		
		
		
		double distancia = Double.MAX_VALUE;
		for(int j=0; j<5; j++) {
			for(int i =0; i<4; i++) {
				
				// ejercicio A
				
				
				if(Math.abs(entropiaCMemoria[i][j]-promedioentropia) < distancia ) {
					bloquePromedioE = p.getBlock(i, j);
					distancia = Math.abs(entropiaCMemoria[i][j]-promedioentropia);
				}
	   		  	if(entropiaCMemoria[i][j] > mayorentropia) {
	   		  		bloqueMayorE = p.getBlock(i, j);
	   		  		mayorentropia = entropiaCMemoria[i][j];
	   		  	}
	   		  	if(entropiaCMemoria[i][j] < menorentropia) {
	   		  		bloqueMenorE = p.getBlock(i, j);
	   		  		menorentropia = entropiaCMemoria[i][j];
	   		  	}
			}
		}
		
		// ejercicio B
		
		SaveHTML ejb = new SaveHTML();
		ejb.addText("Histograma bloque mayor entropia: ");
		ejb.addBreak();
		ejb.addPicture(Utilities.createHistogram(bloqueMayorE, "ejb"));
		ejb.addBreak();
		ejb.addBreak();
		ejb.addText("Numero de repeticiones de cada valor en el bloque de mayor entropia:");
		ejb.addBreak();
		ejb.addText(Utilities.getRepeticiones(bloqueMayorE));
		ejb.addBreak();
		ejb.addText("Histograma bloque menor entropia: ");
		ejb.addBreak();
		ejb.addPicture(Utilities.createHistogram(bloqueMenorE, "ejb"));
		ejb.addBreak();
		ejb.addBreak();
		ejb.addText("Numero de repeticiones de cada valor en el bloque de mayor entropia:");
		ejb.addBreak();
		ejb.addText(Utilities.getRepeticiones(bloqueMenorE));
		ejb.addBreak();
		ejb.addText("Histograma mas cercano a promedio: ");
		ejb.addBreak();
		ejb.addPicture(Utilities.createHistogram(bloquePromedioE, "ejb"));
		ejb.addBreak();
		ejb.addBreak();
		ejb.addText("Numero de repeticiones de cada valor en el bloque de mayor entropia:");
		ejb.addBreak();
		ejb.addText(Utilities.getRepeticiones(bloquePromedioE));
		ejb.addBreak();
		
		// ejercicio C
		
		SaveHTML ejc = new SaveHTML();
		double[][] matCond = Utilities.getMatrizCondicional(bloqueMayorE);
		String matriz = new String();
		ejc.addText("Matriz condicional bloque mayor entropia: ");
		ejc.addBreak();
		ejc.addBreak();
		for(int i=0;i<256;i++) {
			for(int j=0;j<256;j++) {
				ejc.addText(matCond[i][j]+" | ");
			}
			ejc.addBreak();
		}
		ejc.addBreak();
		ejc.addBreak();
		matCond = Utilities.getMatrizCondicional(bloqueMenorE);
		ejc.addText("Matriz condicional bloque menor entropia: ");
		ejc.addBreak();
		ejc.addBreak();
		for(int i=0;i<256;i++) {
			for(int j=0;j<256;j++) {
				ejc.addText(matCond[i][j]+" | ");
			}
			ejc.addBreak();
		}
		
		
		// ejercicio D
		SaveHTML ejd = new SaveHTML();
		double[] proc = Utilities.getProcEstocasticos(bloqueMayorE);
		double[] proc2 = Utilities.getProcEstocasticos(bloqueMenorE);
		ejd.addText("Media del bloque con mayor entropia: "+proc[0]);
		ejd.addBreak();
		ejd.addText("Media del bloque con menor entropia: "+proc2[0]);
		ejd.addBreak();
		ejd.addText("Desvio del bloque con mayor entropia: "+proc[1]);
		ejd.addBreak();
		ejd.addText("Desvio del bloque con menor entropia: "+proc2[1]);
		
		
		eja.saveHTML(path + "/", "EJERCICIO-A.html");
		ejb.saveHTML(path + "/", "EJERCICIO-B.html");
		ejc.saveHTML(path + "/", "EJERCICIO-C.html");
		ejd.saveHTML(path + "/", "EJERCICIO-D.html");
		
		
	}
}
