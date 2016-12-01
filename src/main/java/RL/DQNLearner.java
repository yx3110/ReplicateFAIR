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
    public double getReward(State state, State prevState){
        return 0;
    }

    public DQNLearner(boolean isTraining) {
        super(isTraining);
        random = new Random();
    }

    protected Action playSubTrained(SubState subState) {
        Feature feature = new Feature(subState);

        return null;
    }

    protected Action playSubTraining(SubState subState) {
        Feature feature = new Feature(subState);
        return null;
    }

    public List<Action> playTrained(State state) {
        List<Action> res = new ArrayList<Action>();

        return res;
    }

    public List<Action> playTraining(State state) {

        List<SubState> subStates = new ArrayList<SubState>();
        while(state.hasNextSubState()){
            subStates.add(state.getNextSubState());
        }
        return getActionsTraining(subStates);
    }

    private List<Action> getActionsTraining(List<SubState> subStates) {
        List<Action> res = new ArrayList<Action>();
        List<List<Action>> possActions = new ArrayList<List<Action>>();
        List<Action> curActionSet = new ArrayList<Action>();
        for(int i = 0;i<subStates.size();i++){
            List<Command> curCommands = subStates.get(i).getPossibleCommands();
            for(int j = 0;j<curCommands.size();j++){
                // TODO: 01/12/2016 Does not make sense
               // Action cur = ;
            }
            if(i == subStates.size()-1){

            }
        }

        return res;
    }



    Double getReward(State state, Command command) {
        return null;
    }
}
