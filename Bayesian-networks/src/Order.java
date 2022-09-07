import java.text.DecimalFormat;
import java.util.*;
import java.lang.Math;
import java.util.stream.*;
/**
 * class Order
 * @author Dhananjay Saikumar
 */
public class Order {
	public static ArrayList<String> order(BayesianNetwork bn, String query_label){

		HashMap<String, Node> forward_map = bn.getForwardMap();
		HashMap<Node, String> backward_map = bn.getBackwardMap();
		ArrayList<Node> Nodes = new  ArrayList<Node>();
		ArrayList<String> keys = new ArrayList<String>();
		// add nodes and node names to the arraylists defined above
		for (String key : forward_map.keySet()){
			keys.add(key);
			Nodes.add(forward_map.get(key));
		}

		// generated an undirected graph of node (connecting parents of node together)
		for (Node node: Nodes){
			ArrayList<Node> parents = node.getParents();
			if (parents.size() > 1){
				for (Node parent_x:parents){
					for (Node parent_y:parents){
						if (!parent_x.equals(parent_y)){
							if (!parent_x.getNeighbor().contains(parent_y)){
							parent_x.addNeighbor(parent_y);
							}
						}
					}
				}
			}
		}


		ArrayList<Node> unmarked = new ArrayList<Node>(Nodes);
		ArrayList<Node> marked = new ArrayList<Node>();

		// selecting starter node = query node
		Node starter_node = forward_map.get(query_label);
		unmarked.remove(starter_node);
		marked.add(starter_node);

		// generating order, looping till unmarked list is empty
		while (unmarked.size() > 0){
			int max_marked_neighborCount = -1;
			Node max_marked_node = null;
			// iretating over unmarked neighbours
			for (Node unmark : unmarked){
				ArrayList<Node> temp_neighbours = new ArrayList<Node>(unmark.getNeighbor());
				temp_neighbours.retainAll(marked);

				// selecting nodes with max marked neighbours
				if (temp_neighbours.size() > max_marked_neighborCount){
					max_marked_neighborCount = temp_neighbours.size();
					max_marked_node = unmark;
				}

			}
			// adding unmarked node with max marked neighbours to marked
			marked.add(max_marked_node);
			unmarked.remove(max_marked_node);

		}

		marked.remove(starter_node);
		Collections.reverse(marked);
		marked.add(starter_node);


		ArrayList<String> sorted_order = new ArrayList<String>();
		for (Node n:marked){
			sorted_order.add(n.getName());
		}

		return sorted_order;























	}

}

