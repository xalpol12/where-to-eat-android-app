package dev.xalpol12.wheretoeat.network;

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

    @Inject
    public APIRepository(APIService apiService) {
        this.apiService = apiService;
    }

    public void makeCall(PlaceRequestDTO placeRequestDTO, MutableLiveData<List<Place>> data) {
        Call<List<Place>> call = apiService.getPlaceList(placeRequestDTO);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                } else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                data.postValue(null);
            }
        });
    }

    public void makeCall(ImageRequestDTO imageRequestDTO, MutableLiveData<ImageResult> imgData) {
        Call<ImageResult> call = apiService.getImage(imageRequestDTO);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ImageResult> call, Response<ImageResult> response) {
                if (response.isSuccessful()) {
                    imgData.postValue(response.body());
                } else {
                    imgData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ImageResult> call, Throwable t) {
                imgData.postValue(null);
            }
        });
    }
}
