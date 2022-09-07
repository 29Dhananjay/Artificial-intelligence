import java.util.*;
/**
 * Node class.
 * @author 210034146
 */

public class Node{
  public int N;
  public int state;
  public int goal;
  public Node parent;
  public String path;
  public int path_cost;
  public int depth;
  public int h_cost;
  public int f_cost;

  /**
   * Node constructor.
   * @param state current state.
   * @param N dimentions of the search space (i.e NxN matrix --> N).
   * @param goal goal state.
   */
  public Node(int state,int N, int goal) {
    this.state = state;
    this.N = N;
    this.goal = goal;
    this.h_cost = Utility.manhattanDistance(state,goal,N);
  }
}
