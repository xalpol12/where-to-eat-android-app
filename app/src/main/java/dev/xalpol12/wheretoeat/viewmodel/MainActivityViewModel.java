package dev.xalpol12.wheretoeat.viewmodel;

import androidx.lifecycle.ViewModel;

import java.lang.reflect.Field;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dev.xalpol12.wheretoeat.data.utility.Location;
import dev.xalpol12.wheretoeat.data.utility.PlaceType;
import dev.xalpol12.wheretoeat.data.utility.PriceLevel;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {

    PlaceRequestDTO placeRequestDTO;

    @Inject
    public MainActivityViewModel(PlaceRequestDTO placeRequestDTO) {
        this.placeRequestDTO = placeRequestDTO;
    }

    public void setRequestLocation(Location location) {
        placeRequestDTO.setLocation(location);
    }

    public void setRequestDistance(int distance) {
        placeRequestDTO.setDistance(distance);
    }

    public void setRequestMinPrice(PriceLevel minPrice) {
        placeRequestDTO.setMinPrice(minPrice);
    }

    public void setRequestMaxPrice(PriceLevel maxPrice) {
        placeRequestDTO.setMaxPrice(maxPrice);
    }

    public void setRequestPlaceType(PlaceType type) {
        placeRequestDTO.setType(type);
    }

    public void sendPlaceRequestDTO() {
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
}
