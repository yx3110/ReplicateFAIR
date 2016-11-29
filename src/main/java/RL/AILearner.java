package RL;

import Model.Command;
import Model.Feature;
import Model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public abstract class AILearner {
    boolean isTraining;
    public static final String dataURL = "";

    private List<Command> available;



    public AILearner(boolean isTraining){
        this.isTraining = isTraining;
        available = new ArrayList<Command>();
    }
    public Command play(State state){
        if (isTraining) return playTraining(state);
        else return playTrained(state);
    }

    abstract Command playTrained(State state);

    abstract Command playTraining(State state);

    abstract Double getReward(State state,Command command);
    public static List<Command> getAvailableCommands(State state){
        List<Command> res = new ArrayList<Command>();
        // TODO: 29/11/2016
        
        return res;
    }
}
