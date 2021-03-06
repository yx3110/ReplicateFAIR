package Model;

import RL.DQNLearner;
import bwapi.Game;
import bwapi.Position;
import bwapi.Unit;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by eclipse on 30/11/2016.
 */
public class SubState {
    @Getter
    Unit unit;
    @Getter
    Game game;

    public static Logger logger =  Logger.getLogger( SubState.class.getName() );

    public SubState(Unit unit,Game game){
        this.unit = unit;
        this.game = game;
    }

    public List<Command> getPossibleCommands() {
        List<Command> res = new ArrayList<Command>();
        if(game.enemy()==unit.getPlayer()){
            Command noCommand = new Command(Command.cType.noCommand,unit.getPosition());
            res.add(noCommand);
            return res;
        }else{
            res.addAll(getMoveCommands());
            res.addAll(getAttackCommands());
        }


        return res;
    }

    private List<Command> getAttackCommands() {
        List<Command> res = new ArrayList<Command>();
        List<Unit> enemies =game.enemy().getUnits();
        for(Unit enemy:enemies){
            Command cur = new Command(Command.cType.atk,enemy.getPosition());
            res.add(cur);
        }
        return res;
    }

    private List<Command> getMoveCommands(){
        List<Command> res = new ArrayList<Command>();
        Command n = new Command(Command.cType.move,new Position(unit.getX(),unit.getY()+1));
        res.add(n);
        Command nw = new Command(Command.cType.move,new Position(unit.getX()-1,unit.getY()+1));
        res.add(nw);
        Command ne = new Command(Command.cType.move,new Position(unit.getX()+1,unit.getY()+1));
        res.add(ne);
        Command e = new Command(Command.cType.move,new Position(unit.getX()+1,unit.getY()));
        res.add(e);
        Command w = new Command(Command.cType.move,new Position(unit.getX()-1,unit.getY()));
        res.add(w);
        Command s = new Command(Command.cType.move,new Position(unit.getX(),unit.getY()-1));
        res.add(s);
        Command sw = new Command(Command.cType.move,new Position(unit.getX()-1,unit.getY()-1));
        res.add(sw);
        Command se = new Command(Command.cType.move,new Position(unit.getX()+1,unit.getY()-1));
        res.add(se);
        return res;
    }

    public List<Feature> getPossibleFeatures(List<Feature> prev) {
        List<Feature> res = new ArrayList<>();
        List<Command> commands = getPossibleCommands();
        for(Command curCommand:commands){
            Feature curFeature = new Feature(this,curCommand,prev);
            res.add(curFeature);
        }
        return res;
    }
}
