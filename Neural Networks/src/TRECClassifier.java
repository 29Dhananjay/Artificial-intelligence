package src;

import minet.layer.*;
import minet.loss.CrossEntropy;
import minet.loss.Loss;
import minet.optim.Optimizer;
import minet.optim.SGD;
import minet.util.Pair;

import org.jblas.DoubleMatrix;
import org.jblas.util.Logger;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import minet.layer.init.*;

import src.EmbeddingBag;
import src.TRECDataset;


public class TRECClassifier {
    // Using the MNIST classifier as a template for TREC classifier (as suggested in the practical spec)

    /**
     * mini batch for the TREC dataset (part 1: one hot encoded vectors)
     * @param batch TREC tems
     * @return two DoubleMatrix objects: X (input) and Y (labels)
    */
    public static Pair<DoubleMatrix, DoubleMatrix> fromBatchp1(List<Pair<double[], Integer>> batch) {
        if (batch == null)
            return null;

        double[][] xs = new double[batch.size()][];
        double[] ys = new double[batch.size()];
        for (int i = 0; i < batch.size(); i++) {
            xs[i] = batch.get(i).first;
            ys[i] = (double)batch.get(i).second;
        }
        DoubleMatrix X = new DoubleMatrix(xs);
        DoubleMatrix Y = new DoubleMatrix(ys.length, 1, ys);
        return new Pair<DoubleMatrix, DoubleMatrix>(X, Y);
    }

    /**
     * mini batch for the TREC dataset (!part 1: reading indices directly (cannott be stored as a double matrix))
     * @param batch TREC tems
     * @return two objects: double[][] X (input) and DoubleMatrix Y (labels)
    */
    public static Pair<double[][], DoubleMatrix> fromBatch(List<Pair<double[], Integer>> batch) {
        if (batch == null)
            return null;

        double[][] xs = new double[batch.size()][];
        double[] ys = new double[batch.size()];
        for (int i = 0; i < batch.size(); i++) {
            xs[i] = batch.get(i).first;
            ys[i] = (double)batch.get(i).second;
        }
        //DoubleMatrix X = new DoubleMatrix(xs);
        DoubleMatrix Y = new DoubleMatrix(ys.length, 1, ys);
        return new Pair<double[][], DoubleMatrix>(xs, Y);
    }




	/**
     * classification accuracy of an ANN on a given dataset.
     * @param net an ANN model
	 * @param data an TREC dataset
     * @return the classification accuracy value (double, in the range of [0,1])
    */
    public static double eval(Layer net, TRECDataset data, String part) {
        // using the same template as the MNIST dataset with the exception of the conditional statements
        // reset index of the data
        data.reset();

        // the number of correct predictions so far
        double correct = 0;

        while (true) {
            // mini-batches of part 1 differes from the rest, hence the two branches
            if (part.equals("part1")){
                // we evaluate per mini-batch

                Pair<DoubleMatrix, DoubleMatrix> batch = fromBatchp1(data.getNextMiniBatch());
                if (batch == null)
                    break;

                // perform forward pass to compute Yhat (the predictions)
                // each row of Yhat is a probabilty distribution over 10 digits
                DoubleMatrix Yhat = net.forward(batch.first);

                // the predicted digit for each image is the one with the highest probability
                int[] preds = Yhat.rowArgmaxs();

                // count how many predictions are correct
                for (int i = 0; i < preds.length; i++) {
                    if (preds[i] == (int) batch.second.data[i])
                        correct++;
                }
            }

            if (!part.equals("part1")){
                // we evaluate per mini-batch
                Pair<double[][], DoubleMatrix> batch = fromBatch(data.getNextMiniBatch());
                if (batch == null)
                    break;

                // perform forward pass to compute Yhat (the predictions)
                // each row of Yhat is a probabilty distribution over 10 digits
                DoubleMatrix Yhat = net.forward(batch.first);

                // the predicted digit for each image is the one with the highest probability
                int[] preds = Yhat.rowArgmaxs();

                // count how many predictions are correct
                for (int i = 0; i < preds.length; i++) {
                    if (preds[i] == (int) batch.second.data[i])
                        correct++;
                }
            }

        }

        // compute classification accuracy
        double acc = correct / data.getSize();
        return acc;
    }

	/**
     * train an ANN for TREC
     * @param net an ANN model to be trained
	 * @param loss a loss function object
	 * @param optimizer the optimizer used for updating the model's weights (currently only SGD is supported)
	 * @param traindata training dataset
	 * @param devdata validation dataset (also called development dataset), used for early stopping
	 * @param nEpochs the maximum number of training epochs
	 * @param patience the maximum number of consecutive epochs where validation performance is allowed to non-increased, used for early stopping
    */
    public static void train(Layer net, Loss loss, Optimizer optimizer, TRECDataset traindata,
                             TRECDataset devdata, int nEpochs, int patience, String part) {
		int notAtPeak = 0;  // the number of times not at peak
		double peakAcc = -1;  // the best accuracy of the previous epochs
		double totalLoss = 0;  // the total loss of the current epoch

        traindata.reset(); // reset index and shuffle the data before training

        for (int e = 0; e < nEpochs; e++) {
            totalLoss = 0;

            while (true) {
                // mini-batches of part 1 differes from the rest, hence the two branches
                // get the next mini-batch
                if (part.equals("part1")){
                    Pair<DoubleMatrix, DoubleMatrix> batch = fromBatchp1(traindata.getNextMiniBatch());

                    if (batch == null)
                        break;

                    // always reset the gradients before performing backward
                    optimizer.resetGradients();

                    // calculate the loss value
                    DoubleMatrix Yhat = net.forward(batch.first);
                    double lossVal = loss.forward(batch.second, Yhat);

                    // calculate gradients of the weights using backprop algorithm
                    net.backward(loss.backward());

                    // update the weights using the calculated gradients
                    optimizer.updateWeights();

                    totalLoss += lossVal;
                }

                if (!part.equals("part1")){
                    Pair<double[][], DoubleMatrix> batch = fromBatch(traindata.getNextMiniBatch());

                    if (batch == null)
                        break;

                    // always reset the gradients before performing backward
                    optimizer.resetGradients();

                    // calculate the loss value
                    DoubleMatrix Yhat = net.forward(batch.first);
                    double lossVal = loss.forward(batch.second, Yhat);

                    // calculate gradients of the weights using backprop algorithm
                    net.backward(loss.backward());

                    // update the weights using the calculated gradients
                    optimizer.updateWeights();

                    totalLoss += lossVal;
                }

            }

            // evaluate and print performance
            double trainAcc = eval(net, traindata,part);
            double valAcc = eval(net, devdata,part);
            System.out.printf("epoch: %4d\tloss: %5.4f\ttrain-accuracy: %3.4f\tdev-accuracy: %3.4f\n", e, totalLoss, trainAcc, valAcc);

            // check termination condition
            if (valAcc <= peakAcc) {
                notAtPeak += 1;
                System.out.printf("not at peak %d times consecutively\n", notAtPeak);
            }
            else {
                notAtPeak = 0;
                peakAcc = valAcc;
            }
            if (notAtPeak == patience)
                break;
        }

        System.out.println("\ntraining is finished");
    }


    public static void main(String[] args) throws IOException {
        // print usage error
        if (args.length < 4){
            System.out.println("Usage: java TRECClassifier <seed> <traindata> <devdata> <testdata>");
            return;
        }

        // part of assigment (part1 or part 2 etc)
        String part = args[6];
        // set jblas random seed (for reproducibility)
		org.jblas.util.Random.seed(Integer.parseInt(args[0]));
		Random rnd = new Random(Integer.parseInt(args[0]));

        // turn off jblas info messages
        Logger.getLogger().setLevel(Logger.WARNING);

        double learningRate = 0.1;
        int batchsize = 50;
        int nEpochs = 500;
        int patience = 10;


        // load datasets
        System.out.println("\nSection: " + part);
        System.out.println("Loading data...");
        TRECDataset trainset = new TRECDataset(batchsize, true, rnd, part,args[4]);
        trainset.fromFile(args[1]);
        TRECDataset devset = new TRECDataset(batchsize, false, rnd, part,args[4]);
        devset.fromFile(args[2]);
        TRECDataset testset = new TRECDataset(batchsize, false, rnd, part,args[4]);
        testset.fromFile(args[3]);

        System.out.printf("train: %d instances\n", trainset.getSize());
        System.out.printf("dev: %d instances\n", devset.getSize());
        System.out.printf("test: %d instances\n", testset.getSize());
        System.out.printf("batchsize: %d\n", batchsize);
        System.out.printf("learning rate: "+ learningRate);

        // create a network
        System.out.println("\nCreating network...");
        int indims = trainset.getInputDims();
        int hiddenlayer1dims = 100;
        // changing the hidden layer size for part 5 and 6 for the word2vec embeddings
        if (part.equals("part5") || part.equals("part6")){
            hiddenlayer1dims = 300;
        }
        int hiddenlayer2dims = 200;
        int hiddenlayer3dims = 200;
        int outdims = 0;
        // estimating the output dimentions
        try{
            BufferedReader reader = new BufferedReader(new FileReader(args[5]));
            while (reader.readLine() != null) outdims++;
            reader.close();
        }
        catch (IOException e) {System.out.println(e.getMessage());}

        //int outdims = 50;
        Sequential net =
                    new Sequential(new Layer[] {
                    new Linear(indims, hiddenlayer1dims, new WeightInitXavier()),
                    new ReLU(),
                    new Linear(hiddenlayer1dims, hiddenlayer2dims, new WeightInitXavier()),
                    new ReLU(),
                    new Linear(hiddenlayer2dims, hiddenlayer3dims, new WeightInitXavier()),
                    new ReLU(),
                    new Linear(hiddenlayer3dims, outdims, new WeightInitXavier()),
                    new Softmax()});

        // using embedding layer for parts 2-6
        if (!part.equals("part1")){
                net = new Sequential(new Layer[] {
                    new EmbeddingBag(indims, hiddenlayer1dims, new WeightInitXavier(),part,args[4]),
                    new ReLU(),
                    new Linear(hiddenlayer1dims, hiddenlayer2dims, new WeightInitXavier()),
                    new ReLU(),
                    new Linear(hiddenlayer2dims, hiddenlayer3dims, new WeightInitXavier()),
                    new ReLU(),
                    new Linear(hiddenlayer3dims, outdims, new WeightInitXavier()),
                    new Softmax()});
        }




        CrossEntropy loss = new CrossEntropy();
        Optimizer sgd = new SGD(net, learningRate);
        System.out.println(net);

        // train network
        System.out.println("\nTraining...");
        train(net, loss, sgd, trainset, devset, nEpochs, patience,part);

        // perform on test set
        double testAcc = eval(net, testset,part);
        System.out.printf("\nTest accuracy: %.4f\n", testAcc);
    }
}
