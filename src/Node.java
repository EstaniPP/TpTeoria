import java.util.HashMap;

public abstract class Node {
	Integer name;
	static Integer cont = 257;
	double prob;
	
	public abstract HashMap<Integer,Integer> getCode();
	
	protected abstract HashMap<Integer,Integer> getCode(Integer lastcode, Integer level);
	
	public double getProb() {
		return prob;
	}
	
	public int getName() {
		return name;
	}
}
