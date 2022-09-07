import java.util.*;
/**
 * P2 class: implimentation of the basic agent solver using single point strategy.
 * @author Dhananjay Saikumar
 */
public class P2 {
	/**
	 * solver: This function solves the game using the sps strategy.
	 * @param board truth board.
	 * @param current_map agent's board.
	 * @param verbose verbose.
	 * @param map agents knowledge.
	 * @param board_size number of cells.
	 * @param mine_size number of mines.
	 * @param blocked_size number of blocked cells.
	 * @param game_won game won or lost
	 *
	 */
	public static void solver(char[][] board,char[][] current_map,Boolean verbose,HashMap<Integer, Character> map,int board_size,int mine_size, int blocked_size,Boolean game_won){
		int mines_probed = 0;
		int previous_unmarked = -99;
		int current_unmaked = board_size - blocked_size;

		// list of all possible states.
		List<Integer> state_labels = new ArrayList<>();
			for(int i = 0; i < board_size;i++){state_labels.add(i);}

		// repeat process until no new information is added (i.e number of unmark cells do not change)
		while (previous_unmarked != current_unmaked){
			previous_unmarked = current_unmaked;
			for(int i = 0; i < state_labels.size(); i++){
				int k = state_labels.get(i);
				char temp_state = Utility.mapper(k,current_map);
				// only working with unmarked state
				if (temp_state == '?'){
					//System.out.println("State: " + k);
					//System.out.println("label: " + temp_state);
					//neighbours of current unmarked state
					List<Integer> neighbours = Utility.neighbours(k,current_map);

					for (int neighbour : neighbours){
						char temp_temp_state = Utility.mapper(neighbour,current_map);
						temp_state = Utility.mapper(k,current_map);
						// selecting only non unmarked/blocked/danger neighbours of the current unmarked state
						if (temp_state == '?' && temp_temp_state != '?' && temp_temp_state != 'b' && temp_temp_state != '*' ){
							//System.out.println("neighbour: " + neighbour);
							//System.out.println("neighbour label: " + temp_temp_state);

							int clue = Character.getNumericValue(temp_temp_state);

							// uncovering neighbours if one of the neighbours is 0
							if (clue == 0){
								Utility.map_neighbours(neighbour,board,map);
								current_map = Utility.uncover(board,map);
								}
							// if clue > 0
							if (clue > 0){

								List<Integer> adjacent_cells = Utility.neighbours(neighbour,current_map);
								int unknown_neighbours = adjacent_cells.size();
								int num_danger = 0;
								List<Integer> unprobed_cells = new ArrayList<>();

								for (int a_cell: adjacent_cells){
									if (map.containsKey(a_cell)){
										if (map.get(a_cell) != '*'){
											unknown_neighbours -=1;
										}
										if (map.get(a_cell) == '*'){
											num_danger +=1;
										}
									}
									else {unprobed_cells.add(a_cell);}
								}
								//System.out.println("unknown_neighbours: " + unknown_neighbours);
								//System.out.println("num_danger: " + num_danger);

								if (clue == num_danger){
									for (int u_cell: unprobed_cells){
										// All Free Neighbours
										map.put(u_cell,Utility.mapper(u_cell, board));
										//System.out.println("state: " + u_cell + " marked as: " + Utility.mapper(u_cell, board));
										current_map = Utility.uncover(board,map);
									}
								}

								if (clue == unknown_neighbours){
									//map.put(k,'*');
									//System.out.println("state: " + k + " marked as: *");


									for (int u_cell: unprobed_cells){
										// all marked neighbours
										map.put(u_cell,'*');
										if (Utility.mapper(u_cell,board) == 'm'){mines_probed += 1;}
										//System.out.println("state: " + u_cell + " marked as: *");
									}
									current_map = Utility.uncover(board,map);
								}

								}

							if ((mines_probed == mine_size)){
								break;
							}

							//if(verbose == true){A2main.printBoard(current_map);}

						}

					}
					}

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
