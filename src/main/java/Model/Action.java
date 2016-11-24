package Model;

import bwapi.Unit;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class Action {
    private Unit unit;
    private Command command;

    public Action(Unit cur, Command command) {
        this.unit = cur;
        this.command = command;
    }
}
