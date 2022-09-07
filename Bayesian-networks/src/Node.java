import java.text.DecimalFormat;
import java.util.*;
import java.util.ArrayList;
import java.lang.Math;

/**
 * class Node @ Describes node
 * @author Dhananjay Saikumar
 */
public class Node {
	private String name;
	private ArrayList<Node> parent =  new ArrayList<Node>();
	private ArrayList<Node> child = new ArrayList<Node>();
	private ArrayList<Node> undirected_neighbor = new ArrayList<Node>();
	private CPT cpt;


    /**
     * Node constructor.
     * @param name node name.
     */
	public Node(String name){
		this.name = name;
		this.cpt = new CPT(this);
	}

	public String getName(){
		return name;
	}

	// get parents
	public ArrayList<Node> getParents(){
		return parent;
	}
	// get children
	public ArrayList<Node> getChildren(){
		return child;
	}

	// gets undirected neightbour (childs + parents + co-parent's used for generating order)
	public ArrayList<Node> getNeighbor(){
		return undirected_neighbor;
	}

	// add parent node
	public void addParent(Node node){
		undirected_neighbor.add(node);
		parent.add(node);
	}
	// add child node
	public void addChild(Node node){
		child.add(node);
		undirected_neighbor.add(node);
		node.addParent(this);
	}
	// add undirected_neighbor which is not a child or parent (i.e co parent)
	public void addNeighbor(Node node){
		undirected_neighbor.add(node);
		if (!node.getNeighbor().contains(this)){
			node.addNeighbor(this);
		}
	}

	// remove parent
	public void removeParent(Node node){
		parent.remove(node);
	}

	// remove child
	public void removeChild(Node node){
		child.remove(node);
		node.removeParent(this);
	}
	// remove all
	public void removeAll(){
		parent.clear();
		child.clear();
	}
	// add cpt values
	public void addCPTvalues(double ... vals){
		this.cpt.addvalues(vals);
	}
	// count # undirected neightbours
	public int neighborCount(){
		return undirected_neighbor.size();
	}
	// getNeighborNames
	public ArrayList<String> getNeighborNames(){
		ArrayList<String> combined = new ArrayList<String>();
		for (Node p:parent){
			combined.add(p.getName());
		}
		for (Node c:child){
			combined.add(c.getName());
		}

		return combined;
	}
	// get cpt
	public ArrayList<ArrayList<Object>> getCPT(){
		return this.cpt.getCPT();
	}

}
