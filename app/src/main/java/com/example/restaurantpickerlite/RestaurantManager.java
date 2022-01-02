package com.example.restaurantpickerlite;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RestaurantManager {

    final private static String RESTAURANTS_SHARED_PREFS = "RESTAURANTS_SHARED_PREFS";
    private SharedPreferences sharedPreferences;

    public RestaurantManager(Context context) {
        sharedPreferences = context.getSharedPreferences(RESTAURANTS_SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public void saveRestaurant(RestaurantItem restaurantItem) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(restaurantItem);
        editor.putString(restaurantItem.getRestaurant(), json);
        editor.commit();
    }

    public List<RestaurantItem> getRestaurants() {
        List<RestaurantItem> restaurants = new ArrayList<>();

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
}
