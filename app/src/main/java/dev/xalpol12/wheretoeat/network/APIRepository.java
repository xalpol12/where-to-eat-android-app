package dev.xalpol12.wheretoeat.network;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dev.xalpol12.wheretoeat.data.ImageResult;
import dev.xalpol12.wheretoeat.data.Place;
import dev.xalpol12.wheretoeat.network.dto.ImageRequestDTO;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;
import dev.xalpol12.wheretoeat.view.utility.ScreenDimensions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIRepository {
    private final APIService apiService;
    private final MutableLiveData<List<Place>> placeList;
    private final MutableLiveData<List<ImageResult>> imageList;
    private Location currentDeviceLocation;

    @Inject
    public APIRepository(APIService apiService,
                         MutableLiveData<List<Place>> placeList,
                         MutableLiveData<List<ImageResult>> imageList) {
        this.apiService = apiService;
        this.placeList = placeList;
        this.imageList = imageList;
    }

    public void makeCall(PlaceRequestDTO placeRequestDTO) {
        Call<List<Place>> call = apiService.getPlaceList(placeRequestDTO);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                if (response.isSuccessful()) {
                    placeList.postValue(response.body());
                } else {
                    placeList.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                placeList.postValue(null);
            }
        });
    }

    public void makeCall(ImageRequestDTO imageRequestDTO) {
        Call<ImageResult> call = apiService.getImage(imageRequestDTO);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ImageResult> call, Response<ImageResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (imageList.getValue() != null) {
                        List<ImageResult> currentList = imageList.getValue();
                        currentList.add(response.body());
                        imageList.setValue(currentList);
                    } else {        //if MutableLiveData is empty create new list and insert response image
                        imageList.setValue(new ArrayList<>(Arrays.asList(response.body())));
                    }
                }
            }
            @Override
            public void onFailure(Call<ImageResult> call, Throwable t) {
                //left empty on purpose, when call failed don't set anything, later if no image was found
                //for this specific id, then display default image
            }
        });
    }

    public void callFindAllImages(ScreenDimensions dimensions) {
        List<Place> places = Objects.requireNonNull(placeList.getValue());
        for (Place place : places) {
            ImageRequestDTO request = new ImageRequestDTO(place.getPhotoReference(),
                    dimensions.getHeight(), dimensions.getWidth());
            callFindImage(request);
        }
    }

    private void callFindImage(ImageRequestDTO imageRequestDTO) {
        makeCall(imageRequestDTO);
    }

    public MutableLiveData<List<Place>> getPlaceList() {
        return placeList;
    }

    public MutableLiveData<List<ImageResult>> getImageList() {
        return imageList;
    }

    public void clearImageList() { //TODO: fix this
        imageList.setValue(null);
    }

    public void setCurrentDeviceLocation(Location location) {
        currentDeviceLocation = location;
    }

    public Location getCurrentDeviceLocation() {
        return currentDeviceLocation;
    }

}
