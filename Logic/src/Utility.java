import java.util.*;

import org.logicng.datastructures.Tristate;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;
import org.logicng.solvers.MiniSat;
import org.logicng.solvers.SATSolver;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;
import org.sat4j.core.VecInt;
import org.sat4j.pb.SolverFactory;

/**
 * Utility class: this class contains all the helper functions for various operations.
 * @author Dhananjay Saikumar
 */
public class Utility {
	/**
	 * state_to_coordinate: given a state (0<= state <= (N*N)-1), this function returns a coordinate.
	 * @param N dimentions of the search space (i.e NxN matrix --> N).
	 * @param state current state.
	 */
	public static int[] state_to_coordinate(int N, int state){
		int[] coordinates = new int[2];
		int x;
		int y;
		for(int i = 0; i < 50; i++){
			if((state - N*i) >= 0 && (state - N*i) <= N){
			x = 1*(state - N*i);
			y = i;
			coordinates[0] = y;
			coordinates[1] = x;
			}
		}
		return coordinates;
	}

	/** function taken from //https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
	 * combinationUtil: given a state (0<= state <= (N*N)-1), this function returns a coordinate.
	 * @param arr array of ints which need to be combined.
	 * @param n n in n choose r.
	 * @param r r in n choose r.
	 */
    public static void combinationUtil(int arr[], int n, int r, int index, int data[], int i,List<Integer> array_combo){
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            for (int j=0; j<r; j++){
				//System.out.print(data[j]+" ");
				array_combo.add(data[j]);
			}
            	//System.out.println("");

        return;

        }
        // When no more elements are there to put in data[]
        if (i >= n)
        return;

        // current is included, put next at next location
        data[index] = arr[i];
        combinationUtil(arr, n, r, index+1, data, i+1,array_combo);

        // current is excluded, replace it with next (Note that
        // i+1 is passed, but index is not changed)
        combinationUtil(arr, n, r, index, data, i+1,array_combo);
    }

	// The main function that prints all combinations of size r
    public static void printCombination(int arr[], int n, int r,List<Integer> array_combo){
        // A temporary array to store all combination one by one
        int data[]=new int[r];
        // Print all combination using temporary array 'data[]'
        combinationUtil(arr, n, r, 0, data, 0,array_combo);
    }

	/**
	 * coordinate_to_state: given a coordinate, this function returns the state the coordinate represents.
	 * @param N size of the board.
	 * @param coordinates coordinates to be converted.
	 */
	public static int coordinate_to_state(int N,int[] coordinates){
		if((coordinates[0] >= 0 && coordinates[0] < N) && (coordinates[1] >= 0 && coordinates[1] < N)){
		  return (coordinates[0]*N + coordinates[1]);
		}
		// returns invalid state if the input coordinate is invalid (out of bounds)
		else return -5;
	  }


	/**
	 * mapper: given an input state, this function returns the label of that state on the map (i.e clue or mine or blocked).
	 * @param state input state.
	 * @param board truth board.
	 */
	public static char mapper(int state, char[][] board){
		int[] coord = state_to_coordinate(board.length,state);
		char cell = board[coord[0]][coord[1]];
		return cell;
	}

	/**
	 * uncover: this function updates the player's map to represent once the player's updated knowledge (knowledge is represented by a hashmap)
	 * @param board truth board.
	 * @param map agent's knowledge.
	 */
	public static char[][] uncover(char[][] board, HashMap<Integer, Character> map){
		char[][] agent_board = new char[board.length][board.length];
		for(int i = 0;i<board.length*board.length;i++){
			int[] state_coord = state_to_coordinate(board.length,i);

			if (map.containsKey(i)){
				if (board[state_coord[0]][state_coord[1]] == 'm'){
					if (map.get(i) == 'm'){agent_board[state_coord[0]][state_coord[1]] = '-';}
					//agent_board[state_coord[0]][state_coord[1]] = map.get(i);

					if (map.get(i) == '*'){agent_board[state_coord[0]][state_coord[1]] = '*';}

				}
				else {
					//agent_board[state_coord[0]][state_coord[1]] = board[state_coord[0]][state_coord[1]];
					agent_board[state_coord[0]][state_coord[1]] = map.get(i);
				}
			}
			else {
				agent_board[state_coord[0]][state_coord[1]] = '?';
			}
		}
		return agent_board;
	}

	/**
	 * neighbours: given an input state, this function returns a list of neighbours for that state.
	 * @param temp_state input state.
	 * @param board truth board.
	 */
	public static List<Integer> neighbours(int temp_state,char[][] board){
		// converting state to coordinates
		int[] temp_cord = state_to_coordinate(board.length, temp_state);
		List<Integer> neighbours = new ArrayList<>();
		// finding neighbours using a nested for loop
		for (int i = temp_cord[0]-1; i < temp_cord[0]+2;i++){
			for (int j = temp_cord[1]-1; j <temp_cord[1]+2;j++){
				if (!(i < 0 || i > (board.length -1) || j < 0 || j > (board.length -1) )){
						int neighbour = coordinate_to_state(board.length,new int[]{i,j});
						if (neighbour != temp_state){
							neighbours.add(neighbour);
						}
				}
			}
		}
		return neighbours;
	}

	/**
	 * map_neighbours: given an input state (which is probed), this function updates the player's knowledge (map) by expanding the board (i.e cascading 0 clue cells)
	 * @param i input state.
	 * @param board truth board.
	 * @param map agent's knowledge.
	 */
	public static void map_neighbours(int i, char[][]board, HashMap<Integer, Character> map){
		List<Integer> adjacent_cells = neighbours(i,board);
		ListIterator<Integer> iter = adjacent_cells.listIterator();

		for (int j = 0; j < adjacent_cells.size(); j++){
			int t_state = adjacent_cells.get(j);
			map.put(t_state, mapper(t_state, board));
			if (mapper(t_state, board) == '0'){
				List<Integer> a_adjacent_cells = neighbours(t_state,board);
				for (int element : a_adjacent_cells){
					if (!map.containsKey(element)){
						//System.out.println(element);
						adjacent_cells.add(element);
					}
				}
			}
		}
	}

	/**
	 * proveMineorFreeCNF: given an input state and a knowledge base of unknowns in CNf, this functtion proves if the input state is aproachable or not.
	 * @param KBU knowledge base of unknowns.
	 * @param state state in question.
	 */
	public static Boolean proveMineorFreeCNF(List<List<Integer>> KBU, int state){
		int MAXVAR = 1000000;
		int NBCLAUSES = 500000;
		Boolean result = true;
		ISolver solver = SolverFactory.newDefault();
		solver.newVar(MAXVAR);
		solver.setExpectedNumberOfClauses(NBCLAUSES);
		try {
			//System.out.println(KBU);
			for (List<Integer> k : KBU){
				int arr[] = k.stream().mapToInt(i->i).toArray();
				solver.addClause(new VecInt(arr));
				//System.out.println(arr);
			}
			//solver.addClause(new VecInt(new int[] {5,8}));
			solver.addClause(new VecInt(new int[] {state}));
			//System.out.println("[" + state + "]");

		}
		catch(ContradictionException e){
			//e.printStackTrace();
		}
		IProblem problem = solver;
		try {
			//System.out.println(problem.isSatisfiable());
			result = problem.isSatisfiable();
			//System.out.println("RESULT: " + result);
		}
		catch(TimeoutException e){}

		return result;
	}



	/**
	 * proveMineorFreeDNF: given an input state and a knowledge base of unknowns in DNF, this functtion proves if the input state is aproachable or not.
	 * @param KBU knowledge base of unknowns.
	 * @param state state in question.
	 */
	public static Boolean proveMineorFreeDNF(String KBU, int state){
		String AND = "&";
		String OR = "|";
		String NOT = "~";

		FormulaFactory f = new FormulaFactory();
		PropositionalParser p = new PropositionalParser(f);
		SATSolver miniSat = MiniSat.miniSat(f);

		try {
			String query = KBU + AND + state;
			//query = "(A)" + AND + NOT + "(A)";
			//System.out.println("query: "+ query);
			if (query.charAt(0) != '&'){
				Formula formula = p.parse(query);
				miniSat.add(formula);
				Tristate result = miniSat.sat();
				//System.out.println("RESULT: " + result);
				if (result.equals(Tristate.TRUE)){return true;}
				if (result.equals(Tristate.FALSE)){return false;}

			}
		}
		catch (ParserException e){
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * sentenceConstructorCNF: This function generates a KBU for a given input state.
	 * @param cclue clue.
	 * @param combo possible combinations of unmarked neighbours.
	 * @param arr unmarked neighbours.
	 * @param e unmarked neighbours.
	 * @param KBU knowledge base of unknowns.
	 */
	public static void sentenceConstructorCNF(int cclue, List<Integer> combo, int arr[],List<Integer> e,List<List<Integer>> KBU){
		if (cclue == 1 && arr.length == 2){
			List<Integer> clause1 = new ArrayList<Integer>();
			clause1.add(combo.get(0));
			clause1.add(combo.get(1));
			KBU.add(clause1);

			List<Integer> clause2 = new ArrayList<Integer>();
			clause2.add(-1*combo.get(0));
			clause2.add(-1*combo.get(1));
			KBU.add(clause2);

			//System.out.println("KBU: " + KBU);
			//System.out.println("\n ");
			//int[] clause1 = new int[] {combo.get(0),combo.get(1)};
			//int[] clause2 = new int[] {-1*combo.get(0),-1*combo.get(1)};
		}

		if (cclue == arr.length){
			List<Integer> clause1 = new ArrayList<Integer>();
			for (int n:arr){
				clause1.add(n);
			}
			KBU.add(clause1);
			//System.out.println("KBU: " + KBU);
			//System.out.println("\n ");


			//clause1.add(combo.get(0));
			//int[] clause1 = new int[] {combo.get(0)};
		}

		if (cclue >= 1 && arr.length != 1 && arr.length != 2 && cclue != arr.length){
			//String sentence = "(";
			int[][] int_combo_array = new int[combo.size()/cclue][cclue];
			for (int i = 0; i <int_combo_array.length ; i++) {
				for (int j = 0; j <int_combo_array[j].length ; j++) {
					int_combo_array[i][j] = combo.get(i * int_combo_array[j].length + j);
				}
			}

			for (int[] ele:int_combo_array){
				List<Integer> negated = new ArrayList<Integer>(e);
				List<Integer> sen = new ArrayList<Integer>();
				int[] comb = ele;
				for (int c: comb){
					sen.add(c);
					//sentence += c + AND;
					negated.remove(Integer.valueOf(c));
				}
				for (int n:negated){
					sen.add(-1*n);
					//sentence += NOT + n + AND;
				}
				KBU.add(sen);
				//sentence = sentence.replaceFirst(".$","");
				//sentence += ")" + OR + "(";
			}
			KBU.add(e);
			//System.out.println("KBU: " + KBU);
			//System.out.println("\n ");

		}

	}

	/**
	 * sentenceConstructorDNF: This function generates a KBU for a given input state.
	 * @param cclue clue.
	 * @param combo possible combinations of unmarked neighbours.
	 * @param arr unmarked neighbours.
	 * @param e unmarked neighbours.
	 */
	public static String sentenceConstructorDNF(int cclue, List<Integer> combo, int arr[],List<Integer> e){
		String AND = "&";
		String OR = "|";
		String NOT = "~";

		String sentence = "(";
		if (arr.length == cclue){
			for (int ele: combo){
				sentence += ele + AND;
			}
			sentence += ")";
			sentence = sentence.substring(0, sentence.length() - 2) + sentence.charAt(sentence.length() - 1);
		}

		if (cclue == 1 && arr.length != cclue){
			for (int ele: combo){
				List<Integer> negated = new ArrayList<Integer>(e);
				negated.remove(Integer.valueOf(ele));
				sentence += ele + AND;
				for (int n:negated){
					sentence += NOT + n + AND;
				}
				sentence = sentence.replaceFirst(".$","");
				sentence += ")" + OR + "(";
			}
			sentence = sentence.replaceFirst(".$","");
			sentence = sentence.replaceFirst(".$","");
		}

		if (cclue > 1 && arr.length != cclue){
			int[][] int_combo_array = new int[combo.size()/cclue][cclue];
			for (int i = 0; i <int_combo_array.length ; i++) {
				for (int j = 0; j <int_combo_array[j].length ; j++) {
					int_combo_array[i][j] = combo.get(i * int_combo_array[j].length + j);
				}
			}

			for (int[] ele:int_combo_array){
				List<Integer> negated = new ArrayList<Integer>(e);
				int[] comb = ele;
				for (int c: comb){
					sentence += c + AND;
					negated.remove(Integer.valueOf(c));
				}
				for (int n:negated){
					sentence += NOT + n + AND;
				}
				sentence = sentence.replaceFirst(".$","");
				sentence += ")" + OR + "(";
			}
			sentence = sentence.replaceFirst(".$","");
			sentence = sentence.replaceFirst(".$","");
			}
			return sentence;

	}

}
