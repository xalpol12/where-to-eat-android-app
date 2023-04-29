package dev.xalpol12.wheretoeat.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dev.xalpol12.wheretoeat.model.entity.Place;
import dev.xalpol12.wheretoeat.model.entity.dto.PlaceRequestDTO;
import dev.xalpol12.wheretoeat.network.APIRepository;
import dev.xalpol12.wheretoeat.network.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class PlaceViewModel extends ViewModel {
    APIRepository repository;
    final MutableLiveData<List<Place>> placeList;

    @Inject
    public PlaceViewModel(APIRepository repository, MutableLiveData<List<Place>> placeList) {
        this.repository = repository;
        this.placeList = placeList;
    }


    public void makeCall(PlaceRequestDTO placeRequestDTO) {
        repository.makeCall(placeRequestDTO, placeList);
    }
}
