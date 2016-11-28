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

    @Getter@Setter
    private cType commandType;

    private final Unit unit;
    @Getter
    private Position targetPos;

    public static cType parseCType(UnitCommandType type){
        if(type==UnitCommandType.Attack_Move||type==UnitCommandType.Attack_Unit){
            return Command.cType.atk;
        }else if (type == UnitCommandType.Move){
            return cType.move;
        }
        return cType.noCommand;
    }

    public Command(Unit unit){
        this.unit = unit;
    }

    public Command(Unit unit,cType cType,Position targetPos){
        this.commandType = cType;
        this.unit = unit;
        this.targetPos = targetPos;
    }

    public void execute(){
        Position position= unit.getPosition();
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
