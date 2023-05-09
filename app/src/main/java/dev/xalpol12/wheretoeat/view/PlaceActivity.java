package dev.xalpol12.wheretoeat.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import dagger.hilt.android.AndroidEntryPoint;
import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.view.adapter.RecyclerViewAdapter;
import dev.xalpol12.wheretoeat.viewmodel.PlaceActivityViewModel;

@AndroidEntryPoint
public class PlaceActivity extends AppCompatActivity {

    private PlaceActivityViewModel viewModel;
    private ViewPager2 viewPager;
    private RecyclerView recyclerView;
    private AppCompatButton btnPrevious;
    private AppCompatButton btnRandom;
    private AppCompatButton btnGoThere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewModel = (PlaceActivityViewModel) getIntent().getSerializableExtra("placeViewModel");
        viewModel = new ViewModelProvider(this).get(PlaceActivityViewModel.class);
        setContentView(R.layout.activity_place);
        setImagesObserver();
        initializeUI();
        setOnClickListeners();
    }

    private void setImagesObserver() {
//        viewModel.getImages().observe(this, new Observer<ImageResult>() {
//            @Override
//            public void onChanged(ImageResult imageResult) {
//                if (imageResult != null) {
//                    PlaceDetailsViewModel.Factory factory = new PlaceDetailsViewModel
//                            .Factory(nextPlace, imageResult.getImageData());
//                    PlaceDetailsViewModel detailsViewModel = new ViewModelProvider(
//                            PlaceActivity.this, factory).get(PlaceDetailsViewModel.class);
//                    PlaceDetails placeDetails = new PlaceDetails(PlaceActivity.this);
//                    placeDetails.setViewModel(detailsViewModel);
//                    ConstraintLayout layout = findViewById(R.id.place_main_layout);
//                    layout.addView(placeDetails);
//                }
//            }
//        });

    }

    private void initializeUI() {
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
    }

    private void goThereButtonClick(View v) {

    }
}