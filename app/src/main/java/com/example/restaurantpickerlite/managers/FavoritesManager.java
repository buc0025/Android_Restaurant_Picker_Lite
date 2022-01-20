package com.example.restaurantpickerlite.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.restaurantpickerlite.models.RestaurantItem;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FavoritesManager {

    final private static String FAVORITE_SHARED_PREFS = "FAVORITE_SHARED_PREFS";
    private SharedPreferences sharedPreferences;

    public FavoritesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(FAVORITE_SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public void saveFavorite(RestaurantItem restaurantItem) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(restaurantItem);
        editor.putString(restaurantItem.getRestaurant(), json);
        editor.apply();
    }

    public ArrayList<RestaurantItem> getFavorites() {
        ArrayList<RestaurantItem> restaurants = new ArrayList<>();

        for (String entry : sharedPreferences.getAll().keySet()) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(entry, null);
            RestaurantItem restaurant = gson.fromJson(json, RestaurantItem.class);

            if (entry != null) {
                restaurants.add(restaurant);
            }
        }
        return restaurants;
    }

    public void removeFavorite(RestaurantItem restaurantItem) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(restaurantItem.getRestaurant());
        editor.apply();
    }

    public void deleteAllFavorites() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (String entry : sharedPreferences.getAll().keySet()) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(entry, null);
            RestaurantItem restaurantItem = gson.fromJson(json, RestaurantItem.class);

            if (entry != null) {
                editor.remove(restaurantItem.getRestaurant());
                editor.apply();
            }
        }
    }

    public boolean isFavorited(String restaurantName) {
        return sharedPreferences.contains(restaurantName);
    }
}
