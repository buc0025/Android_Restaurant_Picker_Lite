package com.example.restaurantpickerlite.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.restaurantpickerlite.models.RestaurantItem;
import com.google.gson.Gson;

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

    public void removeFavorite(RestaurantItem restaurantItem) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(restaurantItem.getRestaurant());
        editor.apply();
    }
}
