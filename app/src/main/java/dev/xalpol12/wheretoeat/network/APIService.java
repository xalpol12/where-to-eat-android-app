package dev.xalpol12.wheretoeat.network;

import java.util.List;

import dev.xalpol12.wheretoeat.data.ImageResult;
import dev.xalpol12.wheretoeat.network.dto.ImageRequestDTO;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;
import dev.xalpol12.wheretoeat.data.Place;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @POST("/places/find")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Place>> getPlaceList(@Body PlaceRequestDTO placeRequestDTO);

    @POST("/places/image")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<ImageResult> getImage(@Body ImageRequestDTO imageRequestDTO);
}
