package RL;

import Model.Command;
import Model.Feature;
import Model.State;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public abstract class AILearner {
    boolean isTraining;




    public AILearner(boolean isTraining){
        this.isTraining = isTraining;
    }
    public Command play(Feature feature){
        if (isTraining) return playTraining(feature);
        else return playTrained(feature);
    }

    abstract Command playTrained(Feature feature);

    abstract Command playTraining(Feature feature);

    abstract Double getReward(State state,Command command);
}
