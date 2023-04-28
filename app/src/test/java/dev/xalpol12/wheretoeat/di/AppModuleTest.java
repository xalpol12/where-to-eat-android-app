package dev.xalpol12.wheretoeat.di;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dev.xalpol12.App;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

@HiltAndroidTest
//@RunWith(JUnit4.class)
public class AppModuleTest {

    Retrofit retrofitClient;

    @Before
    public void init() {
        HttpLoggingInterceptor interceptor = new AppModule().getInterceptor();
        OkHttpClient client = new AppModule().getOkHttpClient(interceptor);
        retrofitClient = new AppModule().getRetrofitClient(client);
    }


    @Test
    public void givenRetrofitClient_whenBaseUrlSpecified_thenRetrofitClientUrlEqualsBaseUrl() {
        // given
        String specifiedUrl = AppModule.BASE_URL;

        // when
        String baseUrl = retrofitClient.baseUrl().url().toString();

        // then
        assert(baseUrl.equals(specifiedUrl));
    }
}