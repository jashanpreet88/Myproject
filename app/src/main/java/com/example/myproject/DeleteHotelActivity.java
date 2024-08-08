package com.example.myproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DeleteHotelActivity extends AppCompatActivity {

    private com.google.firebase.database.DatabaseReference HotelsDB;
    String hotelId;
    android.widget.Button button_delete, button_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_hotel);
        button_delete = findViewById(R.id.btn_yes);
        button_back = findViewById(R.id.btn_no);
        hotelId = getIntent().getStringExtra("hotel_id");
        HotelsDB = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("hotels");

        button_back.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                finish();
            }
        });
        button_delete.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                HotelsDB.child(hotelId).removeValue().addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull com.google.android.gms.tasks.Task<Void> task) {
                        android.widget.Toast.makeText(DeleteHotelActivity.this, "Hotel deleted successfully", android.widget.Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception e) {
                        android.widget.Toast.makeText(DeleteHotelActivity.this, "Hotel deleted failed", android.widget.Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}