package com.example.myproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {
    TextView textView_username, textView_email;
    Button button_go_back,button_go_edit;
    DatabaseReference UsersDB;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);
        textView_username = findViewById(R.id.userName);
        textView_email = findViewById(R.id.userEmail);
        button_go_back = findViewById(R.id.btn_go_back);
        button_go_edit = findViewById(R.id.btn_go_edit_profile);
        button_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_go_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.content.Intent intent = new android.content.Intent(ProfilePage.this, EditProfilePage.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        UsersDB = FirebaseDatabase.getInstance().getReference("users");

        if (currentUser != null) {
            String userId = currentUser.getUid();
            if (userId != null) {
                UsersDB.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            String username = snapshot.child("name").getValue(String.class);
                            String email = snapshot.child("email").getValue(String.class);
                            if (username != null) {
                                textView_username.setText("Name: " + username);
                            }

                            if (email != null) {
                                textView_email.setText("Email: " + email);
                            }

                        } else {
                            Toast.makeText(ProfilePage.this, "No data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ProfilePage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }



    }
}