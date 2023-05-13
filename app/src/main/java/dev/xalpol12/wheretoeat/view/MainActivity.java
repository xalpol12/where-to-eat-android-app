package dev.xalpol12.wheretoeat.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.data.Place;
import dev.xalpol12.wheretoeat.viewmodel.MainActivityViewModel;
import dev.xalpol12.wheretoeat.viewmodel.PlaceActivityViewModel;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    // screen diagonal:     5 inches
    // screen dimensions:   W 2.45 : H 4.36
    // aspect ratio:        16:9 (~294 ppi density)
    // pixel resolution:    720x1080
    // dp resolution:       391x587
    // density bucket:      xhdpi

    private MainActivityViewModel mainViewModel;
    private PlaceActivityViewModel placeViewModel;
    private final List<Integer> priceButtonIds = List.of(
                R.id.price_1_button,
                R.id.price_2_button,
                R.id.price_3_button,
                R.id.price_4_button);
    private final List<AppCompatButton> priceButtons = new ArrayList<>();
    private final List<Integer> placeButtonIds = List.of(
            R.id.place_type_restaurant,
            R.id.place_type_bakery,
            R.id.place_type_cafe,
            R.id.place_type_pub);
    private final List<AppCompatButton> placeButtons = new ArrayList<>();
    private AppCompatButton btnFindLocation;
    private AppCompatButton btnFindPlace;
    private Slider rangeSlider;
    private int primaryColor;
    private int accentColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        placeViewModel = new ViewModelProvider(this).get(PlaceActivityViewModel.class);
        setContentView(R.layout.activity_main);
        configurePlaceViewModelObserver();
        initializeUI();
        setOnClickListeners();
    }

    private void configurePlaceViewModelObserver() {
        placeViewModel.getPlaceList().observe(this, new Observer<>() {
            @Override
            public void onChanged(List<Place> places) {
                if (places != null && !places.isEmpty()) {
                    startActivity(new Intent(MainActivity.this, PlaceActivity.class));
                }
            }
        });
    }

    private void initializeUI() {
        btnFindLocation = findViewById(R.id.find_location_button);
        btnFindPlace = findViewById(R.id.find_place_button);
        rangeSlider = findViewById(R.id.range_slider);

        for (int id : priceButtonIds) {
            priceButtons.add(findViewById(id));
        }

        for (int id : placeButtonIds) {
            placeButtons.add(findViewById(id));
        }

        primaryColor = ContextCompat.getColor(this, R.color.primary);
        accentColor = ContextCompat.getColor(this, R.color.accent);
    }

    private void setOnClickListeners() {
        View.OnClickListener locationButtonClickListener = this::locationButtonClick;
        btnFindLocation.setOnClickListener(locationButtonClickListener);

        Slider.OnChangeListener sliderChangeListener = this::sliderChange;
        rangeSlider.addOnChangeListener(sliderChangeListener);

        View.OnClickListener priceButtonClickListener = this::priceButtonClick;
        for (AppCompatButton btn : priceButtons) {
            btn.setOnClickListener(priceButtonClickListener);
        }

        View.OnClickListener placeButtonClickListener = this::placeButtonClick;
        for (AppCompatButton btn : placeButtons) {
            btn.setOnClickListener(placeButtonClickListener);
        }

        View.OnClickListener findPlaceButtonClickListener = this::findPlaceButtonClick;
        btnFindPlace.setOnClickListener(findPlaceButtonClickListener);
    }

    private void locationButtonClick(View v) {
        //TODO: Implement location based on GPS
        double lat = 52.402;
        double lng = 16.93521;

        mainViewModel.setRequestLocation(lat, lng);

        v.setAlpha(1.f);
    }

    private void sliderChange(Slider slider, float v, boolean b) {
        mainViewModel.setRequestDistance((int) (v * 1000));
    }

    private void priceButtonClick(View v) {
        String tag = (String) v.getTag();
        int selectedBtnIndex = Integer.parseInt(tag);

        mainViewModel.setRequestMaxPrice(selectedBtnIndex);

        for (int i = 0; i < selectedBtnIndex; i++) {
            priceButtons.get(i).getCompoundDrawables()[0].setColorFilter(accentColor, PorterDuff.Mode.SRC_ATOP);
        }
        for (int i = selectedBtnIndex; i < priceButtons.size(); i++) {
            priceButtons.get(i).getCompoundDrawables()[0].setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void placeButtonClick(View v) {
        Object tag = v.getTag();
        String selectedBtnLabel = (String) v.getTag();

        for (AppCompatButton btn : placeButtons) {
            if (btn.getTag().equals(selectedBtnLabel)) {
                mainViewModel.setPlaceType(selectedBtnLabel.toUpperCase());
                Objects.requireNonNull(btn).getCompoundDrawables()[0].setColorFilter(accentColor, PorterDuff.Mode.SRC_ATOP);
            } else {
                Objects.requireNonNull(btn).getCompoundDrawables()[0].setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    private void findPlaceButtonClick(View v) {
        try {
            if (mainViewModel.areAllFieldsNotNull()) {
                openPlaceActivity();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void openPlaceActivity() {
        placeViewModel.callFindPlaces(mainViewModel.getPlaceRequestDTO());
    }
}