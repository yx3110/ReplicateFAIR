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

    Command playTrained(Feature feature) {
        return null;
    }

    Command playTraining(Feature feature) {
        return null;
    }


    Double getReward(State state, Command command) {
        return null;
    }
}
