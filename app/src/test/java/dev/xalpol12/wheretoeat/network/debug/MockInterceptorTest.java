package dev.xalpol12.wheretoeat.network.debug;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.JsonArray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import dev.xalpol12.wheretoeat.data.utility.Location;
import dev.xalpol12.wheretoeat.data.utility.PlaceType;
import dev.xalpol12.wheretoeat.data.utility.PriceLevel;
import dev.xalpol12.wheretoeat.di.APIResponseModule;
import dev.xalpol12.wheretoeat.network.APIService;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;
import mockwebserver3.MockWebServer;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MockInterceptorTest {

    @Test
    public void givenRequest_interceptReturnsPredefinedResponse() throws IOException {
        MockInterceptor interceptor = new MockInterceptor();

        Interceptor.Chain chain = mock(Interceptor.Chain.class);
        Request request = new Request.Builder().url("https://google.com").build();

        JsonArray successfulResponse = MockInterceptor.getSampleResponse();


        //when
        when(chain.request()).thenReturn(request);
        Response response = interceptor.intercept(chain);

        //then
        assertNotNull(response);
        assertEquals(200,response.code());
        assertEquals(response.body().string(), successfulResponse.toString());
    }

    @Test
    public void getSampleResponse_returnsJSONArray() {
        JsonArray array = MockInterceptor.getSampleResponse();
        assertNotNull(array);
    }
}