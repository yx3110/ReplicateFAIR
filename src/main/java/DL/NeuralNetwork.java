package DL;

import Model.Command;
import Model.Feature;
import Model.State;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.*;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.rl4j.util.DataManager;

import java.util.List;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public class NeuralNetwork {



    final int numRows = 100; // The number of rows of a matrix.
    final int numColumns = 100; // The number of columns of a matrix.
    int outputNum = 100; // Number of possible outcomes (e.g. labels 0 through 9).

    static final int rngSeed = 123;

    public NeuralNetwork(String dataURL){
        loadData(dataURL);
    }
    public NeuralNetwork(){

        initNN();
    }

    private void initNN() {
        MultiLayerConfiguration conf1 = new NeuralNetConfiguration.Builder().seed(rngSeed).optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .iterations(1).learningRate(0.01).updater(Updater.NESTEROVS).momentum(0.9).list().build()
                ;
        MultiLayerNetwork model1 = new MultiLayerNetwork(conf1);
        model1.init();
    }

    public static NeuralNetwork loadData(String dataURL) {
        MultiLayerConfiguration conf1 = new NeuralNetConfiguration.Builder().seed(rngSeed).optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .iterations(1).learningRate(0.01).updater(Updater.NESTEROVS).momentum(0.9).list().build()
        ;
        MultiLayerNetwork model1 = new MultiLayerNetwork(conf1);
        model1.init();

        return null;
    }

    public double getScore(List<Feature> features) {

        return 0;
    }
}
