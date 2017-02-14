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
    public static final String dataURL = "";
    List<GameRecord> actions;

    public AILearner(){
        actions = new ArrayList<>();
    }

    public abstract List<Action> play(State state);

    public abstract void TrainNN();

    public abstract void clearRecords();
}
