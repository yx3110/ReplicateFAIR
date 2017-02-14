package Server;

import DL.NeuralNetwork;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Yang Xu on 07/01/2017.
 */
public class PlayThread extends Thread {

    static Logger logger = Logger.getLogger(Server.class.getName());

    NeuralNetwork nn;
    public PlayThread(NeuralNetwork nn){
        this.nn = nn;
    }
    @Override
    public void run(){
        try {
            ServerSocket ss = new ServerSocket(60010);
            logger.info("Play server started");

            while (true) {
                Socket s = ss.accept();
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                List<HashMap<String,Double>> valMatrix = (List<HashMap<String,Double>>) in.readObject();
                logger.info("Play request received");
                Double score = nn.evaluate(valMatrix);
                Socket reply = new Socket("localhost", 60011);
                ObjectOutputStream oos = new ObjectOutputStream(reply.getOutputStream());
                oos.writeObject(score);
                logger.info("return msg sent");
                oos.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
