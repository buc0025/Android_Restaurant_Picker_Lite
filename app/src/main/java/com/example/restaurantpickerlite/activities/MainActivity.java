package com.example.restaurantpickerlite.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener {
    private Spinner spinnerMilesRadius;
    private Button btnApply, btnClear;
    private EditText edtZipCode;
    private RequestQueue requestQueue;
    private RadioGroup radioGroup;
    private RadioButton opened, closed;
    private CheckBox chineseBox, japaneseBox, italianBox, indianBox, vegetarianBox, koreanBox, americanBox, bbqBox, vietnameseBox;
    private CheckBox veganBox, frenchBox, greekBox, mexicanBox, thaiBox;
    private ArrayList<String> cuisines;
    private String milesRadius;
    private LinearLayout linearLayout1, linearLayout2;
    private String zipCode;
    private List<String> cuisineList;
    private String restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating adapter for spinner
        spinnerMilesRadius = findViewById(R.id.spinnerMilesRadius);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.milesRadius, android.R.layout
                .simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMilesRadius.setAdapter(adapter);
        spinnerMilesRadius.setOnItemSelectedListener(this);

        btnApply = findViewById(R.id.btnApply);
        edtZipCode = findViewById(R.id.edtZipCode);
        radioGroup = findViewById(R.id.radioGroup);
        chineseBox = findViewById(R.id.chineseBox);
        japaneseBox = findViewById(R.id.japaneseBox);
        italianBox = findViewById(R.id.italianBox);
        indianBox = findViewById(R.id.indianBox);
        vegetarianBox = findViewById(R.id.vegetarianBox);
        koreanBox = findViewById(R.id.koreanBox);
        americanBox = findViewById(R.id.americanBox);
        bbqBox = findViewById(R.id.bbqBox);
        vietnameseBox = findViewById(R.id.vietnameseBox);
        veganBox = findViewById(R.id.veganBox);
        frenchBox = findViewById(R.id.frenchBox);
        greekBox = findViewById(R.id.greekBox);
        mexicanBox = findViewById(R.id.mexicanBox);
        thaiBox = findViewById(R.id.thaiBox);
        cuisines = new ArrayList<>();

        btnClear = findViewById(R.id.btnClear);
        linearLayout1 = findViewById(R.id.linLayout1);
        linearLayout2 = findViewById(R.id.linLayout2);

        requestQueue = Volley.newRequestQueue(this);

        // Resets shared preferences when app is launched
        RestaurantManager restaurantManager = new RestaurantManager(MainActivity.this);
        ArrayList<RestaurantItem> restaurantList = new ArrayList<>();
        restaurantList = restaurantManager.getRestaurants();
        restaurantManager.deleteRestaurants(restaurantList);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.navigationHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigationDiscover:
                        Toast.makeText(MainActivity.this, "Must apply fields", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigationFavorites:
                        startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigationHome:
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        btnApply.setEnabled(false);
        checkboxesClicked();

        edtZipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                btnApply.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnApply.setEnabled(s.toString().length() == 5);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        btnApply.setOnClickListener(v -> {
            int radioId = radioGroup.getCheckedRadioButtonId();
            opened = findViewById(radioId);
            String openedNow = "true";
            if (opened.getText().equals("closed")) {
                openedNow = "false";
            }

            jsonParse(milesRadius, edtZipCode.getText().toString(), openedNow, cuisines);
        });

        clearButtonClicked();
    }

    private void checkboxesClicked() {
        chineseBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chineseBox.isChecked()) {
                    cuisines.add("chinese");
                } else {
                    cuisines.remove("chinese");
                }
            }
        });

        japaneseBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (japaneseBox.isChecked()) {
                    cuisines.add("japanese");
                } else {
                    cuisines.remove("japanese");
                }
            }
        });

        italianBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (italianBox.isChecked()) {
                    cuisines.add("italian");
                } else {
                    cuisines.remove("italian");
                }
            }
        });

        indianBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indianBox.isChecked()) {
                    cuisines.add("indian");
                } else {
                    cuisines.remove("indian");
                }
            }
        });

        vegetarianBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vegetarianBox.isChecked()) {
                    cuisines.add("vegetarian");
                } else {
                    cuisines.remove("vegetarian");
                }
            }
        });

        koreanBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (koreanBox.isChecked()) {
                    cuisines.add("korean");
                } else {
                    cuisines.remove("korean");
                }
            }
        });

        americanBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (americanBox.isChecked()) {
                    cuisines.add("american");
                } else {
                    cuisines.remove("american");
                }
            }
        });

        bbqBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bbqBox.isChecked()) {
                    cuisines.add("bbq");
                } else {
                    cuisines.remove("bbq");
                }
            }
        });

        vietnameseBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vietnameseBox.isChecked()) {
                    cuisines.add("vietnamese");
                } else {
                    cuisines.remove("vietnamese");
                }
            }
        });

        veganBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (veganBox.isChecked()) {
                    cuisines.add("vegan");
                } else {
                    cuisines.remove("vegan");
                }
            }
        });

        frenchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frenchBox.isChecked()) {
                    cuisines.add("french");
                } else {
                    cuisines.remove("french");
                }
            }
        });

        greekBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (greekBox.isChecked()) {
                    cuisines.add("greek");
                } else {
                    cuisines.remove("greek");
                }
            }
        });

        mexicanBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mexicanBox.isChecked()) {
                    cuisines.add("mexican");
                } else {
                    cuisines.remove("mexican");
                }
            }
        });

        thaiBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thaiBox.isChecked()) {
                    cuisines.add("thai");
                } else {
                    cuisines.remove("thai");
                }
            }
        });
    }

    private void clearButtonClicked() {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numCheckboxes1 = linearLayout1.getChildCount();
                int numCheckboxes2 = linearLayout2.getChildCount();
                Toast.makeText(MainActivity.this, "clear button pressed", Toast.LENGTH_SHORT)
                        .show();

                for (int i = 0; i < numCheckboxes1; i++) {
                    v = linearLayout1.getChildAt(i);
                    if (v instanceof CheckBox) {
                        ((CheckBox) v).setChecked(false);
                    }
                }

                for (int i = 0; i < numCheckboxes2; i++) {
                    v = linearLayout2.getChildAt(i);
                    if (v instanceof CheckBox) {
                        ((CheckBox) v).setChecked(false);
                    }
                }
                cuisines.clear();
                edtZipCode.getText().clear();
            }
        });
    }

    // for spinner
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    // for spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        milesRadius = parent.getItemAtPosition(position).toString();
    }

    // for spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void jsonParse(String miles, String zip, String open, ArrayList<String> types) {
        int milesToMeters = Integer.parseInt(miles) * 1609;
        StringBuilder stringBuilder = new StringBuilder();
        String startUrl = "https://api.yelp.com/v3/businesses/search?term=restaurants&location=";
        stringBuilder.append(startUrl).append(zip);
        stringBuilder.append("&radius=").append(milesToMeters);

        if (types.size() > 0) {
            stringBuilder.append("&categories=");

            for (int i = 0; i < types.size(); i++) {
                if (i == types.size() - 1) {
                    stringBuilder.append(types.get(i));
                } else {
                    stringBuilder.append(types.get(i)).append(",");
                }
            }
        }

        stringBuilder.append("&open_now=").append(open);

        String url = stringBuilder.toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("businesses");

                            if (jsonArray.length() == 0) {
                                Intent intent = new Intent(MainActivity.this, NoResultActivity.class);
                                startActivity(intent);
                            } else {
                                JSONObject entry = jsonArray.getJSONObject(0);
                                String website = entry.getString("url");
                                Intent intent = new Intent(MainActivity.this, DisplayRandomPick.class);
                                intent.putExtra("radius", miles);
                                intent.putExtra("zipcode", zip);
                                intent.putExtra("opened", open);
                                intent.putExtra("cuisines", types);
                                intent.putExtra("website", website);

                                startActivity(intent);
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
                headers.put("Authorization", "Bearer y43TARnbjXmLlswBS0FdDZqIFk9KytIpXuE2gOh_5LK2yLv2OxOkIvMV-Dng0uIf66p_" +
                        "2eZtU9NZ46VrGrdUZMViBmjwySlFwbd_diB7S2dslBV4gwxw6kCQxTjRYHYx");
                return headers;
            }
        };
        requestQueue.add(request);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}