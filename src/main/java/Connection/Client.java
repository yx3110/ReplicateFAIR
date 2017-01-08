package Connection;
import Model.GameRecord;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


/**
 * Created by Yang Xu on 06/12/2016.
 */
public class Client {

    public Client(){

    }


    public static double sendPlay(List<List<Double>> vals) {
        try {
            Socket out = new Socket("localhost", 60010);
            ObjectOutputStream oos = new ObjectOutputStream(out.getOutputStream());
            oos.writeObject(vals);
            oos.close();

            ServerSocket receive = new ServerSocket(60011);
            Socket receiveSocket = receive.accept();
            ObjectInputStream ois =  new ObjectInputStream(receiveSocket.getInputStream());
            double res = (double)ois.readObject();
            receive.close();

            ois.close();
            return res;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public void sendTrain(List<GameRecord> records) {
        try {
            Socket out = new Socket("localhost", 60012);
            ObjectOutputStream oos = new ObjectOutputStream(out.getOutputStream());
            oos.writeObject(records);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
