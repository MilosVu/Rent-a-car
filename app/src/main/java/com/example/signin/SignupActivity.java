package com.example.signin;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import domain.User;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class SignupActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    EditText emailEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    TextView errorSignUpTextView;

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
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        errorSignUpTextView = findViewById(R.id.errorSignUpTextView);

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
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || firstName.isEmpty()  || lastName.isEmpty())
            return;

        if (passwordConfirmed(password,confirmPassword) && isValidEmail(email)) {
            User user = new User(username,password, firstName, lastName, email);
            try {
                registerUser(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean registerUser(User user) throws IOException{
        MainActivity.objectOutputStream.writeObject(user);
        MainActivity.streamToServer.println("register");
        String response = MainActivity.streamFromServer.readLine();

        System.out.println(response);
        errorSignUpTextView.setText(response);
        if(response.equals("You have registered successfully")) {
            errorSignUpTextView.setTextColor(Color.GREEN);
            return true;
        }
        return false;
    }


    private boolean isValidEmail(String email) {
        if (email.indexOf('@') < 0 || email.indexOf('.') < 0) {
            errorSignUpTextView.setText("Invalid email address.");
            return false;
        }
        return true;
    }

    private boolean passwordConfirmed(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return true;
        }
        errorSignUpTextView.setText("Passwords don't match.");
        return false;
    }


}
