package dev.xalpol12.wheretoeat.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {
    public static String BASE_URL = "localhost:8080/api";        // TODO: create .properties file for storing strings

    private static Retrofit retrofit;
    public static Retrofit getRetroClient() {


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
