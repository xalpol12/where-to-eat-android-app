package dev.xalpol12.wheretoeat.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import dagger.hilt.android.AndroidEntryPoint;
import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.viewmodel.PlaceDetailsViewModel;

@AndroidEntryPoint
public class PlaceDetails extends ConstraintLayout {

    private PlaceDetailsViewModel viewModel;
    private ImageView image;
    private TextView placeName;
    private TextView rating;
    private TextView ratingCount;
    private TextView address;
    private TextView distance;
    private TextView openNow;

    public PlaceDetails(@NonNull Context context) {
        super(context);
        init();
    }

    public PlaceDetails(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlaceDetails(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PlaceDetails(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.place_details_card, this, true);
        initializeUI();
        setUIElements();
    }

    private void initializeUI() {
        image = findViewById(R.id.place_image);
        placeName = findViewById(R.id.place_name);
        rating = findViewById(R.id.rating);
        ratingCount = findViewById(R.id.rating_count);
        address = findViewById(R.id.address);
        distance = findViewById(R.id.distance);
        openNow = findViewById(R.id.open_now);
    }

    public void setViewModel(PlaceDetailsViewModel viewModel) {
        this.viewModel = viewModel;
        setUIElements();
    }

    private void setUIElements() {
        image.setImageResource(R.drawable.img_default_place_photo);
        placeName.setText(viewModel.getPlaceName());
        rating.setText(String.valueOf(viewModel.getPlaceRating()));
        ratingCount.setText(String.valueOf(viewModel.getPlaceRatingsTotal()));
        address.setText(viewModel.getPlaceVicinity());
        distance.setText("1 km away"); //TODO: fetch distance from place

        if (viewModel.isPlaceOpenNow()) {
            openNow.setText(R.string.open_now);
            openNow.setTextColor(getResources().getColor(R.color.accent)); //TODO: color dependant on theme
        } else {
            openNow.setText(R.string.closed_now);
            openNow.setTextColor(getResources().getColor(R.color.secondary_30_tint));
        }
    }
}