package dev.xalpol12.wheretoeat.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import dev.xalpol12.wheretoeat.model.entity.ImageResult;
import dev.xalpol12.wheretoeat.model.entity.Place;
import dev.xalpol12.wheretoeat.network.APIService;
import dev.xalpol12.wheretoeat.network.RetroInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceViewModel extends ViewModel {
    private final MutableLiveData<List<Place>> placeList;

    public PlaceViewModel() {
        placeList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Place>> getPlaceListObserver() {
        return placeList;
    }

    public void makeApiCall() {
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<List<Place>> call = apiService.getPlaceList();
        call.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                placeList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                placeList.postValue(null);
            }
        });
    }
}
