package com.example.myproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddHotel extends AppCompatActivity {

    android.widget.EditText hotelNameEditText, hotelLocationEditText, hotelRatingEditText,
            hotelImageUrlEditText, hotelPriceEditText;
    android.widget.CheckBox hotelAvailableCheckbox;
    android.widget.Button add_hotel_btn;
    com.google.firebase.database.DatabaseReference hotelsReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);

        hotelsReference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("hotels");
        hotelNameEditText = findViewById(R.id.hotel_name_edittext);
        hotelLocationEditText = findViewById(R.id.hotel_location_edittext);
        hotelRatingEditText = findViewById(R.id.hotel_rating_edittext);
        hotelImageUrlEditText = findViewById(R.id.hotel_image_url_edittext);
        hotelPriceEditText = findViewById(R.id.hotel_price_edittext);
        hotelAvailableCheckbox = findViewById(R.id.hotel_available_checkbox);
        add_hotel_btn = findViewById(com.example.myproject.R.id.save_hotel_button);

        add_hotel_btn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                addHotels();
            }
        });
    }

    private void addHotels() {
        String name = hotelNameEditText.getText().toString().trim();
        String location = hotelLocationEditText.getText().toString().trim();
        String ratingstr = hotelRatingEditText.getText().toString().trim();
        String priceStr = hotelPriceEditText.getText().toString().trim();
        String imageUrl = hotelImageUrlEditText.getText().toString().trim();
        boolean available = hotelAvailableCheckbox.isChecked();


        if (android.text.TextUtils.isEmpty(name)) {
            hotelNameEditText.setError("Please enter hotel name");
            return;
        }

        if (android.text.TextUtils.isEmpty(location)) {
            hotelLocationEditText.setError("Please enter location");
            return;
        }

        double rating = 0.0;
        if (!android.text.TextUtils.isEmpty(ratingstr)) {
            rating = Double.parseDouble(ratingstr);
        }

        if (android.text.TextUtils.isEmpty(imageUrl)) {
            hotelImageUrlEditText.setError("Please enter image URL");
            return;
        }

        double pricePerNight = 0.0;
        if (!android.text.TextUtils.isEmpty(priceStr)) {
            pricePerNight = Double.parseDouble(priceStr);
        }
        String hotelId = hotelsReference.push().getKey();


        Hotel hotel = new Hotel(name, location, rating, imageUrl, pricePerNight, available);


        if (hotelId != null) {
            hotelsReference.child(hotelId).setValue(hotel)
                    .addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            android.widget.Toast.makeText(AddHotel.this, "Hotel added successfully", android.widget.Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                        @Override
                        public void onFailure(@androidx.annotation.NonNull Exception e) {
                            android.widget.Toast.makeText(AddHotel.this, "Failed to add hotel: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }

}