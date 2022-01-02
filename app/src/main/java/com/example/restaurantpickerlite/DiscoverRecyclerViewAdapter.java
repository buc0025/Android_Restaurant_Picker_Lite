package com.example.restaurantpickerlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DiscoverRecyclerViewAdapter extends RecyclerView.Adapter<DiscoverRecyclerViewAdapter.DiscoverViewHolder> {
    private ArrayList<RestaurantItem> restaurantList;
    private Context context;

    public static class DiscoverViewHolder extends RecyclerView.ViewHolder {
        public TextView discoverTextView;
        public TextView discoverTextView2;
        public ImageView restaurantImage;

        public DiscoverViewHolder(@NonNull View itemView) {
            super(itemView);
            discoverTextView = itemView.findViewById(R.id.discoverTextView);
            discoverTextView2 = itemView.findViewById(R.id.discoverTextView2);
            restaurantImage = itemView.findViewById(R.id.restaurantImage);
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

//        holder.restaurantImage.setImageResource(currentItem.getRestaurantImage());

        holder.discoverTextView.setText(currentItem.getRestaurant());
        holder.discoverTextView2.setText(currentItem.getAddress());

        Picasso.get()
                .load(currentItem.getRestaurantImage())
                .into(holder.restaurantImage);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}
