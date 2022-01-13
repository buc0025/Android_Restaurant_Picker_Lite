package com.example.restaurantpickerlite.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantpickerlite.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesRecyclerViewAdapter.FavoritesViewHolder> {
    private ArrayList<RestaurantItem> restaurantList;
    private Context context;

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder{
        public TextView favoriteRestaurantName;
        public TextView favoriteRestaurantAddress;
        public TextView favoriteRestaurantCity;
        public TextView favoriteRestaurantPhone;
        public ImageView restaurantImage;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            favoriteRestaurantName = itemView.findViewById(R.id.discoverRestaurantName);
            favoriteRestaurantAddress = itemView.findViewById(R.id.discoverRestaurantAddress);
            favoriteRestaurantCity = itemView.findViewById(R.id.discoverRestaurantCity);
            favoriteRestaurantPhone = itemView.findViewById(R.id.discoverRestaurantPhone);
            restaurantImage = itemView.findViewById(R.id.restaurantImage);
        }
    }

    public FavoritesRecyclerViewAdapter(ArrayList<RestaurantItem> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }


    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        FavoritesViewHolder favoritesViewHolder = new FavoritesViewHolder(view);
        return favoritesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesRecyclerViewAdapter.FavoritesViewHolder holder, int position) {
        RestaurantItem currentItem = restaurantList.get(position);
        String city = currentItem.getCity() + ", " + currentItem.getState() + " " + currentItem.getZipCode();

        holder.favoriteRestaurantName.setText(currentItem.getRestaurant());
        holder.favoriteRestaurantAddress.setText(currentItem.getAddress());
        holder.favoriteRestaurantCity.setText(city);
        holder.favoriteRestaurantPhone.setText(currentItem.getPhone());

        Picasso.get()
                .load(currentItem.getRestaurantImage())
                .into(holder.restaurantImage);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}