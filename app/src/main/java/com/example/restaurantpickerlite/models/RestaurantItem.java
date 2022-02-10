package com.example.restaurantpickerlite.models;

public class RestaurantItem {
    private String restaurant;
    private String address;
    private String city;
    private String zipCode;
    private String state;
    private String phone;
    private String restaurantImage;
    private String webSite;
    private double rating;

    public RestaurantItem(String restaurant, String address, String city, String zipCode, String state, String phone,
                          String restaurantImage, String webSite, double rating) {
        this.restaurant = restaurant;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.phone = phone;
        this.restaurantImage = restaurantImage;
        this.webSite = webSite;
        this.rating = rating;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
