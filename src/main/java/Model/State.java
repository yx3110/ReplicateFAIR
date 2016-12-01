package Model;

import bwapi.Game;
import bwapi.Unit;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class State {
    @Getter
    private Game game;
    Random random;

    private List<Unit> unitLeft;
    @Getter
    private List<Action> prevActions;

    public State(Game game){
        this.game = game;
        random = new Random();
        prevActions = new ArrayList<Action>();
    }

    public SubState getNextSubState() {

        Unit cur = fetchNextUnit(unitLeft);
        SubState res = new SubState(cur,game,prevActions);
        unitLeft = new ArrayList<Unit>();
        unitLeft.addAll(game.getAllUnits());

        return res;
    }

    private Unit fetchNextUnit(List<Unit> unitLeft) {
        int index = random.nextInt(unitLeft.size()-1);
        Unit res = unitLeft.remove(index);
        return res ;
    }

    public boolean hasNextSubState() {
        return unitLeft.size()!=0;
    }

    public void updatePrevActions(Action cur) {
        prevActions.add(cur);
    }

}
