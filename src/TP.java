import java.io.File;

public class TP {
	
	public double[] getProbabiliades(ImageParser image) {
		double[] probabilidades = new double[256];
		int[] veces = new int[256];
		
		for(int i = 0; i < 256; i++) {
			probabilidades[i] = 0;
			veces[i] = 0;
		}
		
		for(int i = 0; i < 500; i++) {
			for(int j = 0; j < 500; j++) {
				veces[image.getRGB(i, j).getRed()]++;
			}
		}
		for(int i = 0; i < 256; i++) {
			probabilidades[i] = ((double) veces[i]) / (double) (500*500);
			System.out.println(Integer.toString(i) + " = " + probabilidades[i]);
		}
		
		return probabilidades;
	}
	
	public double getEntropia(ImageParser image) {
		double[] p = getProbabiliades(image);
		double suma = 0;
		for(int i = 0; i < 256; i++) {
			if(p[i] != 0) {
				suma += p[i] *  ((double)Math.log((double) p[i]) / (double)Math.log((double) 2));
			}
		}
		
		return -suma;
	}
	
	public double[][] getMatrizCondicional(ImageParser image){
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
				int coloractual=image.getRGB(i, j).getRed();;
				if(j==0 && i==0) 
					coloractual= image.getRGB(0, 1).getRed();
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
}
