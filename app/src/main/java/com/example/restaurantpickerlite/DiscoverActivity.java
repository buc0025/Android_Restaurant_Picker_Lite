package com.example.restaurantpickerlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        restaurantList.add(new RestaurantItem(R.drawable.ic_baseline_home_24, "Line 1", "Line 2"));
        restaurantList.add(new RestaurantItem(R.drawable.ic_baseline_favorite_24, "Line 1", "Line 2"));
        restaurantList.add(new RestaurantItem(R.drawable.ic_baseline_search_24, "Line 1", "Line 2"));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new DiscoverRecyclerViewAdapter(restaurantList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}