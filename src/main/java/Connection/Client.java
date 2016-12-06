package Connection;
import org.eclipse.jetty.server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;


/**
 * Created by Yang Xu on 06/12/2016.
 */
public class Client {

    public Client(){

    }


    public static double sendAndReceive(List<List<Double>> vals) {
        try {
            Socket out = new Socket("localhost", 60010);
            ObjectOutputStream oos = new ObjectOutputStream(out.getOutputStream());
            oos.writeObject(vals);
            oos.close();


            ServerSocket receive = new ServerSocket(60011);
            Socket receiveSocket = receive.accept();
            ObjectInputStream ois =  new ObjectInputStream(receiveSocket.getInputStream());

            receive.close();
            ois.close();
            return (double) ois.readObject();
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}
