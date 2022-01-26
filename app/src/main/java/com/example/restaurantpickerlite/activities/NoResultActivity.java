package com.example.restaurantpickerlite.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.restaurantpickerlite.R;

public class NoResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_result);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NoResultActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}