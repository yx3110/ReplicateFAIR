package Model;

import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitCommandType;
import bwapi.UnitType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class Feature {
    private SubState subState;
    private Unit curUnit;
    private Unit prevUnit;
    private List<Action> prevActions;

    private List<Double> vals;


    public Feature(SubState subState){
        curUnit = subState.getUnit();
        this.subState = subState;
        prevUnit = subState.getPrevActions().get(subState.getPrevActions().size()-1).getUnit();
        prevActions = subState.getPrevActions();
        vals = new ArrayList<Double>();
        initFeature();
    }

    public void updateWithCommand(Command candidate){
        double a1b3;
        double a2b3;
        double a3b3;
    }

    public void initFeature(){
        double prevIsEnemy;
        double prevUnitType;
        double prevHP;
        double prevShield;
        double prevCD;
        double prevCurCommandType;
        double prevNextCommandType;
        double curUnitType;
        //a1:u.pos,a2: u.cur_cmd.target_pos,a3:u.next_cmd.target_pos
        //b1:uk+1.pos, b2:uk+1.cur_cmd.target_pos, b3:c.target_pos
        double a1b1;
        double a1b2;
        double a2b1;
        double a2b2;
        double a3b1;
        double a3b2;


        prevIsEnemy = subState.getGame().self().equals(curUnit.getPlayer())?1:0;
        vals.add(prevIsEnemy);
        // type always marine ATM
        prevUnitType = curUnit.getType().equals(UnitType.Terran_Marine)?1:0;
        vals.add(prevUnitType);
        prevHP = curUnit.getHitPoints();
        vals.add(prevHP);
        prevShield = curUnit.getShields();
        vals.add(prevShield);
        prevCD = curUnit.getGroundWeaponCooldown();
        vals.add(prevCD);
        //!!!!!!!!!!!!!!
        prevCurCommandType =prevActions.get(prevActions.size()-1).getCommand().getCommandType().getVal();
        vals.add(prevCurCommandType);
        prevNextCommandType = prevActions.get(prevActions.size()-1).getCommand().getCommandType().getVal();
        vals.add(prevNextCommandType);
        //!!!!!!!!!!!!!
        curUnitType = curUnit.getType().equals(UnitType.Terran_Marine)?1:0;
        vals.add(curUnitType);
        a1b1 = getDistance(prevUnit.getPosition(),curUnit.getPosition());
        vals.add(a1b1);
        a1b2 = getDistance(prevUnit.getPosition(),curUnit.getTargetPosition());
        vals.add(a1b2);
        a2b1 = getDistance(prevUnit.getTargetPosition(),curUnit.getPosition());
        vals.add(a2b1);
        a2b2 = getDistance(prevUnit.getTargetPosition(),curUnit.getTargetPosition());
        vals.add(a2b2);
        a3b1 = getDistance(prevActions.get(prevActions.size()-1).getCommand().getTargetPos(),curUnit.getPosition());
        vals.add(a3b1);
        a3b2 = getDistance(prevActions.get(prevActions.size()-1).getCommand().getTargetPos(),curUnit.getLastCommand().getTargetPosition());
        vals.add(a3b2);


    }

    private double getDistance(Position pos1, Position pos2){
        double a = Math.pow(pos1.getX()-pos2.getX(),2);
        double b = Math.pow(pos1.getY()-pos2.getY(),2);

        return Math.sqrt(a+b);
    }




}
