package com.example;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JokeClient {

    private OkHttpClient mClient = new OkHttpClient();
    private static final String BASE_URL = "http://10.0.2.2:8080/_ah/api/myApi/v1/";

    // methods
    private static final String JOKE = "joke";

    // json keys
    private static final String JOKE_KEY = "text";

    /**
     * Get a joke
     * Currently, the joke is always the same.
     * @return A bad Halloween joke
     */
    public String getJoke() throws IOException, JSONException {
        // fetch data from the server
        String urlString = BASE_URL + JOKE;
        Request request = new Request.Builder().url(urlString).build();
        Response response = mClient.newCall(request).execute();
        String result = response.body().string();

        // extract the joke
        JSONObject jsonObject = new JSONObject(result);
        return jsonObject.getString(JOKE_KEY);
    }

}
