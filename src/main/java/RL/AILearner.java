package RL;

import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public abstract class AILearner {
    boolean isTraining;
    public static final String dataURL = "";
    protected Map<Feature,Double> rewardMap;

    public AILearner(boolean isTraining){
        this.isTraining = isTraining;
        rewardMap = new HashMap<Feature, Double>();
    }
    public  List<Action> play(State state){
        if (isTraining) return playTraining(state);
        else return playTrained(state);
    }

    public abstract List<Action> playTrained(State state);

    public abstract List<Action> playTraining(State state);

}
