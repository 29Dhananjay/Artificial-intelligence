package src;
/**
 * @author Dhananjay Saikumar
 */
import org.jblas.util.Logger;

import java.util.Random;
import src.EmbeddingBag;
import src.TRECClassifier;
import src.TRECDataset;
import java.io.IOException;

public class A4Main {

    public static void main(String[] args) {
        // reads user input as arguments
        if (args.length < 6){
            System.out.println("Usage: java A4Main <part1/part2/part3/part4> <seed> <trainFile> <devFile> <testFile> <vocabFile> <classesFile>");
            return;
        }
        // start time
        long startTime = System.currentTimeMillis();

        // set jblas random seed (for reproducibility)
        String part = args[0];
        int seed = Integer.parseInt(args[1]);
		org.jblas.util.Random.seed(seed);
		Random rnd = new Random(seed);

        // feeding the arguments to the classifier program
        try {TRECClassifier.main(new String[] {args[1],args[2],args[3],args[4],args[5],args[6],args[0]});}
        catch (IOException e) {System.out.println(e);}




        // turn off jblas info messages
        Logger.getLogger().setLevel(Logger.WARNING);

        // end time
        long endTime = System.currentTimeMillis();

        System.out.println("That took " + (endTime - startTime)/1000 + " seconds");


    }
}
