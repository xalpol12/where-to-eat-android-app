package dev.xalpol12.wheretoeat.network;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import dev.xalpol12.wheretoeat.di.AppModule;
import dev.xalpol12.wheretoeat.data.Place;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(JUnit4.class)
public class APIServiceTest extends TestCase {
    private final String RESOURCES_PATH = "src\\test\\java\\dev\\xalpol12\\wheretoeat\\resources\\";

    private MockWebServer mockWebServer;
    private APIService apiService;

    private Retrofit getMockRetrofitInstance(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private List<Place> getMockListOfPlace() {
        return List.of(new Place("Brovaria",
                                "id1",
                                "Stary Rynek 73-74, Poznań",
                                4.4f,
                                2227,
                                false,
                            "test1"),
                new Place("Matii Robata & Sushi",
                        "id2",
                        "Półwiejska 42, Poznań",
                        4.7f,
                        2548,
                        false,
                        "test2"));
    }

    private PlaceRequestDTO getMockRequest(String placeRequestPath) throws FileNotFoundException {
        Gson gson = new Gson();
        return gson.fromJson(
                new FileReader(placeRequestPath),
                PlaceRequestDTO.class);
    }

    private String getMockResponseBody(String jsonResponsePath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonElement json = gson.fromJson(
                new FileReader(jsonResponsePath),
                JsonElement.class);
        return gson.toJson(json);
    }

    private MockResponse getMockResponse(String responseBody) {
        return new MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(responseBody);
    }


    @Before
    public void init() {
        mockWebServer = new MockWebServer();
        HttpLoggingInterceptor interceptor = new AppModule().getInterceptor();
        OkHttpClient client = new AppModule().getOkHttpClient(interceptor);
        Retrofit retrofit = getMockRetrofitInstance(client);
        apiService = new AppModule().getAPIService(retrofit);
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void given200Response_shouldFetchPlacesCorrectly() throws IOException {
        // given
        String jsonResponsePath = RESOURCES_PATH + "apiresponse\\restaurant_response_200.json";
        String requestPath = RESOURCES_PATH + "request\\request_dto.json";

        String responseBody = getMockResponseBody(jsonResponsePath);
        MockResponse response = getMockResponse(responseBody);
        mockWebServer.enqueue(response);
        List<Place> expected = getMockListOfPlace();
        PlaceRequestDTO placeRequestDTO = getMockRequest(requestPath);


        // when
        MutableLiveData<List<Place>> requested = new MutableLiveData<>(
                apiService.getPlaceList(placeRequestDTO).execute().body());


        //then
        assertEquals(expected, requested.getValue());
    }
}