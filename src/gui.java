import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		frame.setBounds(100, 100, 458, 574);
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
					gui.this.textField.setText(image.getPath());
					gui.this.lblNewLabel.setIcon(new ImageIcon(gui.this.resize(p.getBI(), 403, 383)));
				}
			}
		});
		btnSeleccionar.setBounds(314, 24, 128, 29);
		frame.getContentPane().add(btnSeleccionar);
		
		
		lblNewLabel.setBounds(6, 59, 436, 436);
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
			      crearArchivos(p);
			    }
				
			}
		});
		btnCrearArchivos.setBounds(305, 504, 137, 25);
		frame.getContentPane().add(btnCrearArchivos);
		
	}
	
	public void crearArchivos(ImageParser p) {
		ImageParser bloqueMayorE = null;
		ImageParser bloqueMenorE= null;
		ImageParser bloquePromedioE= null;
		double mayorentropia = Double.MIN_VALUE;
		double menorentropia = Double.MAX_VALUE;
		double promedioentropia = 0;
		
		//double[][] entropiaSMemoria = new double[4][5];
		double[][] entropiaCMemoria = new double[4][5];
	    
		for(int j=0; j<5; j++) {
			for(int i =0; i<4; i++) {
				//entropiaSMemoria[i][j] = Utilities.getEntropiaSMemoria(p.getBlock(i, j));
				entropiaCMemoria[i][j] = Utilities.getEntropiaCMemoria(p.getBlock(i, j));
	   		  	promedioentropia += entropiaCMemoria[i][j];
	   	  	}
	    }
		promedioentropia = promedioentropia/20;
		double distancia = Double.MAX_VALUE;
		for(int j=0; j<5; j++) {
			for(int i =0; i<4; i++) {
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
		
		System.out.println(Utilities.getEntropiaCMemoria(bloqueMayorE));
		System.out.println(Utilities.getEntropiaCMemoria(bloqueMenorE));
		System.out.println(Utilities.getEntropiaCMemoria(bloquePromedioE));
		System.out.println(promedioentropia);
	}
}
