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
            System.out.println("Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void OpenSignupPage(View view) {
        startActivity(new Intent(MainActivity.this,SignupActivity.class));
    }

    public void OpenDiscoverPage(View view) {
        startActivity(new Intent(MainActivity.this,DiscoverActivity.class));
        try {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            User user = new User(username,password,null,null,null);
            objectOutputStream.writeObject(user);

            streamToServer.println("logIn");
            System.out.println("PROSAO");
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
            System.out.println("FATAL ERROR");
        }

    }

}
