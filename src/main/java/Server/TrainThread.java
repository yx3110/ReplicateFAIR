package Server;

import DL.NeuralNetwork;
import Model.GameRecord;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Yang Xu on 07/01/2017.
 */
public class TrainThread extends Thread{
    static Logger logger = Logger.getLogger(Server.class.getName());

    private NeuralNetwork nn;
    public TrainThread(NeuralNetwork nn) {
    }
    @Override
    public void run(){
        try{
            ServerSocket ss = new ServerSocket(60012);
            logger.info("Training server started");

            while (true){
                Socket s = ss.accept();
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                List<GameRecord> records= (List<GameRecord>) in.readObject();
                logger.info("msg received");

                nn.train(records);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
