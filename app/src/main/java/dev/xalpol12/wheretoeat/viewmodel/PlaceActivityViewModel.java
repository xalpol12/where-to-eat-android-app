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
import dev.xalpol12.wheretoeat.network.dto.ImageRequestDTO;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;
import dev.xalpol12.wheretoeat.network.APIRepository;
import dev.xalpol12.wheretoeat.view.utility.ScreenDimensions;

@HiltViewModel
public class PlaceActivityViewModel extends ViewModel {
    private final APIRepository repository;
    private final PlaceRepository placeRepository;
    int currentItemIndex;

    @Inject
    public PlaceActivityViewModel(APIRepository repository, PlaceRepository placeRepository) {
        this.repository = repository;
        this.placeRepository = placeRepository;
        currentItemIndex = 0;
    }

    public void clearImageList() {
        repository.clearImageList();
    }

    public void callFindPlaces(PlaceRequestDTO placeRequestDTO) {
        repository.makeCall(placeRequestDTO);
    }

    public void callFindAllImages(ScreenDimensions dimensions) {
        List<Place> places = Objects.requireNonNull(getPlaceList().getValue());
        for (Place place : places) {
            ImageRequestDTO request = new ImageRequestDTO(place.getPhotoReference(),
                    dimensions.getHeight(), dimensions.getWidth());
            callFindImage(request);
        }
    }

    public void callFindImage(ImageRequestDTO imageRequestDTO) {
        repository.makeCall(imageRequestDTO);
    }

    public MutableLiveData<List<Place>> getPlaceList() {
        return repository.getPlaceList();
    }

    public MutableLiveData<List<ImageResult>> getImageList() {
        return repository.getImageList();
    }

    public Place getNextPlaceDetails() {
        if (currentItemIndex < Objects.requireNonNull(repository.getPlaceList().getValue()).size()) {
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

        for (ImageResult image : Objects.requireNonNull(getImageList().getValue())) {
            String photoReference = image.getPhotoReference();
            if (photoReference.equals(currentPlacePhotoReference)) {
                byte[] byteArray = Base64.decode(image.getImageData(), Base64.DEFAULT);
                return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            }
        }
        return null;
    }

    public String getCurrentPlaceId() {
        return repository.getPlaceList().getValue().get(currentItemIndex - 1).getPlaceId();
    }

    public String getCurrentPlaceName() {
        return repository.getPlaceList().getValue().get(currentItemIndex - 1).getName();
    }

    public Location getCurrentPlaceLocation() {
        return repository.getPlaceList().getValue().get(currentItemIndex - 1).getLocation();
    }

    public void setCurrentLocation(android.location.Location location) {
        repository.setCurrentDeviceLocation(location);
    }

    public android.location.Location getCurrentLocation() {
        return repository.getCurrentDeviceLocation();
    }

    public LiveData<List<PlaceEntity>> getAllPlaces() {
        return placeRepository.getAllPlaces();
    }

    public void savePlaceToDb() {
        Place place = repository.getPlaceList().getValue().get(currentItemIndex - 1);
        ImageResult image = repository.getImageList().getValue().get(currentItemIndex - 1);
        placeRepository.insertPlace(new PlaceEntity(place, image));
    }

    public void deletePlaceFromDb() {
        placeRepository.deletePlaceById(getCurrentPlaceId());
    }

    public boolean isInDatabase() {
        return placeRepository.isInDatabase(getCurrentPlaceId());
    }
}
