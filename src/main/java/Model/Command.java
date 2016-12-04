package Model;

import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitCommandType;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class Command {

    public enum cType{
        move(0),atk(1),noCommand(2);
        @Getter
        private int val;

        cType(int i) {
            this.val = i;
        }

    }

    public Command(cType type, Position targetPos){
        this.commandType = type;
        this.targetPos = targetPos;
    }

    @Getter@Setter
    private cType commandType;

    @Getter
    private Position targetPos;



    public void execute(Unit unit){
        switch (commandType){
            case move:
                unit.move(targetPos);
                break;
            case atk:
                unit.attack(targetPos);
                break;
        }
    }
}
