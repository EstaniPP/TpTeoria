import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

import java.io.*;
import java.util.Random;
import org.jfree.chart.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;

public class Main {
	
	public static void main(String[] args) {
		ImageParser p = new ImageParser(new File("src/marsSurface.bmp"));
		
		double[] bufferImagen = new double[250000];
		ImageParser bloque = p.getBlock(0, 0);
		for(int j=0;j<500;j++) {
			for(int i=0;i<500;i++) {
				bufferImagen[j*500+i] = (double) bloque.getRGB(i, j).getRed();
			}
		}
	    HistogramDataset dataset = new HistogramDataset();
	    dataset.setType(HistogramType.FREQUENCY);
	    dataset.addSeries("Histograma", bufferImagen, 256);
	    String plotTitle = "Histograma de entropias"; 
	    String xaxis = "Entropias";
	    String yaxis = "Valor"; 
	    PlotOrientation orientation = PlotOrientation.VERTICAL; 
	    boolean show = false; 
	    boolean toolTips = false;
	    boolean urls = false; 
	    JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, dataset, orientation, show, toolTips, urls);
	    
	    int width = 500;
	    int height = 300; 
	    
	    try {
	 	   ChartUtilities.saveChartAsPNG(new File("histogram.PNG"), chart, width, height);
	    } catch (IOException e) {}
	}
}