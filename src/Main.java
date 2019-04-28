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
		TP tp = new TP();
		System.out.println(tp.getEntropia(p.getBlock(0, 0)));
		double[][] mat = tp.getMatrizCondicional(p.getBlock(0, 0));
		double[] suma= new double[256];
		for(int i=0; i<256;i++)
			suma[i]=0;
		
		
		for(int i=0;i<256;i++){	
			for(int j=0; j<256; j++) {
				if(mat[i][j]!=0.0) {
					System.out.println(i+" "+j+" "+mat[i][j]+"    ");
					suma[j]+=mat[i][j];
				}
			}			
		}
		for(int i=0; i<256;i++)
			System.out.println(i+": "+suma[i]);
	}
	
	
		double[] value = new double[100];
		Random generator = new Random();
		for (int i=0; i < 100; i++) {
		   value[i] = generator.nextDouble();
		}
	    int number = 10;
	    HistogramDataset dataset = new HistogramDataset();
	    dataset.setType(HistogramType.RELATIVE_FREQUENCY);
	    dataset.addSeries("Histogram",value,number);
	    String plotTitle = "Histogram"; 
	    String xaxis = "number";
	    String yaxis = "value"; 
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