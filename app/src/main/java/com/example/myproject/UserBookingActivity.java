package com.example.myproject;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserBookingActivity extends AppCompatActivity {

    private ListView listViewBookings;
    private BookingAdapter bookingAdapter;
    private List<HotelBooking> bookingList;

    private DatabaseReference bookingsDB;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);

        listViewBookings = findViewById(R.id.listViewBookings);
        bookingList = new ArrayList<>();
        bookingAdapter = new BookingAdapter(this, bookingList);

        listViewBookings.setAdapter(bookingAdapter);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        bookingsDB = FirebaseDatabase.getInstance().getReference("bookings");

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            if (userEmail != null && userEmail.equals("manager@gmail.com")) {

                bookingsDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        bookingList.clear();
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            for (DataSnapshot bookingSnapshot : userSnapshot.getChildren()) {
                                HotelBooking booking = bookingSnapshot.getValue(HotelBooking.class);
                                if (booking != null) {
                                    bookingList.add(booking);
                                }
                            }
                        }
                        bookingAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UserBookingActivity.this, "Failed to load bookings: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {

                String userId = currentUser.getUid();

                bookingsDB.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        bookingList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            HotelBooking booking = dataSnapshot.getValue(HotelBooking.class);
                            if (booking != null) {
                                bookingList.add(booking);
                            }
                        }
                        bookingAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UserBookingActivity.this, "Failed to load bookings: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(this, "You need to be logged in to see your bookings", Toast.LENGTH_SHORT).show();
        }
    }
}
