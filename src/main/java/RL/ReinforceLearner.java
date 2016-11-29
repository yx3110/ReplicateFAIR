package RL;

import Model.Command;
import Model.Feature;
import Model.State;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public class ReinforceLearner extends AILearner {
    public ReinforceLearner(boolean isTraining) {
        super(isTraining);
    }

    Command playTrained(State state) {
        return null;
    }

    Command playTraining(State state) {
        return null;
    }


    Double getReward(State state, Command command) {
        return null;
    }
}
