package dev.xalpol12.wheretoeat.view.place;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.data.Place;
import lombok.Getter;
import lombok.Setter;

public class PlaceFragment extends Fragment {
    private final float METERS_IN_KILOMETER = 1000f;

    private Place place;
    private Bitmap photo;
    private Location userLocation;
    private ImageView imageView;
    private TextView placeName;
    private TextView rating;
    private TextView ratingCount;
    private TextView address;
    private TextView distance;
    private TextView openNow;
    private final int defaultPhoto = R.drawable.zielona_weranda;

    private ImageView saveButton;
    @Setter @Getter
    private View.OnClickListener saveButtonCallback;
    private boolean isInitializedFromRecyclerView = false; //not very clean-code of me...

    private AppCompatButton btnPrevious;
    @Setter
    private View.OnClickListener previousButtonCallback;

    private AppCompatButton btnRandom;
    @Setter
    private View.OnClickListener randomButtonCallback;

    private AppCompatButton btnGoThere;
    @Setter
    private View.OnClickListener goThereButtonCallback;

    public PlaceFragment() {
        super(R.layout.fragment_place);
    }

    public PlaceFragment(Place place, Bitmap photo, Location location) {
        super(R.layout.fragment_place);
        this.place = place;
        this.photo = photo;
        userLocation = location;
        isInitializedFromRecyclerView = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        initializeUI(view);
        setSaveButtonVisibility();
        setOnClickListeners();
        updateUI();
        return view;
    }

    public void changePlace(Place place) {
        this.place = place;
    }

    public void changePhoto(Bitmap photo) {
        if (photo != null) {
        this.photo = photo;
        } else {
            this.photo = BitmapFactory.decodeResource(getContext().getResources(), defaultPhoto);
        }
    }

    public void setUserLocation(Location location) {
        userLocation = location;
    }

    private void initializeUI(View view) {
        imageView = view.findViewById(R.id.place_image);
        placeName = view.findViewById(R.id.place_name);
        rating = view.findViewById(R.id.rating);
        ratingCount = view.findViewById(R.id.rating_count);
        address = view.findViewById(R.id.address);
        distance = view.findViewById(R.id.distance);
        openNow = view.findViewById(R.id.open_now);

        saveButton = view.findViewById(R.id.place_save_button);

        btnPrevious = view.findViewById(R.id.previous_button);
        btnRandom = view.findViewById(R.id.random_button);
        btnGoThere = view.findViewById(R.id.go_there_button);
    }

    private void setSaveButtonVisibility() {
        if (isInitializedFromRecyclerView) {
            saveButton.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
            btnRandom.setVisibility(View.GONE);
        }
    }

    private void setOnClickListeners() {
        saveButton.setOnClickListener(v -> saveButtonCallback.onClick(v));

        btnPrevious.setOnClickListener(v -> previousButtonCallback.onClick(v));

        btnRandom.setOnClickListener(v -> randomButtonCallback.onClick(v));

        btnGoThere.setOnClickListener(v -> goThereButtonCallback.onClick(v));
    }

    public void updateUI() {
        imageView.setImageBitmap(photo);
        placeName.setText(place.getName());
        rating.setText(String.valueOf(place.getRating()));
        ratingCount.setText(String.valueOf(place.getUserRatingsTotal()));
        address.setText(place.getVicinity());
        distance.setText(calculateDistance());

        if (place.isOpenNow()) {
            openNow.setText(R.string.open_now);
            openNow.setTextColor(getResources().getColor(R.color.accent));
        } else {
            openNow.setText(R.string.closed_now);
            openNow.setTextColor(getResources().getColor(R.color.secondary_30_tint));
        }
    }

    private String calculateDistance() {
        Location location = new Location("provider");
        location.setLatitude(place.getLocation().getLat());
        location.setLongitude(place.getLocation().getLng());
        float distance = userLocation.distanceTo(location) / METERS_IN_KILOMETER;
        return formatCalculatedDistance(distance);
    }

    private String formatCalculatedDistance(float solution) {
        DecimalFormat format = new DecimalFormat("#.#");
        format.setMaximumFractionDigits(1);
        return format.format(solution) + " " + getString(R.string.km_away); //yes, a workaround...
    }

    public void setColorFilterOnSaveButton(int color) {
        saveButton.setColorFilter(color);
    }
}