package com.example.restaurantpickerlite.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.restaurantpickerlite.R;
import com.example.restaurantpickerlite.managers.FavoritesManager;
import com.example.restaurantpickerlite.managers.RestaurantManager;
import com.example.restaurantpickerlite.models.DiscoverRecyclerViewAdapter;
import com.example.restaurantpickerlite.models.FavoritesRecyclerViewAdapter;
import com.example.restaurantpickerlite.models.RestaurantItem;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ArrayList<RestaurantItem> restaurantList = new ArrayList<>();

        // Retrieving shared preferences saved from DisplayRandomPick.class
        FavoritesManager favoritesManager = new FavoritesManager(FavoritesActivity.this);
        restaurantList = favoritesManager.getRestaurants();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new FavoritesRecyclerViewAdapter(restaurantList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}