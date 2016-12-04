package RL;

import DL.NeuralNetwork;
import Model.*;
import bwapi.Unit;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public class DQNLearner extends AILearner {
    private State prevState;
    Random random;
    NeuralNetwork nn;

    private static final double epsilon = 0.9;

    public DQNLearner(boolean isTraining) {
        super(isTraining);
        random = new Random();
        nn = new NeuralNetwork();
        if(!isTraining)nn.loadData(dataURL);

    }

    public List<Action> playTrained(State state) {
        List<SubState> subStates = new ArrayList<SubState>();
        while (state.hasNextSubState()) {
            subStates.add(state.getNextSubState());
        }

        return getActionsTrained(subStates);

    }

    public List<Action> playTraining(State state) {

        List<SubState> subStates = new ArrayList<SubState>();
        while (state.hasNextSubState()) {
            subStates.add(state.getNextSubState());
        }
        return getActionsTraining(subStates);
    }

    private List<Action> getActionsTrained(List<SubState> subStates){
        List<List<Feature>> possFeatures = getPossFeatures(subStates);

        double bestScore = 0;
        List<Feature> best = possFeatures.get(0);
        for(int i = 0;i<possFeatures.size();i++){
            double curScore = getScore(possFeatures.get(i));
            if(curScore>bestScore) {
                bestScore = curScore;
                best = possFeatures.get(i);
            }
        }
        return transToActions(best);
    }

    private List<Action> getActionsTraining(List<SubState> subStates) {
        List<List<Feature>> possFeatures = getPossFeatures(subStates);

        double dice = random.nextDouble();
        if(dice>=epsilon){
            return transToActions(possFeatures.get(random.nextInt(possFeatures.size())));
        }else {
            double bestScore = 0;
            List<Feature> best = possFeatures.get(0);
            for (int i = 0; i < possFeatures.size(); i++) {
                double curScore = getScore(possFeatures.get(i));
                if (curScore > bestScore) {
                    bestScore = curScore;
                    best = possFeatures.get(i);
                }
            }
            return transToActions(best);
        }
    }

    private double getScore(List<Feature> features) {

        return nn.getScore(features);
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
