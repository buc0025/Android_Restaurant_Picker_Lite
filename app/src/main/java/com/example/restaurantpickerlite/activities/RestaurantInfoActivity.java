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
    TextView restaurantInfoName, restaurantInfoAddress, restaurantInfoCity, restaurantInfoPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        Intent intent = getIntent();
        String url = intent.getStringExtra("imageUrl");
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String city = intent.getStringExtra("city");
        String state = intent.getStringExtra("state");
        String zipCode = intent.getStringExtra("zip code");
        String phone = intent.getStringExtra("phone");
        String cityStateZip = city + ", " + state + " " + zipCode;

        restaurantInfoImg = findViewById(R.id.restaurantInfoImg);
        restaurantInfoName = findViewById(R.id.restaurantInfoName);
        restaurantInfoAddress = findViewById(R.id.restaurantInfoAddress);
        restaurantInfoCity = findViewById(R.id.restaurantInfoCity);
        restaurantInfoPhone = findViewById(R.id.restaurantInfoPhone);

        Picasso.get().load(url).into(restaurantInfoImg);
        restaurantInfoName.setText(name);
        restaurantInfoAddress.setText(address);
        restaurantInfoCity.setText(cityStateZip);
        restaurantInfoPhone.setText(phone);

//        restaurantInfoName.append(name + "\n" + address + "\n" + city + ", " + state + " " + zipCode
//        + "\n" + phone);
    }
}