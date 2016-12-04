package Model;

import bwapi.Unit;
import lombok.Getter;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class Action {
    @Getter
    private Unit unit;
    @Getter
    private Command command;

    public Action(Unit cur, Command command) {
        this.unit = cur;
        this.command = command;
    }

    public void execute() {
        command.execute(unit);
    }
}
