package com.example.myproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserBookingActivity extends AppCompatActivity {
    private android.widget.ListView listViewBookings;
    private BookingAdapter bookingAdapter;
    private java.util.List<HotelBooking> bookingList;

    private com.google.firebase.database.DatabaseReference bookingsDB;
    private com.google.firebase.auth.FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);

        listViewBookings = findViewById(R.id.listViewBookings);
        bookingList = new java.util.ArrayList<>();
        bookingAdapter = new BookingAdapter(this, bookingList);

        listViewBookings.setAdapter(bookingAdapter);

        currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
        bookingsDB = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("bookings");
    }
}