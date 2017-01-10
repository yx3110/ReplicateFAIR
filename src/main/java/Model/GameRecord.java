package Model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yang Xu on 07/01/2017.
 */
public class GameRecord implements Serializable {
    @Getter@Setter
    private List<List<Double>> curState;
    @Getter@Setter
    private List<List<Double>> prevState;
    @Getter@Setter
    private List<Action> prevAction;
    @Getter@Setter
    private double reward;

    public GameRecord(){}
}
