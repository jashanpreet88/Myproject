package com.example.myproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Calendar;

public class ViewHotelActivity extends AppCompatActivity {
    private String hotelId;
    private String name, location, imageUrl;
    private Double rating, pricePerNight;
    private Boolean available;
    private DatabaseReference hotelsDB, bookingsDB;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private TextView hotelName, hotelLocation, hotelRating, hotelPricePerNight, hotelAvailable;
    private ImageView hotelImage;
    private EditText checkInDate, checkOutDate;
    private Button btnBook;
    private RatingBar ratingBar;

    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotel);


        hotelName = findViewById(R.id.hotel_name);
        hotelLocation = findViewById(R.id.hotel_location);
        hotelRating = findViewById(R.id.hotel_rating);
        hotelPricePerNight = findViewById(R.id.hotel_pricePerNight);
        hotelAvailable = findViewById(R.id.hotel_available);
        hotelImage = findViewById(R.id.hotel_image);
        checkInDate = findViewById(R.id.check_in_date);
        checkOutDate = findViewById(R.id.check_out_date);
        btnBook = findViewById(R.id.btn_book);
        ratingBar = findViewById(R.id.ratingBar);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        LinearLayout bookingSection = findViewById(R.id.booking_section);
        if(currentUser.getEmail().equals("manager@gmail.com")){
            bookingSection.setVisibility(View.GONE);
        } else {
            bookingSection.setVisibility(View.VISIBLE);

        }

        hotelsDB = FirebaseDatabase.getInstance().getReference("hotels");
        bookingsDB = FirebaseDatabase.getInstance().getReference("bookings");


        hotelId = getIntent().getStringExtra("hotel_id");

        if (hotelId != null) {

            hotelsDB.child(hotelId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        name = snapshot.child("name").getValue(String.class);
                        location = snapshot.child("location").getValue(String.class);
                        rating = snapshot.child("rating").getValue(Double.class);
                        pricePerNight = snapshot.child("pricePerNight").getValue(Double.class);
                        imageUrl = snapshot.child("imageUrl").getValue(String.class);
                        available = snapshot.child("available").getValue(Boolean.class);


                        hotelName.setText(name);
                        hotelLocation.setText("Location: " + location);
                        hotelRating.setText("Rating: " + String.valueOf(rating));
                        hotelPricePerNight.setText("Price per night (CAD): " + String.valueOf(pricePerNight));
                        hotelAvailable.setText(available != null && available ? "Available" : "Not available");

                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Picasso.get()
                                    .load(imageUrl)
//                                    .placeholder(R.drawable.placeholder_image)
                                    .into(hotelImage);
                        }
                    } else {
                        Toast.makeText(ViewHotelActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ViewHotelActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


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

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    String checkIn = checkInDate.getText().toString();
                    String checkOut = checkOutDate.getText().toString();

                    if (available != null && available) {
                        if (checkIn.isEmpty()) {
                            checkInDate.setError("Please select a check-in date");
                            return;
                        } else {
                            checkInDate.setError(null);
                        }

                        if (checkOut.isEmpty()) {
                            checkOutDate.setError("Please select a check-out date");
                            return;
                        } else {
                            checkOutDate.setError(null);
                        }


                        HotelBooking hotelBooking = new HotelBooking(name, location, ratingBar.getRating(), imageUrl, pricePerNight, available, checkIn, checkOut);


                        bookingsDB.child(userId).child(hotelId).setValue(hotelBooking)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(ViewHotelActivity.this, "Booking successful", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ViewHotelActivity.this, "Booking failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(ViewHotelActivity.this, "This hotel is not available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ViewHotelActivity.this, "You need to be signed in to book a hotel", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showDatePickerDialog(final EditText dateEditText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ViewHotelActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        dateEditText.setText(selectedDate);
                    }
                }, year, month, day);


        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

}
