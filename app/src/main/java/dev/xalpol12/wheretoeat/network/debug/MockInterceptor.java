package dev.xalpol12.wheretoeat.network.debug;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;

import dev.xalpol12.wheretoeat.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MockInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String uri = chain.request().url().uri().toString();
        String responseString = String.valueOf(getSampleResponse());
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

    public static JsonArray getSampleResponse() {
        JsonArray sampleResponse = new JsonArray();
        JsonObject object = new JsonObject();
        object.addProperty("name", "Zielona Weranda");
        object.addProperty("placeId", "ChIJBUn2SD9bBEcRThe9iausrn8");
        object.addProperty("vicinity", "Paderewskiego 7, Poznań");
        object.addProperty("rating", 4.4f);
        object.addProperty("userRatingsTotal", 2524);
        object.addProperty("openNow", true);
        object.addProperty("photoReference", "AZose0km65w3gBpnFu7nDoPAczA1Ih" +
                "dwLJ0DFcC0uRI17clR8jlNogV04tvpTcV870ER4ELWk1ZTnCD5P7fsPHZgZ6hs6KAM2Ei_xdNZhGBx39" +
                "-mTGDwLQDbmDODFz5-6eUq6vCUN5-0zi9XigvZWR-A_uqEWOrCCmYgTNof9JAzi4DrWEVh");
        sampleResponse.add(object);
        sampleResponse.add(object);
        return sampleResponse;
    }
}
