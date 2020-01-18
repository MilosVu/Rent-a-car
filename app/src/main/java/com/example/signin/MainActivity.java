package com.example.signin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

import client.Service;
import domain.User;

public class MainActivity extends AppCompatActivity {

    Service service;
    private static final int SERVER_PORT = 14000;
    private static final String SERVER_IP = "192.168.1.32";
    static Socket socketForCommunication = null;
    static BufferedReader streamFromServer = null;
    static PrintStream streamToServer = null;
    static OutputStream outputStream = null;
    static InputStream inputStream = null;
    static ObjectOutputStream objectOutputStream = null;
    static ObjectInputStream objectInputStream = null;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            socketForCommunication = new Socket(SERVER_IP,SERVER_PORT);
            outputStream = socketForCommunication.getOutputStream();
            inputStream = socketForCommunication.getInputStream();
            streamToServer = new PrintStream(outputStream);
            streamFromServer = new BufferedReader(new InputStreamReader(inputStream));
            objectOutputStream = new ObjectOutputStream((socketForCommunication.getOutputStream()));
            objectInputStream = new ObjectInputStream((socketForCommunication.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        new Thread(new Client()).start();
//
//        System.out.println("prosao tread");

    }

    public void OpenSignupPage(View view) {
        startActivity(new Intent(MainActivity.this,SignupActivity.class));
//        String value = "hello";
//        Intent i = new Intent(MainActivity.this, SignupActivity.class);
//        i.putExtra("key",value);
//        startActivity(i);

    }

    public void OpenDiscoverPage(View view) {
        try {
//            EditText usernameEditText = findViewById(R.id.usernameEditText);
            String username = usernameEditText.getText().toString();
//            EditText passwordEditText = findViewById(R.id.passwordEditText);
            String password = passwordEditText.getText().toString();

//            socketForCommunication = new Socket(SERVER_IP,SERVER_PORT);
//            outputStream = socketForCommunication.getOutputStream();
//            inputStream = socketForCommunication.getInputStream();
//            streamToServer = new PrintStream(outputStream);
//            streamFromServer = new BufferedReader(new InputStreamReader(inputStream));
//            objectOutputStream = new ObjectOutputStream((socketForCommunication.getOutputStream()));
            System.out.println("Connected");
            User user = new User(username,password,null,null,null);
            objectOutputStream.writeObject(user);

            streamToServer.println("logIn");
            System.out.println("PROSAO");
//            System.out.println("Poslao");
            response = streamFromServer.readLine();
            System.out.println(response );
            if(response.equals("You have signed in")){
                startActivity(new Intent(MainActivity.this,DiscoverActivity.class));

            }
            else {
                TextView errorTextView = findViewById(R.id.errorTextView);
                errorTextView.setText("Wrong username/password.");
                usernameEditText.setText("");
                passwordEditText.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PUKO");
        }

    }

//    public class Client implements Runnable{
//
//        @Override
//        public void run() {
//
//        try {
//            System.out.println("usao u run");
//            socketForCommunication = new Socket(SERVER_IP, SERVERPORT);
//            outputStream = socketForCommunication.getOutputStream();
//            inputStream = socketForCommunication.getInputStream();
//            streamToServer = new PrintStream(outputStream);
//            streamFromServer = new BufferedReader(new InputStreamReader(inputStream));
//            objectOutputStream = new ObjectOutputStream((socketForCommunication.getOutputStream()));
//          //  service = new Service(socketForCommunication,streamFromServer,streamToServer,objectOutputStream,objectInputStream);
//            System.out.println("Uspeo");
//            streamToServer.println("cao servere");
//
//        } catch (UnknownHostException e1) {
//            e1.printStackTrace();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//
//    }
//    }

//    class Client extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try{
////                socketForCommunication = new Socket(SERVER_IP, SERVERPORT);
////                outputStream = socketForCommunication.getOutputStream();
////                inputStream = socketForCommunication.getInputStream();
////                streamToServer = new PrintStream(outputStream);
////                streamFromServer = new BufferedReader(new InputStreamReader(inputStream));
////                objectOutputStream = new ObjectOutputStream((socketForCommunication.getOutputStream()));
////                service = new Service(socketForCommunication, streamFromServer, streamToServer, objectOutputStream, objectInputStream);
//
////                EditText usernameEditText = findViewById(R.id.usernameEditText);
////                String username = usernameEditText.getText().toString();
////                EditText passwordEditText = findViewById(R.id.passwordEditText);
////                String password = passwordEditText.getText().toString();
////
////                User user = new User(username,password,null,null,null);
////                streamToServer.println(username);
////                streamToServer.println(password);
////                try {
////                    objectOutputStream.writeObject(user);
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//            return null;
//        }
//    }
}
