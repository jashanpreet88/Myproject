package com.example.myproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "52efd2b5e04dddf397b7836827933f94\n";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=montreal&appid=" + API_KEY + "&units=metric";

    private DatabaseReference HotelsDB;
    private TextView weatherTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setTitle("RoomOver");

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
                        } else {
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
        inflater.inflate(R.menu.user_menu, menu);
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
        } else if (itemId == R.id.menu_booking) {
            startActivity(new android.content.Intent(MainActivity.this, UserBookingActivity.class));
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void fetchWeatherData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(WEATHER_URL).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to load weather data", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    runOnUiThread(() -> updateWeatherUI(responseData));
                }
            }
        });
    }

    private void updateWeatherUI(String weatherData) {
        try {
            JSONObject jsonObject = new JSONObject(weatherData);
            JSONObject main = jsonObject.getJSONObject("main");
            double temperature = main.getDouble("temp");
            String weatherInfo = "Today's Weather: " + temperature + "°C";
            weatherTextView.setText(weatherInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
