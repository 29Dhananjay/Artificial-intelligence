import java.text.DecimalFormat;
import java.util.*;
import java.lang.Math;
import java.util.stream.Collectors;

/**
 * class CPT @ Describes conditional probability tables
 * @author Dhananjay Saikumar
 */
public class CPT {
	private Node currentNode;
	private ArrayList<Node> parent;
	private ArrayList<ArrayList<Object>> cpt;
	private int variableCount;
    /**
     * CPT constructor.
     * @param Node current node as input.
     */
	public CPT(Node currentNode){
		this.currentNode = currentNode;
		this.parent = this.currentNode.getParents();
		this.variableCount = this.parent.size() + 1;
		this.cpt = new ArrayList<ArrayList<Object>>();
		}

	// get cpt
	public ArrayList<ArrayList<Object>> getCPT(){
		return this.cpt;
	}


	// generating binary strings for combinattions of cpt values
	public static void generateAllBinaryStrings(int n,int arr[], int i,ArrayList<ArrayList<Integer>> combo){
		if (i == n)
		{
		ArrayList<Integer> kn = new ArrayList<Integer>();
		for (int a : arr)
			kn.add(a);

		combo.add(kn);
		return;
		}

		arr[i] = 0;
		generateAllBinaryStrings(n, arr, i + 1,combo);

		arr[i] = 1;
		generateAllBinaryStrings(n, arr, i + 1,combo);

		}

	// add cpt values
	public void addvalues(double ... vals){
		ArrayList<Object> first = new ArrayList<Object>();
		// adding labels the cpt
		for(int i = 0; i < this.parent.size(); i++){first.add(this.parent.get(i).getName());}
		// adding current node's label to the cpt
		first.add(currentNode.getName());
		first.add("Prob");

		this.cpt.add(first);


		ArrayList<ArrayList<Integer>> combo = new ArrayList<ArrayList<Integer>>();
		int reps = (int)(Math.log(vals.length) / Math.log(2));

		int[] arr = new int[reps];
		// combinations of true and false values
		generateAllBinaryStrings(reps, arr, 0,combo);

		// adding combinations to the CPT
		for (int i = 0; i < combo.size(); i++){
			ArrayList<Integer> binary_temp_row= combo.get(i);
			ArrayList<Object> temp_row = new ArrayList<Object>();
			for (int j = 0; j < binary_temp_row.size(); j++){
				if (binary_temp_row.get(j) == 1){temp_row.add("F");}
				else{temp_row.add("T");}
			}
			temp_row.add(vals[i]);
			this.cpt.add(temp_row);

		}
	}




}

