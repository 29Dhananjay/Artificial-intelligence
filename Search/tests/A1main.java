import java.util.*;
/**
 * class A1main
 * This class is the main class which is responsible for performing search. This class impliments the following search
 * algorithms: BFS, DFS, BestF, AStar
 * @author Dhananjay Saikumar
 */

public class A1main {
    public static String main(String[] args){
    String console = "";

    // The following conditional statements ensure that the input parameters are of the correct format
    if(args.length == 2){
    try {
      Conf check =Conf.valueOf(args[1]);
      } catch(IllegalArgumentException e){
      console = "Invalid ConfID";
      return console;
      //System.exit(0);
    }}

    if(args.length != 2){
      console = "usage: java A1main <DFS|BFS|AStar|BestF|...> <ConfID>";
      return console;}

    if(!(args[0].equals("BFS") || args[0].equals("DFS") || args[0].equals("AStar") || args[0].equals("BestF"))){
      console = "usage: java A1main <DFS|BFS|AStar|BestF|...> <ConfID>";
      return console;}

    else{
    //String algorithm = "BFS";
    //String algorithm = "DFS";
    //String algorithm = "BestF";
    //String algorithm = "AStar";
    //String algorithm = "AStar";


    String algorithm = args[0];

    // "CONF11"
    //Conf conf = Conf.valueOf("CONF11");
    //Conf conf = Conf.valueOf("CONF11");


    Conf conf = Conf.valueOf(args[1]);


    int[][] two_d_map = conf.getMap().getMap();
    int N = two_d_map.length;
    //int N = 6;

    int[] start_coordinate = new int[]{conf.getS().getC(),conf.getS().getR()};
    int[] goal_coordinate = new int[]{conf.getG().getC(),conf.getG().getR()};

    //coordinate_to_state converts the coordinnates into an integer state: (0,0) - > 0 and (0,1) - > 1 and (0,5) - > 5
    int start = Utility.coordinate_to_state(N,start_coordinate);
    int goal = Utility.coordinate_to_state(N,goal_coordinate);


    //Starter.printMap(conf.getMap(), conf.getS(), conf.getG());


    /*
    int[][] two_d_map = new int [][] {
      {0,0,0,0,0,0},
      {0,0,0,0,0,0},
      {0,0,1,1,0,0},
      {0,0,0,0,0,0},
      {0,0,0,0,0,0},
      {0,0,0,0,0,0}
    };*/

    //Integer states of cells which are lands (inaccessible)
    int[] land_states = Utility.land_states(two_d_map);

    //for(int i = 0; i < land_states.length; i++){System.out.println(land_states[i]);}


    // The following hashmap keeps track of states and its successor states: state 0 has neighbours 1 and 6 (for a 6x6 space)
    HashMap<Integer, Integer[]> map = new HashMap<Integer, Integer[]>();
    for(int i = 0; i < N*N; i++){
      map.put(i, Utility.successor(N, i,land_states,algorithm));

      /*
      System.out.println("State: " + i);
      Integer[] k = map.get(i);

      for(int j = 0 ;j < k.length; j ++){
        System.out.println(k[j]);
      }
      System.out.println(" "); */
    }

    //Creating the root node
    Node root = Utility.makeNode(new Node(0,N,goal), start,N,goal);
    root.parent = null;
    root.path = "" + "" + "(" + Utility.state_to_coordinate(N, root.state)[1] +
    "," + Utility.state_to_coordinate(N, root.state)[0] +")";
    root.path_cost = 0;
    root.f_cost = root.h_cost;
    root.depth = 0;




    // Conditional statement for basic agent
    if(algorithm.equals("BFS") || algorithm.equals("DFS")){

    // Using a Deque to create the frontier and explored data structures.
    Deque<Node> frontier = new ArrayDeque<Node>();
    Deque<Node> explored = new ArrayDeque<Node>();

    frontier.add(root);

    console = "["+ "(" + Utility.state_to_coordinate(N, root.state)[1] +
    "," + Utility.state_to_coordinate(N,root.state)[0] + ")" +"]";
    boolean achived_goal = false;


    while(!achived_goal){
      // if frontier is empty, then the algorithm fails
      if(frontier.size() == 0){
        achived_goal = true;
        console += "\n" + "fail";
        console += "\n" + explored.size();
        break;
      }
      // if frontier is not empty, then the algorithm seeks to expand its search by accessing all accessible states
      if(frontier.size() > 0){
        Node temp = frontier.peek();

        if (temp.state == goal){
          achived_goal = true;
          //System.out.println(" ");
          console += "\n" + temp.path;
          console += "\n" + (float) temp.path_cost;
          console += "\n" + ((explored.size()) + 1);
          break;
        }
        explored.add(temp);
        frontier.remove();

        Node[] succ = Utility.expand(temp,map,N,goal);
        // iterating through the successor states.
        for(int i = 0; i < succ.length; i++){
            int match = 0;
            // checking if the successor is already in the frontier
            for(Node frontier_element : frontier){
              if(frontier_element.state == succ[i].state){
                match = 1;
              }}
               // checking if the successor has already been explored.
              for(Node explored_element : explored){
                if(explored_element.state == succ[i].state){
                  match = 1;
                }}

                if(match == 0){
                  // if BFS is the agent type then the nodes are added in first-in first-out (FIFO) fashion
                  if(algorithm.equals("BFS")){frontier.add(succ[i]);}
                  // if DFS is the agent type then the nodes are added in last-in first-out (LIFO) fashion
                  else if(algorithm.equals("DFS")){frontier.addFirst(succ[i]);}

                  }
                }

                if(frontier.size() != 0){
                String f = "[";

                // Prints the frontier at every step
                for(Node frontier_element : frontier){
                  f += "" + "(" + Utility.state_to_coordinate(N, frontier_element.state)[1] +
                  "," + Utility.state_to_coordinate(N,frontier_element.state)[0] + ")"  + ",";
                }
                console += "\n" + f.substring(0, f.length() - 1)+"]";
              }

            }
        }

        return console;}
    if(algorithm.equals("BestF")){
      // The BestF agent operates using a priorityQueue which picks the nodes with the lowest heuristic.

      // The following Comparator comparess the h_cost of the said nodes.
      Comparator<Node> h_costComparator = (s1, s2) -> {
        return s1.h_cost - s2.h_cost;
      };

      PriorityQueue<Node> priorityQueue = new PriorityQueue<>(h_costComparator);

      //Deque<Node> frontier = new ArrayDeque<Node>();
      Deque<Node> explored = new ArrayDeque<Node>();

      priorityQueue.add(root);

      console = "["+ "(" + Utility.state_to_coordinate(N, root.state)[1] +
      "," + Utility.state_to_coordinate(N,root.state)[0] + ")" + ":" + (float) root.h_cost +"]";
      boolean achived_goal = false;

      while(!achived_goal){
        // if frontier is empty, then the algorithm fails
        if(priorityQueue.size() == 0){
          achived_goal = true;
          console += "\n" +  "fail";
          console += "\n" + explored.size();
          break;
        }
        // if frontier is not empty, then the algorithm seeks to expand its search by accessing all accessible states
        if(priorityQueue.size() > 0){
          Node temp = priorityQueue.peek();
          //System.out.println(temp.state);

          //System.out.println(temp.state);
          if (temp.state == goal){
            achived_goal = true;
            //System.out.println(" ");
            console += "\n" + temp.path;
            console += "\n" + (float) temp.path_cost;
            console += "\n" + (explored.size() + 1);
            break;
          }
          explored.add(temp);
          priorityQueue.remove();

          Node[] succ = Utility.expand(temp,map,N,goal);
          // iterating through the successor states.
          for(int i = 0; i < succ.length; i++){
              int match = 0;
              // checking if the successor is already in the frontier
              for(Node frontier_element : priorityQueue){
                if(frontier_element.state == succ[i].state){
                  match = 1;
                }}
                // checking if the successor has already been explored.
                for(Node explored_element : explored){
                  if(explored_element.state == succ[i].state){
                    match = 1;
                  }}

                  if(match == 0){
                    priorityQueue.add(succ[i]);
                    }
                  }
                  if(priorityQueue.size() != 0){
                  String f = "[";

                  /*
                  for(Node frontier_element : priorityQueue){
                    f += "" + "(" + Utility.state_to_coordinate(N, frontier_element.state)[0] +
                    "," + Utility.state_to_coordinate(N,frontier_element.state)[1] + ")" + ":" + frontier_element.h_cost + ",";
                  }
                  System.out.println(f.substring(0, f.length() - 1)+"]");
                  */
                  PriorityQueue<Node> placeholder = new PriorityQueue<>(h_costComparator);
                  Iterator<Node> iterator = priorityQueue.iterator();
                  // Prints the sorted (nodes arranged in acending order by their h_cost) BestF frontier at every step
                  while(iterator.hasNext()) {
                    Node frontier_element = priorityQueue.poll();
                    placeholder.add(frontier_element);
                    f += "" + "(" + Utility.state_to_coordinate(N, frontier_element.state)[1] +
                    "," + Utility.state_to_coordinate(N,frontier_element.state)[0] + ")" + ":" + (float) frontier_element.h_cost + ",";
                  }
                  console += "\n" + f.substring(0, f.length() - 1)+"]";
                  priorityQueue = placeholder;

                }

              }
          }

          return console;}

      if(algorithm.equals("AStar")){
      // The AStar agent operates using a priorityQueue which picks the nodes with the lowest f_cost.

      // The following Comparator comparess the f_cost of the said nodes.

        Comparator<Node> f_costComparator = (s1, s2) -> {
          return s1.f_cost - s2.f_cost;
        };
        PriorityQueue<Node> priorityQueueA = new PriorityQueue<>(f_costComparator);


        //Deque<Node> frontier = new ArrayDeque<Node>();
        Deque<Node> explored = new ArrayDeque<Node>();

        priorityQueueA.add(root);


        console = "["+ "(" + Utility.state_to_coordinate(N, root.state)[1] +
        "," + Utility.state_to_coordinate(N,root.state)[0] + ")" + ":" + (float) root.f_cost +"]";
        boolean achived_goal = false;

        while(!achived_goal){
        // if frontier is empty, then the algorithm fails

          if(priorityQueueA.size() == 0){
            achived_goal = true;
            console += "\n" + "fail";
            console += "\n" + explored.size();
            break;
          }
          // if frontier is not empty, then the algorithm seeks to expand its search by accessing all accessible states
          if(priorityQueueA.size() > 0){
            Node temp = priorityQueueA.peek();

            //System.out.println(temp.state);
            if (temp.state == goal){
              achived_goal = true;
              //System.out.println(" ");
              console += "\n" + temp.path;
              console += "\n" + (float) temp.path_cost;
              console += "\n" + (explored.size() + 1);
              break;
            }
            explored.add(temp);
            priorityQueueA.remove();

            Node[] succ = Utility.expand(temp,map,N,goal);
            // iterating through the successor states.
            for(int i = 0; i < succ.length; i++){
                int match = 0;
                int min_match = 0;
                // checking if the successor is already in the frontier and if the successor has a larger f_cost compared to its match in the frontier.
                for(Node frontier_element : priorityQueueA){
                  if((frontier_element.state == succ[i].state) && (frontier_element.f_cost < succ[i].f_cost)){
                    //System.out.println(frontier_element.state);
                    match = 1;
                  }}
                // checking if the successor has already been explored.
                for(Node explored_element : explored){
                  if(explored_element.state == succ[i].state){
                    match = 1;
                  }}

                  Node placeholder = priorityQueueA.peek();
                  // checking if the successor is already in the frontier and if the successor has a lower f_cost compared to its match in the frontier.
                  for(Node frontier_element : priorityQueueA){
                    if((frontier_element.state == succ[i].state) && (frontier_element.f_cost > succ[i].f_cost)){
                      placeholder = frontier_element;
                      min_match = 1;
                    }}

                    if(match == 0){
                      // adding the successor to the frontier if the successor if has not been explored or is already present in the frontier
                      priorityQueueA.add(succ[i]);
                      }

                    if(min_match == 1){
                      // adding the successor to the frontier if the successor if present in the frontier but has a lower f_cost.

                      //System.out.println("replacing: " + "(" + Utility.state_to_coordinate(N, placeholder.state)[1] +
                      //"," + Utility.state_to_coordinate(N,placeholder.state)[0] + ")" + ":" + (float) placeholder.f_cost + " with" + "(" + Utility.state_to_coordinate(N, succ[i].state)[1] +
                      //"," + Utility.state_to_coordinate(N,succ[i].state)[0] + ")" + ":" + succ[i].f_cost);
                      priorityQueueA.remove(placeholder);
                      priorityQueueA.add(succ[i]);
                      }
                    }

                    String f = "[";
                    PriorityQueue<Node> placeholder1 = new PriorityQueue<>(f_costComparator);
                    Iterator<Node> iterator = priorityQueueA.iterator();
                    // Prints the sorted (nodes arranged in acending order by their h_cost) BestF frontier at every step
                    while(iterator.hasNext()) {
                      Node frontier_element = priorityQueueA.poll();
                      placeholder1.add(frontier_element);
                      f += "" + "(" + Utility.state_to_coordinate(N, frontier_element.state)[1] +
                      "," + Utility.state_to_coordinate(N,frontier_element.state)[0] + ")" + ":" + (float) frontier_element.f_cost + ",";
                    }
                    if (f.length() > 1){
                      console += "\n" + f.substring(0, f.length() - 1)+"]";
                    priorityQueueA = placeholder1;
                    }}

            }

            return console;}

    }

  return console;}

}

