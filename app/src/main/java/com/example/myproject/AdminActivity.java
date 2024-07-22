package com.example.myproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminActivity extends AppCompatActivity {
    private android.widget.Button add_hotel_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setTitle(" Admin Room-Rover");
        add_hotel_btn = findViewById(com.example.myproject.R.id.add_hotel_btn);

        add_hotel_btn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(AdminActivity.this,AddHotel.class);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        android.view.MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_profile) {
            startActivity(new android.content.Intent(AdminActivity.this, ProfilePage.class));
            return true;
        } else if (itemId == R.id.menu_sign_out) {
            startActivity(new android.content.Intent(AdminActivity.this, SignOutPage.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}