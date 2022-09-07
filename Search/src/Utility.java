import java.util.*;
/**
 * Utility class which contains helper functions.
 * @author Dhananjay Saikumar
 */

public class Utility {
    /**
   * makeNode function: creates a node from a given input state.
   * @param n parent node of the current node.
   * @param state current state.
   * @param N dimentions of the search space (i.e NxN matrix --> N).
   * @param start_y start position (y) of the vector drawing.
   * @param goal goal state.
   */
  public static Node makeNode(Node n, int state, int N, int goal){
    Node node = new Node(state,N,goal);
    node.parent = n;
    node.path = n.path + "" + "(" + state_to_coordinate(N, state)[1] + "," + state_to_coordinate(N, state)[0] +")";
    node.path_cost = n.path_cost + 1;
    node.depth = n.depth + 1;
    node.f_cost = node.h_cost + 1*node.path_cost;
    return node;
  }

  /**
   * expand function: returns successor nodes given the current node.
   * @param n current node.
   * @param map Hashmap of all states and their successors.
   * @param N dimentions of the search space (i.e NxN matrix --> N).
   * @param goal goal state.
   */
  public static Node[] expand(Node n,HashMap<Integer, Integer[]> map, int N, int goal){
    Node[] successor_nodes = new Node[0];
    if (map.containsKey(n.state)){
      Integer[] successor_states = map.get(n.state);
      //System.out.println(successor_states.length);
      successor_nodes = new Node[successor_states.length];
      for(int i = 0; i < successor_states.length; i++){
        successor_nodes[i] = makeNode(n, successor_states[i], N, goal);
      }
      return successor_nodes;
    }
    return successor_nodes;
  }

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
      coordinates[0] = x;
      coordinates[1] = y;
    }
  }
  return coordinates;
  }

  /**
   * land_states: given a 2d map, this function returns an array of states which are inaccessible.
   * @param array2D map/state space.
   */
  public static int[] land_states(int[][] array2D) {
    int[] array1D = new int[array2D.length*array2D.length];
    int[] land_states = new int[array2D.length*array2D.length];
    int pos = 0;
    for (int[] row: array2D) {
        System.arraycopy(row, 0, array1D, pos, row.length);
        pos += row.length;
    }

    for(int j = 0; j < land_states.length; j++) {
      if(array1D[j] == 0){
        land_states[j] = - 5;
      }
      else if(array1D[j] == 1){
        land_states[j] = j;
      }
    }
    land_states = Arrays.stream(land_states).filter(x -> x >= 0).toArray();
    return land_states;
  }

  /**
   * coordinate_to_state: given a coordinate (row,col), this function returns a state (0<= state <= (N*N)-1).
   * @param N dimentions of the search space (i.e NxN matrix --> N).
   * @param coordinates coordinates of the given state.
   */
  public static int coordinate_to_state(int N,int[] coordinates){
    if((coordinates[0] >= 0 && coordinates[0] < N) && (coordinates[1] >= 0 && coordinates[1] < N)){
      return (coordinates[1]*N + coordinates[0]);
    }
    // returns invalid state if the input coordinate is invalid (out of bounds)
    else return -5;
  }

  /**
   * manhattanDistance: given an input state and the goal state, this function returns the manhattan Distance (H_cost) between
   * the input and goal state.
   * @param state input state.
   * @param goal goal state..
   * @param N dimentions of the search space (i.e NxN matrix --> N).
   */
  public static int manhattanDistance(int state, int goal, int N){
    int h_cost = 0;
    int x1 = state_to_coordinate(N, state)[0];
    int y1 = state_to_coordinate(N, state)[1];
    int dir1 = -99;

    if((x1+y1) % 2 == 0){
      dir1 = 0;
    }
    else if((x1+y1) % 2 != 0){
      dir1 = 1;
    }
    int a1 = -y1;
    int b1 = (x1+y1-dir1)/2;
    int c1 = (x1+y1-dir1)/2 -y1 + dir1;

    int x2 = state_to_coordinate(N, goal)[0];
    int y2 = state_to_coordinate(N, goal)[1];
    int dir2 = -99;

    if((x2+y2) % 2 == 0){
      dir2 = 0;
    }

    else if((x2+y2) % 2 != 0){
      dir2 = 1;
    }
    int a2 = -y2;
    int b2 = (x2+y2-dir2)/2;
    int c2 = (x2+y2-dir2)/2 -y2 + dir2;

    h_cost = Math.abs(a1-a2) + Math.abs(b1-b2) + Math.abs(c1-c2);

    return h_cost;
  }

  /**
   * successor: This function returns potential successors states for a given input state.
   * @param N dimentions of the search space (i.e NxN matrix --> N).
   * @param state input state.
   * @param land_states array of land states.
   * @param algorithm type of agent (BFS, DFS, BestF, AStar ).
   */
  public static Integer[] successor(int N, int state,int[] land_states, String algorithm){
    // empty array to store potential states
    int[] successor_states = new int[4];
    // coordinates of a state which is to the left of the current state.
    int[] left = new int[2];
    // coordinates of a state which is to the right of the current state.
    int[] right = new int[2];
    // coordinates of a state which is above the current state.
    int[] up = new int[2];
    // coordinates of a state which is below the current state.
    int[] down = new int[2];

    // coordinates of a the current state.
    int[] coord = state_to_coordinate(N, state);

    int number = coord[0] + coord[1];

    // if column of current state = 0, then the current state has no neighboring state to the left (boundary state).
    if (coord[0] == 0){
      left[0] = 99;
      left[1] = 99;
    }
    else if (coord[0] > 0){
    left[0] = coord[0] -1;
    left[1] = coord[1];
    }

    // if column of current state = N-1, then the current state has no neighboring state to the right (boundary state).
    if(coord[0] == (N-1)){
      right[0] = 99;
      right[1] = 99;
    }
    // if the current state is not an edge state then the current state has neighbours to the right.
    if(coord[0] >= 0 && coord[0] < (N-1)){
    right[0] = coord[0] + 1;
    right[1] = coord[1];
    }


    // neighbour state above
    up[0] = coord[0];
    up[1] = coord[1]  -1;

    // downward state below
    down[0] = coord[0];
    down[1] = coord[1]  + 1;

    // upward facing triangle has no neighbours above
    if(number % 2 == 0){
      up[0] = 99;
      up[1] = 99;
    }

    // downward facing triangle has no neighbours below
    else if(number % 2 != 0){
      down[0] = 99;
      down[1] = 99;
    }

    if(algorithm.equals("BFS") || algorithm.equals("BestF") || algorithm.equals("AStar")){
      successor_states[0] = coordinate_to_state(N,right);
      successor_states[1] = coordinate_to_state(N,down);
      successor_states[2] = coordinate_to_state(N,left);
      successor_states[3] = coordinate_to_state(N,up);
    }

    if(algorithm.equals("DFS")){
      successor_states[0] = coordinate_to_state(N,up);
      successor_states[1] = coordinate_to_state(N,left);
      successor_states[2] = coordinate_to_state(N,down);
      successor_states[3] = coordinate_to_state(N,right);
    }


    if(land_states.length > 0){
      for(int i = 0; i < successor_states.length; i++){
        for(int j = 0; j < land_states.length; j++){
          if(successor_states[i] == land_states[j]){
            successor_states[i] = -5;
          }
        }
      }
  }

    // removes invalid states
    successor_states = Arrays.stream(successor_states).filter(x -> x >= 0).toArray();
    successor_states = Arrays.stream(successor_states).filter(x -> x <= (N*N -1)).toArray();

    Integer[] successors = new Integer[successor_states.length];
    for (int j = 0; j<successor_states.length; j++){
      successors[j] = Integer.valueOf(successor_states[j]);
    }
    return successors;
  }
}
