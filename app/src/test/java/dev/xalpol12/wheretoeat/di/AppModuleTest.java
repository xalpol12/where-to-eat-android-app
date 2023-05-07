package dev.xalpol12.wheretoeat.di;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@RunWith(JUnit4.class)
public class AppModuleTest {

    Retrofit retrofitClient;

    @Before
    public void init() {
        Interceptor interceptor = new APIResponseModule().getInterceptorDebug();
        OkHttpClient client = new APIResponseModule().getOkHttpClientDebug(interceptor);
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