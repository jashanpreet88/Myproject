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
    private android.widget.EditText checkInDate, checkOutDate;
    private java.util.Calendar calendar;
    private int year, month, day;
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
        BookingsDB = FirebaseDatabase.getInstance().getReference("booking");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        android.view.View checkInDate = findViewById(com.example.myproject.R.id.check_in_date);
        checkOutDate = findViewById(R.id.check_out_date);
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

        calendar = java.util.Calendar.getInstance();
        year = calendar.get(java.util.Calendar.YEAR);
        month = calendar.get(java.util.Calendar.MONTH);
        day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        checkInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(checkInDate);
            }
        });

        checkOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(checkOutDate);
            }
        });

        btn_book.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
               android.content.Intent intent = new android.content.Intent(ViewHotelActivity.this,HotelBookingActivity.class);
               startActivity(intent);
            }
        });


    }
}