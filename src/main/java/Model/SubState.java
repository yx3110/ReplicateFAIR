package Model;

import bwapi.Game;
import bwapi.Unit;
import lombok.Getter;

import java.util.List;

/**
 * Created by eclipse on 30/11/2016.
 */
public class SubState {
    @Getter
    Unit unit;
    @Getter
    Game game;
    @Getter
    private List<Action> prevActions;

    public SubState(Unit unit,Game game,List<Action> prevActions){
        this.unit = unit;
        this.game = game;
        this.prevActions = prevActions;
    }
}
