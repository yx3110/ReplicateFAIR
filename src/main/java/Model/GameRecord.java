package Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Yang Xu on 07/01/2017.
 */
public class GameRecord {
    @Getter@Setter
    private List<Feature> curState;
    @Getter@Setter
    private List<Feature> prevState;
    @Getter@Setter
    private List<Action> prevAction;
    @Getter@Setter
    private double reward;

    public GameRecord(){}
}
