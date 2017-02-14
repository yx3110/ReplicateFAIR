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
import java.util.*;
import java.util.logging.Logger;


/**
 * Created by Yang Xu on 06/12/2016.
 */
public class NeuralNetwork {
    static Logger logger = Logger.getLogger(NeuralNetwork.class.getName() );

    private final double gamma = 0.90;

    private static final boolean isTraining = true;
    MultiLayerNetwork network;
    static String saveLocationString = "C:\\ReplicateFAIR\\ExampleBot\\src\\Data\\testSaving1.zip";
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
                .layer(1,new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(100)
                        .nOut(1)
                        .activation("tanh")
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

    public Double evaluate(List<HashMap<String,Double>> valMatrix) {
        INDArray input = preProcess(valMatrix);

        INDArray output= network.output(input);

        double res = output.getDouble(0);
        return res;
    }


    private INDArray preProcess(List<HashMap<String,Double>> valMatrix) {
        double[][] array = new double[valMatrix.size()][valMatrix.get(0).keySet().size()];
        for(int i = 0;i<valMatrix.size();i++){
            Set<String> curKeySet = valMatrix.get(i).keySet();
            int j = 0;
            for(String key:curKeySet){
                array[i][j] = valMatrix.get(i).get(key);
                j++;
            }
        }
        return Nd4j.create(array);
    }

/*
    private List<Double> addCTypes(double res) {
        return null;
    }

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
        if(!isTraining) return;
        logger.info("Starting training");
        for(int i =0;i<records.size();i++) {
            GameRecord sample = records.get(i);
            if(sample.getPrevState().size()!=0) {
                double[] labelArray = new double[1];
                labelArray[0] = getQValue(sample);

                INDArray inputArray = preProcess(sample.getPrevState());
                INDArray label = Nd4j.create(labelArray);
                network.fit(inputArray, label);
            }
        }
        logger.info("Training Complete");
        saveData();
    }

    private double getQValue(GameRecord sample) {
        if(sample==null) return 0;
        List<HashMap<String,Double>> vals1 = sample.getPrevState();
        List<HashMap<String,Double>> vals2 = sample.getCurState();
        int numOfUnits1 =sample.getCurNumberOfUnits();
        int numOfUnits2 = sample.getPrevNumberOfUnits();
        double myUnitHPDif = getHPDif(sample,true);
        double enemyUnitHPDif = getHPDif(sample,false);
        double nextBestQ = 0;
        List<List<HashMap<String,Double>>> possibleNextMoves = sample.getCurStatePossibleFeatures();
        for(List<HashMap<String,Double>> cur:possibleNextMoves){
            INDArray curINDArray = preProcess(cur);
            INDArray output= network.output(curINDArray);
            double res = output.getDouble(0);
            if(res>=nextBestQ) nextBestQ = res;
        }
        logger.info("myUnitHPDif:"+myUnitHPDif+", enemyUnitHPDif:"+enemyUnitHPDif);
        double res = ((myUnitHPDif-enemyUnitHPDif)+numOfUnits2*nextBestQ)/numOfUnits1;
        logger.info("NextBestQ = "+nextBestQ+",Expected Q value = "+res );
        return res;
    }

    private double getHPDif(GameRecord sample,boolean myUnits ) {
        double hp1 = 0;
        double hp2 = 0;
        if(myUnits){
            hp1 = sample.getMyCurTotalHP();
            hp2 = sample.getMyPrevTotalHP();
        }else{
            hp1 = sample.getEnemyCurTotalHP();
            hp2 = sample.getEnemyPrevTotalHP();
        }
        logger.info("hp1="+hp1+",hp2="+hp2);
        return Math.abs(hp1-hp2);
    }

    private GameRecord sampleRecord(List<GameRecord> records) {
        Random random = new Random();
        GameRecord res =records.get(random.nextInt(records.size()-1));

        return res;
    }

    private void saveData() {
        File locationToSave = new File(saveLocationString);
        boolean saveUpdater = true;                                             //Updater: i.e., the state for Momentum, RMSProp, Adagrad etc. Save this if you want to train your network more in the future
        try {
            ModelSerializer.writeModel(network, locationToSave, saveUpdater);
            logger.info("Data saved");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
