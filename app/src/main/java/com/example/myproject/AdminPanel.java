package com.example.myproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class AdminPanel extends AppCompatActivity {

     android.widget.EditText hotelName, hotelLocation, hotelRating;
     android.widget.ImageView hotelImageView;
     android.net.Uri imageUri;
     FirebaseFirestore db;
     FirebaseStorage storage;
     java.util.List<Hotel> hotelList;
     HotelAdapter hotelAdapter;
     String selectedHotelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_panel);

    }
}