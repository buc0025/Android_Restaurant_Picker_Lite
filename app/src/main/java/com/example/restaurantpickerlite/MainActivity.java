package com.example.restaurantpickerlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener {
    private Spinner spinnerMilesRadius;
    private Button btnApply, btnClear;
    private EditText edtZipCode;
    private RequestQueue requestQueue;
    private RadioGroup radioGroup;
    private RadioButton opened, closed;
    private CheckBox chineseBox, japaneseBox, italianBox, indianBox, vegetarianBox, koreanBox;
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
        cuisines = new ArrayList<>();

        btnClear = findViewById(R.id.btnClear);
        linearLayout1 = findViewById(R.id.linLayout1);
        linearLayout2 = findViewById(R.id.linLayout2);

        requestQueue = Volley.newRequestQueue(this);

        btnApply.setEnabled(false);

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

        btnApply.setOnClickListener(v -> {
            int radioId = radioGroup.getCheckedRadioButtonId();
            opened = findViewById(radioId);
            String openedNow = "true";
            if (opened.getText().equals("closed")) {
                openedNow = "false";
            }

            Toast.makeText(MainActivity.this, "selected radio button is: " + milesRadius, Toast.LENGTH_SHORT).show();
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
}