package com.example.myproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginPage extends AppCompatActivity {

    EditText username, loginpassword;
    Button loginbtn, forgotpassword;
    TextView signuplink;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_page);

        username = findViewById(R.id.usernamelogin);
        loginpassword = findViewById(R.id.passwordlogin);
        forgotpassword = findViewById(R.id.forgot_password);
        loginbtn = findViewById(R.id.login);
        signuplink = findViewById(R.id.signuplink);
        mAuth = FirebaseAuth.getInstance();

        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, SignupPage.class);
                startActivity(intent);
                finish();
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkuser();
            }
        });
    }

    public void checkuser() {
        String email = username.getText().toString().trim();
        String password = loginpassword.getText().toString().trim();


        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            username.setError("Valid email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            this.loginpassword.setError("Password is required");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login success
                        com.google.firebase.auth.FirebaseUser user = mAuth.getCurrentUser();
                        if(email.equals("manager@gmail.com")){
                        Intent intent  = new android.content.Intent(LoginPage.this,AdminActivity.class);
                        startActivity(intent);
                        }else {
                            Intent intent  = new android.content.Intent(LoginPage.this,MainActivity.class);
                            startActivity(intent);
                        }
                        finish();
                    } else {

                        String errorMessage = "Authentication failed.";
                        try {
                            throw task.getException();
                        } catch (com.google.firebase.auth.FirebaseAuthInvalidUserException e) {
                            errorMessage = "User does not exist.";
                        } catch (com.google.firebase.auth.FirebaseAuthInvalidCredentialsException e) {
                            errorMessage = "Invalid password.";
                        } catch (Exception e) {
                            errorMessage = e.getMessage();
                        }
                        android.widget.Toast.makeText(LoginPage.this, errorMessage, android.widget.Toast.LENGTH_LONG).show();
                    }
                });



    }
}
