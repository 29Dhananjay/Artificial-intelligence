import java.util.*;
/**
 * P4 class: implimentation of the intermediate agent solver using CNF logic which does not use the logic NG library.
 * @author Dhananjay Saikumar
 */
public class P4 {
	/**
	 * solver: This function solves the game using the sps strategy.
	 * @param board truth board.
	 * @param current_map agent's board.
	 * @param verbose verbose.
	 * @param map agents knowledge.
	 * @param board_size number of cells.
	 * @param mine_size number of mines.
	 * @param mine_size number of blocked cells.
	 * @param game_won game won or lost
	 *
	 */
	public static void solver(char[][] board,char[][] current_map,Boolean verbose,HashMap<Integer, Character> map,int board_size,int mine_size, int blocked_size,Boolean game_won){
		int previous_unmarked = -99;
		int current_unmaked = board_size - blocked_size;
		// list of all possible states.
		List<Integer> state_labels = new ArrayList<>();
		for(int i = 0; i < board_size;i++){state_labels.add(i);}

		// repeat process until no new information is added (i.e number of unmark cells do not change)
		while (previous_unmarked != current_unmaked){
			previous_unmarked = current_unmaked;
			List<List<Integer>> KBU = new ArrayList<List<Integer>>();
		// list of clue states
		List<Integer> clue_states = new ArrayList<>();
		// list of unprobed/unmarked_states
		List<Integer> unprobed_states = new ArrayList<>();
		for(int i = 0; i < state_labels.size(); i++){
			int k = state_labels.get(i);
			char temp_state = Utility.mapper(k,current_map);
			if (temp_state != '0' && temp_state != 'b' && temp_state != '*'){
				if(temp_state == '?'){unprobed_states.add(k);}
				else{clue_states.add(k);}
			}
		}

		for (int clue_state: clue_states){
			// narrowing down unmarked cells for each clue state
			char tp_label = Utility.mapper(clue_state, current_map);
			List<Integer> unprobed_neighbours = new ArrayList<>();
			List<Integer> n_neighbours = Utility.neighbours(clue_state,current_map);
			for (int n_neighbour : n_neighbours){
				if (unprobed_states.contains(n_neighbour)){unprobed_neighbours.add(n_neighbour);}
			}


			int clue = Character.getNumericValue(Utility.mapper(clue_state, current_map));
			List<Integer> clue_neighbours = Utility.neighbours(clue_state,current_map);
			// reduce clue if one or more of the neighbours are already marked.
			for (int clue_neighbour : clue_neighbours){
				if (Utility.mapper(clue_neighbour, current_map) == '*'){
					clue -= 1;
				}
			}

			if (unprobed_neighbours.size() != 0 && (clue <= unprobed_neighbours.size())){
			//if(verbose == true){A2main.printBoard(current_map);}

			//System.out.println("Clue state: "+clue_state);
			//System.out.println("Clue state label: "+Utility.mapper(clue_state, current_map));
			//System.out.println("Actual clue : "+clue);
			//System.out.println("unprobed_neighbours: "+ unprobed_neighbours);
			List<Integer> combo = new ArrayList<Integer>();
			combo.removeAll(combo);
			int arr[] = unprobed_neighbours.stream().mapToInt(i->i).toArray();
			Utility.printCombination(arr,arr.length, clue,combo);
			//System.out.println("Combination: " + combo);
			// generating KBU and expanding KBU in CNF
			Utility.sentenceConstructorCNF(clue, combo,arr,unprobed_neighbours, KBU);
			//System.out.println("\n");

			//System.out.println(clue_state);
			//System.out.println(unprobed_neighbours);
			}
		}


		for (int unprobed: unprobed_states){
			// adding unprobed states if they are safe (KBU ^ danger = false)
			if (Utility.proveMineorFreeCNF(KBU, unprobed) == false){
				map.put(unprobed,Utility.mapper(unprobed, board));
				//System.out.println("state: " + unprobed + " marked as: " + Utility.mapper(unprobed, board));
				current_map = Utility.uncover(board,map);
			}
			//System.out.println("\n");

		}
		current_unmaked = 0;
		for (int a = 0; a < board.length; a++){
			for (int b = 0; b < board.length; b++){
				if (current_map[a][b] == '?'){
					current_unmaked += 1;
				}
			}
		}


	}





	}


}
