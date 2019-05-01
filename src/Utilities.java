import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

public class Utilities {
	
	public static double[] getProbabiliades(ImageParser image) {
		double[] probabilidades = new double[256];
		int[] veces = new int[256];
		
		for(int i = 0; i < 256; i++) {
			probabilidades[i] = 0;
			veces[i] = 0;
		}
		
		for(int i = 0; i < 500; i++) {
			for(int j = 0; j < 500; j++) {
				veces[image.getRGB(j, i).getRed()]++;
			}
		}
		for(int i = 0; i < 256; i++) {
			probabilidades[i] = ((double) veces[i]) / (double) (500*500);
		}
		
		return probabilidades;
	}
	
	public static double getEntropia(ImageParser image) {
		double[] p = getProbabiliades(image);
		double suma = 0;
		for(int i = 0; i < 256; i++) {
			if(p[i] != 0) {
				suma += p[i] *  ((double)Math.log((double) p[i]) / (double)Math.log((double) 2));
			}
		}
		
		return -suma;
	}
	
	public static double[][] getMatrizCondicional(ImageParser image){
		double[][] matrizcond = new double[256][256];
		double[] tiradas = new double[256];
		for(int i=0;i<256;i++) {
			for(int j=0;j<256;j++) {
				matrizcond[i][j]=0;
			}
			tiradas[i]=0;
		}
		int coloranterior= image.getRGB(0, 0).getRed();
		for(int i =0;i<256;i++) {
			for(int j=0; j<256; j++) {
				int coloractual=image.getRGB(j, i).getRed();;
				if(j==0 && i==0) 
					coloractual= image.getRGB(1, 0).getRed();
				matrizcond[coloractual][coloranterior]++;
				tiradas[coloranterior]++;
				coloranterior=coloractual;
			}
		}
		for(int i=0;i<256;i++) {
			for(int j=0; j<256; j++) {
				if(tiradas[j]==0) {
					matrizcond[i][j]=0.0f;
				}else {
					matrizcond[i][j]=matrizcond[i][j]/tiradas[j];
				}
			}
		}
		
		return matrizcond;
	}
	
	public static void createHistogram(ImageParser img, String chartName) {
		
		double[] bufferImagen = new double[250000];
		ImageParser bloque = img.getBlock(0, 0);
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
	    	ChartUtilities.saveChartAsPNG(new File(chartName), chart, width, height);
	    } catch (IOException e) {
	    	
	    }
	}
}
