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
    android.widget.Button button_update;
    private com.google.firebase.database.DatabaseReference hotelsReference;
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

    }
}