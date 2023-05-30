package dev.xalpol12.wheretoeat.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dev.xalpol12.wheretoeat.data.ImageResult;
import dev.xalpol12.wheretoeat.data.Place;
import dev.xalpol12.wheretoeat.data.utility.Location;
import dev.xalpol12.wheretoeat.database.PlaceRepository;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;
import dev.xalpol12.wheretoeat.network.APIRepository;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;
import dev.xalpol12.wheretoeat.view.utility.ImageDecoder;
import dev.xalpol12.wheretoeat.view.utility.ScreenDimensions;

@HiltViewModel
public class PlaceActivityViewModel extends ViewModel {
    private final APIRepository apiRepository;
    private final PlaceRepository placeRepository;
    int currentItemIndex;

    @Inject
    public PlaceActivityViewModel(APIRepository apiRepository, PlaceRepository placeRepository) {
        this.apiRepository = apiRepository;
        this.placeRepository = placeRepository;
        currentItemIndex = 0;
    }

    public void clearImageList() {
        apiRepository.clearImageList();
    }

    public void callFindPlaces(PlaceRequestDTO placeRequestDTO) {
        apiRepository.makeCall(placeRequestDTO);
    }

    public void callFindAllImages(ScreenDimensions dimensions) {
        apiRepository.callFindAllImages(dimensions);
    }

    public MutableLiveData<List<Place>> getPlaceList() {
        return apiRepository.getPlaceList();
    }

    public MutableLiveData<List<ImageResult>> getImageList() {
        return apiRepository.getImageList();
    }

    public Place getNextPlaceDetails() {
        if (currentItemIndex < Objects.requireNonNull(apiRepository.getPlaceList().getValue()).size()) {
            currentItemIndex++;
        } else currentItemIndex = 1;
        return getPlaceList().getValue().get(currentItemIndex - 1);
    }

    public Place getPreviousPlaceDetails() {
        if (currentItemIndex != 1) currentItemIndex--;
        else {
            currentItemIndex = Objects.requireNonNull(
                    getPlaceList().getValue()).size();
        }
        return getPlaceList().getValue().get(currentItemIndex - 1);
    }

    public Bitmap getCorrespondingImage() {
        String currentPlacePhotoReference = getPlaceList().getValue().get(currentItemIndex -1).getPhotoReference();
        Bitmap bitmap = null;

        for (ImageResult image : Objects.requireNonNull(getImageList().getValue())) {
            String photoReference = image.getPhotoReference();
            if (photoReference.equals(currentPlacePhotoReference)) {
                bitmap = ImageDecoder.decode(image.getImageData());
            }
        }
        return bitmap;
    }

    public String getCurrentPlaceId() {
        return apiRepository.getPlaceList().getValue().get(currentItemIndex - 1).getPlaceId();
    }

    public String getCurrentPlaceName() {
        return apiRepository.getPlaceList().getValue().get(currentItemIndex - 1).getName();
    }

    public Location getCurrentPlaceLocation() {
        return apiRepository.getPlaceList().getValue().get(currentItemIndex - 1).getLocation();
    }

    public void setCurrentLocation(android.location.Location location) {
        apiRepository.setCurrentDeviceLocation(location);
    }

    public android.location.Location getCurrentLocation() {
        return apiRepository.getCurrentDeviceLocation();
    }

    public LiveData<List<PlaceEntity>> getAllPlaces() {
        return placeRepository.getAllPlaceEntities();
    }

    public void savePlaceToDb() {
        Place place = apiRepository.getPlaceList().getValue().get(currentItemIndex - 1);
        ImageResult image = apiRepository.getImageList().getValue().get(currentItemIndex - 1);
        placeRepository.insertAllPlaces(new PlaceEntity(place, image));
    }

    public void deletePlaceFromDb(String placeId) {
        placeRepository.deletePlaceById(placeId);
    }

    public boolean isInDatabase() {
        return placeRepository.isInDatabase(getCurrentPlaceId());
    }
}
