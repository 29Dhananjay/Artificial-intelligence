import java.text.DecimalFormat;
import java.util.*;
import java.lang.Math;
import java.util.stream.*;
public class Test {
	public static ArrayList<ArrayList<Object>> multi_multi_joinCPT(ArrayList<ArrayList<Object>> r_s_cpt, ArrayList<ArrayList<Object>> v_cpt){
		List<Object> common = new ArrayList<Object>(r_s_cpt.get(0));
		common.retainAll(v_cpt.get(0));
		common.remove("Prob");

		ArrayList<ArrayList<Object>> joint = new ArrayList<ArrayList<Object>>();

		ArrayList<Integer> pos1 = new ArrayList<Integer>();
		ArrayList<Integer> pos2 = new ArrayList<Integer>();


		for (Object com:common){
			pos1.add(v_cpt.get(0).indexOf(com));
			pos2.add(r_s_cpt.get(0).indexOf(com));
		}

		for (int i = 0; i < v_cpt.size(); i++){
			ArrayList<Object> temp_row1 = new ArrayList<Object>(v_cpt.get(i));
			ArrayList<Object> row1_val = new ArrayList<Object>();
			for (Integer pos: pos1){row1_val.add(temp_row1.get(pos));}
			for (int j = 0; j < r_s_cpt.size(); j++){
				ArrayList<Object> temp_row2 = new ArrayList<Object>(r_s_cpt.get(j));
				ArrayList<Object> row2_val = new ArrayList<Object>();
				for (Integer pos: pos2){row2_val.add(temp_row2.get(pos));}
				if (row1_val.equals(row2_val)){
					ArrayList<Object> temp_temp_row1 = new ArrayList<Object>(temp_row1);
					ArrayList<Object> temp_temp_row2 = new ArrayList<Object>(temp_row2);



					if (i == 0 && j == 0){
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

						/*
						Collections.reverse(pos1_copy);
							for(Integer index : pos1_copy){
								temp_temp_row1.remove((int)index);
							}

						Collections.reverse(pos2_copy);
							for(Integer index : pos2_copy){
							temp_temp_row2.remove((int)index);
						}*/

						temp_temp_temp_row1.remove(temp_temp_temp_row1.size()-1);
						temp_temp_temp_row2.remove(temp_temp_temp_row2.size()-1);







						//temp_temp_row1.removeAll(row1_val);
						//temp_temp_row2.removeAll(row2_val);

						//temp_temp_row1.remove(temp_temp_row1.size()-1);
						//temp_temp_row2.remove(temp_temp_row2.size()-1);

						temp_temp_temp_row1.addAll(temp_temp_temp_row2);
						ArrayList<Object> temp_row_val = new ArrayList<Object>(row1_val);
						Collections.reverse(temp_row_val);
						for (Object toadd:temp_row_val){temp_temp_temp_row1.add(0,toadd);}
						temp_temp_temp_row1.add("Prob");
						//System.out.println(temp_row_val);
						//System.out.println(temp_temp_temp_row1);
						joint.add(temp_temp_temp_row1);


						//System.out.println(" ");
					}
					else{
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


						//System.out.println(row1_val);


						double p1 = (double)temp_temp_row1.get(temp_temp_row1.size()-1);
						double p2 = (double)temp_temp_row2.get(temp_temp_row2.size()-1);
						double p3 = p1*p2;


						//temp_temp_row1.remove(temp_temp_row1.size()-1);
						//temp_temp_row2.remove(temp_temp_row2.size()-1);
						//temp_temp_row1.addAll(temp_temp_row2);
						temp_temp_temp_row1.remove(temp_temp_temp_row1.size()-1);
						temp_temp_temp_row2.remove(temp_temp_temp_row2.size()-1);
						temp_temp_temp_row1.addAll(temp_temp_temp_row2);

						ArrayList<Object> temp_row_val = new ArrayList<Object>(row1_val);
						Collections.reverse(temp_row_val);
						for (Object toadd:temp_row_val){temp_temp_temp_row1.add(0,toadd);}
						temp_temp_temp_row1.add(p3);
						//System.out.println(temp_temp_temp_row1);
						joint.add(temp_temp_temp_row1);
						//System.out.println(p3);

						//System.out.println(" ");


					}
				}


			}




		}
		return joint;

	}

	public static ArrayList<ArrayList<Object>> joinCPT(ArrayList<ArrayList<Object>> b_cpt, ArrayList<ArrayList<Object>> c_cpt){
		List<Object> common = new ArrayList<Object>(b_cpt.get(0));
		common.retainAll(c_cpt.get(0));

		common.remove("Prob");

		ArrayList<ArrayList<Object>> joint = new ArrayList<ArrayList<Object>>();


		int pos1 = b_cpt.get(0).indexOf(common.get(0));
		int pos2 = c_cpt.get(0).indexOf(common.get(0));

		for (int i = 0; i < b_cpt.size(); i++){
			ArrayList<Object> temp_row1 = new ArrayList<Object>(b_cpt.get(i));
			for (int j = 0; j < c_cpt.size(); j++){
				ArrayList<Object> temp_row2 = new ArrayList<Object>(c_cpt.get(j));
				ArrayList<Object> place_holder = new ArrayList<Object>();
				if (temp_row1.get(pos1).equals(temp_row2.get(pos2))){

					ArrayList<Object> temp_temp_row1 = new ArrayList<Object>(temp_row1);
					ArrayList<Object> temp_temp_row2 = new ArrayList<Object>(temp_row2);
					if (i == 0 && j == 0){
						temp_temp_row1.remove(pos1);
						temp_temp_row2.remove(pos2);
						temp_temp_row1.remove(temp_temp_row1.size()-1);
						temp_temp_row2.remove(temp_temp_row2.size()-1);
						temp_temp_row1.addAll(temp_temp_row2);
						temp_temp_row1.add(0,temp_row1.get(pos1));
						temp_temp_row1.add("Prob");
						joint.add(temp_temp_row1);
					}
					else{

					double p1 = (double)temp_temp_row1.get(temp_temp_row1.size()-1);
					double p2 = (double)temp_temp_row2.get(temp_temp_row2.size()-1);

					double p3 = p1*p2;
					temp_temp_row1.remove(pos1);
					temp_temp_row2.remove(pos2);
					temp_temp_row1.remove(temp_temp_row1.size()-1);
					temp_temp_row2.remove(temp_temp_row2.size()-1);

					temp_temp_row1.addAll(temp_temp_row2);

					temp_temp_row1.add(0,temp_row1.get(pos1));
					temp_temp_row1.add(p3);
					joint.add(temp_temp_row1);
					}
				}
			}
		}
		return joint;
	}

	public static ArrayList<ArrayList<Object>> muljoinCPT(ArrayList<ArrayList<Object>> b_cpt, ArrayList<ArrayList<Object>> c_cpt){
		List<Object> common = new ArrayList<Object>(b_cpt.get(0));
		common.retainAll(c_cpt.get(0));

		common.remove("Prob");
		//System.out.println("common");
		//System.out.println(common);
		//System.out.println("");


		ArrayList<ArrayList<Object>> joint = new ArrayList<ArrayList<Object>>();

		ArrayList<Integer> pos1 = new ArrayList<Integer>();
		ArrayList<Integer> pos2 = new ArrayList<Integer>();

		for (Object com:common){
			pos1.add(b_cpt.get(0).indexOf(com));
			pos2.add(c_cpt.get(0).indexOf(com));

		}
		for (int i = 0; i < b_cpt.size(); i++){
			ArrayList<Object> temp_row1 = new ArrayList<Object>(b_cpt.get(i));
			ArrayList<Object> row1_val = new ArrayList<Object>();
			for (Integer pos: pos1){
				row1_val.add(temp_row1.get(pos));
			}
			for (int j = 0; j < c_cpt.size(); j++){
				ArrayList<Object> temp_row2 = new ArrayList<Object>(c_cpt.get(j));
				ArrayList<Object> row2_val = new ArrayList<Object>();
				for (Integer pos: pos2){
					row2_val.add(temp_row2.get(pos));
				}
				if (row1_val.equals(row2_val)){
					ArrayList<Object> temp_temp_row1 = new ArrayList<Object>(temp_row1);
					ArrayList<Object> temp_temp_row2 = new ArrayList<Object>(temp_row2);
					if (i == 0 && j == 0){

						temp_temp_row1.removeAll(row1_val);
						temp_temp_row2.removeAll(row2_val);

						temp_temp_row1.remove(temp_temp_row1.size()-1);
						temp_temp_row2.remove(temp_temp_row2.size()-1);
						temp_temp_row1.addAll(temp_temp_row2);
						for (Object toadd:row1_val){temp_temp_row1.add(0,toadd);}
						temp_temp_row1.add("Prob");
						//System.out.println(temp_temp_row1);
						joint.add(temp_temp_row1);
					}
					else{
						double p1 = (double)temp_temp_row1.get(temp_temp_row1.size()-1);
						double p2 = (double)temp_temp_row2.get(temp_temp_row2.size()-1);
						double p3 = p1*p2;

						Collections.sort(pos1, Collections.reverseOrder());
						for (int index : pos1){
							temp_temp_row1.remove(index);
						}

						Collections.sort(pos2, Collections.reverseOrder());
						for (int index : pos2){
							temp_temp_row2.remove(index);
						}

						temp_temp_row1.remove(temp_temp_row1.size()-1);
						temp_temp_row2.remove(temp_temp_row2.size()-1);
						temp_temp_row1.addAll(temp_temp_row2);

						for (Object toadd:row1_val){temp_temp_row1.add(0,toadd);}
						temp_temp_row1.add(p3);
						//System.out.println(temp_temp_row1);
						joint.add(temp_temp_row1);

					}

				}


			}
		}

		return joint;
	}



	public static ArrayList<ArrayList<Object>> elimination(String condition, ArrayList<ArrayList<Object>> b_cpt){
		int pos1 = b_cpt.get(0).indexOf(condition);
		ArrayList<ArrayList<Object>> sum_over = new ArrayList<ArrayList<Object>>();

		sum_over.add(b_cpt.get(0));
		sum_over.get(0).remove(pos1);

		for(int i = 1; i < b_cpt.size();i++){
			ArrayList<Object> temp_row = b_cpt.get(i);
			temp_row.remove(pos1);
			Boolean added = false;
			for (ArrayList<Object> sum_over_row: sum_over){
				ArrayList<Object> sum_over_row_trim = new ArrayList<Object>(sum_over_row);
				sum_over_row_trim.remove(sum_over_row_trim.size() - 1);
				ArrayList<Object> temp_row_trim = new ArrayList<Object>(temp_row);
				temp_row_trim.remove(temp_row_trim.size() - 1);

				if (sum_over_row_trim.equals(temp_row_trim)){
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

			Stack<Node> stk = new Stack<Node>();
			for (String query : queries){
				stk.push(forward_map.get(query));
			}


			ArrayList<Node> relavant_nodes = new ArrayList<Node>();
			while (!stk.empty()){
				Node current = stk.peek();
				ArrayList<Node> parents = current.getParents();
				relavant_nodes.add(current);
				stk.pop();
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
	public static void main(String[] args){
		// BNC

		Node P = new Node("P");
		Node Q = new Node("Q");
		Node R = new Node("R");
		Node V = new Node("V");
		Node S = new Node("S");
		Node Z = new Node("Z");
		Node U = new Node("U");
		BayesianNetwork bn = new BayesianNetwork();

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

		//BNb

		//BNB
		/*
		Node J = new Node("J");
		Node K = new Node("K");
		Node L = new Node("L");
		Node M = new Node("M");
		Node N = new Node("N");
		Node O = new Node("O");
		BayesianNetwork bn = new BayesianNetwork();

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
		*/


		//BNA
		/*
		//BNA
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		BayesianNetwork bn = new BayesianNetwork();

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
		*/

		HashMap<String, Node> forward_map = bn.getForwardMap();
		HashMap<Node, String> backward_map = bn.getBackwardMap();
		ArrayList<String> inquestion = new ArrayList<String>(Arrays.asList(new String[]{"U","Z","Q","R"}));
		ArrayList<String> evidence = new ArrayList<String>(Arrays.asList(new String[]{"Z","Q","R"}));
		ArrayList<String> evidence_bool = new ArrayList<String>(Arrays.asList(new String[]{"T","T","T"}));



		ArrayList<Node> relavant = prune(bn,inquestion);

		List<String> prune = new ArrayList<String>();

		for (Node node:relavant){
			prune.add(backward_map.get(node));
		}
		//System.out.println(" ");


		//List<String> sorted_order = Order.order(bn,inquestion.get(0));





		List<String> orderr = new ArrayList<String>(Arrays.asList(new String[] {"P","U","R","S","Q","V","Z"}));
		List<String> pruned_order = new ArrayList<String>();


		for (int i = 0; i < orderr.size(); i++){
			String current = orderr.get(i);
			if (prune.contains(current)){
				pruned_order.add(current);
		}}


		orderr = pruned_order;

		ArrayList<ArrayList<ArrayList<Object>>> factors  =  new ArrayList<ArrayList<ArrayList<Object>>>();
		for (String nnn:orderr){
			factors.add(forward_map.get(nnn).getCPT());
		}

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
		/*
		for (ArrayList<ArrayList<Object>> fact : factors){
			for (ArrayList<Object> row : fact){
				System.out.println(row);
			}
			System.out.println(" ");
			System.out.println(" ");
		}*/



		System.out.println("ORDER: "+orderr);






		System.out.println(" ");




		for (int i = 0; i < orderr.size(); i++){
			if ((factors.size() == 1 && factors.get(0).size() == 3)){
				break;
			}
			String label = orderr.get(i);
			System.out.println("ALL FACTORS: "+factors);


			ArrayList<ArrayList<ArrayList<Object>>> temp_factors = new ArrayList<ArrayList<ArrayList<Object>>>();
			for (int jj = 0; jj < factors.size(); jj++){
				ArrayList<ArrayList<Object>> current_factor = factors.get(jj);
				if (current_factor.get(0).contains(label)){
					temp_factors.add(current_factor);
				}
			}

			for (ArrayList<ArrayList<Object>> factor: temp_factors){
				factors.remove(factor);
			}

			System.out.println("Current label: "+label);
			System.out.println("RELAVENT FACTORS: "+temp_factors);
			//System.out.println(label);
			//System.out.println(temp_factors);
			while (temp_factors.size() > 1){
				ArrayList<ArrayList<Object>> temp_factors0 = temp_factors.get(0);
				ArrayList<ArrayList<Object>> temp_factors1 = temp_factors.get(1);
				System.out.println("start Join: ");
				System.out.println("factor 1: ");
				for (ArrayList<Object> row : temp_factors0){
					System.out.println(row);
				}
				temp_factors.remove(temp_factors0);
				System.out.println("factor 2: ");
				for (ArrayList<Object> row : temp_factors1){
					System.out.println(row);
				}
				temp_factors.remove(temp_factors1);

				System.out.println("Combined: ");
				for (ArrayList<Object> row : multi_multi_joinCPT(temp_factors0,temp_factors1)){
					System.out.println(row);
				}

				System.out.println("end Join: ");
				System.out.println(" ");
				temp_factors.add(multi_multi_joinCPT(temp_factors0,temp_factors1));
			}



		//for (ArrayList<Object> rowww: temp_factors.get(0)){System.out.println(rowww);}

			if (temp_factors.size() != 0){

				if (temp_factors.get(0).size() == 3 || label.equals(inquestion.get(0))){
					factors.add(temp_factors.get(0));
					for (ArrayList<Object> row : temp_factors.get(0)){
						System.out.println(row);
					}
					System.out.println(" ");
					System.out.println(" ");
				}

				if (temp_factors.get(0).size() != 3 && !label.equals(inquestion.get(0))){
				//System.out.println(temp_factors.get(0));


				ArrayList<ArrayList<Object>> updated_factor = elimination(label, temp_factors.get(0));

				System.out.println("updated_factor: ");



				for (ArrayList<Object> row : updated_factor){
					System.out.println(row);
				}
				factors.add(updated_factor);
				System.out.println("ALL FACTORS: "+ factors);
				System.out.println(" ");
				System.out.println(" ");

				}
			}

		}

		ArrayList<ArrayList<Object>> final_factors = factors.get(0);
		//ArrayList<ArrayList<Object>> final_factors = factors.get(0);
		double true_prob = (double)final_factors.get(1).get(1);
		double false_prob = (double)final_factors.get(2).get(1);
		double sum = true_prob+false_prob;
		final_factors.get(1).set(1,true_prob/sum);
		final_factors.get(2).set(1,false_prob/sum);
		System.out.println(final_factors);











































































































	}
}

