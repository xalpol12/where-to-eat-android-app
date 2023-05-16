package dev.xalpol12.wheretoeat.view;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import dev.xalpol12.wheretoeat.R;
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
    private FusedLocationProviderClient fusedLocationClient;
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_main);
        configurePlaceViewModelObserver();
        initializeUI();
        setOnClickListeners();
    }

    private void configurePlaceViewModelObserver() {
        placeViewModel.getPlaceList().observe(this, places -> {
            if (places != null && !places.isEmpty()) {
                startActivity(new Intent(MainActivity.this, PlaceActivity.class));
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
        getLastLocation();
        v.setAlpha(1.f);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {

        if (hasGPSPermissions()) {

            if (isLocationEnabled()) {
                fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        mainViewModel.setRequestLocation(location.getLatitude(), location.getLongitude());
                        Toast.makeText(this, "Location requested successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + "your location...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestGPSPermissions();
        }
    }

    private boolean hasGPSPermissions() {
        boolean hasCoarseLocation = ContextCompat.checkSelfPermission(MainActivity.this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean hasFineLocation = ContextCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        return hasCoarseLocation && hasFineLocation;
    }

    private void requestGPSPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, 1);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest locationRequest = new LocationRequest
                .Builder(Priority.PRIORITY_HIGH_ACCURACY)
                .setIntervalMillis(10000)
                .setMinUpdateIntervalMillis(0)
                .setMaxUpdates(1)
                .build();
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location lastLocation = locationResult.getLastLocation();
                if (lastLocation != null ) {
                    mainViewModel.setRequestLocation(lastLocation.getLatitude(), lastLocation.getLongitude());
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location permission denied. App cannot function without access to GPS.", Toast.LENGTH_SHORT).show();
            }
        }
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