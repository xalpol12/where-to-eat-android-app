package dev.xalpol12.wheretoeat.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import dagger.hilt.android.AndroidEntryPoint;
import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.data.utility.Location;
import dev.xalpol12.wheretoeat.network.dto.ImageRequestDTO;
import dev.xalpol12.wheretoeat.viewmodel.PlaceActivityViewModel;

@AndroidEntryPoint
public class PlaceActivity extends AppCompatActivity {

    private PlaceActivityViewModel viewModel;
    private PlaceFragment placeFragment;
    private AppCompatButton btnPrevious;
    private AppCompatButton btnRandom;
    private AppCompatButton btnGoThere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PlaceActivityViewModel.class);
        setContentView(R.layout.activity_place);
        initializeUI();
        setPlaceFragment();
        setOnClickListeners();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.clearImageList();
    }

    private void initializeUI() {
        placeFragment = (PlaceFragment) getSupportFragmentManager().findFragmentById(R.id.place_fragment_container);
        btnPrevious = findViewById(R.id.previous_button);
        btnRandom = findViewById(R.id.random_button);
        btnGoThere = findViewById(R.id.go_there_button);
    }

    private void setPlaceFragment() {
        placeFragment.changePlace(viewModel.getNextPlaceDetails());
        placeFragment.changePhoto(viewModel.getCorrespondingImage());
    }

    private void setOnClickListeners() {
        View.OnClickListener previousButtonClickListener = this::previousButtonClick;
        btnPrevious.setOnClickListener(previousButtonClickListener);

        View.OnClickListener randomButtonClickListener = this::randomButtonClick;
        btnRandom.setOnClickListener(randomButtonClickListener);

        View.OnClickListener goThereButtonClickListener = this::goThereButtonClick;
        btnGoThere.setOnClickListener(goThereButtonClickListener);
    }

    private void previousButtonClick(View v) {
        setPreviousPlace();
    }

    private void randomButtonClick(View v) {
        setNextPlace();
    }

    private void goThereButtonClick(View v) {
        Location location = viewModel.getCurrentPlaceLocation();
        String name = viewModel.getCurrentPlaceName();
        String strUri = "http://maps.google.com/maps?q=loc:" + location.getLat() + "," + location.getLng() + " (" + name + ")";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

        startActivity(intent);
    }

    private void setNextPlace() {
        placeFragment.changePlace(viewModel.getNextPlaceDetails());
        placeFragment.changePhoto(viewModel.getCorrespondingImage());
        placeFragment.updateUI();
    }

    private void setPreviousPlace() {
        placeFragment.changePlace(viewModel.getPreviousPlaceDetails());
        placeFragment.changePhoto(viewModel.getCorrespondingImage());
        placeFragment.updateUI();
    }
}