package com.example.restaurantpickerlite.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restaurantpickerlite.R;
import com.squareup.picasso.Picasso;

public class RestaurantInfoActivity extends AppCompatActivity {
    ImageView restaurantInfoImg;
    TextView restaurantInfoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        Intent intent = getIntent();
        String url = intent.getStringExtra("imageUrl");
        String name = intent.getStringExtra("name");

        restaurantInfoImg = findViewById(R.id.restaurantInfoImg);
        restaurantInfoName = findViewById(R.id.restaurantInfoName);

        Picasso.get().load(url).into(restaurantInfoImg);
        restaurantInfoName.setText(name);
    }
}