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

        String url = "https://s3-media2.fl.yelpcdn.com/bphoto/KyELb2OMVcIVwXZA0QgWLw/o.jpg";
        ArrayList<RestaurantItem> restaurantList = new ArrayList<>();
        restaurantList.add(new RestaurantItem(url, "Line 1", "Line 2"));
        restaurantList.add(new RestaurantItem(url, "Line 1", "Line 2"));
//        restaurantList.add(new RestaurantItem(R.drawable.ic_baseline_search_24, "Line 1", "Line 2"));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new DiscoverRecyclerViewAdapter(restaurantList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}