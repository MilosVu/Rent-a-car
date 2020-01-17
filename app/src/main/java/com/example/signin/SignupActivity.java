package com.example.signin;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import domain.User;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText emailEditText;
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_signup);

        NavigationView go_back = findViewById(R.id.go_back);
        go_back.setNavigationItemSelectedListener(listener);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        emailEditText = findViewById(R.id.emailEditText);

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String value = extras.getString("key");
//            System.out.println(value);
//        }
    }

    private NavigationView.OnNavigationItemSelectedListener listener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            startActivity(new Intent(SignupActivity.this,MainActivity.class));
            return true;
        }
    };

    public void Register(View view){

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String email = emailEditText.getText().toString();

        if(!password.equals(confirmPassword)){
            System.out.println("Passwords don't match");
            return;
        }

        if(email.indexOf('@') < 0 || email.indexOf('.') < 0){
            System.out.println("Invalid email address");
            return;
        }

        User user = new User(username,password,"Somi","CAR",email);
        try {
            MainActivity.objectOutputStream.writeObject(user);
            MainActivity.streamToServer.println("register");
            response = MainActivity.streamFromServer.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
