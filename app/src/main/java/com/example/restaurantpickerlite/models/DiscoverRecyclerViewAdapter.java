package com.example.restaurantpickerlite.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantpickerlite.R;
import com.example.restaurantpickerlite.activities.RestaurantInfoActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DiscoverRecyclerViewAdapter extends RecyclerView.Adapter<DiscoverRecyclerViewAdapter.DiscoverViewHolder> {
    private ArrayList<RestaurantItem> restaurantList;
    private Context context;

    public static class DiscoverViewHolder extends RecyclerView.ViewHolder {
        public TextView discoverRestaurantName;
        public TextView discoverRestaurantAddress;
        public TextView discoverRestaurantCity;
        public TextView discoverRestaurantPhone;
        public ImageView restaurantImage;
        public RatingBar ratingBar;
        public CardView restaurantCardView;

        public DiscoverViewHolder(@NonNull View itemView) {
            super(itemView);
            discoverRestaurantName = itemView.findViewById(R.id.discoverRestaurantName);
            discoverRestaurantAddress = itemView.findViewById(R.id.discoverRestaurantAddress);
            discoverRestaurantCity = itemView.findViewById(R.id.discoverRestaurantCity);
            discoverRestaurantPhone = itemView.findViewById(R.id.discoverRestaurantPhone);
            restaurantImage = itemView.findViewById(R.id.restaurantImage);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            restaurantCardView = itemView.findViewById(R.id.restaurantCardView);
        }
    }

    public DiscoverRecyclerViewAdapter(ArrayList<RestaurantItem> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        DiscoverViewHolder discoverViewHolder = new DiscoverViewHolder(view);
        return discoverViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverRecyclerViewAdapter.DiscoverViewHolder holder, int position) {
        RestaurantItem currentItem = restaurantList.get(position);
        String city = currentItem.getCity() + ", " + currentItem.getState() + " " + currentItem.getZipCode();

        holder.discoverRestaurantName.setText(currentItem.getRestaurant());
        holder.discoverRestaurantAddress.setText(currentItem.getAddress());
        holder.discoverRestaurantCity.setText(city);
        holder.discoverRestaurantPhone.setText(currentItem.getPhone());
        holder.ratingBar.setRating((float) currentItem.getRating());

        Picasso.get()
                .load(currentItem.getRestaurantImage())
                .into(holder.restaurantImage);

        holder.restaurantCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantInfoActivity.class);

                // Passing data to RestaurantInfoActivity
                intent.putExtra("imageUrl", currentItem.getRestaurantImage());
                intent.putExtra("name", currentItem.getRestaurant());
                intent.putExtra("address", currentItem.getAddress());
                intent.putExtra("city", currentItem.getCity());
                intent.putExtra("state", currentItem.getState());
                intent.putExtra("zip code", currentItem.getZipCode());
                intent.putExtra("phone", currentItem.getPhone());
                intent.putExtra("site", currentItem.getWebSite());
                intent.putExtra("rating", currentItem.getRating());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}
