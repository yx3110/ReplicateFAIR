package Model;

import bwapi.Unit;

import java.util.List;

/**
 * Created by Yang Xu on 23/11/2016.
 */
public class Feature {
    private List<Unit> myUnits;
    private List<Unit> enemyUnits;
    public Feature(List<Unit> myUnits,List<Unit> enemyUnits){
        this.myUnits = myUnits;
        this.enemyUnits = enemyUnits;
    }




}
