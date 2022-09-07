package src;
/**
 * @author Dhananjay Saikumar
 */
import org.jblas.*;
import java.util.List;
import minet.layer.init.*;
import minet.layer.Layer;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import minet.util.Pair;

/**
 * A class for Embedding bag layers.
 */
public class EmbeddingBag implements Layer, java.io.Serializable {

	private static final long serialVersionUID = -10445336293457309L;
	DoubleMatrix W;  // weight matrix (for simplicity, we can ignore the bias term b)
    int indims;
    int outdims;
    String part;
    String path;


    // for backward
    double[][] X;   // store input X for computing backward
    //List<int[]> X;  // store input X for computing backward, each element in this list is a sample (an array of word indices).
    DoubleMatrix gW;    // gradient of W

    /**
     * Constructor for EmbeddingBag
     * @param vocabSize (int) vocabulary size
     * @param outdims (int) output of this layer
     * @param wInit (WeightInit) weight initialisation method
     */
    public EmbeddingBag(int vocabSize, int outdims, WeightInit wInit,String part,String path) throws IOException{
        this.indims = vocabSize;
        this.outdims = outdims;
        this.part = part;
        this.path = path;
        this.W = wInit.generate(vocabSize, outdims);
        if (part.equals("part3") || part.equals("part4") || part.equals("part5") || part.equals("part6")){
            // loading custom word embeddings (GloVe or word2vec) for parts 3-6
            loadEmbedding();
        }
        this.gW = DoubleMatrix.zeros(vocabSize, outdims);
        // YOUR CODE HERE
    }

    /**
     * Forward pass
     * @param input (List<int[]>) input for forward calculation
     * @return a [batchsize x outdims] matrix, each row is the output of a sample in the batch
     */
    @Override
    public DoubleMatrix forward(Object input) {
        //DoubleMatrix Y = null; // output of this layer (to be computed by you)
        double[][] X =  (double[][])input;
        this.X = X;


            DoubleMatrix Y = DoubleMatrix.zeros(X.length,outdims);
            for (int i = 0; i < X.length; i++){
                DoubleMatrix sample = DoubleMatrix.zeros(outdims);
                double[] row = X[i];
                // SUMMING OVER EACH WORD EMBEDDING VECTOR FOR EACH WORD IN SENTENCE aka 'row'
                for (int j = 0; j < row.length; j++) {
                    sample.addi(W.getRow((int) (row[j])));
                }
                Y.putRow(i,sample);
            }
            return Y;

    }

    @Override
    public DoubleMatrix backward(DoubleMatrix gY) {
        // fills the gradient weights with 0 for parts 4 and part 6 (effectively freezing the weights)
        if (part.equals("part4") || part.equals("part6")){gW.fill(0);}
        else{
            for (int i = 0; i <X.length; i++){
                double[] indices = X[i];
                for (double index:indices){
                    int indexx = (int) index;
                    gW.putRow(indexx,gW.getRow(indexx).add(gY.getRow(i)));
                }
            }
        }
        return null;
    }

    @Override
    public List<DoubleMatrix> getAllWeights(List<DoubleMatrix> weights) {
        weights.add(W);
        return weights;
    }

    @Override
    public List<DoubleMatrix> getAllGradients(List<DoubleMatrix> grads) {
        grads.add(gW);
        return grads;
    }

    @Override
    public String toString() {
        return String.format("Embedding: %d rows, %d dims", W.rows, W.columns);
    }

    // loads embedding_vectors from appropriate files
    public void loadEmbedding() throws IOException {
        double[][] embedding_vectors = new double[8594][];
        BufferedReader br = new BufferedReader(new FileReader(path));
        String[] ss;
        String line;
        int count = 0;
        while ( (line = br.readLine()) != null ) {
            ss = line.split(" ");
            ArrayList<Double> Xs = new ArrayList<Double>();
            for (int j = 1; j < ss.length; j++) {
                Xs.add(Double.parseDouble(ss[j]));
            }
            double[] xs = new double[Xs.size()];
            xs = Xs.stream().mapToDouble(d -> d).toArray();
            embedding_vectors[count] = xs;
            count += 1;
        }
        this.W = new DoubleMatrix(embedding_vectors);

    }


}
