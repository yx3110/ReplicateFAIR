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

    private List<Command> subCommands;



    public AILearner(boolean isTraining){
        this.isTraining = isTraining;
        rewardMap = new HashMap<Feature, Double>();
    }
    public  List<Action> play(State state){
        if (isTraining) return playTraining(state);
        else return playTrained(state);
    }

    public Action playSub(SubState subState){
        if(isTraining) return playSubTraining(subState);
        else  return playSubTrained(subState);
    }

    protected abstract Action playSubTrained(SubState subState);

    protected abstract Action playSubTraining(SubState subState);

    public abstract List<Action> playTrained(State state);

    public abstract List<Action> playTraining(State state);

    public static List<Command> getAvailableCommands(State state){
        List<Command> res = new ArrayList<Command>();
        // TODO: 29/11/2016
        
        return res;
    }

    public static List<List<Feature>> getPossFeatures(List<SubState> subStates){
        List<List<Feature>> possFeatures = new ArrayList<List<Feature>>();
        for(int i = 0;i<subStates.size();i++){
            List<Command> curCommands = subStates.get(i).getPossibleCommands();
            for (int j = 0;j<curCommands.size();j++){
                for(int k = 0;k<subStates.size();k++){
                    List<Feature> curList = new ArrayList<Feature>();
                    possFeatures.add(curList);
                }
            }
        }
        //fill with features.
        for (int i = 0; i < subStates.size(); i++) {
            List<Command> curCommands = subStates.get(i).getPossibleCommands();
            for (int j = 0; j < curCommands.size(); j++) {
                for(int k = 0;k<subStates.size();k++){
                    possFeatures.get(i+k).add(new Feature(subStates.get(i),curCommands.get(j)));
                }
            }
        }
        return possFeatures;
    }
}
