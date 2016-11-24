package RL;

import Model.Command;
import Model.State;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public abstract class AILearner {
    boolean isTraining;


    public AILearner(boolean isTraining){
        this.isTraining = isTraining;
    }
    public Command play(State state){
        if (isTraining) return playTraining(state);
        else return playTrained(state);
    }

    abstract Command playTrained(State state);

    abstract Command playTraining(State state);

}
