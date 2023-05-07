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
        JsonObject firstObject = getFirstJsonObject();
        JsonObject secondObject = getSecondJsonObject();

        sampleResponse.add(firstObject);
        sampleResponse.add(secondObject);
        return sampleResponse;
    }

    private static JsonObject getFirstJsonObject() {
        JsonObject firstObject = new JsonObject();
        firstObject.addProperty("name", "Zielona Weranda");
        firstObject.addProperty("placeId", "ChIJBUn2SD9bBEcRThe9iausrn8");
        firstObject.addProperty("vicinity", "Paderewskiego 7, Poznań");
        firstObject.addProperty("rating", 4.4f);
        firstObject.addProperty("userRatingsTotal", 2524);
        firstObject.addProperty("openNow", true);
        firstObject.addProperty("photoReference", "AZose0km65w3gBpnFu7nDoPAczA1Ih" +
                "dwLJ0DFcC0uRI17clR8jlNogV04tvpTcV870ER4ELWk1ZTnCD5P7fsPHZgZ6hs6KAM2Ei_xdNZhGBx39" +
                "-mTGDwLQDbmDODFz5-6eUq6vCUN5-0zi9XigvZWR-A_uqEWOrCCmYgTNof9JAzi4DrWEVh");
        return firstObject;
    }

    private static JsonObject getSecondJsonObject() {
        JsonObject secondObject = new JsonObject();
        secondObject.addProperty("name", "Ptasie Radio");
        secondObject.addProperty("placeId", "ChIJB7T44zZbBEcR1yge5KHLwYk");
        secondObject.addProperty("vicinity", "Kościuszki 74/3, Poznań");
        secondObject.addProperty("rating", 4.6f);
        secondObject.addProperty("userRatingsTotal", 2635);
        secondObject.addProperty("openNow", true);
        secondObject.addProperty("photoReference", "AZose0lamWfrp_Lc-TVDgYWAJEbR_PgTRQH9" +
                "_mkRMc5rk6LrfvUoIqPylB6nkd4E8o9EZ-AlXqbY44jKHlWFRymkOuDURGzOHjxq2RMhqmI6cPvWZkms_A_" +
                "dFl6OGTRFDH2zPncupH1PYId0i6ON8IICeN0CTdJurT6S-b9UKCGlUDSaR5Mc");
        return secondObject;
    }
}
