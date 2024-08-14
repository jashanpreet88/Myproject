package com.example.myproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProfilePage extends AppCompatActivity {

    private com.google.firebase.database.DatabaseReference UsersDB;
    private com.google.firebase.auth.FirebaseUser currentUser;
    private com.google.firebase.auth.FirebaseAuth mAuth;
    private String userId;
    private android.widget.EditText etUsername;
    private android.widget.Button btnEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);
        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        UsersDB = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("users");
        etUsername = findViewById(R.id.tvUsername);
        btnEdit = findViewById(R.id.btnEdit);

        if (currentUser != null) {
            userId = currentUser.getUid();
            UsersDB.child(userId).child("name").addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(@androidx.annotation.NonNull com.google.firebase.database.DataSnapshot snapshot) {

                }

                @Override
                public void onCancelled(@androidx.annotation.NonNull com.google.firebase.database.DatabaseError error) {

                }
            });

        }
    }
}