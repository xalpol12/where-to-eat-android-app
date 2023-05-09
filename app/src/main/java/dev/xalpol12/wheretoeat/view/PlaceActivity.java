package dev.xalpol12.wheretoeat.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import dagger.hilt.android.AndroidEntryPoint;
import dev.xalpol12.wheretoeat.R;
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
//        viewModel = (PlaceActivityViewModel) getIntent().getSerializableExtra("placeViewModel");
        viewModel = new ViewModelProvider(this).get(PlaceActivityViewModel.class);
        setContentView(R.layout.activity_place);
        initializeUI();
        setOnClickListeners();
    }

    private void initializeUI() {
        placeFragment = (PlaceFragment) getSupportFragmentManager().findFragmentById(R.id.place_fragment_container);
        btnPrevious = findViewById(R.id.previous_button);
        btnRandom = findViewById(R.id.random_button);
        btnGoThere = findViewById(R.id.go_there_button);
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

    }

    private void randomButtonClick(View v) {
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.img_default_place_photo);
        placeFragment.changePlace(viewModel.getNextPlaceDetails(), image);
    }

    private void goThereButtonClick(View v) {

    }
}