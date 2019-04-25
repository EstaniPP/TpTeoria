import java.awt.Color;
import java.io.File;

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
	
}
