package Model;

import bwapi.Unit;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class Command {

    public enum cType{
        moveN,moveS,MoveW,MoveE,moveNW,moveNE,moveSW,moveSE,atk,hold;
    }

    @Getter@Setter
    private cType cType;

    private final Unit unit;

    public Command(Unit unit){
        this.unit = unit;
    }

    public Command(Unit unit,cType cType){
        this.cType = cType;
        this.unit = unit;
    }

    public void execute(){
        switch (cType){


        }
    }
}
