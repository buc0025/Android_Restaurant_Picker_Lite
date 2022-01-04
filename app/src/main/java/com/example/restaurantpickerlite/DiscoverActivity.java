package com.example.restaurantpickerlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DiscoverActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        ArrayList<RestaurantItem> restaurantList = new ArrayList<>();

        // Retrieving shared preferences saved from DisplayRandomPick.class
        RestaurantManager restaurantManager = new RestaurantManager(DiscoverActivity.this);
        restaurantList = restaurantManager.getRestaurants();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new DiscoverRecyclerViewAdapter(restaurantList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}