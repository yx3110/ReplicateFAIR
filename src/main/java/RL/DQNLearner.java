package RL;

import DL.NeuralNetwork;
import Model.*;

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

    public double getReward(State state, State prevState) {
        return 0;
    }

    public DQNLearner(boolean isTraining) {
        super(isTraining);
        random = new Random();
    }

    protected Action playSubTrained(SubState subState) {
        // TODO: 02/12/2016

        return null;
    }

    protected Action playSubTraining(SubState subState) {
        // TODO: 02/12/2016
        return null;
    }

    public List<Action> playTrained(State state) {
        List<Action> res = new ArrayList<Action>();

        return res;
    }

    public List<Action> playTraining(State state) {

        List<SubState> subStates = new ArrayList<SubState>();
        while (state.hasNextSubState()) {
            subStates.add(state.getNextSubState());
        }
        return getActionsTraining(subStates);
    }

    private List<Action> getActionsTraining(List<SubState> subStates) {
        List<Action> res = new ArrayList<Action>();
        List<List<Feature>> possFeatures = new ArrayList<List<Feature>>();
        //initialize list
        //No. of substates = No. of units
        for(int i = 0;i<subStates.size();i++){
            List<Command> curCommands = subStates.get(i).getPossibleCommands();
            for (int j = 0;j<curCommands.size();j++){
                for(int k = 0;k<subStates.size();k++){
                    List<Feature> curList = new ArrayList<Feature>();
                    possFeatures.add(curList);
                }
            }
        }

        //fill with features.

        for (int i = 0; i < subStates.size(); i++) {
            List<Command> curCommands = subStates.get(i).getPossibleCommands();
            for (int j = 0; j < curCommands.size(); j++) {
                for(int k = 0;k<subStates.size();k++){
                    possFeatures.get(i+k).add()
                }
            }
        }
        return res;
    }


    Double getReward(State state, Command command) {
        return null;
    }
}
