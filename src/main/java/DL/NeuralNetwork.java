package DL;


import Model.GameRecord;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;


/**
 * Created by Yang Xu on 06/12/2016.
 */
public class NeuralNetwork {
    static Logger logger = Logger.getLogger(NeuralNetwork.class.getName() );

    private final double gamma = 0.90;

    MultiLayerNetwork network;
    static String saveLocationString = "C:\\ReplicateFAIR\\ExampleBot\\src\\Data\\testSaving.zip";
    INDArray weights;
    private static final String url = "";
    //Random number generator seed, for reproducability
    public static final int seed = 123;
    //Number of iterations per minibatch
    public static final int iterations = 1;

    //Number of data points
    public static final int nSamples = 1000;
    //Batch size: i.e., each epoch has nSamples/batchSize parameter updates
    public static final int batchSize = 100;
    //Network learning rate
    public static final double learningRate = 0.01;
    public static final int numInputs = 14;
    public static final int numOutputs = 100;
    public NeuralNetwork(boolean loadData) {
        if(loadData){
            loadNetwork();
        }
        else{
            initNetwork();
            weights = Nd4j.zeros(numInputs);
        }
    }

    private void initNetwork() {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder().seed(seed)
                .iterations(iterations)
                .weightInit(WeightInit.XAVIER) // Weight initialization.
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(learningRate)
                .list()
                .layer(0,new DenseLayer.Builder()
                        .nIn(numInputs)
                        .nOut(numOutputs)
                        .activation("elu")
                        .build())
                .layer(1,new DenseLayer.Builder()
                        .nIn(100)
                        .nOut(100)
                        .activation("tanh")
                        .build())
                .layer(2,new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation("relu")
                        .nIn(100)
                        .nOut(1)
                        .build())
                .pretrain(false).backprop(true)
                .build();
        network = new MultiLayerNetwork(conf);
        network.init();
    }


    private void loadNetwork() {

        File firstLocation = new File(saveLocationString);
        try {
            network = ModelSerializer.restoreMultiLayerNetwork(firstLocation);
            network.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Double evaluate(List<List<Double>> valMatrix) {
        INDArray input = preProcess(valMatrix);

        INDArray output= network.output(input);

        return output.getDouble(0);

        // return 0.00;
        // TODO: 06/12/2016 evaluate action based on input features}
    }

    private INDArray maxPool(INDArray firstOutput) {
        return null;
    }

    private void appendType(INDArray poolRes) {
    }

    private INDArray avgPool(INDArray firstOutput) {
        return Nd4j.zeros(14);
    }

    private INDArray preProcess(List<List<Double>> valMatrix) {
        double[][] array = new double[valMatrix.size()][valMatrix.get(0).size()];
        for(int i = 0;i<valMatrix.size();i++){
            for(int j = 0;j<valMatrix.get(0).size();j++){
                array[i][j] = valMatrix.get(i).get(j);
            }
        }
        return Nd4j.create(array);
    }


    private List<Double> addCTypes(double res) {
        return null;
    }
/*
    List<Double> getCTypes(List<List<Double>> valMatrix){
        List<Double> res = new ArrayList<>();
        for(List<Double> curList:valMatrix){
            double cType = curList.remove(curList.size()-1);
            res.add(cType);
        }
        return res;
    }
*/
    public void train(List<GameRecord> records) {
        // TODO: 07/01/2017
        logger.info("Starting training");
        for(int i =0;i<batchSize;i++) {
            List<GameRecord> sample = sampleRecord(records);
            double reward1 = sample.get(0).getReward();
            double reward2 = sample.get(1).getReward();
            double[] array = new double[1];
            array[0] = reward2-reward1;
            INDArray label = Nd4j.create(array);
            network.fit(label);
        }
        saveData();
    }

    private List<GameRecord> sampleRecord(List<GameRecord> records) {
        if(records.size()==0) return new ArrayList<>();
        Random random = new Random();


        int sampleIndex = random.nextInt(records.size()-1);
        List<GameRecord> res = new ArrayList<>();
        res.add(records.get(sampleIndex));
        res.add(records.get(sampleIndex+1));
        return res;
    }

    private void saveData() {
        File locationToSave = new File(saveLocationString);
        boolean saveUpdater = true;                                             //Updater: i.e., the state for Momentum, RMSProp, Adagrad etc. Save this if you want to train your network more in the future
        try {
            ModelSerializer.writeModel(network, locationToSave, saveUpdater);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
