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
                public void onDataChange(@androidx.annotation.NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                    String username = dataSnapshot.getValue(String.class);
                    if (username != null) {
                        etUsername.setText(username);
                    }else {
                        etUsername.setError("User name can't be empty");
                    }
                }

                @Override
                public void onCancelled(@androidx.annotation.NonNull com.google.firebase.database.DatabaseError databaseError) {

                    android.widget.Toast.makeText(EditProfilePage.this, "Can't load user name" + databaseError.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
                }
            });
            btnEdit.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    String newUsername = etUsername.getText().toString().trim();
                    if (!newUsername.isEmpty()) {
                        UsersDB.child(userId).child("name").setValue(newUsername).addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@androidx.annotation.NonNull com.google.android.gms.tasks.Task<Void> task) {
                                android.widget.Toast.makeText(EditProfilePage.this, "User name updated", android.widget.Toast.LENGTH_SHORT).show();
                            }

                        }).addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                            @Override
                            public void onFailure(@androidx.annotation.NonNull Exception e) {

                                android.widget.Toast.makeText(EditProfilePage.this, "User name update failed", android.widget.Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

        }
    }
}