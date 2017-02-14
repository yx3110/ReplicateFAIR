package Model;

import bwapi.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class Feature implements Serializable {
    static Logger logger = Logger.getLogger( Feature.class.getName() );

    private SubState subState;
    @Getter
    private Unit curUnit;
    @Getter
    private Command command;

    List<Feature> prevFeatures;
    @Getter
        private HashMap<String,Double> vals;


    public Feature(SubState subState,Command command,List<Feature> prevFeatures){
        curUnit = subState.getUnit();
        this.subState = subState;
        this.prevFeatures = prevFeatures;
        vals = new HashMap<>();
        this.command = command;
        initFeature();
    }

    private Unit getPrevUnit() {
        if (prevFeatures.size() != 0) {
            return prevFeatures.get(prevFeatures.size() - 1).getCurUnit();
        }else return null;
    }
    private Command getPrevCommand(){
        if(prevFeatures.size()>0){
            return prevFeatures.get(prevFeatures.size()-1).getCommand();
        }
        else return null;
    }

    public void initFeature(){
        double HP= curUnit.getHitPoints();
        double shield = curUnit.getShields();
        double CD = curUnit.getGroundWeaponCooldown();
        double isEnemy = subState.getGame().self().equals(curUnit.getPlayer())?0:1;
        //double unitType = curUnit.getType().equals(UnitType.Terran_Marine)?1:0;
        vals.put("hp",HP);
        vals.put("shield",shield);
        vals.put("cd",CD);
        vals.put("isEnemy",isEnemy);
        //vals.add(unitType);

        initRelativeFeatures();

        Command.cType prevCmdType =Command.parseCommandType
                (curUnit.getLastCommand().getUnitCommandType());
        vals.put("prevCmdType",(double)prevCmdType.getVal());
    }

    private void initRelativeFeatures() {
        Position curUnitPosition = curUnit.getPosition();
        Position curUnitTargetPosition = curUnit.getTargetPosition();
        Position nextCommandTargetPosition = command.getTargetPos();

        Position prevUnitPosition = getPrevUnit()==null?null:getPrevUnit().getPosition();
        Position prevUnitTargetPosition = getPrevUnit()==null?null:getPrevUnit().getPosition();
        Position prevUnitCommandTargetPosition = getPrevCommand()==null?null:getPrevCommand().getTargetPos();

        double a1b1 = getDistance(curUnitPosition,prevUnitPosition);
        double a1b2 = getDistance(curUnitPosition,prevUnitTargetPosition);
        double a1b3 = getDistance(curUnitPosition,prevUnitCommandTargetPosition);

        vals.put("a1b1",a1b1);
        vals.put("a1b2",a1b2);
        vals.put("a1b3",a1b3);

        double a2b1 = getDistance(curUnitTargetPosition,prevUnitPosition);
        double a2b2 = getDistance(curUnitTargetPosition,prevUnitTargetPosition);
        double a2b3 = getDistance(curUnitTargetPosition,prevUnitCommandTargetPosition);

        vals.put("a2b1",a2b1);
        vals.put("a2b2",a2b2);
        vals.put("a2b3",a2b3);

        double a3b1 = getDistance(nextCommandTargetPosition,prevUnitPosition);
        double a3b2 = getDistance(nextCommandTargetPosition,prevUnitTargetPosition);
        double a3b3 = getDistance(nextCommandTargetPosition,prevUnitCommandTargetPosition);
        vals.put("a3b1",a3b1);
        vals.put("a3b2",a3b2);
        vals.put("a3b3",a3b3);
    }

    private double getDistance(Position pos1, Position pos2){
        if(pos1 == null||pos2 == null) return 0;
        double a = Math.pow(pos1.getX()-pos2.getX(),2);
        double b = Math.pow(pos1.getY()-pos2.getY(),2);
        return Math.sqrt(a+b);
    }
}
