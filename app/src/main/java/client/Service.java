package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

import domain.User;

public class Service {
    private Socket socketForCommunication = null;
    private BufferedReader streamFromServer = null;
    private PrintStream streamToServer = null;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;

    public Service() {
    }

    public Service(Socket socketForCommunication, BufferedReader streamFromServer, PrintStream streamToServer, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        this.socketForCommunication = socketForCommunication;
        this.streamFromServer = streamFromServer;
        this.streamToServer = streamToServer;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    public boolean login(String username, String password) {
        User user = new User(username,password,null,null,null);
        streamToServer.println(username);
        streamToServer.println(password);
        try {
            objectOutputStream.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
