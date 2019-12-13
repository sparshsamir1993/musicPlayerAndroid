package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {
    TextView emailField, passwordField;
    Button signinButton;
    DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        signinButton = findViewById(R.id.signin);
        db =  new DBHandler(SignIn.this);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                boolean check = db.loginUser(email, password);
                if(check){
                    Toast.makeText(SignIn.this, "Logged In", Toast.LENGTH_SHORT).show();
                    Intent toHome = new Intent(SignIn.this, Library.class);
                    startActivity(toHome);
                }else{
                    Toast.makeText(SignIn.this, "Error in logging in", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
