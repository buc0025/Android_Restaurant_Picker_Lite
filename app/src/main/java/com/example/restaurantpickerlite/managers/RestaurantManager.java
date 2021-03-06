package com.example.restaurantpickerlite.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.restaurantpickerlite.models.RestaurantItem;
import com.google.gson.Gson;

import java.util.ArrayList;

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
        editor.apply();
    }

    public ArrayList<RestaurantItem> getRestaurants() {
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

    public void deleteRestaurants(ArrayList<RestaurantItem> restaurantItems) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (RestaurantItem restaurant : restaurantItems) {
            editor.remove(restaurant.getRestaurant());
            editor.apply();
        }
    }
}
