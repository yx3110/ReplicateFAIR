package DL;

import Model.Command;
import Model.State;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.*;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public class NeuralNetwork {

    public final static int colNum = 17;

    static final int rngSeed = 123;

    public static NeuralNetwork loadData(String dataURL) {
        MultiLayerConfiguration conf1 = new NeuralNetConfiguration.Builder().seed(rngSeed).optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .iterations(1).learningRate(0.01).updater(Updater.NESTEROVS).momentum(0.9).list().build()
        ;
        MultiLayerNetwork model1 = new MultiLayerNetwork(conf1);
        model1.init();



        return null;
    }

    public double getScore(State state, Command cur) {
        int rowNum = state.getAllUnits().size();

        return 0;
    }
}
