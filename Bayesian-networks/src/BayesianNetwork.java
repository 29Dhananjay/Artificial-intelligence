import java.text.DecimalFormat;
import java.util.*;
import java.util.ArrayList;
import java.lang.Math;

/**
 * class BayesianNetwork @ BayesianNetwork.
 * @author Dhananjay Saikumar
 */
public class BayesianNetwork {
	private ArrayList<Node> nodes =  new ArrayList<Node>();
	// hashmap to find a node given the name
	HashMap<String, Node> forward_map = new HashMap<String, Node>();
	// hashmap to find the name given a name
	HashMap<Node, String> backward_map = new HashMap<Node, String>();

	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void addNode(Node node) {
		nodes.add(node);
	}
	// adds n1's child = n2 and n2's parent = n1
	public void addEdge(Node n1, Node n2) {
		n1.addChild(n2);
	}
	// remove child parent relation
	public void removeEdge(Node n1, Node n2) {
		n1.removeChild(n2);
	}
	// removes all edges
	public void removeAllEdges() {
		for (Node node:nodes){
			node.removeAll();
		}
	}
	// return forward hashmap
	public HashMap<String, Node> getForwardMap() {
		for (Node node: nodes){
			forward_map.put(node.getName(), node);
		}
		return forward_map;
	}
	// return backward hashmap
	public HashMap<Node, String> getBackwardMap() {
		for (Node node: nodes){
			backward_map.put(node,node.getName());
		}
		return backward_map;
	}



}
