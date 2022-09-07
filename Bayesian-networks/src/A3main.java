import java.text.DecimalFormat;
import java.lang.Math;
import java.util.*;
import java.util.stream.*;

/********************Starter Code
 *
 * This class contains some examples on how to handle the required inputs and outputs
 *
 * @author at258
 *
 * run with
 * java A3main <Pn> <NID>
 *
 * Feel free to change and delete parts of the code as you prefer
 *
 */


public class A3main {

	public static void main(String[] args) {

		Scanner sc=new Scanner(System.in);

		switch(args[0]) {
			case "P1": {
				//construct the network in args[1]
				String network = args[1];
				ArrayList<String> order  = new ArrayList<String>();
				ArrayList<ArrayList<String>> evidence = new ArrayList<ArrayList<String>> ();
				ArrayList<ArrayList<Object>> final_factors = Solver.solver(network, "null", order, evidence,"print");


				//System.out.println("Network "+args[1]);
				//print the network
			}break;

			case "P2":  {
				//construct the network in args[1]
				String network = args[1];
				String[] query=getQueriedNode(sc);
				String variable = query[0];
				String value = query[1];
				ArrayList<String> order = getOrder(sc);
				ArrayList<ArrayList<String>> evidence = new ArrayList<ArrayList<String>> ();
				ArrayList<ArrayList<Object>> final_factors = Solver.solver(network, variable, order, evidence,"solve");
				if (value.equals("T")){
					printResult((double)final_factors.get(1).get(1));
				}
				else{
					printResult((double)final_factors.get(1).get(1));
				}



				// execute query of p(variable=value) with given order of elimination
				//double result=0.570501;
				//System.out.println(network);
				//System.out.println(variable);
				//System.out.println(value);
				//System.out.println(order);

				//printResult(result);
			}break;

			case "P3":{
				//construct the network in args[1]
				String[] query=getQueriedNode(sc);
				String network = args[1];
				String variable=query[0];
				String value=query[1];
				ArrayList<String> order = getOrder(sc);
				ArrayList<ArrayList<String>> evidence  = getEvidence(sc);

				ArrayList<ArrayList<Object>> final_factors = Solver.solver(network, variable, order, evidence,"solve");
				if (value.equals("T")){
					printResult((double)final_factors.get(1).get(1));
				}
				else{
					printResult((double)final_factors.get(1).get(1));
				}


				//System.out.println(network);
				//System.out.println(variable);
				//System.out.println(value);
				//System.out.println(evidence);
				//System.out.println(order);

				// execute query of p(variable=value|evidence) with given order of elimination
				//double result=0.570501;
				//printResult(result);
			}break;

			case "P4":{
				//construct the network in args[1]
				String[] query=getQueriedNode(sc);
				String network = args[1];
				String variable  = query[0];
				String value = query[1];
				ArrayList<String> order  = new ArrayList<String>();
				ArrayList<ArrayList<String>> evidence = getEvidence(sc);
				// execute query of p(variable=value|evidence) with given order of elimination
				//print the order

				ArrayList<ArrayList<Object>> final_factors = Solver.solver(network, variable, order, evidence,"solve");
				if (value.equals("T")){
					printResult((double)final_factors.get(1).get(1));
				}
				else{
					printResult((double)final_factors.get(1).get(1));
				}

				//double result=0.570501;
				//printResult(result);
			}break;
		}
		sc.close();
	}

	//method to obtain the evidence from the user
	private static ArrayList<ArrayList<String>> getEvidence(Scanner sc) {

		System.out.println("Evidence:");
		ArrayList<String[]> evidence =new ArrayList<String[]>();


		String[] line=sc.nextLine().split(" ");


		ArrayList<ArrayList<String>> evi = new ArrayList<ArrayList<String>>();
		ArrayList<String> lab = new ArrayList<String>();
		ArrayList<String> val = new ArrayList<String>();



		if (line[0].length() !=0) {
		for(String st:line) {
			String[] ev=st.split(":");
			lab.add(ev[0]);
			val.add(ev[1]);
			//evidence.add(ev);
		}
		evi.add(lab);
		evi.add(val);
		return evi;
		}
		else{return evi;}
		//return evidence;
	}

	//method to obtain the order from the user
	private static ArrayList<String> getOrder(Scanner sc) {
		System.out.println("Order:");
		String[] val=sc.nextLine().split(",");
		ArrayList<String> temp = new ArrayList<String>();
		Collections.addAll(temp, val);
		return temp;
	}


	//method to obtain the queried node from the user
	private static String[] getQueriedNode(Scanner sc) {

		System.out.println("Query:");
		String[] val=sc.nextLine().split(":");

		return val;

	}

	//method to format and print the result
	private static void printResult(double result) {

		DecimalFormat dd = new DecimalFormat("#0.00000");
		System.out.println(dd.format(result));
	}

}
