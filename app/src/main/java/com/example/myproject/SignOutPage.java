package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignOutActivity extends AppCompatActivity {

    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.myproject.R.layout.activity_sign_out_page);

        signOutButton = findViewById(R.id.sign_out_button);
       signOutButton.setOnClickListener(new android.view.View.OnClickListener() {
           @Override
           public void onClick(android.view.View v) {
               FirebaseAuth.getInstance().signOut();
               startActivity(new Intent(SignOutActivity.this, LoginPage.class));
               finish();
           }
       });
    }
}
