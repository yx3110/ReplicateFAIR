package RL;

import Model.*;

import java.util.List;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public class ReinforceLearner extends AILearner {
    public ReinforceLearner(boolean isTraining) {
        super(isTraining);
    }

    protected Action playSubTrained(SubState subState) {
        return null;
    }

    protected Action playSubTraining(SubState subState) {
        return null;
    }


    Double getReward(State state, Command command) {
        return null;
    }

}
