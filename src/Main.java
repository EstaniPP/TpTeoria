import java.io.File;

public class Main {
	
	public static void main(String[] args) {
		ImageParser p = new ImageParser(new File("src/marsSurface.bmp"));
		TP tp = new TP(p.getBlock(0, 0));
		System.out.println(tp.getEntropia());
	}
	
}
