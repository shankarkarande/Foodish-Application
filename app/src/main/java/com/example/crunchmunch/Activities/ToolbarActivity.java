package com.example.crunchmunch.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crunchmunch.R;

public class ToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
            switch (item.getItemId())
            {
                case  R.id.location_menu:
                    Toast.makeText(this, "Location Selected", Toast.LENGTH_SHORT).show();

                case  R.id.about_menu:
                    Toast.makeText(this, "About us Selected", Toast.LENGTH_SHORT).show();

                case  R.id.logout_menu:
                    Toast.makeText(this, "Logout Selected", Toast.LENGTH_SHORT).show();

                default : return super.onOptionsItemSelected(item);
            }
    }
}