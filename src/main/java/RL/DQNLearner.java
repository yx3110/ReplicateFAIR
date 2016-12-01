package RL;

import DL.NeuralNetwork;
import Model.*;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public class DQNLearner extends AILearner {
    private State prevState;
    public double getReward(State state, State prevState){
        return 0;
    }

    public DQNLearner(boolean isTraining) {
        super(isTraining);
    }

    protected Action playSubTrained(SubState subState) {
        Feature feature = new Feature(subState);
        return null;
    }

    protected Action playSubTraining(SubState subState) {
        Feature feature = new Feature(subState);
        return null;
    }


    Double getReward(State state, Command command) {
        return null;
    }
}
