package dev.xalpol12.wheretoeat.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dev.xalpol12.wheretoeat.data.utility.Location;
import dev.xalpol12.wheretoeat.data.utility.PlaceType;
import dev.xalpol12.wheretoeat.data.utility.PriceLevel;
import dev.xalpol12.wheretoeat.database.PlaceRepository;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;
import dev.xalpol12.wheretoeat.view.utility.AssetManagerWrapper;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {

    PlaceRepository placeRepository;
    PlaceRequestDTO placeRequestDTO;
    AssetManagerWrapper assetManager;

    @Inject
    public MainActivityViewModel(PlaceRepository placeRepository,
                                 PlaceRequestDTO placeRequestDTO,
                                 AssetManagerWrapper assetManager) {
        this.placeRepository = placeRepository;
        this.placeRequestDTO = placeRequestDTO;
        this.assetManager = assetManager;
        setupVariables();
    }

    private void setupVariables() {
        placeRequestDTO.setMinPrice(PriceLevel.INEXPENSIVE);
    }

    public void setAssetManagerContext(Context context) {
        assetManager.setContext(context);
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

    public void insertPlace(PlaceEntity place) {
        placeRepository.insertPlace(place);
    }

    public void insertAllPlaces(PlaceEntity... places) {
        placeRepository.insertAllPlaces(places);
    }

    public LiveData<List<PlaceEntity>> getAllPlaces() {
        return placeRepository.getAllPlaces();
    }

    public void deletePlaceById(String id) {
        placeRepository.deletePlaceById(id);
    }
}
