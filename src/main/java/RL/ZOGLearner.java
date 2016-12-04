package RL;

import Model.*;

import java.util.List;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public class ZOGLearner extends AILearner {
    public ZOGLearner(boolean isTraining) {
        super(isTraining);
    }

    protected Action playSubTrained(SubState subState) {
        return null;
    }

    protected Action playSubTraining(SubState subState) {
        return null;
    }

    public List<Action> playTrained(State state) {
        return null;
    }

    public List<Action> playTraining(State state) {
        return null;
    }

}
