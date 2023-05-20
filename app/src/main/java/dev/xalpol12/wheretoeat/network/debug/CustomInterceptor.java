package dev.xalpol12.wheretoeat.network.debug;

import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import dev.xalpol12.wheretoeat.view.utility.AssetManagerWrapper;
import hilt_aggregated_deps._dev_xalpol12_wheretoeat_view_PlaceActivity_GeneratedInjector;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CustomInterceptor implements Interceptor {
    private final static String path = "app" + File.separatorChar + "src" +
            File.separatorChar + "main" + File.separatorChar + "assets" + File.separatorChar;
    private final static String image1 = "first-image.txt";
    private final static String image2 = "second-image.txt";
    private final static String image3 = "third-image.txt";
    private final static String reference1 = "ref1";
    private final static String reference2 = "ref2";
    private final static String reference3 = "ref3";
    private final static List<String> imageFiles = List.of(image1, image2, image3);
    private final static List<String> refs = List.of(reference1, reference2, reference3);
    private final AssetManagerWrapper assetManager;
    private int photoCounter;

    static class Counter {
        private static final AtomicInteger counter = new AtomicInteger(0);

        static int getValue() {
            return counter.get();
        }

        static void increment() {
            counter.incrementAndGet();
        }
    }

    @Inject
    public CustomInterceptor(AssetManagerWrapper assetManagerWrapper) {
        assetManager = assetManagerWrapper;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String uri = chain.request().url().uri().toString();
        String responseString = Objects.requireNonNull(getResponseString(uri));
        return new Response
                .Builder()
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(responseString, MediaType.parse("application/json")))
                .request(chain.request())
                .addHeader("content-type", "application/json")
                .build();
    }

    private String getResponseString(String currentUri) throws IOException {
        if (currentUri.contains("places/find")) {
            return String.valueOf(getPlaceResponse());
        }
        else if (currentUri.contains("places/image")) {
            return String.valueOf(getImageResponse());
        }
        return null;
    }

    public JsonArray getPlaceResponse() {
        JsonArray sampleResponse = new JsonArray();
        sampleResponse.add(getFirstPlaceResponse());
        sampleResponse.add(getSecondPlaceResponse());
        sampleResponse.add(getThirdPlaceResponse());
        return sampleResponse;
    }

    private JsonObject getFirstPlaceResponse() {
        JsonObject object = new JsonObject();
        object.addProperty("name", "Zielona Weranda");
        object.addProperty("placeId", "ChIJBUn2SD9bBEcRThe9iausrn8");
        object.addProperty("vicinity", "Paderewskiego 7, Poznań");
        object.addProperty("rating", 4.4f);
        object.addProperty("userRatingsTotal", 2524);
        object.addProperty("openNow", true);
        object.addProperty("photoReference", reference1);
        return object;
    }

    private JsonObject getSecondPlaceResponse() {
        JsonObject object = new JsonObject();
        object.addProperty("name", "Ptasie Radio");
        object.addProperty("placeId", "ChIJB7T44zZbBEcR1yge5KHLwYk");
        object.addProperty("vicinity", "Kościuszki 74/3, Poznań");
        object.addProperty("rating", 4.6f);
        object.addProperty("userRatingsTotal", 2635);
        object.addProperty("openNow", false);
        object.addProperty("photoReference", reference2);
        return object;
    }

    private JsonObject getThirdPlaceResponse() {
        JsonObject object = new JsonObject();
        object.addProperty("name", "Piece of Cake");
        object.addProperty("placeId", "ChIJ3S-G0UBbBEcREu9sx0ZMlKk");
        object.addProperty("vicinity", "Święty Wojciech 27, Poznań");
        object.addProperty("rating", 4.7f);
        object.addProperty("userRatingsTotal", 428);
        object.addProperty("openNow", true);
        object.addProperty("photoReference", reference3);
        return object;
    }

    public synchronized JsonObject getImageResponse() throws IOException {
        JsonObject response = getImageResponse(imageFiles.get(photoCounter), refs.get(photoCounter));
        if (photoCounter < imageFiles.size() - 1) {
            photoCounter++;
        } else {
            photoCounter = 0;
        }
        return response;
    }

    private JsonObject getImageResponse(String image, String photoReference) throws IOException {
        JsonObject object = new JsonObject();
        InputStream inputStream = assetManager.openAssetFile(image);
        object.addProperty("imageData", convertToString(inputStream));
        object.addProperty("photoReference", photoReference);
        return object;
    }

    private String convertToString(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("");
        }
        reader.close();
        return stringBuilder.toString();
    }
}
