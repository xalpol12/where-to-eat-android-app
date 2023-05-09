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

public class MockInterceptorTest {

    @Test
    public void givenRequest_interceptReturnsPredefinedResponse() throws IOException {
        MockPlaceRequestInterceptor interceptor = new MockPlaceRequestInterceptor();

        Interceptor.Chain chain = mock(Interceptor.Chain.class);
        Request request = new Request.Builder().url("https://google.com").build();

        JsonArray successfulResponse = MockPlaceRequestInterceptor.getSampleResponse();


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
        JsonArray array = MockPlaceRequestInterceptor.getSampleResponse();
        assertNotNull(array);
    }
}