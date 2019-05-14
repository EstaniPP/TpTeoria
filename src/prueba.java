import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class prueba {

	public static void main(String[] args) {
		double[] probabilities = {0.125,0.25,0.125,0.5};
		HashMap<Integer,Node> huffmanMap = new HashMap<Integer,Node>();
		for(Integer i=0; i<probabilities.length; i++) {
			ChildNode node = new ChildNode((Integer)i,probabilities[i]);
			huffmanMap.put(node.getName(),node);
		}
		
		for(Entry<Integer, Node> n: huffmanMap.entrySet()) {
			System.out.println("Probabilidad de "+n.getValue().getName()+" es "+ n.getValue().getProb());
		}
		
		while(huffmanMap.size() != 1) {
			Node minor1,minor2;
			Entry<Integer, Node> it = huffmanMap.entrySet().iterator().next();
			minor1 = it.getValue();
			minor2 = minor1;
			for(Entry<Integer, Node> n: huffmanMap.entrySet()) {
				if(minor1.getProb()>=n.getValue().getProb()) {
					minor2=minor1;
					minor1=n.getValue();
				}
			}
			FatherNode n = new FatherNode(minor2,minor1);
			huffmanMap.put(n.getName(), n);
			huffmanMap.remove(minor1.getName());
			huffmanMap.remove(minor2.getName());
		}
		
		Entry<Integer, Node> it = huffmanMap.entrySet().iterator().next();
		Node lastnode = it.getValue();
		HashMap<Integer, Integer> codesHash = lastnode.getCode();
		
		for(Entry<Integer, Integer> n: codesHash.entrySet()) {
			System.out.println("Codigo de "+n.getKey()+" es "+ n.getValue());
		}
		
	}

}
