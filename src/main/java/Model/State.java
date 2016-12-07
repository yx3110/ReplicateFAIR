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

    private List<Unit> unitLeft;

    public State(Game game){
        this.game = game;
        random = new Random();
        unitLeft = new ArrayList<Unit>();
        List<Unit> allUnits = game.getAllUnits();
        for(Unit curUnit:allUnits){
            if(curUnit.getType().equals(UnitType.Terran_Marine))unitLeft.add(curUnit);
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

    public double healthDiff(State state2){
        State state1 = this;
        List<Unit> enemyUnits1= state1.getGame().enemy().getUnits();
        List<Unit> enemyUnits2 = state2.getGame().enemy().getUnits();
        List<Unit> myUnits1 = state1.getGame().self().getUnits();
        List<Unit> myUnits2 = state2.getGame().self().getUnits();
        double enemyHealth1 = 0,enemyHealth2 = 0, myHealth1 = 0,myHealth2=0;
        for(Unit cur:enemyUnits1){
            enemyHealth1+= cur.getHitPoints();
        }
        for(Unit cur:enemyUnits2){
            enemyHealth2+=cur.getHitPoints();
        }

        for(Unit cur:myUnits1){
            myHealth1+=cur.getHitPoints();
        }

        for(Unit cur:myUnits2){
            enemyHealth2+=cur.getHitPoints();
        }
        double damageTaken = myHealth1 - myHealth2;
        double damageCaused = enemyHealth1-enemyHealth2;
        return damageCaused-damageTaken;
    }


}
