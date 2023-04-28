package dev.xalpol12.wheretoeat.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dev.xalpol12.wheretoeat.model.entity.Place;
import dev.xalpol12.wheretoeat.model.entity.dto.PlaceRequestDTO;
import dev.xalpol12.wheretoeat.network.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class PlaceViewModel extends ViewModel {
    APIService apiService;
    private final MutableLiveData<List<Place>> placeList;

    public PlaceViewModel() {
        placeList = new MutableLiveData<>();
    }


    public void getPlaceList(PlaceRequestDTO placeRequestDTO) {
        Call<List<Place>> call = apiService.getPlaceList(placeRequestDTO);
        call.enqueue(new Callback<List<Place>>() {
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
}
