package dev.xalpol12.wheretoeat.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import java.lang.reflect.Field;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dev.xalpol12.wheretoeat.data.utility.Location;
import dev.xalpol12.wheretoeat.data.utility.PlaceType;
import dev.xalpol12.wheretoeat.data.utility.PriceLevel;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;

@HiltViewModel
public class MainActivityViewModel extends ViewModel{

    PlaceRequestDTO placeRequestDTO;

    @Inject
    public MainActivityViewModel(PlaceRequestDTO placeRequestDTO) {
        this.placeRequestDTO = placeRequestDTO;
        setupVariables();
    }

    private void setupVariables() {
        placeRequestDTO.setMinPrice(PriceLevel.INEXPENSIVE);
    }

    public void setRequestLocation(double lat, double lng) {
        placeRequestDTO.setLocation(new Location(lat, lng));
    }

    public void setRequestDistance(int distance) {
        placeRequestDTO.setDistance(distance);
    }

    public void setRequestMaxPrice(int maxPrice) {
        PriceLevel[] priceLevels = PriceLevel.values();
        PriceLevel priceLevel = priceLevels[maxPrice];
        placeRequestDTO.setMaxPrice(priceLevel);
    }

    public void setPlaceType(String buttonTag) {
        PlaceType placeType = stringToPlaceType(buttonTag);
        placeRequestDTO.setPlaceType(placeType);
    }

    public PlaceRequestDTO getPlaceRequestDTO() {
        return placeRequestDTO;
    }

    public boolean areAllFieldsNotNull() throws IllegalAccessException {
        for (Field field : PlaceRequestDTO.class.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.get(placeRequestDTO) == null) {
                return false;
            }
        }
        return true;
    }

    private PlaceType stringToPlaceType(String str) {
        return PlaceType.valueOf(str.toUpperCase());
    }
}
