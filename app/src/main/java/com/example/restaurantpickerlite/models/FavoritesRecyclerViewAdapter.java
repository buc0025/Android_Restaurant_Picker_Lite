package com.example.restaurantpickerlite.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesRecyclerViewAdapter.FavoritesViewHolder> {
    private ArrayList<RestaurantItem> restaurantList;
    private Context context;

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder{

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public FavoritesRecyclerViewAdapter(ArrayList<RestaurantItem> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }


    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesRecyclerViewAdapter.FavoritesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
