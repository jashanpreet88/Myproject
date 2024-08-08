package com.example.myproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditHotelActivity extends AppCompatActivity {

    String hotelId;
    com.google.firebase.database.DatabaseReference HotelsDB;
    android.widget.EditText hotel_name, hotel_location, hotel_rating, hotel_pricePerNight, hotel_available, hotel_image_url;
    android.widget.CheckBox hotelAvailableCheckbox;
    android.widget.Button button_update,button_back;
    private com.google.firebase.database.DatabaseReference hotelsReference;
    @android.annotation.SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hotel);
        hotelsReference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("hotels");
        hotel_name = findViewById(R.id.hotel_name_edittext);
        hotel_location = findViewById(R.id.hotel_location_edittext);
        hotel_rating = findViewById(R.id.hotel_rating_edittext);
        hotel_pricePerNight = findViewById(R.id.hotel_price_edittext);
        hotelAvailableCheckbox = findViewById(R.id.hotel_available_checkbox);
        hotel_image_url = findViewById(R.id.hotel_image_url_edittext);
        button_update = findViewById(R.id.update_hotel_btn);
        button_back = findViewById(com.example.myproject.R.id.backbtn);

        button_back.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                finish();
            }
        });

        button_update.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                updateHotel();
            }
        });

        hotelId = getIntent().getStringExtra("hotel_id");
        HotelsDB = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("hotels");
        if(hotelId != null){
            HotelsDB.child(hotelId).addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(@androidx.annotation.NonNull com.google.firebase.database.DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String location = snapshot.child("location").getValue(String.class);
                        Double rating = snapshot.child("rating").getValue(Double.class);
                        Double pricePerNight = snapshot.child("pricePerNight").getValue(Double.class);
                        String imageUrl = snapshot.child("imageUrl").getValue(String.class);
                        Boolean available = snapshot.child("available").getValue(Boolean.class);
                        hotel_name.setText(name);
                        hotel_location.setText(location);
                        hotel_rating.setText(String.valueOf(rating));
                        hotel_pricePerNight.setText(String.valueOf(pricePerNight));
                        hotel_image_url.setText(imageUrl);
                        if (available != null && available) {
                            hotelAvailableCheckbox.setChecked(true);
                        } else {
                            hotelAvailableCheckbox.setChecked(false);
                        }
                    } else {
                        android.widget.Toast.makeText(EditHotelActivity.this, "No data", android.widget.Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@androidx.annotation.NonNull com.google.firebase.database.DatabaseError error) {

                }
            });
        }
    }
        private void updateHotel () {
            String name = hotel_name.getText().toString().trim();
            String location = hotel_location.getText().toString().trim();
            String ratingStr = hotel_rating.getText().toString().trim();
            String imageUrl = hotel_image_url.getText().toString().trim();
            String priceStr = hotel_pricePerNight.getText().toString().trim();
            boolean available = hotelAvailableCheckbox.isChecked();

            if (android.text.TextUtils.isEmpty(name)) {
                hotel_name.setError("Please enter hotel name");
                return;
            }

            if (android.text.TextUtils.isEmpty(location)) {
                hotel_location.setError("Please enter location");
                return;
            }

            double rating = 0.0;
            if (!android.text.TextUtils.isEmpty(ratingStr)) {
                rating = Double.parseDouble(ratingStr);
            } else {
                hotel_rating.setError("Please enter rating");
                return;
            }

            if (android.text.TextUtils.isEmpty(imageUrl)) {
                hotel_image_url.setError("Please enter image URL");
                return;
            }

            double pricePerNight = 0.0;
            if (!android.text.TextUtils.isEmpty(priceStr)) {
                pricePerNight = Double.parseDouble(priceStr);
            } else {
                hotel_pricePerNight.setError("Please enter price");
                return;
            }


            Hotel hotel = new Hotel(name, location, rating, imageUrl, pricePerNight, available);


            if (hotelId != null) {
                hotelsReference.child(hotelId).setValue(hotel)
                        .addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                android.widget.Toast.makeText(EditHotelActivity.this, "Hotel updated successfully", android.widget.Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                            @Override
                            public void onFailure(@androidx.annotation.NonNull Exception e) {
                                android.widget.Toast.makeText(EditHotelActivity.this, "Failed to update hotel: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }
    }
