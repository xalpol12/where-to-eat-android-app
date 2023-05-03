package dev.xalpol12.wheretoeat.di;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

@RunWith(JUnit4.class)
public class AppModuleTest {

    Retrofit retrofitClient;

    @Before
    public void init() {
        HttpLoggingInterceptor interceptor = new APIResponseModule().getInterceptor();
        OkHttpClient client = new APIResponseModule().getOkHttpClient(interceptor);
        retrofitClient = new APIResponseModule().getRetrofitClient(client);
    }


    @Test
    public void givenRetrofitClient_whenBaseUrlSpecified_thenRetrofitClientUrlEqualsBaseUrl() {
        // given
        String specifiedUrl = APIResponseModule.BASE_URL;

        // when
        String baseUrl = retrofitClient.baseUrl().url().toString();

        // then
        assert(baseUrl.equals(specifiedUrl));
    }
}