package com.example.restaurantpickerlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class DisplayRandomPick extends AppCompatActivity {
    private TextView mainZipCode, mainMiles, mainCuisine, mainOpened;
    private RequestQueue requestQueue;
    private String zipcode, radius, opened;
    private ArrayList<String> cuisines;
    private Button btnShow, btnDeletePrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_random_pick);

        mainZipCode = findViewById(R.id.mainZipCode);
        mainMiles = findViewById(R.id.mainMiles);
        mainCuisine = findViewById(R.id.mainCuisine);
        mainOpened = findViewById(R.id.mainOpened);
        btnShow = findViewById(R.id.btnShow);
        btnDeletePrefs = findViewById(R.id.btnDeletePrefs);
    }
}