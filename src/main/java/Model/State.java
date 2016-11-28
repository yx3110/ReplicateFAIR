package Model;

import bwapi.Game;
import bwapi.Unit;
import lombok.Getter;


import java.util.List;
import java.util.Map;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class State {
    @Getter
    private Map<Unit,Command> lastCommands;
    @Getter
    private Game game;
    @Getter
    private Unit unit;
    @Getter
    private List<Action> prevActions;
    public State(Game game, Unit unit, List<Action> prevActions,Map<Unit,Command> lastCommands){
        this.unit = unit;
        this.prevActions = prevActions;
        this.game = game;
        this.lastCommands = lastCommands;
    }

    public List<Unit> getMyUnits(){
        return game.self().getUnits();
    }
    public List<Unit> getEnemyUnits(){
        return game.enemy().getUnits();
    }

    public List<Unit> getAllUnits(){
        return game.getAllUnits();
    }




}
