package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import minet.data.Dataset;
import minet.util.Pair;

public class TRECDataset extends Dataset<double[], Integer> {

    int inputDims; // number of input features
    String part;
    String vocab_path;

    public TRECDataset(int batchsize, boolean shuffle, Random rnd,String part, String vocab_path) {
        super(batchsize, shuffle, rnd);
        this.part = part;
        this.vocab_path = vocab_path;
    }


    /**
     * Get the number of TREC input features
     */
    public int getInputDims() {
        return inputDims;
    }

    /**
     * Load TREC data
     */
    @Override
    public void fromFile(String path) throws IOException {
        // Input data file:
        //     First line: [number of samples] [xDims (784)]
        //     Each following line: [input features (a list of double values, separated by spaces)] ; [output label (an integer)]

        items = new ArrayList<Pair<double[], Integer>>();

        // estimating the input dimentions by reading the number of words in the vocabulary
        BufferedReader reader = new BufferedReader(new FileReader(vocab_path));
        inputDims = 0;
        while (reader.readLine() != null) inputDims++;
        reader.close();




        BufferedReader br = new BufferedReader(new FileReader(path));

        String[] ss;
        String line;





        //if (part.equals("part1") || part.equals("part2")) {inputDims = 3249;}
        //if (part.equals("part3") || part.equals("part4") || part.equals("part5") || part.equals("part6")) {inputDims = 8594;}
        //System.out.println(inputDims);

        int count = 0;
        while ( (line = br.readLine()) != null ) {

            count += 1;
            ss = line.split(" ; ");
            String[] sx = ss[0].split(" ");
            double[] xs = new double[inputDims];
            Integer y = Integer.valueOf(ss[1]);

            if (part.equals("part1")) {
                // hot encoding/bag of words
                for (int j = 0; j < xs.length; j++){xs[j] = 0;}

                for (int j = 0; j < sx.length; j++) {
                    int index = (int) Double.parseDouble(sx[j]);
                    xs[index] = 1;
                }
            }

            if (!part.equals("part1")) {
                // reading the word indices in each sentence as is
                ArrayList<Double> Xs = new ArrayList<Double>();
                for (int j = 0; j < sx.length; j++) {
                    Xs.add(Double.parseDouble(sx[j]));
                }

                xs = Xs.stream().mapToDouble(d -> d).toArray();
            }



            items.add(new Pair<double[], Integer>(xs, y));
        }

        br.close();
    }
}
