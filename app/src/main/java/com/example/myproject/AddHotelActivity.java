package com.example.myproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddHotelActivity extends AppCompatActivity {
    private EditText hotelNameEditText, hotelLocationEditText, hotelRatingEditText,
            hotelImageUrlEditText, hotelPriceEditText;
    private CheckBox hotelAvailableCheckbox;
    private Button add_hotel_btn;
    private DatabaseReference HotelsDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);
        HotelsDB = FirebaseDatabase.getInstance().getReference().child("hotels");
        hotelNameEditText = findViewById(R.id.hotel_name_edittext);
        hotelLocationEditText = findViewById(R.id.hotel_location_edittext);
        hotelRatingEditText = findViewById(R.id.hotel_rating_edittext);
        hotelImageUrlEditText = findViewById(R.id.hotel_image_url_edittext);
        hotelPriceEditText = findViewById(R.id.hotel_price_edittext);
        hotelAvailableCheckbox = findViewById(R.id.hotel_available_checkbox);
        add_hotel_btn = findViewById(R.id.add_hotel_button);
        add_hotel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHotel();
            }
        });

    }

    private void addHotel() {
        String name = hotelNameEditText.getText().toString().trim();
        String location = hotelLocationEditText.getText().toString().trim();
        String ratingStr = hotelRatingEditText.getText().toString().trim();
        String imageUrl = hotelImageUrlEditText.getText().toString().trim();
        String priceStr = hotelPriceEditText.getText().toString().trim();
        boolean available = hotelAvailableCheckbox.isChecked();

        if (TextUtils.isEmpty(name)) {
            hotelNameEditText.setError("Please enter hotel name");
            return;
        }

        if (TextUtils.isEmpty(location)) {
            hotelLocationEditText.setError("Please enter location");
            return;
        }

        double rating = 0.0;
        if (!TextUtils.isEmpty(ratingStr)) {
            rating = Double.parseDouble(ratingStr);
        }

        if (TextUtils.isEmpty(imageUrl)) {
            hotelImageUrlEditText.setError("Please enter image URL");
            return;
        }

        double pricePerNight = 0.0;
        if (!TextUtils.isEmpty(priceStr)) {
            pricePerNight = Double.parseDouble(priceStr);
        }


        String hotelId = HotelsDB.push().getKey();


        Hotel hotel = new Hotel(name, location, rating, imageUrl, pricePerNight, available);


        if (hotelId != null) {
            HotelsDB.child(hotelId).setValue(hotel)
                    .addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddHotelActivity.this, "Hotel added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddHotelActivity.this, "Failed to add hotel: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}