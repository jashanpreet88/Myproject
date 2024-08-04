package com.example.myproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewHotelActivity extends AppCompatActivity {
    String hotelId;
    String name, location, imageUrl;
    Double rating, pricePerNight;
    Boolean available;
    DatabaseReference HotelsDB, BookingsDB;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    TextView hotel_name,
            hotel_location,
            hotel_rating,
            hotel_pricePerNight,
            hotel_available;
    ImageView hotel_image;
    Button btn_book;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotel);

        hotel_name = findViewById(R.id.hotel_name);
        hotel_location = findViewById(R.id.hotel_location);
        hotel_rating = findViewById(R.id.hotel_rating);
        hotel_pricePerNight = findViewById(R.id.hotel_pricePerNight);
        hotel_available = findViewById(R.id.hotel_available);
        hotel_image = findViewById(R.id.hotel_image);
        btn_book = findViewById(R.id.btn_book);
        hotelId = getIntent().getStringExtra("hotel_id");
        HotelsDB = FirebaseDatabase.getInstance().getReference("hotels");
        BookingsDB = FirebaseDatabase.getInstance().getReference("bookings");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(hotelId != null){
            HotelsDB.child(hotelId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        name = snapshot.child("name").getValue(String.class);
                        location = snapshot.child("location").getValue(String.class);
                        rating = snapshot.child("rating").getValue(Double.class);
                        pricePerNight = snapshot.child("pricePerNight").getValue(Double.class);
                        imageUrl = snapshot.child("imageUrl").getValue(String.class);
                        available = snapshot.child("available").getValue(Boolean.class);
                        hotel_name.setText(name);
                        hotel_location.setText("Location: " + location);
                        hotel_rating.setText("Rating: " + String.valueOf(rating));
                        hotel_pricePerNight.setText("Price per night(CAD): " + String.valueOf(pricePerNight));
                        if(available != null && available){
                            hotel_available.setText("Available");
                        }else {
                            hotel_available.setText("Not available");
                        }

                        Picasso.get()
                                .load(imageUrl)
                                .into(hotel_image);
                    } else {
                        Toast.makeText(ViewHotelActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser != null){
                    String userId = currentUser.getUid();
                    if(available != null && available){
                        Hotel hotel = new Hotel(name, location, rating, imageUrl, pricePerNight, available);
                        BookingsDB.child(userId).setValue(hotel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ViewHotelActivity.this, "Booking success", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ViewHotelActivity.this, "Booking fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Toast.makeText(ViewHotelActivity.this, "This hotel not available", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
}