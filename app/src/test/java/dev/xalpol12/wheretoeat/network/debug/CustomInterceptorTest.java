package dev.xalpol12.wheretoeat.network.debug;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.JsonArray;

import org.junit.Test;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomInterceptorTest {

    @Test
    public void givenPlaceRequest_interceptReturnsPredefinedPlaceResponse() throws IOException {
        CustomInterceptor interceptor = new CustomInterceptor();

        Interceptor.Chain chain = mock(Interceptor.Chain.class);
        Request request = new Request
                .Builder()
                .url("https://where-to-eat.up.railway.app/places/find")
                .build();

        JsonArray successfulResponse = CustomInterceptor.getPlaceResponse();


        //when
        when(chain.request()).thenReturn(request);
        Response response = interceptor.intercept(chain);

        //then
        assertNotNull(response);
        assertEquals(200,response.code());
        assertEquals(response.body().string(), successfulResponse.toString());
    }

    @Test
    public void getPlaceResponse_returnsJSONArray() {
        JsonArray array = CustomInterceptor.getPlaceResponse();
        assertNotNull(array);
        assertEquals(array.getClass(), JsonArray.class);
    }

    @Test
    public void givenImageRequest_interceptReturnsPredefinedImageResponse() throws IOException {
        CustomInterceptor interceptor = new CustomInterceptor();

        Interceptor.Chain chain = mock(Interceptor.Chain.class);
        Request request = new Request
                .Builder()
                .url("https://where-to-eat.up.railway.app/places/image")
                .build();

        JsonArray successfulResponse = CustomInterceptor.getImageResponse();


        //when
        when(chain.request()).thenReturn(request);
        Response response = interceptor.intercept(chain);

        //then
        assertNotNull(response);
        assertEquals(200,response.code());
        assertEquals(response.body().string(), successfulResponse.toString());
    }

    @Test
    public void getImageResponse_returnsJSONArray() throws IOException {
        JsonArray array = CustomInterceptor.getImageResponse();
        assertNotNull(array);
        assertEquals(array.getClass(), JsonArray.class);
    }
}