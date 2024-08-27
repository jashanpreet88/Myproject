package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    Button add_hotel_btn;
    DatabaseReference HotelsDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Manager Room-Rover");



        add_hotel_btn = findViewById(R.id.add_hotel_btn);
        add_hotel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, AddHotelActivity.class);
                startActivity(intent);
            }
        });




        ListView listView = findViewById(R.id.listView);
        List<Hotel> hotels = new ArrayList<>();
        AdminHotelAdapter adapter = new AdminHotelAdapter(this, hotels);
        listView.setAdapter(adapter);
        HotelsDB = FirebaseDatabase.getInstance().getReference("hotels");
        HotelsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hotels.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Hotel hotel = snapshot.getValue(Hotel.class);
                        if (hotel != null) {
                            hotel.setId(snapshot.getKey());
                            hotels.add(hotel);
                        }else {
                            Toast.makeText(AdminActivity.this, "No hotel", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(AdminActivity.this, "No hotels", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_profile) {
            startActivity(new android.content.Intent(AdminActivity.this, ProfilePage.class));
            return true;
        }else if (itemId == R.id.menu_booking) {
            startActivity(new android.content.Intent(AdminActivity.this, UserBookingActivity.class));
            return true;
        } else if (itemId == R.id.menu_sign_out) {
            startActivity(new android.content.Intent(AdminActivity.this, SignOutPage.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}