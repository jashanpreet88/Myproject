package com.example.myproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;

public class SignupPage extends AppCompatActivity {

    android.widget.EditText usernname,email,password,confirmpass;
    android.widget.Button register;
    android.widget.TextView textView;
    com.google.firebase.auth.FirebaseAuth mAuth;
    DatabaseReference UsersDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        usernname = findViewById(com.example.myproject.R.id.name);
        email = findViewById(com.example.myproject.R.id.email);
        password = findViewById(com.example.myproject.R.id.password);
        confirmpass = findViewById(com.example.myproject.R.id.confirm_password);
        textView = findViewById(com.example.myproject.R.id.acc1);

        textView.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(SignupPage.this, LoginPage.class);
                startActivity(intent);

            }
        });

    }
}