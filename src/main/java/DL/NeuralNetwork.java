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
import java.util.logging.Logger;


/**
 * Created by Yang Xu on 06/12/2016.
 */
public class NeuralNetwork {
    static Logger logger = Logger.getLogger(NeuralNetwork.class.getName() );

    MultiLayerNetwork first2Layers;
    MultiLayerNetwork next2Layers;
    static String firstLocationString = "";
    static String secLocationString= "";
    private static int outputNum = 100;
    private static final String url = "";
    private int rngSeed=123;
    private INDArray weights;
    public NeuralNetwork(boolean loadData) {
        if(loadData){
            loadNetwork();
        }
        else{
            initNetwork();
            weights = Nd4j.zeros(14);
        }
    }

    private void initNetwork() {

        MultiLayerConfiguration first2LayersConf = new NeuralNetConfiguration.Builder().seed(rngSeed)
                .iterations(1)
                .learningRate(0.1)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(14) // Number of input datapoints.
                        .nOut(outputNum) // Number of output datapoints.
                        .activation("elu") // Activation function.
                        .weightInit(WeightInit.XAVIER) // Weight initialization.
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(100)
                        .nOut(outputNum)
                        .activation("tanh")
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .pretrain(false).backprop(true)
                .build();

        MultiLayerConfiguration next2LayersConf = new NeuralNetConfiguration.Builder().seed(rngSeed)
                .iterations(1)
                .learningRate(0.1)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(210) // Number of input datapoints.
                        .nOut(outputNum) // Number of output datapoints.
                        .activation("elu") // Activation function.
                        .weightInit(WeightInit.XAVIER) // Weight initialization.
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(100)
                        .nOut(outputNum)
                        .activation("relu")
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .pretrain(false).backprop(true)
                .build();

        first2Layers = new MultiLayerNetwork(first2LayersConf);
        next2Layers = new MultiLayerNetwork(next2LayersConf);
        first2Layers.init();
        next2Layers.init();
    }
    private void loadNetwork() {

        File firstLocation = new File(firstLocationString);
        File secLocation = new File(secLocationString);
        try {
            first2Layers = ModelSerializer.restoreMultiLayerNetwork(firstLocation);
            next2Layers = ModelSerializer.restoreMultiLayerNetwork(secLocation);
            first2Layers.init();
            next2Layers.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Double evaluate(List<List<Double>> valMatrix) {
/*        List<Double> cTypes = getCTypes(valMatrix);
        INDArray input = preprocess(valMatrix);

        INDArray firstOutput = first2Layers.activate(input);
        INDArray maxPoolRes = maxPool(firstOutput);
        INDArray avgPoolRes = avgPool(firstOutput);
        INDArray poolRes = Nd4j.vstack(maxPoolRes, avgPoolRes);
        appendType(poolRes);
        INDArray secOutput = next2Layers.activate();
        return secOutput.mmul(weights).getDouble(0);

*/
        return 0.00;
        // TODO: 06/12/2016 evaluate action based on input features}
    }
    private void appendType(INDArray poolRes) {
    }

    private INDArray avgPool(INDArray firstOutput) {
        return Nd4j.zeros(14);
    }

    private INDArray preprocess(List<List<Double>> valMatrix) {
        INDArray res = Nd4j.zeros(14);
        for(int i = 1;i<valMatrix.size();i++){
            double[] array = new double[valMatrix.get(i).size()];
            for(int j = 0;j<valMatrix.size();j++){
                array[j] = valMatrix.get(i).get(j);
            }
            INDArray cur = Nd4j.create(array);
            Nd4j.vstack(res,cur);
        }
        logger.info("input size = " +res);
        
        return res;
    }


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

    public void train(List<GameRecord> records) {
        // TODO: 07/01/2017
        saveData(firstLocationString,secLocationString);
    }

    private void saveData(String firstLocationString, String secLocationString) {
    }
}
