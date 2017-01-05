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
    private Command getPrevCommand(){
        if(prevFeatures.size()>0){
            return prevFeatures.get(prevFeatures.size()-1).getCommand();
        }
        else return null;
    }

    public void initFeature(){
        double HP= curUnit.getHitPoints();;
        double shield = curUnit.getShields();
        double CD = curUnit.getGroundWeaponCooldown();
        double isEnemy = subState.getGame().self().equals(curUnit.getPlayer())?1:0;
        double unitType = curUnit.getType().equals(UnitType.Terran_Marine)?1:0;
        vals.add(HP);
        vals.add(shield);
        vals.add(CD);
        vals.add(isEnemy);
        vals.add(unitType);

        initRelativeFeatures();

        Command.cType prevCmdType =Command.parseCommandType
                (curUnit.getLastCommand().getUnitCommandType());
        vals.add((double)prevCmdType.getVal());

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

        vals.add(a1b1);
        vals.add(a1b2);
        vals.add(a1b3);

        double a2b1 = getDistance(curUnitTargetPosition,prevUnitPosition);
        double a2b2 = getDistance(curUnitTargetPosition,prevUnitTargetPosition);
        double a2b3 = getDistance(curUnitTargetPosition,prevUnitCommandTargetPosition);

        vals.add(a2b1);
        vals.add(a2b2);
        vals.add(a2b3);

        double a3b1 = getDistance(nextCommandTargetPosition,prevUnitPosition);
        double a3b2 = getDistance(nextCommandTargetPosition,prevUnitTargetPosition);
        double a3b3 = getDistance(nextCommandTargetPosition,prevUnitCommandTargetPosition);
        vals.add(a3b1);
        vals.add(a3b2);
        vals.add(a3b3);
    }

    private double getDistance(Position pos1, Position pos2){
        if(pos1 == null||pos2 == null) return 0;
        double a = Math.pow(pos1.getX()-pos2.getX(),2);
        double b = Math.pow(pos1.getY()-pos2.getY(),2);
        return Math.sqrt(a+b);
    }
}
