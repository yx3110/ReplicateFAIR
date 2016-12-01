package RL;

import Model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public abstract class AILearner {
    boolean isTraining;
    public static final String dataURL = "";

    private List<Command> subCommands;


    public AILearner(boolean isTraining){
        this.isTraining = isTraining;
    }
    public List<Action> play(State state){
        if (isTraining) return playTraining(state);
        else return playTrained(state);
    }

    public Action playSub(SubState subState){
        if(isTraining) return playSubTraining(subState);
        else  return playSubTrained(subState);
    }

    protected abstract Action playSubTrained(SubState subState);

    protected abstract Action playSubTraining(SubState subState);

    public List<Action> playTrained(State state){
        List<Action> res = new ArrayList<Action>();
        while(state.hasNextSubState()) {
            SubState subState = state.getNextSubState();
            Action cur = playSubTrained(subState);
            state.updatePrevActions(cur);
            res.add(cur);
        }
        return res;
    }

    public List<Action> playTraining(State state){
        List<Action> res = new ArrayList<Action>();
        while(state.hasNextSubState()) {
            SubState subState = state.getNextSubState();
            Action cur = playSubTraining(subState);
            res.add(cur);
        }
        return res;
    }

    abstract Double getReward(State state,Command command);
    public static List<Command> getAvailableCommands(State state){
        List<Command> res = new ArrayList<Command>();
        // TODO: 29/11/2016
        
        return res;
    }
}
