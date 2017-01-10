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
    List<GameRecord> records;

    List<Feature> prevFeatures;
    List<Action> prevAction;
    private static final double epsilon = 0.9;

    public DQNLearner() {
        super();
        random = new Random();
        client = new Client();
        prevFeatures = new ArrayList<>();
        prevAction = new ArrayList<>();
        records = new ArrayList<>();
    }

    public List<Action> play(State curState) {


        List<Feature> features = new ArrayList<Feature>();
        while (curState.hasNextSubState()) {
            SubState curSubState = curState.getNextSubState();
            //logger.info("substate generated");
            Feature curRes = getNextFeature(curSubState,features);
            logger.info("feature generated");
            features.add(curRes);
        }
        List<Action> res = transToActions(features);
        if(prevAction.size()!=0) {
            recordTrajectory(features, prevFeatures, prevAction, getReward());
        }
        prevFeatures = features;
        prevAction = res;
        return res;
    }

    private void recordTrajectory(List<Feature> features, List<Feature> prevFeatures, List<Action> prevAction, double reward) {
        GameRecord curRecord = new GameRecord();
        curRecord.setCurState(getVals(features));
        curRecord.setPrevAction(prevAction);
        curRecord.setPrevState(getVals(prevFeatures));
        curRecord.setReward(reward);
        records.add(curRecord);
    }

    private List<List<Double>> getVals(List<Feature> features) {
        List<List<Double>> res = new ArrayList<>();
        for(Feature curFeature:features){
            res.add(curFeature.getVals());
        }
        return res;
    }

    @Override
    public void TrainNN() {
        client.sendTrain(records);
    }

    private Feature getNextFeature(SubState curSubState, List<Feature> curFeatures) {
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
            if(curScore==-1) logger.warning("score calculation exception");
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
        return Client.sendPlay(vals);
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

    public double getReward() {
        // TODO: 07/01/2017  
        double res = 0;
        return res;
    }
}
