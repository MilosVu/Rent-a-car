package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;



public class Client11 implements Runnable{

    private static final int SERVERPORT = 14000;
    private static final String SERVER_IP = "192.168.43.16";
    static Socket socketForCommunication = null;
    static BufferedReader streamFromServer = null;
    static PrintStream streamToServer = null;
    static OutputStream outputStream = null;
    static InputStream inputStream = null;
    static ObjectOutputStream objectOutputStream = null;
    static ObjectInputStream objectInputStream = null;


    @Override
    public void run() {

        try {
            System.out.println("usao u run");
            socketForCommunication = new Socket(SERVER_IP, SERVERPORT);
            outputStream = socketForCommunication.getOutputStream();
            inputStream = socketForCommunication.getInputStream();
            streamToServer = new PrintStream(outputStream);
            streamFromServer = new BufferedReader(new InputStreamReader(inputStream));
            objectOutputStream = new ObjectOutputStream((socketForCommunication.getOutputStream()));

            System.out.println("Uspeo");
            streamToServer.println("cao servere");



//            objectInputStream = new ObjectInputStream(socketForCommunication.getInputStream());


        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
