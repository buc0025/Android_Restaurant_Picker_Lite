package com.example.restaurantpickerlite;

public class RestaurantItem {
    private String restaurantImage;
    private String restaurant;
    private String address;

    public RestaurantItem(String restaurantImage, String restaurant, String address) {
        this.restaurantImage = restaurantImage;
        this.restaurant = restaurant;
        this.address = address;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
