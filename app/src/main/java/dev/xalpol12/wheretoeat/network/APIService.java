package dev.xalpol12.wheretoeat.network;

import java.util.List;

import dev.xalpol12.wheretoeat.model.entity.Place;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @POST("/find")
    Call<List<Place>> getPlaceList();
}
