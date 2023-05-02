package dev.xalpol12.wheretoeat.network;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import dev.xalpol12.wheretoeat.data.Place;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIRepository {
    private APIService apiService;

    @Inject
    public APIRepository(APIService apiService) {
        this.apiService = apiService;
    }

    public void makeCall(PlaceRequestDTO placeRequestDTO, MutableLiveData<List<Place>> data) {
        Call<List<Place>> call = apiService.getPlaceList(placeRequestDTO);
        call.enqueue(new Callback<List<Place>>() {
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
}
