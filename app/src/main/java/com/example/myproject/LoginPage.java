package com.example.myproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginPage extends AppCompatActivity {

    android.widget.EditText username,password;
    android.widget.Button loginbtn,forgotpassword;
    android.widget.TextView signuplink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        username = findViewById(com.example.myproject.R.id.usernamelogin);
        password = findViewById(com.example.myproject.R.id.passwordlogin);
        forgotpassword = findViewById(com.example.myproject.R.id.forgot_password);
        loginbtn = findViewById(com.example.myproject.R.id.login);
        signuplink = findViewById(com.example.myproject.R.id.signuplink);

        signuplink.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(LoginPage.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgotpassword.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(LoginPage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}