package Model;

import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitCommandType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class Command implements Serializable{

    public static cType parseCommandType(UnitCommandType unitCommandType) {
        if (unitCommandType.equals(UnitCommandType.Attack_Move)||unitCommandType.equals(UnitCommandType.Attack_Unit)){
            return cType.atk;
        }else if (unitCommandType.equals(unitCommandType.Move)){
            return cType.move;
        }else return cType.noCommand;
    }

    public Position getTargetPos() {
        return new Position(targetX,targetY);
    }

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
        targetX = targetPos.getX();
        targetY = targetPos.getY();
    }

    @Getter@Setter
    private cType commandType;

    @Getter
    private int targetX;
    @Getter
    private int targetY;

    public void execute(Unit unit){
        switch (commandType){
            case move:
                unit.move(new Position(targetX,targetY));
                break;
            case atk:
                unit.attack(new Position(targetX,targetY));
                break;
            case noCommand:
                break;
            default:
                break;
        }
    }
}
