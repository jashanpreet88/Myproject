package com.example.myproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setTitle(" Admin Room-Rover");

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