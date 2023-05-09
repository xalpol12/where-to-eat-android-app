package dev.xalpol12.wheretoeat.network;

import android.media.Image;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import dev.xalpol12.wheretoeat.data.ImageResult;
import dev.xalpol12.wheretoeat.data.Place;
import dev.xalpol12.wheretoeat.network.dto.ImageRequestDTO;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIRepository {
    private final APIService apiService;
    private final MutableLiveData<List<Place>> placeList;
    private final MutableLiveData<ImageResult> images;

    @Inject
    public APIRepository(APIService apiService,
                         MutableLiveData<List<Place>> placeList,
                         MutableLiveData<ImageResult> imageList) {
        this.apiService = apiService;
        this.placeList = placeList;
        this.images = imageList;
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
                if (response.isSuccessful()) {
                    images.postValue(response.body());
                } else {
                    images.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ImageResult> call, Throwable t) {
                images.postValue(null);
            }
        });
    }

    public MutableLiveData<List<Place>> getPlaceList() {
        return placeList;
    }

    public MutableLiveData<ImageResult> getImages() {
        return images;
    }
}
