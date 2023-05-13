package dev.xalpol12.wheretoeat.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.data.Place;


public class PlaceFragment extends Fragment {

    private Place place;

    private Bitmap image;

    private ImageView imageView;
    private TextView placeName;
    private TextView rating;
    private TextView ratingCount;
    private TextView address;
    private TextView distance;
    private TextView openNow;

    private final int firstPhoto = R.drawable.zielona_weranda;
    private final int secondPhoto = R.drawable.ptasie_radio;
    private final int thirdPhoto = R.drawable.piece_of_cake;
    private int currentPhoto = firstPhoto;

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
        changeCurrentPhoto();
        imageView.setImageResource(currentPhoto);
        placeName.setText(place.getName());
        rating.setText(String.valueOf(place.getRating()));
        ratingCount.setText(String.valueOf(place.getUserRatingsTotal()));
        address.setText(place.getVicinity());
        distance.setText("1 km away"); //TODO: fetch distance from place

        if (place.isOpenNow()) {
            openNow.setText(R.string.open_now);
            openNow.setTextColor(getResources().getColor(R.color.accent)); //TODO: color dependant on theme
        } else {
            openNow.setText(R.string.closed_now);
            openNow.setTextColor(getResources().getColor(R.color.secondary_30_tint));
        }
    }

    private void changeCurrentPhoto() {
        switch (place.getName()) {
            default:
                currentPhoto = firstPhoto;
                break;
            case "Ptasie Radio":
                currentPhoto = secondPhoto;
                break;
            case "Piece of Cake":
                currentPhoto = thirdPhoto;
                break;
        }
    }
}