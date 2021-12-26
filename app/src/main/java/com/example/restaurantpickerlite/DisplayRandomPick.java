package com.example.restaurantpickerlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DisplayRandomPick extends AppCompatActivity {
    private TextView testUid, mainZipCode, mainMiles, mainCuisine, mainOpened;
    private RequestQueue requestQueue;
    private String zipcode, radius, opened;
    private ArrayList<String> cuisines;
    private Button btnShow, btnPickAgain;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_random_pick);

        testUid = findViewById(R.id.testUid);
        mainZipCode = findViewById(R.id.mainZipCode);
        mainMiles = findViewById(R.id.mainMiles);
        mainCuisine = findViewById(R.id.mainCuisine);
        mainOpened = findViewById(R.id.mainOpened);
        btnShow = findViewById(R.id.btnShow);
        btnPickAgain = findViewById(R.id.btnPickAgain);
        imageView = findViewById(R.id.imageView);

        String url = "https://s3-media2.fl.yelpcdn.com/bphoto/KyELb2OMVcIVwXZA0QgWLw/o.jpg";
        Picasso.get().load(url).into(imageView);

//        URL url = null;
//        try {
//            url = new URL("https://s3-media2.fl.yelpcdn.com/bphoto/KyELb2OMVcIVwXZA0QgWLw/o.jpg");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        Bitmap bmp = null;
//        try {
//            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            imageView.setImageBitmap(bmp);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        zipcode = intent.getExtras().getString("zipcode");
        radius = intent.getExtras().getString("radius");
        opened = intent.getExtras().getString("opened");
        cuisines = (ArrayList<String>) getIntent().getSerializableExtra("cuisines");

        mainZipCode.setText(zipcode);
        mainMiles.setText(radius);
        mainOpened.setText(opened);
//        mainCuisine.setText(cuisines.get(0));

        jsonParse();

        btnPickAgain.setOnClickListener(v -> {
            testUid.setText("");
            jsonParse();
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
                                testUid.append("No restaurants matches your criteria");
                            } else {
                                Random random = new Random();
                                int n = random.nextInt(jsonArray.length());
//                            for (int i = 0; i < 5; i++) {
                                JSONObject entry = jsonArray.getJSONObject(n);
                                String name = entry.getString("name");
                                JSONObject location = entry.getJSONObject("location");
                                String address = location.getString("address1");
                                String city = location.getString("city");
                                String state = location.getString("state");
                                String zipcode = location.getString("zip_code");
                                String phone = entry.getString("display_phone");
                                testUid.append(name + "\n" + phone + "\n" + address + "\n" + city + ", " + state + " " + zipcode);
//                            }
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