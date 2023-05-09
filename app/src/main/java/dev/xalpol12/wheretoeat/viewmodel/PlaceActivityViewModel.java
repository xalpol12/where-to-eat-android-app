package dev.xalpol12.wheretoeat.viewmodel;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dev.xalpol12.wheretoeat.data.ImageResult;
import dev.xalpol12.wheretoeat.data.Place;
import dev.xalpol12.wheretoeat.network.dto.ImageRequestDTO;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;
import dev.xalpol12.wheretoeat.network.APIRepository;

@HiltViewModel
public class PlaceActivityViewModel extends ViewModel {
    APIRepository repository;
    int currentItemIndex;

    @Inject
    public PlaceActivityViewModel(APIRepository repository) {
        this.repository = repository;
        currentItemIndex = 0;
    }

    public void callFindPlaces(PlaceRequestDTO placeRequestDTO) {
        repository.makeCall(placeRequestDTO);
    }

    public void callFindImage(ImageRequestDTO imageRequestDTO) {
        repository.makeCall(imageRequestDTO);
    }

    public MutableLiveData<List<Place>> getPlaceList() {
        return repository.getPlaceList();
    }

    public MutableLiveData<ImageResult> getImages() {
        return repository.getImages();
    }

    public Place getNextPlaceDetails() {
        if (currentItemIndex < Objects.requireNonNull(repository.getPlaceList().getValue()).size()) {
            currentItemIndex++;
        } else currentItemIndex = 1;
        return repository.getPlaceList().getValue().get(currentItemIndex - 1);
    }

//    public Bitmap getNextImage() {
////        repository.getImages.getValue() TODO: implement getNextImage() instead of mock Bitmap
////        return repository.getImages().getValue();
//    }
}
