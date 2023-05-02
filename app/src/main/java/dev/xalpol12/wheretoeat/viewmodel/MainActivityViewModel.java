package dev.xalpol12.wheretoeat.viewmodel;

import androidx.lifecycle.ViewModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dev.xalpol12.wheretoeat.data.utility.Location;
import dev.xalpol12.wheretoeat.data.utility.PlaceType;
import dev.xalpol12.wheretoeat.data.utility.PriceLevel;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {

    PlaceRequestDTO placeRequestDTO;

    List<PlaceType> placeTypeTempList;

    @Inject
    public MainActivityViewModel(PlaceRequestDTO placeRequestDTO) {
        this.placeRequestDTO = placeRequestDTO;
        this.placeTypeTempList = new ArrayList<>();
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

    public void addToPlaceList(String buttonTag) {
        PlaceType placeType = stringToPlaceType(buttonTag);
        placeTypeTempList.add(placeType);
    }

    public void deleteFromPlaceList(String buttonTag) {
        PlaceType placeType = stringToPlaceType(buttonTag);
        placeTypeTempList.remove(placeType);
    }

    public void sendPlaceRequestDTO() {
        placeRequestDTO.setType(placeTypeTempList);
    }

    public boolean isPlaceTypeAlreadyAdded(String buttonTag) {
        PlaceType placeType = stringToPlaceType(buttonTag);
        return placeTypeTempList.contains(placeType);
    }

    public boolean areAllFieldsNotNull() throws IllegalAccessException {
        for (Field field : PlaceRequestDTO.class.getDeclaredFields()) {
//            field.setAccessible(true);
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
