package com.example.restaurantpickerlite.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.restaurantpickerlite.R;
import com.example.restaurantpickerlite.managers.FavoritesManager;
import com.example.restaurantpickerlite.models.RestaurantItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class RestaurantInfoActivity extends AppCompatActivity {
    private ImageView restaurantInfoImg;
    private TextView restaurantInfoName, restaurantInfoAddress, restaurantInfoCity, restaurantInfoPhone;
    private ToggleButton favBtn;
    private Button websiteBtn, directionsBtn;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        // Getting intents from DiscoverRecyclerViewAdapter and FavoritesRecyclerViewAdapter
        Intent intent = getIntent();
        String url = intent.getStringExtra("imageUrl");
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String city = intent.getStringExtra("city");
        String state = intent.getStringExtra("state");
        String zipCode = intent.getStringExtra("zip code");
        String phone = intent.getStringExtra("phone");
        String site = intent.getStringExtra("site");
        double rating = intent.getDoubleExtra("rating", 0);
        String cityStateZip = city + ", " + state + " " + zipCode;

        restaurantInfoImg = findViewById(R.id.restaurantInfoImg);
        restaurantInfoName = findViewById(R.id.restaurantInfoName);
        restaurantInfoAddress = findViewById(R.id.restaurantInfoAddress);
        restaurantInfoCity = findViewById(R.id.restaurantInfoCity);
        restaurantInfoPhone = findViewById(R.id.restaurantInfoPhone);
        favBtn = findViewById(R.id.favoriteBtn);
        websiteBtn = findViewById(R.id.websiteBtn);
        directionsBtn = findViewById(R.id.directionsBtn);
        ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setRating((float) rating);
        restaurantInfoName.setText(name);
        restaurantInfoAddress.setText(address);
        restaurantInfoCity.setText(cityStateZip);
        restaurantInfoPhone.setText(phone);

        try {
            Picasso.get()
                    .load(url)
                    .into(restaurantInfoImg);
        } catch (Exception e) {
            String unavailableImg = "https://eagle-sensors.com/wp-content/uploads/unavailable-image.jpg";
            Picasso.get()
                    .load(unavailableImg)
                    .into(restaurantInfoImg);
        }

        FavoritesManager favoritesManager = new FavoritesManager(RestaurantInfoActivity.this);
        RestaurantItem restaurantItem = new RestaurantItem(name, address, city, zipCode, state, phone, url, site, rating);
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

        websiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantInfoActivity.this, RestaurantWebSite.class);
                intent.putExtra("url", site);
                startActivity(intent);
            }
        });

        directionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent maps = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + address));
                maps.setPackage("com.google.android.apps.maps");

                if (maps.resolveActivity(getPackageManager()) != null) {
                    startActivity(maps);
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigationDiscover:
                        startActivity(new Intent(getApplicationContext(), DiscoverActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigationFavorites:
                        startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigationHome:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


    }
}