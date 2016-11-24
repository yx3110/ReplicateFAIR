package Model;

import bwapi.Game;
import bwapi.Unit;

import java.util.List;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class State {
    private Game game;
    private Unit unit;
    private List<Action> prevActions;
    public State(Game game, Unit unit, List<Action> prevActions){
        this.unit = unit;
        this.prevActions = prevActions;
        this.game = game;
    }




}
