import java.io.File;

public class TP {
	private ImageParser image;
	
	
	
	public TP(ImageParser image) {
		this.image = image;
	}
	
	public float[] getProbabiliades() {
		float[] probabilidades = new float[256];
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
			probabilidades[i] = ((float) veces[i]) / (float) (500*500);
			System.out.println(Integer.toString(i) + " = " + probabilidades[i]);
		}
		
		return probabilidades;
	}
	
	public float getEntropia() {
		float[] p = getProbabiliades();
		float suma = 0;
		for(int i = 0; i < 256; i++) {
			if(p[i] != 0) {
				suma += p[i] *  ((float)Math.log((double) p[i]) / (float)Math.log((double) 2));
			}
		}
		
		return -suma;
	}
}
