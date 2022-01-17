package com.example.restaurantpickerlite.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.restaurantpickerlite.R;
import com.example.restaurantpickerlite.managers.FavoritesManager;
import com.example.restaurantpickerlite.models.RestaurantItem;
import com.squareup.picasso.Picasso;

public class RestaurantInfoActivity extends AppCompatActivity {
    ImageView restaurantInfoImg;
    TextView restaurantInfoName, restaurantInfoAddress, restaurantInfoCity, restaurantInfoPhone;
    ToggleButton favBtn;

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
        favBtn = findViewById(R.id.favoriteBtn);

        Picasso.get().load(url).into(restaurantInfoImg);
        restaurantInfoName.setText(name);
        restaurantInfoAddress.setText(address);
        restaurantInfoCity.setText(cityStateZip);
        restaurantInfoPhone.setText(phone);

        FavoritesManager favoritesManager = new FavoritesManager(RestaurantInfoActivity.this);
        RestaurantItem restaurantItem = new RestaurantItem(name, address, city, zipCode, state, phone, url);
        favBtn.setChecked(favoritesManager.isFavorited(name));

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favBtn.isChecked()) {
                    Toast.makeText(RestaurantInfoActivity.this, "restaurant favorited", Toast.LENGTH_SHORT).show();
                    favoritesManager.saveFavorite(restaurantItem);
                    favBtn.setChecked(true);
                } else {
                    Toast.makeText(RestaurantInfoActivity.this, "restaurant unfavorited", Toast.LENGTH_SHORT).show();
                    favoritesManager.removeFavorite(restaurantItem);
                    favBtn.setChecked(false);
                }
            }
        });


    }
}