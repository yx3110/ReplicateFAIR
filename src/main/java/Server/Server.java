package Server;

import DL.NeuralNetwork;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Yang Xu on 06/12/2016.
 */
public class Server {
    static Logger logger = Logger.getLogger(Server.class.getName());

    static final boolean loadData = false;

    public static void main(String args[]) {
        startServer();
    }

    public static void startServer() {

        PlayThread playThread;
        TrainThread trainThread;
        NeuralNetwork nn = new NeuralNetwork(loadData);
        logger.info("NN created");

        try {
            playThread = new PlayThread(nn);
            trainThread = new TrainThread(nn);
            playThread.start();
            trainThread.start();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
