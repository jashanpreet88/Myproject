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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginPage extends AppCompatActivity {

    EditText username, loginpassword;
    Button loginbtn, forgotpassword;
    TextView signuplink;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this); // Ensure this is a valid class or remove if not needed
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

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginPage.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginPage.this, email, Toast.LENGTH_SHORT).show();
                    if(email.equals("manager@gmail.com")){
                        startActivity(new Intent(LoginPage.this, AdminActivity.class));
                    }else {
                        startActivity(new android.content.Intent(LoginPage.this, MainActivity.class));
                    }
                    finish();

                }else {
                    String errorMessage = "Authentication failed.";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        errorMessage = "Weak password.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        errorMessage = "Invalid email.";
                    } catch (FirebaseAuthUserCollisionException e) {
                        errorMessage = "User with this email already exists.";
                    } catch (Exception e) {
                        errorMessage = e.getMessage();
                    }
                    Toast.makeText(LoginPage.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                android.widget.Toast.makeText(LoginPage.this, "Login failed!", android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }
}
