import Model.*;
import RL.AILearner;
import RL.DQNLearner;
import bwapi.*;
import bwta.BWTA;
import bwta.BaseLocation;

import java.util.*;
import java.util.logging.Logger;

public class Main extends DefaultBWListener {

    static Logger logger = Logger.getLogger( Main.class.getName() );

    private Mirror mirror = new Mirror();

    private final int skipFrame = 9;

    private Game game;
    int gamesPlayed = 0;
    int win = 0;
    private static final boolean isTraining = true;

    private int counter;

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
        counter = 0;
        game = mirror.getGame();
        //set learner
        aiLearner = new DQNLearner();

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

        //Skip Frame
        if(game.getFrameCount()%skipFrame != 0) return;
        counter++;

        State curState = new State(game);

        logger.info("Sending actions at:" + counter);
        List<Action> actions = aiLearner.play(curState);
        logger.info("Actions from server received");
        for(Action action:actions){
            executeAction(action);
        }
        logger.info("Actions executed");
    }

    private void executeAction(Action action) {
        Unit unit = game.getUnit(action.getUnitID());
        Command command = action.getCommand();
        command.execute(unit);
    }

    @Override
    public void onEnd(boolean isWinner) {
        gamesPlayed++;
        if(isWinner) win++;
        logger.info("win/gamesPlayed = " +win+"/"+gamesPlayed);
        aiLearner.TrainNN();
        aiLearner.clearRecords();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}