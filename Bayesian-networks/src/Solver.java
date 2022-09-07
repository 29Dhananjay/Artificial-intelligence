import java.text.DecimalFormat;
import java.util.*;
import java.lang.Math;
import java.util.stream.*;

/**
 * class Solver @ solver class.
 * @author Dhananjay Saikumar
 */
public class Solver {
	 /**
     * joinCPT @ joins cpt.
     * @param r_s_cpt 1st input cpt.
	 * @param v_cpt 2nd input cpt.
     */
	public static ArrayList<ArrayList<Object>> joinCPT(ArrayList<ArrayList<Object>> r_s_cpt, ArrayList<ArrayList<Object>> v_cpt){

		List<Object> common = new ArrayList<Object>(r_s_cpt.get(0));
		// get common variables between two classses
		common.retainAll(v_cpt.get(0));
		common.remove("Prob");

		// final cpt table
		ArrayList<ArrayList<Object>> joint = new ArrayList<ArrayList<Object>>();


		ArrayList<Integer> pos1 = new ArrayList<Integer>();
		ArrayList<Integer> pos2 = new ArrayList<Integer>();


		for (Object com:common){
			// postion of labels in fist cpt
			pos1.add(v_cpt.get(0).indexOf(com));
			// postion of labels in second cpt
			pos2.add(r_s_cpt.get(0).indexOf(com));
		}

		// joining process
		for (int i = 0; i < v_cpt.size(); i++){

			ArrayList<Object> temp_row1 = new ArrayList<Object>(v_cpt.get(i));
			ArrayList<Object> row1_val = new ArrayList<Object>();

			for (Integer pos: pos1){row1_val.add(temp_row1.get(pos));}
			// iterating over the first row
			for (int j = 0; j < r_s_cpt.size(); j++){
				ArrayList<Object> temp_row2 = new ArrayList<Object>(r_s_cpt.get(j));
				ArrayList<Object> row2_val = new ArrayList<Object>();
				// iterating over the second row
				for (Integer pos: pos2){row2_val.add(temp_row2.get(pos));}
				// if common label match in value
				if (row1_val.equals(row2_val)){
					ArrayList<Object> temp_temp_row1 = new ArrayList<Object>(temp_row1);
					ArrayList<Object> temp_temp_row2 = new ArrayList<Object>(temp_row2);



					if (i == 0 && j == 0){
						// create new row with joint properties
						ArrayList<Object> temp_temp_temp_row1 = new ArrayList<Object>();
						ArrayList<Object> temp_temp_temp_row2 = new ArrayList<Object>();

						for (int temp_temp_temp_row1_index = 0; temp_temp_temp_row1_index < temp_temp_row1.size(); temp_temp_temp_row1_index++){
							if (!pos1.contains(temp_temp_temp_row1_index)){
								temp_temp_temp_row1.add(temp_row1.get(temp_temp_temp_row1_index));
							}
						}

						for (int temp_temp_temp_row2_index = 0; temp_temp_temp_row2_index < temp_temp_row2.size(); temp_temp_temp_row2_index++){
							if (!pos2.contains(temp_temp_temp_row2_index)){
								temp_temp_temp_row2.add(temp_row2.get(temp_temp_temp_row2_index));
							}
						}

						// removing common variables
						temp_temp_temp_row1.remove(temp_temp_temp_row1.size()-1);
						temp_temp_temp_row2.remove(temp_temp_temp_row2.size()-1);

						temp_temp_temp_row1.addAll(temp_temp_temp_row2);
						// adding common variables
						ArrayList<Object> temp_row_val = new ArrayList<Object>(row1_val);
						Collections.reverse(temp_row_val);
						for (Object toadd:temp_row_val){temp_temp_temp_row1.add(0,toadd);}
						temp_temp_temp_row1.add("Prob");
						joint.add(temp_temp_temp_row1);


					}
					else{
						// create new row with joint properties
						ArrayList<Object> temp_temp_temp_row1 = new ArrayList<Object>();
						ArrayList<Object> temp_temp_temp_row2 = new ArrayList<Object>();

						for (int temp_temp_temp_row1_index = 0; temp_temp_temp_row1_index < temp_temp_row1.size(); temp_temp_temp_row1_index++){
							if (!pos1.contains(temp_temp_temp_row1_index)){
								temp_temp_temp_row1.add(temp_row1.get(temp_temp_temp_row1_index));
							}
						}

						for (int temp_temp_temp_row2_index = 0; temp_temp_temp_row2_index < temp_temp_row2.size(); temp_temp_temp_row2_index++){
							if (!pos2.contains(temp_temp_temp_row2_index)){
								temp_temp_temp_row2.add(temp_row2.get(temp_temp_temp_row2_index));
							}
						}



						// calculating product of common elements
						double p1 = (double)temp_temp_row1.get(temp_temp_row1.size()-1);
						double p2 = (double)temp_temp_row2.get(temp_temp_row2.size()-1);
						double p3 = p1*p2;

						temp_temp_temp_row1.remove(temp_temp_temp_row1.size()-1);
						temp_temp_temp_row2.remove(temp_temp_temp_row2.size()-1);
						temp_temp_temp_row1.addAll(temp_temp_temp_row2);

						ArrayList<Object> temp_row_val = new ArrayList<Object>(row1_val);
						Collections.reverse(temp_row_val);
						for (Object toadd:temp_row_val){temp_temp_temp_row1.add(0,toadd);}
						temp_temp_temp_row1.add(p3);
						//System.out.println(temp_temp_temp_row1);
						joint.add(temp_temp_temp_row1);


					}
				}


			}




		}
		return joint;

	}


    /**
     * elimination, sums over a given variable
     * @param condition sum over variable.
	 * @param b_cpt cpt to sum over over variable.
     */
	public static ArrayList<ArrayList<Object>> elimination(String condition, ArrayList<ArrayList<Object>> b_cpt){
		int pos1 = b_cpt.get(0).indexOf(condition);
		ArrayList<ArrayList<Object>> sum_over = new ArrayList<ArrayList<Object>>();
		// postion of sum over label
		sum_over.add(b_cpt.get(0));
		sum_over.get(0).remove(pos1);


		for(int i = 1; i < b_cpt.size();i++){
			ArrayList<Object> temp_row = b_cpt.get(i);
			// removing ssum over column
			temp_row.remove(pos1);
			Boolean added = false;
			for (ArrayList<Object> sum_over_row: sum_over){
				ArrayList<Object> sum_over_row_trim = new ArrayList<Object>(sum_over_row);
				sum_over_row_trim.remove(sum_over_row_trim.size() - 1);
				ArrayList<Object> temp_row_trim = new ArrayList<Object>(temp_row);
				temp_row_trim.remove(temp_row_trim.size() - 1);

				if (sum_over_row_trim.equals(temp_row_trim)){
					// adding variables (summing over condition)
					double old = (double)sum_over_row.get(sum_over_row.size()-1);
					double addon = (double)temp_row.get(temp_row.size()-1);
					double updated = old + addon;
					sum_over_row.set(sum_over_row.size()-1,updated);
					added = true;
				}

			}
			if (!added){
				sum_over.add(temp_row);
			}
		}

		return sum_over;
	}

	/**
     * prune: removes irelevent variables
     * @param bn takes the Bayesian Network as the input.
	 * @param queries a list of queries to prune (query + evidence).
     */
	public static ArrayList<Node> prune(BayesianNetwork bn, ArrayList<String> queries){
		if (queries.size() == 0){
			return bn.getNodes();
		}
		else{
			HashMap<String, Node> forward_map = new HashMap<String, Node>();
			HashMap<Node, String> backward_map = new HashMap<Node, String>();

			for (Node node: bn.getNodes()){
				forward_map.put(node.getName(), node);
				backward_map.put(node,node.getName());
			}

			// adding query and evidence to the stack
			Stack<Node> stk = new Stack<Node>();
			for (String query : queries){
				stk.push(forward_map.get(query));
			}


			ArrayList<Node> relavant_nodes = new ArrayList<Node>();
			// itetate over the stack till sstack is empty
			while (!stk.empty()){
				Node current = stk.peek();
				ArrayList<Node> parents = current.getParents();
				// add current node to relevant node list
				relavant_nodes.add(current);
				stk.pop();
				// add parents of query and evidence to stack
				for (Node parent:parents){
					stk.push(parent);
				}
			}
			Set<Node> set = new HashSet<Node>(relavant_nodes);
			relavant_nodes.clear();
			relavant_nodes.addAll(set);

			return relavant_nodes;
		}








	}

    /**
     * solver function.
     * @param network, describes which network needs to be initialised.
	 * @param variable, query node's label.
	 * @param order_given, order.
	 * @param evidence_list, evidence.
     */
	public static ArrayList<ArrayList<Object>> solver(String network, String variable, ArrayList<String> order_given, ArrayList<ArrayList<String>> evidence_list, String solveORprint){

		BayesianNetwork bn = new BayesianNetwork();
		if (network.equals("CNX")){
			Node Outdated = new Node("Outdated");
			Node Maintenance = new Node("Maintenance");
			Node Firewall = new Node("Firewall");
			Node Website = new Node("Website");
			Node DDoS = new Node("DDoS");
			Node Attack = new Node("Attack");
			Node Classification = new Node("Classification");
			Node Noise = new Node("Noise");
			Node Alert = new Node("Alert");

			bn.addNode(Outdated);
			bn.addNode(Maintenance);
			bn.addNode(Firewall);
			bn.addNode(Website);
			bn.addNode(DDoS);
			bn.addNode(Attack);
			bn.addNode(Classification);
			bn.addNode(Noise);
			bn.addNode(Alert);

			bn.addEdge(Outdated, Maintenance);
			bn.addEdge(Maintenance,Firewall);
			bn.addEdge(Website,Attack);
			bn.addEdge(DDoS,Attack);
			bn.addEdge(Firewall,Attack);
			bn.addEdge(Attack,Classification);
			bn.addEdge(Attack,Alert);
			bn.addEdge(Noise,Alert);

			Outdated.addCPTvalues(0.02,0.98);
			Maintenance.addCPTvalues(0.33333,0.66667,0.08333,0.91667);
			Firewall.addCPTvalues(0.97,0.03,1.0,0.0);
			Website.addCPTvalues(0.85,0.15);
			DDoS.addCPTvalues(0.12329,0.87671);
			Attack.addCPTvalues(0.1,0.9,1.0,0.0,0.01,0.99,0.2,0.8,0.15,0.85,1.0,0.0,0.08,0.92,0.7,0.3);
			Classification.addCPTvalues(0.7,0.3,0.0,1.0);
			Noise.addCPTvalues(0.01,0.99);
			Alert.addCPTvalues(0.99,0.01,0.9,0.1,0.95,0.05,0.05,0.95);
		}
		if (network.equals("BNA")){
			Node A = new Node("A");
			Node B = new Node("B");
			Node C = new Node("C");
			Node D = new Node("D");


			bn.addNode(A);
			bn.addNode(B);
			bn.addNode(C);
			bn.addNode(D);


			bn.addEdge(A, B);
			bn.addEdge(B, C);
			bn.addEdge(C, D);
			A.addCPTvalues(0.05,0.95);
			B.addCPTvalues(0.05,0.95,0.8,0.2);
			C.addCPTvalues(0.1,0.9,0.3,0.7);
			D.addCPTvalues(0.4,0.6,0.6,0.4);
		}
		if (network.equals("BNB")){
			Node J = new Node("J");
			Node K = new Node("K");
			Node L = new Node("L");
			Node M = new Node("M");
			Node N = new Node("N");
			Node O = new Node("O");

			bn.addNode(J);
			bn.addNode(K);
			bn.addNode(L);
			bn.addNode(M);
			bn.addNode(N);
			bn.addNode(O);


			bn.addEdge(J, K);
			bn.addEdge(K, M);
			bn.addEdge(L, M);
			bn.addEdge(M, N);
			bn.addEdge(M, O);
			J.addCPTvalues(0.05,0.95);
			K.addCPTvalues(0.9,0.1,0.7,0.3);
			L.addCPTvalues(0.7,0.3);
			M.addCPTvalues(0.6,0.4,0.7,0.3,0.2,0.8,0.1,0.9);
			N.addCPTvalues(0.6,0.4,0.2,0.8);
			O.addCPTvalues(0.05,0.95,0.8,0.2);
		}

		if (network.equals("BNC")){

		Node P = new Node("P");
		Node Q = new Node("Q");
		Node R = new Node("R");
		Node V = new Node("V");
		Node S = new Node("S");
		Node Z = new Node("Z");
		Node U = new Node("U");

		bn.addNode(P);
		bn.addNode(Q);
		bn.addNode(R);
		bn.addNode(V);
		bn.addNode(S);
		bn.addNode(Z);
		bn.addNode(U);


		bn.addEdge(P, Q);
		bn.addEdge(Q, V);
		bn.addEdge(R, V);
		bn.addEdge(Q, S);
		bn.addEdge(R, S);
		bn.addEdge(V, Z);
		bn.addEdge(S, Z);
		bn.addEdge(S, U);
		P.addCPTvalues(0.05,0.95);
		Q.addCPTvalues(0.9,0.1,0.7,0.3);
		R.addCPTvalues(0.7,0.3);
		V.addCPTvalues(0.7,0.3,0.55,0.45,0.15,0.85,0.1,0.9);
		S.addCPTvalues(0.6,0.4,0.7,0.3,0.2,0.8,0.1,0.9);
		Z.addCPTvalues(0.65,0.35,0.7,0.3,0.4,0.6,0.2,0.8);
		U.addCPTvalues(0.05,0.95,0.8,0.2);

		}

		HashMap<String, Node> forward_map = bn.getForwardMap();
		HashMap<Node, String> backward_map = bn.getBackwardMap();

		if (solveORprint.equals("print")){
			for (Node node: bn.getNodes()){
					for (ArrayList<Object> row : node.getCPT()){
						System.out.println(row);
					}
					System.out.println(" ");
					System.out.println(" ");
				}

			return new ArrayList<ArrayList<Object>>();
		}

		else{


		ArrayList<String> inquestion = new ArrayList<String>();
		// adding query and evidence labels to inquetion  array
		inquestion.add(variable);

		if (evidence_list.size() > 0) {
			for (String others:evidence_list.get(0)){
				inquestion.add(others);
			}
		}

		ArrayList<String> evidence = new ArrayList<String>();
		ArrayList<String> evidence_bool = new ArrayList<String>();
		// adding evidence and evidence value to evidence and evidence_bool array
		if (evidence_list.size() > 0){
			evidence = evidence_list.get(0);
			evidence_bool = evidence_list.get(1);
		}

		// pruning relevant nodess
		ArrayList<Node> relavant = prune(bn,inquestion);

		List<String> prune = new ArrayList<String>();

		for (Node node:relavant){prune.add(backward_map.get(node));}

		List<String> sorted_order;
		//  generating order if no order is provided
		if (order_given.size() == 0){
			sorted_order = Order.order(bn,inquestion.get(0));
			List<String> sorted_orderr = new ArrayList<String>(sorted_order);
			sorted_orderr.remove(sorted_orderr.size()-1);
			System.out.println(sorted_orderr);
		}

		else {
			sorted_order = order_given;
			sorted_order.add(variable);
		}


		List<String> pruned_order = new ArrayList<String>();

		// pruning nodes from orden given/generated
		for (int i = 0; i < sorted_order.size(); i++){
			String current = sorted_order.get(i);
			if (prune.contains(current)){
				pruned_order.add(current);
		}}

		sorted_order = pruned_order;


		ArrayList<ArrayList<ArrayList<Object>>> factors  =  new ArrayList<ArrayList<ArrayList<Object>>>();
		// adding cpts from relevant nodes to factors
		for (String nnn:sorted_order){
			factors.add(forward_map.get(nnn).getCPT());
		}

		// setting certain cpt rows to 0 if the evidence is provided
		for (ArrayList<ArrayList<Object>> fact : factors){
			for (int zz = 0; zz < evidence.size(); zz++){
				if (fact.get(0).contains(evidence.get(zz))){
					int pos1 = fact.get(0).indexOf(evidence.get(zz));
					for (ArrayList<Object> row:fact){
						if (!row.get(pos1).equals(evidence.get(zz)) && !row.get(pos1).equals(evidence_bool.get(zz))){
							row.set(row.size()-1,(double)0);
						}
					}
				}
			}
		}


			// iterating over order
			for (int i = 0; i < sorted_order.size(); i++){
				if ((factors.size() == 1 && factors.get(0).size() == 3)){
					break;
				}

				String label = sorted_order.get(i);


				ArrayList<ArrayList<ArrayList<Object>>> temp_factors = new ArrayList<ArrayList<ArrayList<Object>>>();
				for (int jj = 0; jj < factors.size(); jj++){
					ArrayList<ArrayList<Object>> current_factor = factors.get(jj);
					if (current_factor.get(0).contains(label)){
						temp_factors.add(current_factor);
					}
				}

				// remove selected factors from factors
				for (ArrayList<ArrayList<Object>> factor: temp_factors){
					factors.remove(factor);
				}

				// join cpts
				while (temp_factors.size() > 1){
					ArrayList<ArrayList<Object>> temp_factors0 = temp_factors.get(0);
					ArrayList<ArrayList<Object>> temp_factors1 = temp_factors.get(1);

					temp_factors.remove(temp_factors0);
					temp_factors.remove(temp_factors1);

					temp_factors.add(joinCPT(temp_factors0,temp_factors1));
				}




				if (temp_factors.size() != 0){
					// if cpt only contains one label, then dont sum out
					if (temp_factors.get(0).size() == 3 || label.equals(inquestion.get(0))){
						factors.add(temp_factors.get(0));
					}

					// else, sum over the current variable
					if (temp_factors.get(0).size() != 3 && !label.equals(inquestion.get(0))){

					// ssumming over the cuurrent variable
					ArrayList<ArrayList<Object>> updated_factor = elimination(label, temp_factors.get(0));

					// add it to the factors
					factors.add(updated_factor);
					}
			}

			}

			ArrayList<ArrayList<Object>> final_factors = factors.get(0);
			// normalize the last remaining factor once all looping over order
			double true_prob = (double)final_factors.get(1).get(1);
			double false_prob = (double)final_factors.get(2).get(1);
			double sum = true_prob+false_prob;
			final_factors.get(1).set(1,true_prob/sum);
			final_factors.get(2).set(1,false_prob/sum);
			// return desired cpt
			return final_factors;


		}

	}
}

