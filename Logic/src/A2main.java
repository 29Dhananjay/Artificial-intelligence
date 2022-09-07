
import java.util.*;
/**
 * A2main class.
 * @author Dhananjay Saikumar
 */
public class A2main {
	public static void main(String[] args) {
		String AND = "&";
		String OR = "|";
		String NOT = "~";
		Boolean game_won = false;
		Boolean game_lost = false;
		Boolean game_illogical = false;

		//String[] input = new String[] {"P4", "TEST4","verbose"};
		//args = input;

		boolean verbose=false;
		if (args.length>2 && args[2].equals("verbose") ){
			verbose=true; //prints agent's view at each step if true
		}

		System.out.println("-------------------------------------------\n");
		System.out.println("Agent " + args[0] + " plays " + args[1] + "\n");


		World world = World.valueOf(args[1]);

		char[][] board = world.map;
		printBoard(board);

		// Player's information
		HashMap<Integer, Character> map = new HashMap<Integer, Character>();


		//System.out.println("Input state: " + state);
		//System.out.println(mapper(state,board));

		int board_size = board.length*board.length;
		int mine_size = 0;
		int blocked_size;


		for(int i = 0;i<board_size;i++){
			char temp_label = Utility.mapper(i, board);
			// Counting mine_size
			if (temp_label == 'm'){
				mine_size += 1;
			}
			// location of blocked cells.
			if (temp_label == 'b'){
				map.put(i,'b');
			}
		}
		// addiing blocked cells to the map
		blocked_size = map.size();
		char[][] current_map = Utility.uncover(board,map);
		//number of proable cells
		int probable = board_size - blocked_size - mine_size;

		System.out.println("Start!");
		char temp_label;

		if (args[0].equals("P1")){
				if (verbose == true){printBoard(current_map);}
				// sweeping through each cell.
				for(int i = 0; i < board_size;i++){
					/*
					if (map.size() == (board_size - mine_size) || game_lost == true){
						game_won = true;
						break;
					}*/

					temp_label = Utility.mapper(i, board);

					if (!map.containsKey(i)){
						// adding non mine probed cells to the map.
						map.put(i,temp_label);
						if (temp_label == '0'){
							Utility.map_neighbours(i,board,map);
						}
						current_map = Utility.uncover(board,map);
						// player loses game if a mine is probed
						if (temp_label == 'm'){
							//current_map = uncover(board,map);
							game_lost = true;
							break;
						}

						if (map.size() == (board_size - mine_size)){ //|| game_lost == true){
							game_won = true;
							break;
						}
						if (game_won == false && game_lost == false){
								if(verbose == true){printBoard(current_map);}

						}
				}
			}
		}



		if (args[0].equals("P2")){
			//int mines_probed = 0;
			if (verbose == true){printBoard(current_map);}
			// list of states that are known to be safe (top left and the center states)
			List<Integer> safe_states = new ArrayList<>();
			safe_states.add(0);
			safe_states.add((board.length*board.length-1)/2);

			// adding safe states to the map
			for (int i: safe_states){
				map.put(i,Utility.mapper(i,board));
				if (Utility.mapper(i,board) == '0'){
					Utility.map_neighbours(i,board,map);
				}
				//System.out.println(mapper(i,board));
				current_map = Utility.uncover(board,map);
				//printBoard(current_map);
			}

			//System.out.println(map);
			// invoking p2 solver
			P2.solver(board, current_map, verbose, map, board_size, mine_size, blocked_size, game_won);
			// updating player's board
			current_map = Utility.uncover(board,map);


			int cells_probed = 0;
			int unmarked_cells = 0;
			for (int key : map.keySet()) {
				if (map.get(key) != '*'){cells_probed += 1;}
				if (map.get(key) == '?'){unmarked_cells += 1;}}
			// game ends in an illogical condition if player cannot resolve any more unmarked cells.
			if (cells_probed != (board_size-mine_size) || unmarked_cells != 0){game_illogical = true;}
			// player wins the game if all non mine cells have been probed/viewed.
			if (cells_probed == (board_size-mine_size) && unmarked_cells == 0){game_won = true;}
		}



		if (args[0].equals("P3")){
			if (verbose == true){printBoard(current_map);}
			// list of states that are known to be safe (top left and the center states)
			List<Integer> safe_states = new ArrayList<>();
			safe_states.add(0);
			safe_states.add((board.length*board.length-1)/2);

			// adding safe states to the map
			for (int i: safe_states){
				map.put(i,Utility.mapper(i,board));
				if (Utility.mapper(i,board) == '0'){
					Utility.map_neighbours(i,board,map);
				}
				//System.out.println(mapper(i,board));
				current_map = Utility.uncover(board,map);
				//printBoard(current_map);
			}
		if(verbose == true){A2main.printBoard(current_map);}
		int previous_unprobed = -95;
		int current_unprobed = -100;

		int count = 50;
		// repeating the process until no new infomration is added to the agent's map/board
		while (previous_unprobed != current_unprobed){
			previous_unprobed = current_unprobed;

			// invoking p3 solver
			P3.solver(board,current_map,verbose,map,board_size, mine_size,  blocked_size, game_won);
			current_map = Utility.uncover(board,map);
			//System.out.println("post logic");

			// invoking p2 solver
			P2.solver(board, current_map, verbose, map, board_size, mine_size, blocked_size, game_won);
			current_map = Utility.uncover(board,map);
			// counting unprobed cells.
			current_unprobed = 0;
			for (int a = 0; a < board.length; a++){
				for (int b = 0; b < board.length; b++){
					if (current_map[a][b] == '?'){
						current_unprobed += 1;
					}
				}

			}

		}
		//printBoard(current_map);

		//System.out.println(board_size-mine_size);
		int cells_probed = 0;
		int unmarked_cells = 0;
		for (int key : map.keySet()) {
			if (map.get(key) != '*'){cells_probed += 1;}
			if (map.get(key) == '?'){unmarked_cells += 1;}
		}
		// game ends in an illogical condition if player cannot resolve any more unmarked cells.
		if (cells_probed != (board_size-mine_size) || unmarked_cells != 0){game_illogical = true;}
		// player wins the game if all non mine cells have been probed/viewed.
		if (cells_probed == (board_size-mine_size) && unmarked_cells == 0){game_won = true;}
		}
		//System.out.println(map);




		if (args[0].equals("P4")){
			if (verbose == true){printBoard(current_map);}
			// list of states that are known to be safe (top left and the center states)
			List<Integer> safe_states = new ArrayList<>();
			safe_states.add(0);
			safe_states.add((board.length*board.length-1)/2);

			// adding safe states to the map
			for (int i: safe_states){
				map.put(i,Utility.mapper(i,board));
				if (Utility.mapper(i,board) == '0'){
					Utility.map_neighbours(i,board,map);
				}
				current_map = Utility.uncover(board,map);
			}
			if (verbose == true){printBoard(current_map);}
			int previous_unprobed = -95;
			int current_unprobed = -100;
			// repeating the process until no new infomration is added to the agent's map/board
			while (previous_unprobed != current_unprobed){
				previous_unprobed = current_unprobed;
				//invoking p4 solver
				P4.solver(board,current_map,verbose,map,board_size, mine_size,  blocked_size, game_won);
				current_map = Utility.uncover(board,map);
				//System.out.println("post logic");
				//invoking p2 solver
				P2.solver(board, current_map, verbose, map, board_size, mine_size, blocked_size, game_won);
				current_map = Utility.uncover(board,map);
				// counting unprobed cells.
				current_unprobed = 0;
				for (int a = 0; a < board.length; a++){
					for (int b = 0; b < board.length; b++){
						if (current_map[a][b] == '?'){
							current_unprobed += 1;
						}
					}

				}

			}
			//printBoard(current_map);



		int cells_probed = 0;
		int unmarked_cells = 0;
		for (int key : map.keySet()) {
			if (map.get(key) != '*'){cells_probed += 1;}
			if (map.get(key) == '?'){unmarked_cells += 1;}
		}
		// game ends in an illogical condition if player cannot resolve any more unmarked cells.
		if (cells_probed != (board_size-mine_size) || unmarked_cells != 0){game_illogical = true;}
		// player wins the game if all non mine cells have been probed/viewed.
		if (cells_probed == (board_size-mine_size) && unmarked_cells == 0){game_won = true;}
	}


		//templates to print results - copy to appropriate places
		System.out.println("Final map");
		printBoard(current_map);
		if (game_won == true){System.out.println("\nResult: Agent alive: all solved\n");}
		if (game_lost == true){System.out.println("\nResult: Agent dead: found mine\n");}
		if (game_illogical == true){System.out.println("\nResult: Agent not terminated\n");}
		//System.out.println("\nResult: Agent alive: all solved\n");
		//System.out.println("\nResult: Agent dead: found mine\n");
		//System.out.println("\nResult: Agent not terminated\n");


	}

	//prints the board in the required format - PLEASE DO NOT MODIFY
	public static void printBoard(char[][] board) {
		System.out.println();
		// first line
		System.out.print("    ");
		for (int j = 0; j < board[0].length; j++) {
			System.out.print(j + " "); // x indexes
		}
		System.out.println();
		// second line
		System.out.print("    ");
		for (int j = 0; j < board[0].length; j++) {
			System.out.print("- ");// separator
		}
		System.out.println();
		// the board
		for (int i = 0; i < board.length; i++) {
			System.out.print(" "+ i + "| ");// index+separator
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j] + " ");// value in the board
			}
			System.out.println();
		}
		System.out.println();
	}

}
