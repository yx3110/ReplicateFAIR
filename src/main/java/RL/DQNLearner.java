package RL;

import Model.*;
import Client.Client;
import bwapi.Unit;

import java.util.ArrayList;
import java.util.HashMap;
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
    private static final double epsilon = 0.98;
    private int prevNumUnits;
    private double myPrevTotalHP;
    private double enemyPrevTotalHP;
    public DQNLearner() {
        super();
        random = new Random();
        client = new Client();
        prevFeatures = new ArrayList<>();
        records = new ArrayList<>();
        prevNumUnits = 0;
        myPrevTotalHP=0;
        enemyPrevTotalHP=0;
    }

    public List<Action> play(State curState) {

        List<Feature> features = new ArrayList<Feature>();
        SubState curSubState = curState.getNextSubState();
        while (curState.hasNextSubState()) {
            //logger.info("substate generated");
            Feature curRes = getNextFeature(curSubState,features);

            logger.info("feature generated");
            features.add(curRes);

            if(prevFeatures.size()==0)prevFeatures.add(curRes);
            if(prevNumUnits==0)prevNumUnits = curState.getNumUnits();
            if(myPrevTotalHP==0)myPrevTotalHP = curState.getMyCurTotalHP();
            if(enemyPrevTotalHP==0)enemyPrevTotalHP = curState.getEnemyCurTotalHP();

            curSubState = curState.getNextSubState();
            List<Feature> possibleFeatures = curSubState.getPossibleFeatures(features);
            List<List<HashMap<String,Double>>> curStatePossibleFeatures = generateNextPossibleMoves(possibleFeatures,features);

            recordTrajectory(curStatePossibleFeatures,features,prevFeatures,curRes,getReward(),prevNumUnits,curState.getNumUnits(),curState.getMyCurTotalHP(),myPrevTotalHP,curState.getEnemyCurTotalHP(),enemyPrevTotalHP);
            prevFeatures = features;
            prevNumUnits = curState.getNumUnits();
            myPrevTotalHP = curState.getMyCurTotalHP();
            enemyPrevTotalHP = curState.getEnemyCurTotalHP();
        }
        List<Action> res = transToActions(features);
        return res;
    }

    private List<List<HashMap<String,Double>>> generateNextPossibleMoves(List<Feature> possibleFeatures,List<Feature> curState) {
        List<List<HashMap<String,Double>>> res = new ArrayList<>();
        for(Feature curFeature:possibleFeatures){
            curState.add(curState.size()-1,curFeature);
            res.add(getVals(curState));
            curState.remove(curState.size()-1);
        }
        return res;
    }

    private void recordTrajectory(List<List<HashMap<String,Double>>> possibleNextMoves,List<Feature> features, List<Feature> prevFeatures, Feature prevAction, double reward,int curNumUnits,int prevNumUnits,double myCurTotalHP,double myPrevTotalHP,double enemyCurTotalHP,double enemyPrevTotalHP) {
        GameRecord curRecord = new GameRecord();
        curRecord.setMyCurTotalHP(myCurTotalHP);
        curRecord.setMyPrevTotalHP(myPrevTotalHP);
        curRecord.setEnemyCurTotalHP(enemyCurTotalHP);
        curRecord.setEnemyPrevTotalHP(enemyPrevTotalHP);
        curRecord.setCurNumberOfUnits(curNumUnits);
        curRecord.setPrevNumberOfUnits(prevNumUnits);
        curRecord.setCurState(getVals(features));
        curRecord.setPrevAction(prevAction.getVals());
        curRecord.setCurStatePossibleFeatures(possibleNextMoves);
        curRecord.setPrevState(getVals(prevFeatures));
        curRecord.setReward(reward);
        records.add(curRecord);
    }

    private List<HashMap<String,Double>> getVals(List<Feature> features) {
        List<HashMap<String,Double>> res = new ArrayList<>();
        for(Feature curFeature:features){
            res.add(curFeature.getVals());
        }
        return res;
    }

    @Override
    public void TrainNN() {
        logger.info("Sending training data, record size = "+records.size());
        client.sendTrain(records);
    }

    @Override
    public void clearRecords() {
        this.records = new ArrayList<>();
    }

    private Feature getNextFeature(SubState curSubState, List<Feature> curFeatures) {
        List<Feature> possNextFeatures = curSubState.getPossibleFeatures(curFeatures);
        logger.info("Possible features got");
        double dice = random.nextDouble();

        if(dice>epsilon){
            logger.info("Random feature returned");
            return possNextFeatures.get(random.nextInt(possNextFeatures.size()));

        }

        double bestScore = 0;
        Feature bestFeature = possNextFeatures.get(0);
        for(Feature cur:possNextFeatures){
            curFeatures.add(curFeatures.size(),cur);
            double curScore = getScore(curFeatures);
            if(curScore==-1) logger.warning("score calculation exception");
            if(curScore>bestScore){
                bestScore = curScore;
                bestFeature = cur;
            }
            curFeatures.remove(curFeatures.size()-1);
        }
        logger.info("best feature returned with score"+bestScore);
        return bestFeature;
    }
    

    private double getScore(List<Feature> features) {
        List<HashMap<String,Double>> vals = new ArrayList<>();
        for(Feature cur:features){
            HashMap<String,Double> curVals =cur.getVals();
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
