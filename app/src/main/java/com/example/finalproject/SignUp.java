package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    TextView emailField, passwordField, confirmField;
    Button registerButton;
    DBHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        confirmField = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.registerButton);
        db = new DBHandler(SignUp.this, getPackageName());


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(SignUp.this, "Check if all fields are filled", Toast.LENGTH_SHORT).show();
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                String confirmPassword = confirmField.getText().toString();

                if(email.equals("") || password.equals("") || confirmPassword.equals("")){
                    Toast.makeText(SignUp.this, "Check if all fields are filled", Toast.LENGTH_SHORT).show();
                }else if(!password.equals(confirmPassword)){
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean check = db.registerUser(email, password);
                    if(check){
                        Toast.makeText(SignUp.this, "Registered", Toast.LENGTH_SHORT).show();
                        Intent toLogin = new Intent(SignUp.this, SignIn.class);
                        startActivity(toLogin);
                    }else{
                        Toast.makeText(SignUp.this, "Error in registration", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

}
