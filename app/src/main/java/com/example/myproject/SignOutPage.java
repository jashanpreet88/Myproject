package com.example.myproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignOutPage extends AppCompatActivity {
     android.widget.Button signOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_out_page);
        signOutButton = findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new android.view.View.OnClickListener() {
           @Override
           public void onClick(android.view.View v) {
               com.google.firebase.auth.FirebaseAuth.getInstance().signOut();
               android.content.Intent intent = new android.content.Intent(SignOutPage.this,LoginPage.class);
               startActivity(intent);
               finish();
           }
       });

    }
}