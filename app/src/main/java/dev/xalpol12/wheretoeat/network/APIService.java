package dev.xalpol12.wheretoeat.network;

import java.util.List;

import dev.xalpol12.wheretoeat.model.entity.dto.PlaceRequestDTO;
import dev.xalpol12.wheretoeat.model.entity.Place;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @POST("/find")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Place>> getPlaceList(@Body PlaceRequestDTO placeRequestDTO);
}
