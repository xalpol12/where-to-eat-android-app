package dev.xalpol12.wheretoeat.di;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dev.xalpol12.wheretoeat.data.ImageResult;
import dev.xalpol12.wheretoeat.data.Place;
import dev.xalpol12.wheretoeat.network.APIRepository;
import dev.xalpol12.wheretoeat.network.APIService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class APIResponseModule {
    public static final String BASE_URL = "https://google.com/"; // TODO: add baseURL

    @Singleton
    @Provides
    public HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Singleton
    @Provides
    public OkHttpClient getOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    public Retrofit getRetrofitClient(OkHttpClient client) {
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
    public APIRepository getAPIRepository(APIService apiService) {
        return new APIRepository(apiService);
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
}
