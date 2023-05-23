package dev.xalpol12.wheretoeat.view;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.network.dto.ImageRequestDTO;
import dev.xalpol12.wheretoeat.view.utility.ScreenDensityHelper;
import dev.xalpol12.wheretoeat.viewmodel.MainActivityViewModel;
import dev.xalpol12.wheretoeat.viewmodel.PlaceActivityViewModel;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // screen diagonal:     5 inches
    // screen dimensions:   W 2.45 : H 4.36
    // aspect ratio:        16:9 (~294 ppi density)
    // pixel resolution:    720x1080
    // dp resolution:       391x587
    // density bucket:      xhdpi

    private MainActivityViewModel mainViewModel;
    private PlaceActivityViewModel placeViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private ScreenDensityHelper screenHelper;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageButton btnHamburger;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDependencies();
        configureObservers();
        initializeUI(savedInstanceState);
        setOnClickListeners();
    }

    private void setDependencies() {
        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        placeViewModel = new ViewModelProvider(this).get(PlaceActivityViewModel.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        screenHelper = new ScreenDensityHelper(getResources().getDisplayMetrics().densityDpi);
        mainViewModel.setAssetManagerContext(this);
    }

    private void configureObservers() {
        configurePlaceListObserver();
        configureImageListObserver();
    }

    private void configurePlaceListObserver() {
        placeViewModel.getPlaceList().observe(this, places -> {
            Toast.makeText(this, "called successfully", Toast.LENGTH_SHORT).show();
            if (places != null && !places.isEmpty()) {
                placeViewModel.callFindAllImages(screenHelper.getScreenDimensions());
            }
        });
    }

    private void configureImageListObserver() {
        placeViewModel.getImageList().observe(this, images -> {
            if (images != null && images.size() == 1) {
                startActivity(new Intent(MainActivity.this, PlaceActivity.class));
            }
        });
    }

    private void initializeUI(Bundle savedInstanceState) {
        configureHamburgerMenu(savedInstanceState);
        btnHamburger = findViewById(R.id.hamburger_button);
        btnHamburger.bringToFront();
    }

    private void configureHamburgerMenu(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            replaceFragment(new FindPlaceFragment());
            navigationView.setCheckedItem(R.id.nav_find_place);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_find_place:
                replaceFragment(new FindPlaceFragment());
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_saved_places:
                replaceFragment(new SavedPlacesFragment());
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_about_me:
                replaceFragment(new AboutMeFragment());
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_language:
                Toast.makeText(this, "Language changed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_theme_mode:
                Toast.makeText(this, "Theme changed", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_activity_fragment_container, fragment)
                .commit();
    }

    private void hamburgerButtonClick(View v) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }

    private void setOnClickListeners() {
        View.OnClickListener hamburgerButtonClickListener = this::hamburgerButtonClick;
        btnHamburger.setOnClickListener(hamburgerButtonClickListener);
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation() {

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
                Toast.makeText(this, "Please turn on your location", Toast.LENGTH_SHORT).show();
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
                    placeViewModel.setCurrentLocation(lastLocation);
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
}