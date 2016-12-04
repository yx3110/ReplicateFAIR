import Model.*;
import RL.AILearner;
import RL.DQNLearner;
import bwapi.*;
import bwta.BWTA;
import bwta.BaseLocation;

import java.util.*;

public class Main extends DefaultBWListener {

    private Mirror mirror = new Mirror();

    private State prevState;
    private Game game;

    private Player self;

    private Map<Unit,Command> lastCommands;
    private static final boolean isTrianing = true;

    private Player enemy;
    private AILearner aiLearner;

    public void run() {
        mirror.getModule().setEventListener(this);
        mirror.startGame();
    }

    @Override
    public void onUnitCreate(Unit unit) {
        System.out.println("New unit discovered " + unit.getType());
    }

    @Override
    public void onStart() {
        game = mirror.getGame();
        self = game.self();
        enemy = game.enemy();
        lastCommands = new HashMap<Unit, Command>();
        //set learner
        aiLearner = new DQNLearner(isTrianing);

        //Use BWTA to analyze map
        //This may take a few minutes if the map is processed first time!
        System.out.println("Analyzing map...");
        BWTA.readMap();
        BWTA.analyze();
        System.out.println("Map data ready");

        int i = 0;
        for(BaseLocation baseLocation : BWTA.getBaseLocations()){
        	System.out.println("Base location #" + (++i) + ". Printing location's region polygon:");
        	for(Position position : baseLocation.getRegion().getPolygon().getPoints()){
        		System.out.print(position + ", ");
        	}
        	System.out.println();
        }

    }


    @Override
    public void onFrame() {
        if(self.getUnits().size()== 0||enemy.getUnits().size()==0){
            gameEnd();
        }
        //Skip Frame

        if(game.getFrameCount()%9 != 0) return;

        game.drawTextScreen(10, 10, "Playing as " + self.getName() + " - " + self.getRace());
        List<Unit> myUnits = self.getUnits();

        Collections.shuffle(myUnits);

        State curState = new State(game);
        List<Action> actions = aiLearner.play(curState);
        for(Action action:actions){
            action.execute();
        }
    }

    private void gameEnd() {

    }

    private void updateReward() {
    }

    public static void main(String[] args) {
        new Main().run();
    }
}