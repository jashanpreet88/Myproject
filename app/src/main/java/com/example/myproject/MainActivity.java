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

public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "52efd2b5e04dddf397b7836827933f94\n";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=montreal&appid=" + API_KEY + "&units=metric";
    private Button button_profile;
    private DatabaseReference HotelsDB;
    private android.widget.TextView weatherTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Room-Rover");
        weatherTextView = findViewById(R.id.weatherTextView);

        fetchWeatherData();
        ListView listView = findViewById(R.id.listView);
        List<Hotel> hotels = new ArrayList<>();
        HotelAdapter adapter = new HotelAdapter(this, hotels);
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
                            Toast.makeText(MainActivity.this, "No hotel", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(MainActivity.this, "No hotels", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
            startActivity(new android.content.Intent(MainActivity.this, ProfilePage.class));
            return true;
        } else if (itemId == R.id.menu_sign_out) {
            startActivity(new android.content.Intent(MainActivity.this, SignOutPage.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

        private void fetchWeatherData(){
            okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
            okhttp3.Request request = new okhttp3.Request.Builder().url(WEATHER_URL).build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, java.io.IOException e) {
                    runOnUiThread(()->Toast.makeText(MainActivity.this, "Failed to load weather data", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws java.io.IOException {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        runOnUiThread(() -> updateWeatherUI(responseData));
                }
            });
        }

}