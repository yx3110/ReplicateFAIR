package RL;

import Model.*;
import Connection.Client;
import bwapi.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public class DQNLearner extends AILearner {
    static Logger logger = Logger.getLogger( DQNLearner.class.getName() );

    Random random;
    Client client;

    private static final double epsilon = 0.9;

    public DQNLearner(boolean isTraining) {
        super(isTraining);
        random = new Random();
        client = new Client();
    }

    public List<Action> playTrained(State state) {
        List<Feature> features = new ArrayList<Feature>();
        while (state.hasNextSubState()) {
            SubState curSubState = state.getNextSubState();
            Feature curRes = getNextFeatureTrained(curSubState,features);
            features.add(curRes);
        }
        return transToActions(features);

    }

    private Feature getNextFeatureTrained(SubState curSubState, List<Feature> features) {
        // TODO: 07/12/2016  
        return null;
    }

    public List<Action> playTraining(State state) {

        List<Feature> features = new ArrayList<Feature>();
        while (state.hasNextSubState()) {
            SubState curSubState = state.getNextSubState();
            //logger.info("substate generated");
            Feature curRes = getNextFeatureTraining(curSubState,features);
            logger.info("feature generated");
            features.add(curRes);
        }
        return transToActions(features);
    }

    private Feature getNextFeatureTraining(SubState curSubState, List<Feature> curFeatures) {
        List<Feature> possNextFeatures = curSubState.getPossibleFeatures(curFeatures);
        //logger.info("Possible features got");
        double dice = random.nextDouble();

        if(dice>epsilon){
            return possNextFeatures.get(random.nextInt(possNextFeatures.size()));
        }
        logger.info("curFeature added:"+possNextFeatures.size());

        double bestScore = 0;
        Feature bestFeature = possNextFeatures.get(0);
        for(Feature cur:possNextFeatures){
            curFeatures.add(curFeatures.size()-1,cur);
            double curScore = getScore(curFeatures);
            if(curScore>bestScore){
                bestScore = curScore;
                bestFeature = cur;
            }
            curFeatures.remove(curFeatures.size()-1);
        }
        logger.info("best feature returned");
        return bestFeature;

    }
    

    private double getScore(List<Feature> features) {
        List<List<Double>> vals = new ArrayList<>();
        for(Feature cur:features){
            List<Double> curVals =cur.getVals();
            vals.add(curVals);
        }
        return Client.sendAndReceive(vals);
    }

    private List<Action> transToActions(List<Feature> features) {
        List<Action> res = new ArrayList<Action>();

        for (Feature curFeature:features){
            Unit curUnit = curFeature.getCurUnit();
            Command curCommand = curFeature.getCommand();
            Action curAction = new Action(curUnit,curCommand);
            res.add(curAction);
        }
        return res;
    }
}
