package RL;

import DL.NeuralNetwork;
import Model.Command;
import Model.State;

import java.net.CookieHandler;
import java.util.List;

/**
 * Created by Yang Xu on 24/11/2016.
 */
public class DQNLearner extends AILearner {
    private State prevState;
    public double getReward(State state, State prevState){
        return 0;
    }

    public DQNLearner(boolean isTraining) {
        super(isTraining);
    }

    Command playTrained(State state) {
        NeuralNetwork nn= NeuralNetwork.loadData(super.dataURL);
        List<Command> commands= super.getAvailableCommands(state);
        double maxScore = 0;
        Command res = commands.get(0);
        for(Command cur:commands){
            double curScore = nn.getScore(state,cur);
            maxScore = curScore>maxScore?curScore:maxScore;
            res = curScore>maxScore?cur:res;
        }
        return res;
    }

    Command playTraining(State state) {
        return null;
    }



    Double getReward(State state, Command command) {
        return null;
    }
}
