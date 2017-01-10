package Model;

import bwapi.Unit;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class Action implements Serializable {
    @Getter
    private int unitID;
    @Getter
    private Command command;

    public Action(Unit cur, Command command) {
        unitID = cur.getID();
        this.command = command;
    }

}
