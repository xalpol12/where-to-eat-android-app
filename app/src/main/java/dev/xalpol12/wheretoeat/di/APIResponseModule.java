package dev.xalpol12.wheretoeat.di;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dev.xalpol12.wheretoeat.data.ImageResult;
import dev.xalpol12.wheretoeat.data.Place;
import dev.xalpol12.wheretoeat.network.APIRepository;
import dev.xalpol12.wheretoeat.network.APIService;
import dev.xalpol12.wheretoeat.network.debug.MockInterceptor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class APIResponseModule {
    public static final String BASE_URL = "https://where-to-eat.up.railway.app/";

    @Singleton
    @Provides
    @Named("InterceptorDebug")
    public Interceptor getInterceptorDebug() {
        return new MockInterceptor();
    }

    @Singleton
    @Provides
    @Named("OkHttpClientDebug")
    public OkHttpClient getOkHttpClientDebug(@Named("InterceptorDebug")
                                        Interceptor interceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Singleton
    @Provides
    @Named("InterceptorProd")
    public HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Singleton
    @Provides
    @Named("OkHttpClientProd")
    public OkHttpClient getOkHttpClient(@Named("InterceptorProd")
                                            Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .build();
    }




    @Singleton
    @Provides
    @Inject
    public Retrofit getRetrofitClient(@Named("OkHttpClientDebug")
                                          OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public APIService getAPIService(Retrofit retrofitClient) {
        return retrofitClient.create(APIService.class);
    }

    @Singleton
    @Provides
    public MutableLiveData<List<Place>> getPlaceList() {
        return new MutableLiveData<>();
    }

    @Singleton
    @Provides
    public MutableLiveData<ImageResult> getImageResult() {
        return new MutableLiveData<>();
    }

    @Singleton
    @Provides
    public APIRepository getAPIRepository(APIService apiService,
                                          MutableLiveData<List<Place>> placeList,
                                          MutableLiveData<ImageResult> images) {
        return new APIRepository(apiService, placeList, images);
    }
}