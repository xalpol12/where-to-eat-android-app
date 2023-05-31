package dev.xalpol12.wheretoeat.view.place;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;
import dev.xalpol12.wheretoeat.viewmodel.PlaceActivityViewModel;
import lombok.SneakyThrows;

@AndroidEntryPoint
public class PlaceActivity extends AppCompatActivity {

    private PlaceActivityViewModel placeViewModel;
    private PlaceFragment placeFragment;
    private AppCompatButton btnPrevious;
    private AppCompatButton btnRandom;
    private AppCompatButton btnGoThere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeViewModel = new ViewModelProvider(this).get(PlaceActivityViewModel.class);
        setContentView(R.layout.activity_place);
        setObservers();
        initializeUI();
        setPlaceFragment();
        setOnClickListeners();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        placeViewModel.clearImageList();
    }

    private void setObservers() {
        setPlaceRepositoryObserver();
    }

    private void setPlaceRepositoryObserver() {
        placeViewModel.getAllPlaces().observe(this, places -> updateRibbonColor());
    }

    private void initializeUI() {
        placeFragment = (PlaceFragment) getSupportFragmentManager().findFragmentById(R.id.place_fragment_container);

        View.OnClickListener saveButtonClickListener = this::savePlaceButtonClick;
        assert placeFragment != null;
        placeFragment.setSaveButtonCallback(saveButtonClickListener);

        btnPrevious = findViewById(R.id.previous_button);
        btnRandom = findViewById(R.id.random_button);
        btnGoThere = findViewById(R.id.go_there_button);
    }

    private void setPlaceFragment() {
        placeFragment.changePlace(placeViewModel.getNextPlaceDetails());
        placeFragment.changePhoto(placeViewModel.getCorrespondingImage());
        placeFragment.setUserLocation(placeViewModel.getCurrentLocation());
    }

    private void setOnClickListeners() {
        View.OnClickListener previousButtonClickListener = this::previousButtonClick;
        btnPrevious.setOnClickListener(previousButtonClickListener);

        View.OnClickListener randomButtonClickListener = this::randomButtonClick;
        btnRandom.setOnClickListener(randomButtonClickListener);

        View.OnClickListener goThereButtonClickListener = this::goThereButtonClick;
        btnGoThere.setOnClickListener(goThereButtonClickListener);
    }

    private void updateRibbonColor() {
        if (isPlaceInDatabase()) {
            placeFragment.setColorFilterOnSaveButton(ContextCompat.getColor(this, R.color.accent));
        } else {
            placeFragment.setColorFilterOnSaveButton(ContextCompat.getColor(this, R.color.secondary));
        }
    }

    private void savePlaceButtonClick(View view) {
        executeDatabaseOperationOnCurrentPlace(isPlaceInDatabase());
    }

    private void executeDatabaseOperationOnCurrentPlace(boolean isPlaceInDatabase) {
        if (isPlaceInDatabase) {
            placeViewModel.deletePlaceFromDb(placeViewModel.getCurrentPlaceId());
            Toast.makeText(this, R.string.place_deleted_prompt, Toast.LENGTH_SHORT).show();
        } else {
            placeViewModel.savePlaceToDb();
            Toast.makeText(this, R.string.place_saved_prompt, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPlaceInDatabase() {
        return placeViewModel.isInDatabase();
    }

    private void previousButtonClick(View v) {
        setPreviousPlace();
    }

    private void randomButtonClick(View v) {
        setNextPlace();
    }

    private void goThereButtonClick(View v) {
        List<PlaceEntity> savedPlaces = placeViewModel.getAllPlaces().getValue();
        if (savedPlaces != null) {
            Toast.makeText(this, String.valueOf(savedPlaces.size()), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "null value", Toast.LENGTH_SHORT).show();
        }
//        Location location = viewModel.getCurrentPlaceLocation();
//        String name = viewModel.getCurrentPlaceName();
//        String strUri = "http://maps.google.com/maps?q=loc:" + location.getLat() + "," + location.getLng() + " (" + name + ")";
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
//
//        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//
//        startActivity(intent);
    }

    private void setNextPlace() {
        placeFragment.changePlace(placeViewModel.getNextPlaceDetails());
        placeFragment.changePhoto(placeViewModel.getCorrespondingImage());
        placeFragment.updateUI();
        updateRibbonColor();
    }

    private void setPreviousPlace() {
        placeFragment.changePlace(placeViewModel.getPreviousPlaceDetails());
        placeFragment.changePhoto(placeViewModel.getCorrespondingImage());
        placeFragment.updateUI();
        updateRibbonColor();
    }
}