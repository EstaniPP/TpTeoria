import java.util.HashMap;

public class ChildNode extends Node{
	public ChildNode(Integer name, double prob) {
		this.name = name;
		this.prob = prob;
	}
	
	public HashMap<Integer, Integer> getCode() {
		HashMap<Integer, Integer> codes=new HashMap<Integer, Integer>();
		codes.put(name,1);
		return codes;
	}
	
	@Override
	protected HashMap<Integer, Integer> getCode(Integer lastcode, Integer level) {
		HashMap<Integer, Integer> codes=new HashMap<Integer, Integer>();
		codes.put(name,(int)Math.pow(10,level)+lastcode);
		return codes;
	}
	
}
