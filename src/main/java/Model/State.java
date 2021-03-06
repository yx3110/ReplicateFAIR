package Model;

import bwapi.Game;
import bwapi.Unit;
import bwapi.UnitType;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class State {
    @Getter
    private Game game;
    Random random;

    static Logger logger = Logger.getLogger( State.class.getName() );
    @Getter
    private List<Unit> unitLeft;

    public State(Game game){
        this.game = game;
        random = new Random();
        unitLeft = new ArrayList<>();
        List<Unit> allUnits = game.getAllUnits();
        for(Unit curUnit:allUnits){
            if(curUnit.getType()==UnitType.Terran_Marine){
                unitLeft.add(curUnit);
            }
        }
    }

    public SubState getNextSubState() {
        Unit cur = fetchNextUnit(unitLeft);
        SubState res = new SubState(cur,game);
        return res;
    }

    private Unit fetchNextUnit(List<Unit> unitLeft) {
        int index = random.nextInt(unitLeft.size());
        Unit res = unitLeft.remove(index);
        return res ;
    }

    public boolean hasNextSubState() {
        return unitLeft.size()!=0;
    }

    public int getNumUnits() {
        int res = 0;
        List<Unit> allUnits = game.getAllUnits();
        for(Unit curUnit:allUnits){
            if(curUnit.getType()==UnitType.Terran_Marine){
                res++;
            }
        }
        return res;
    }

    public double getMyCurTotalHP() {
        double res = 0;
        List<Unit> allUnits = game.getAllUnits();
        for(Unit curUnit:allUnits){
            if(curUnit.getType()==UnitType.Terran_Marine&&curUnit.getPlayer()==game.self()){
                res+=curUnit.getHitPoints();
            }
        }
        return res;
    }
    public double getEnemyCurTotalHP(){
        double res = 0;
        List<Unit> allUnits = game.getAllUnits();
        for(Unit curUnit:allUnits){
            if(curUnit.getType()==UnitType.Terran_Marine&&curUnit.getPlayer()==game.enemy()){
                res+=curUnit.getHitPoints();
            }
        }
        return res;
    }
}
