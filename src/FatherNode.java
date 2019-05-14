import java.util.HashMap;

public class FatherNode extends Node{
	Node n1,n2;
	
	public FatherNode(Node n1, Node n2) {
		this.n1 = n1;
		this.n2 = n2;
		this.name = Node.cont++;
		this.prob = n1.getProb() + n2.getProb();
	}

	public HashMap<Integer, Integer> getCode() {
		HashMap<Integer, Integer> codes=new HashMap<Integer, Integer>();
		codes.putAll(n1.getCode(0,1));
		codes.putAll(n2.getCode(1,1));
		return codes;
	}
	
	@Override
	protected HashMap<Integer, Integer> getCode(Integer lastcode, Integer level) {
		HashMap<Integer, Integer> codes=new HashMap<Integer, Integer>();
		codes.putAll(n1.getCode(lastcode, level+1));
		int exp;
		if(lastcode!=0) {
			exp = (int)(Math.log10(lastcode))+1;
		}else {
			exp = 1;
		}
		System.out.println((int)Math.pow(10,level)+lastcode);
		codes.putAll(n2.getCode((int)Math.pow(10,level)+lastcode,level+1));
		return codes;
	}
	
}
