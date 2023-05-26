package dev.xalpol12.wheretoeat.view.placedisplay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.data.Place;


public class PlaceFragment extends Fragment {

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

    public PlaceFragment() {
        super(R.layout.fragment_place);
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
        float metersInKilometer = 1000f;
        Location location = new Location("provider");
        location.setLatitude(place.getLocation().getLat());
        location.setLongitude(place.getLocation().getLng());
        float distance = userLocation.distanceTo(location) / metersInKilometer;
        return formatCalculatedDistance(distance);
    }

    private String formatCalculatedDistance(float solution) {
        DecimalFormat format = new DecimalFormat("#.#");
        format.setMaximumFractionDigits(1);
        return format.format(solution) + " " + getString(R.string.km_away); //yes, a workaround...
    }
}