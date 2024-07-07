package com.example.myproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SignupPage extends AppCompatActivity {

    android.widget.EditText username, email, password, confirmPassword;
    android.widget.Button register;
    android.widget.TextView textView;
    FirebaseAuth mAuth;
    DatabaseReference UsersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        username = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        textView = findViewById(R.id.acc1);
        register = findViewById(R.id.register_button);

        mAuth = FirebaseAuth.getInstance();
        UsersDB = FirebaseDatabase.getInstance().getReference("users");

        textView.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(SignupPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Signup();
            }
        });
    }

    private void Signup() {
        String name = username.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String confirmPassword = this.confirmPassword.getText().toString().trim();

        if (android.text.TextUtils.isEmpty(name)) {
            username.setError("Name is required");
            return;
        }

        if (android.text.TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email.setError("Valid email is required");
            return;
        }

        if (android.text.TextUtils.isEmpty(password)) {
            this.password.setError("Password is required");
            return;
        }

        if (password.length() < 6) {
            this.password.setError("Password must be at least 6 characters");
            return;
        }

        if (android.text.TextUtils.isEmpty(confirmPassword)) {
            this.confirmPassword.setError("Confirm Password is required");
            return;
        }

        if (!password.equals(confirmPassword)) {
            this.confirmPassword.setError("Passwords do not match");
            return;
        }




    }
}
