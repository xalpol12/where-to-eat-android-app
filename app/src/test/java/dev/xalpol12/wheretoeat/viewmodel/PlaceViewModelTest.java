//package dev.xalpol12.wheretoeat.viewmodel;
//
//import androidx.lifecycle.MutableLiveData;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.net.HttpURLConnection;
//import java.util.List;
//
//import dev.xalpol12.wheretoeat.di.AppModule;
//import dev.xalpol12.wheretoeat.model.entity.Place;
//import dev.xalpol12.wheretoeat.model.entity.dto.PlaceRequestDTO;
//import dev.xalpol12.wheretoeat.network.APIService;
//import mockwebserver3.MockResponse;
//import mockwebserver3.MockWebServer;
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//
//public class PlaceViewModelTest {
//    private final String RESOURCES_PATH = "src\\test\\java\\dev\\xalpol12\\wheretoeat\\resources\\";
//
//    private MockWebServer mockWebServer;
//    private APIService apiService;
//    private PlaceViewModel placeViewModel;
//    private MutableLiveData<List<Place>> placeList;
//
//    private Retrofit getMockRetrofit(OkHttpClient client) {
//        return new Retrofit.Builder()
//                .baseUrl(mockWebServer.url("/"))
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
//
//    private PlaceRequestDTO getMockRequest(String placeRequestPath) throws FileNotFoundException {
//        Gson gson = new Gson();
//        return gson.fromJson(
//                new FileReader(placeRequestPath),
//                PlaceRequestDTO.class);
//    }
//
//    private String getMockResponseBody(String jsonResponsePath) throws FileNotFoundException {
//        Gson gson = new Gson();
//        JsonElement json = gson.fromJson(
//                new FileReader(jsonResponsePath),
//                JsonElement.class);
//        return gson.toJson(json);
//    }
//
//    private MockResponse getMockResponse(int statusCode, String responseBody) {
//        return new MockResponse()
//                .setResponseCode(statusCode)
//                .setBody(responseBody);
//    }
//
//    private List<Place> getMockListOfPlace() {
//        return List.of(new Place("Brovaria",
//                        "id1",
//                        "Stary Rynek 73-74, Poznań",
//                        4.4f,
//                        2227,
//                        false,
//                        "test1"),
//                new Place("Matii Robata & Sushi",
//                        "id2",
//                        "Półwiejska 42, Poznań",
//                        4.7f,
//                        2548,
//                        false,
//                        "test2"));
//    }
//
//    @Before
//    public void init() {
//        mockWebServer = new MockWebServer();
//        HttpLoggingInterceptor interceptor = new AppModule().getInterceptor();
//        OkHttpClient client = new AppModule().getOkHttpClient(interceptor);
//        Retrofit retrofit = getMockRetrofit(client);
//        apiService = new AppModule().getAPIService(retrofit);
//        placeList = new MutableLiveData<>();
//        placeViewModel = new PlaceViewModel(apiService, placeList);
//    }
//
//    @Test
//    public void givenPlaceRequestDTO_placeViewModelMakesSuccessfulCall() throws FileNotFoundException {
//        // given
//        String jsonResponsePath = RESOURCES_PATH + "apiresponse\\restaurant_response_200.json";
//        String requestPath = RESOURCES_PATH + "request\\request_dto.json";
//
//        PlaceRequestDTO request = getMockRequest(requestPath);
//        String responseBody = getMockResponseBody(jsonResponsePath);
//        MockResponse response = getMockResponse(HttpURLConnection.HTTP_OK, responseBody);
//        mockWebServer.enqueue(response);
//
//        // when
//        placeViewModel.makeCall(request);
//
//
//        // then
//        assert(placeViewModel.placeList.getValue() != null);
//    }
//
//    @Test
//    public void givenIncorrectRequest_placeViewModelMakesUnsuccessfulCall() throws FileNotFoundException {
//        // given
//        String incorrectRequestPath = RESOURCES_PATH + "request\\request_dto_incorrect.json";
//        PlaceRequestDTO request = getMockRequest(incorrectRequestPath);
//
//        MockResponse response = getMockResponse(HttpURLConnection.HTTP_BAD_REQUEST, "");
//        mockWebServer.enqueue(response);
//
//        // when
//        placeViewModel.makeCall(request);
//
//        // then
//        assert(placeViewModel.placeList.getValue() == null);
//    }
//}