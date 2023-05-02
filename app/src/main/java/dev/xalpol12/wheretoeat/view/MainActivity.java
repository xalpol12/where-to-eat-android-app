package dev.xalpol12.wheretoeat.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;

import dagger.hilt.android.AndroidEntryPoint;
import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    // screen diagonal:     5 inches
    // screen dimensions:   W 2.45 : H 4.36
    // aspect ratio:        16:9 (~294 ppi density)
    // pixel resolution:    720x1080
    // dp resolution:       391x587
    // density bucket:      xhdpi

    AppCompatButton btnFindLocation;
    AppCompatButton btnPrice1;
    AppCompatButton btnPrice2;
    AppCompatButton btnPrice3;
    AppCompatButton btnPrice4;
    AppCompatButton btnRestaurant;
    AppCompatButton btnBakery;
    AppCompatButton btnCafe;
    AppCompatButton btnPub;

    AppCompatButton btnFindPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
        setOnClickListeners();
    }

    private void initializeUI() {
        btnFindLocation = findViewById(R.id.find_location_button);
        btnPrice1 = findViewById(R.id.price_1_button);
        btnPrice2 = findViewById(R.id.price_2_button);
        btnPrice3 = findViewById(R.id.price_3_button);
        btnPrice4 = findViewById(R.id.price_4_button);
        btnRestaurant = findViewById(R.id.place_type_restaurant);
        btnBakery = findViewById(R.id.place_type_bakery);
        btnCafe = findViewById(R.id.place_type_cafe);
        btnPub = findViewById(R.id.place_type_pub);
        btnFindPlace = findViewById(R.id.find_place_button);
    }

    private void setOnClickListeners() {
        View.OnClickListener priceButtonClickListener = this::priceButtonClick;
        btnPrice1.setOnClickListener(priceButtonClickListener);
        btnPrice2.setOnClickListener(priceButtonClickListener);
        btnPrice3.setOnClickListener(priceButtonClickListener);
        btnPrice4.setOnClickListener(priceButtonClickListener);
    }

    public void priceButtonClick(View v) {
        Toast.makeText(this, (String) v.getTag(), Toast.LENGTH_SHORT).show();
    }

}