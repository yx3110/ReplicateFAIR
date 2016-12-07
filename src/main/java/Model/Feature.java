package Model;

import bwapi.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class Feature {
    static Logger logger = Logger.getLogger( Feature.class.getName() );


    private SubState subState;
    @Getter
    private Unit curUnit;
    @Getter
    private Command command;

    List<Feature> prevFeatures;
    @Getter
    private List<Double> vals;


    public Feature(SubState subState,Command command,List<Feature> prevFeatures){
        curUnit = subState.getUnit();
        this.subState = subState;
        this.prevFeatures = prevFeatures;
        vals = new ArrayList<Double>();
        this.command = command;
        initFeature();
    }

    private Unit getPrevUnit() {
        if (prevFeatures.size() != 0) {
            return prevFeatures.get(prevFeatures.size() - 1).getCurUnit();
        }else return null;
    }
    public void updateWithCommand(List<Double> values){
        double a1b3 = getDistance(getPrevUnit().getPosition(),command.getTargetPos());
        values.add(a1b3);
        double a2b3 = getDistance(getPrevUnit().getTargetPosition(),command.getTargetPos()) ;
        values.add(a2b3);
        double a3b3 = getDistance(prevFeatures.get(prevFeatures.size()-1).getCommand().getTargetPos(),command.getTargetPos());
        values.add(a3b3);
    }

    public void initFeature(){
        double isEnemy;
        double unitType;
        double HP;
        double shield;
        double CD;
        double curCommandType;
        double nextCommandType;
        double curUnitType;
        //a1:u.pos,a2: u.cur_cmd.target_pos,a3:u.next_cmd.target_pos
        //b1:uk+1.pos, b2:uk+1.cur_cmd.target_pos, b3:c.target_pos
        double a1b1;
        double a1b2;
        double a2b1;
        double a2b2;
        double a3b1;
        double a3b2;


        isEnemy = subState.getGame().self().equals(curUnit.getPlayer())?1:0;
        vals.add(isEnemy);
        // type always marine ATM
        unitType = curUnit.getType().equals(UnitType.Terran_Marine)?1:0;
        vals.add(unitType);
        HP = curUnit.getHitPoints();
        vals.add(HP);
        shield = curUnit.getShields();
        vals.add(shield);
        CD = curUnit.getGroundWeaponCooldown();
        vals.add(CD);
        //!!!!!!!!!!!!!!
        UnitCommand prevCommand = curUnit.getLastCommand();
        curCommandType =Command.parseCommandType(prevCommand.getUnitCommandType()).getVal();
        vals.add(curCommandType);
        //!!!!!!!!!!!!!
        nextCommandType = command.getCommandType().getVal();
        vals.add(nextCommandType);

        curUnitType = curUnit.getType().equals(UnitType.Terran_Marine)?1:0;
        vals.add(curUnitType);
        a1b1 = getDistance(getPrevUnit().getPosition(),curUnit.getPosition());
        vals.add(a1b1);
        a1b2 = getDistance(getPrevUnit().getPosition(),curUnit.getTargetPosition());
        vals.add(a1b2);
        a2b1 = getDistance(getPrevUnit().getTargetPosition(),curUnit.getPosition());
        vals.add(a2b1);
        a2b2 = getDistance(getPrevUnit().getTargetPosition(),curUnit.getTargetPosition());
        vals.add(a2b2);
        a3b1 = getDistance(prevFeatures.get(prevFeatures.size()-1).getCommand().getTargetPos(),curUnit.getPosition());
        vals.add(a3b1);
        a3b2 = getDistance(prevFeatures.get(prevFeatures.size()-1).getCommand().getTargetPos(),curUnit.getLastCommand().getTargetPosition());
        vals.add(a3b2);

    }

    private double getDistance(Position pos1, Position pos2){
        double a = Math.pow(pos1.getX()-pos2.getX(),2);
        double b = Math.pow(pos1.getY()-pos2.getY(),2);
        return Math.sqrt(a+b);
    }
}
