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
import dev.xalpol12.wheretoeat.model.entity.Place;
import dev.xalpol12.wheretoeat.model.entity.dto.PlaceRequestDTO;
import dev.xalpol12.wheretoeat.viewmodel.PlaceViewModel;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(JUnit4.class)
public class APIServiceTest extends TestCase {

    private final String RESOURCES_PATH = "src\\test\\java\\dev\\xalpol12\\wheretoeat\\resources\\";
    private final String JSON_RESPONSE_PATH = RESOURCES_PATH + "apiresponse\\restaurant_response_200.json";
    private final String REQUEST_PATH = RESOURCES_PATH + "request\\request_dto.json";

    private MockWebServer mockWebServer;
    private APIService apiService;

    public Retrofit getMockRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public List<Place> getListOfPlace() {
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

    public PlaceRequestDTO getPlaceRequest() throws FileNotFoundException {
        Gson gson = new Gson();
        return gson.fromJson(
                new FileReader(REQUEST_PATH),
                PlaceRequestDTO.class);
    }

    public String readResponseBody() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonElement json = gson.fromJson(
                new FileReader(JSON_RESPONSE_PATH),
                JsonElement.class);
        return gson.toJson(json);
    }


    @Before
    public void init() {
        mockWebServer = new MockWebServer();
        HttpLoggingInterceptor interceptor = new AppModule().getInterceptor();
        OkHttpClient client = new AppModule().getOkHttpClient(interceptor);
        Retrofit retrofit = getMockRetrofit(client);
        apiService = new AppModule().getAPIService(retrofit);
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void given200Response_shouldFetchPlacesCorrectly() throws IOException {
        // given
        String responseBody = readResponseBody();
        MockResponse response = new MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(responseBody);
        mockWebServer.enqueue(response);

        List<Place> expected = getListOfPlace();

        PlaceRequestDTO placeRequestDTO = getPlaceRequest();

        // when
        MutableLiveData<List<Place>> requested = new MutableLiveData<>(
                apiService.getPlaceList(placeRequestDTO).execute().body());

        //then
        assertEquals(expected, requested.getValue());
    }
}