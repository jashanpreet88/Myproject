package com.example.myproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewHotelActivity extends AppCompatActivity {

    String hotelId;
    com.google.firebase.database.DatabaseReference HotelsDB;
    android.widget.TextView hotel_name,hotel_location,hotel_rating,hotel_pricePerNight,hotel_available;
    android.widget.ImageView hotel_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotel);
        hotel_name =findViewById(com.example.myproject.R.id.hotel_name);
        hotel_location = findViewById(com.example.myproject.R.id.hotel_location);
        hotel_rating = findViewById(com.example.myproject.R.id.hotel_rating);
        hotel_pricePerNight = findViewById(com.example.myproject.R.id.hotel_pricePerNight);
        hotel_available = findViewById(com.example.myproject.R.id.hotel_available);
        hotel_image = findViewById(com.example.myproject.R.id.hotel_image);
        HotelsDB = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("hotels");
        if(hotelId!=null){
            HotelsDB.child(hotelId).addValueEventListener(new com.google.firebase.database.ValueEventListener() {
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