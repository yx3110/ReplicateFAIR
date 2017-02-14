package Model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yang Xu on 07/01/2017.
 */
public class GameRecord implements Serializable {
    @Setter@Getter
    int curNumberOfUnits;
    @Setter@Getter
    int prevNumberOfUnits;
    @Getter@Setter
    private List<HashMap<String,Double>> curState;
    @Getter@Setter
    private List<List<HashMap<String,Double>>> curStatePossibleFeatures;
    @Getter@Setter
    private List<HashMap<String,Double>> prevState;
    @Getter@Setter
    private HashMap<String,Double> prevAction;
    @Getter@Setter
    private double reward;
    @Setter@Getter
    double myCurTotalHP, myPrevTotalHP,enemyCurTotalHP,enemyPrevTotalHP;

    public GameRecord(){}
}
