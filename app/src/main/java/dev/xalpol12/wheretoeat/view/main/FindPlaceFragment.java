package dev.xalpol12.wheretoeat.view.main;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.view.main.MainActivity;
import dev.xalpol12.wheretoeat.viewmodel.MainActivityViewModel;
import dev.xalpol12.wheretoeat.viewmodel.PlaceActivityViewModel;

@AndroidEntryPoint
public class FindPlaceFragment extends Fragment {
    private MainActivityViewModel mainViewModel;
    private PlaceActivityViewModel placeViewModel;

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

    public FindPlaceFragment() {
        super(R.layout.fragment_find_place);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_place, container, false);
        initializeUI(view);
        setOnClickListeners();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        placeViewModel = new ViewModelProvider(requireActivity()).get(PlaceActivityViewModel.class);
    }

    private void initializeUI(View view) {
        btnFindLocation = view.findViewById(R.id.find_location_button);
        btnFindPlace = view.findViewById(R.id.find_place_button);
        rangeSlider = view.findViewById(R.id.range_slider);

        for (int id : priceButtonIds) {
            priceButtons.add(view.findViewById(id));
        }

        for (int id : placeButtonIds) {
            placeButtons.add(view.findViewById(id));
        }

        primaryColor = getResources().getColor(R.color.primary);
        accentColor = getResources().getColor(R.color.accent);
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
        ((MainActivity) requireActivity()).getLastLocation();

//        mainViewModel.setRequestLocation(52.39f, 16.94f);  //Uncomment for debug purposes
//        android.location.Location location = new Location("provider");
//        location.setLatitude(56.39f);
//        location.setLongitude(10.94f);
//        placeViewModel.setCurrentLocation(location);

        v.setAlpha(1.f);
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
