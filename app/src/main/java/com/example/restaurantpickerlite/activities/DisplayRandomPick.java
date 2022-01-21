package com.example.restaurantpickerlite.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurantpickerlite.R;
import com.example.restaurantpickerlite.managers.FavoritesManager;
import com.example.restaurantpickerlite.models.RestaurantItem;
import com.example.restaurantpickerlite.managers.RestaurantManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DisplayRandomPick extends AppCompatActivity {
    private TextView testUid, mainZipCode, mainMiles, mainCuisine, mainOpened, restaurantInfoName, restaurantInfoAddress,
            restaurantInfoCity, restaurantInfoPhone;;
    private RequestQueue requestQueue;
    private String zipcode, radius, opened, address;
    private ArrayList<String> cuisines;
    private Button btnShow, btnPickAgain;
    private ImageView restaurantImg;
    private ArrayList<RestaurantItem> restaurantList;
    private ToggleButton favoriteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_random_pick);


        restaurantInfoName = findViewById(R.id.restaurantInfoName);
        restaurantInfoAddress = findViewById(R.id.restaurantInfoAddress);
        restaurantInfoCity = findViewById(R.id.restaurantInfoCity);
        restaurantInfoPhone = findViewById(R.id.restaurantInfoPhone);
//        testUid = findViewById(R.id.testUid);
//        mainZipCode = findViewById(R.id.mainZipCode);
//        mainMiles = findViewById(R.id.mainMiles);
//        mainCuisine = findViewById(R.id.mainCuisine);
//        mainOpened = findViewById(R.id.mainOpened);
        btnShow = findViewById(R.id.btnShow);
        btnPickAgain = findViewById(R.id.btnPickAgain);
        restaurantImg = findViewById(R.id.restaurantImg);
        address = "";
        restaurantList = new ArrayList<>();
        favoriteBtn = findViewById(R.id.favoriteBtn);



//        String url = "https://s3-media2.fl.yelpcdn.com/bphoto/KyELb2OMVcIVwXZA0QgWLw/o.jpg";
//        Picasso.get().load(url).into(imageView);

        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        zipcode = intent.getExtras().getString("zipcode");
        radius = intent.getExtras().getString("radius");
        opened = intent.getExtras().getString("opened");
        cuisines = (ArrayList<String>) getIntent().getSerializableExtra("cuisines");

//        mainZipCode.setText(zipcode);
//        mainMiles.setText(radius);
//        mainOpened.setText(opened);
//        mainCuisine.setText(cuisines.get(0));

        jsonParse();

        btnShow.setOnClickListener(v -> {
//            Intent maps = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("google.navigation:q=" + address));
//            maps.setPackage("com.google.android.apps.maps");
//
//            if (maps.resolveActivity(getPackageManager()) != null) {
//                startActivity(maps);
//            }
//            Toast.makeText(this, address, Toast.LENGTH_LONG).show();

            // Opens discover tab
//            Intent discover = new Intent(this, DiscoverActivity.class);
//            startActivity(discover);

            RestaurantManager restaurantManager = new RestaurantManager(DisplayRandomPick.this);
            ArrayList<RestaurantItem> restaurantList = new ArrayList<>();
            restaurantList = restaurantManager.getRestaurants();
            restaurantManager.deleteRestaurants(restaurantList);
        });

        btnPickAgain.setOnClickListener(v -> {
//            testUid.setText("");
            jsonParse();
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.navigationDiscover);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigationDiscover:
                        startActivity(new Intent(getApplicationContext(), DiscoverActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigationFavorites:
                        startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void jsonParse() {
        int milesToMeters = Integer.parseInt(radius) * 1609;
        StringBuilder stringBuilder = new StringBuilder();
        String startUrl = "https://api.yelp.com/v3/businesses/search?term=food&location=";
        stringBuilder.append(startUrl).append(zipcode);
        stringBuilder.append("&radius=").append(milesToMeters);

        if (cuisines.size() > 0) {
            stringBuilder.append("&categories=");

            for (int i = 0; i < cuisines.size(); i++) {
                if (i == cuisines.size() - 1) {
                    stringBuilder.append(cuisines.get(i));
                } else {
                    stringBuilder.append(cuisines.get(i)).append(",");
                }
            }
        }

        stringBuilder.append("&open_now=").append(opened);

        String url = stringBuilder.toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("businesses");

                            if (jsonArray.length() == 0) {
//                                testUid.append("No restaurants matches your criteria");
                                restaurantInfoName.append("No restaurants matches your criteria");
                            } else {
                                Random random = new Random();
                                int n = random.nextInt(jsonArray.length());
//                            for (int i = 0; i < 5; i++) {
                                JSONObject entry = jsonArray.getJSONObject(n);
                                String name = entry.getString("name");
                                String url = entry.getString("image_url");

                                // location object is an array that contains address elements
                                JSONObject location = entry.getJSONObject("location");
                                String address = location.getString("address1");
                                String city = location.getString("city");
                                String state = location.getString("state");
                                String zipcode = location.getString("zip_code");
                                String phone = entry.getString("display_phone");

                                String cityStateZip = city + ", " + state + " " + zipcode;

//                                DisplayRandomPick.this.address = address + ", " + city + " " + state;

//                                testUid.append(name + "\n" + phone + "\n" + address + "\n" + city + ", " + state + " " + zipcode);

                                restaurantInfoName.setText(name);
                                restaurantInfoAddress.setText(address);
                                restaurantInfoCity.setText(cityStateZip);
                                restaurantInfoPhone.setText(phone);

                                Picasso.get()
                                        .load(url)
//                                        .resize(600, 500)
                                        .into(restaurantImg);

                                RestaurantItem favoriteRestaurant = new RestaurantItem(name, address,
                                        city, zipcode, state, phone, url);

                                FavoritesManager favoritesManager = new FavoritesManager(DisplayRandomPick.this);
                                favoriteBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (favoriteBtn.isChecked()) {
                                            Toast.makeText(DisplayRandomPick.this, "restaurant favorited", Toast.LENGTH_SHORT).show();
                                            favoritesManager.saveFavorite(favoriteRestaurant);
                                            favoriteBtn.setChecked(true);
                                        } else {
                                            Toast.makeText(DisplayRandomPick.this, "restaurant unfavorited", Toast.LENGTH_SHORT).show();
                                            favoritesManager.removeFavorite(favoriteRestaurant);
                                            favoriteBtn.setChecked(false);
                                        }
                                    }
                                });
//                            }
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject restaurant = jsonArray.getJSONObject(i);
                                    String restaurantName = restaurant.getString("name");
                                    String restaurantImage = restaurant.getString("image_url");

                                    // need to get location address
                                    JSONObject jsonObject = restaurant.getJSONObject("location");
                                    String restaurantAddress = jsonObject.getString("address1");
                                    String restaurantCity = jsonObject.getString("city");
                                    String restaurantZip = jsonObject.getString("zip_code");
                                    String restaurantState = jsonObject.getString("state");
                                    String restaurantPhone = restaurant.getString("display_phone");

                                    RestaurantItem restaurantItem = new RestaurantItem(restaurantName, restaurantAddress,
                                            restaurantCity, restaurantZip, restaurantState, restaurantPhone, restaurantImage);

                                    // Save restaurant item to shared preferences
                                    RestaurantManager restaurantManager = new RestaurantManager(DisplayRandomPick.this);
                                    restaurantManager.saveRestaurant(restaurantItem);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer y43TARnbjXmLlswBS0FdDZqIFk9KytIpXuE2gOh_5LK2yLv2OxOkIvMV-Dng0uIf66p_" +
                        "2eZtU9NZ46VrGrdUZMViBmjwySlFwbd_diB7S2dslBV4gwxw6kCQxTjRYHYx");
                return headers;
            }
        };
        requestQueue.add(request);
    }
}