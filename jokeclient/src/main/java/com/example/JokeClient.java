package com.example;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JokeClient {

    private OkHttpClient mClient = new OkHttpClient();
    // use IP address for Genymotion emulator
    private static final String BASE_URL = "http://10.0.3.2:8080/_ah/api/myApi/v1/";

    // methods
    private static final String GET_JOKE = "get_joke";
    private static final String POST_JOKE = "post_joke";
    private static final String UPVOTE_JOKE = "up_vote";
    private static final String DOWNVOTE_JOKE = "down_vote";

    // parameters
    private static final String PARAM_ID = "id";
    private static final String PARAM_JOKE = "joke";

    // json keys
    private static final String JOKE_KEY = "text";
    private static final String JOKE_ID = "id";
    private static final String REQUEST_SUCCESS = "success";

    /**
     * Get a joke
     * Currently, the joke is always the same.
     * @return A bad Halloween joke
     * @throws IOException
     * @throws JSONException
     */
    public Joke getJoke() throws IOException, JSONException {
        // fetch data from the server
        String urlString = BASE_URL + GET_JOKE;
        Request request = new Request.Builder().url(urlString).build();
        Response response = mClient.newCall(request).execute();
        String result = response.body().string();

        // extract the joke
        JSONObject jsonObject = new JSONObject(result);

        Joke joke = new Joke();
        joke.text = jsonObject.getString(JOKE_KEY);
        joke.id = jsonObject.getInt(JOKE_ID);

        return joke;
    }

    /**
     * Upload a joke to the server
     * @param joke Joke submitted by the user
     * @return true if upload succeeded, otherwise false
     * @throws IOException
     * @throws JSONException
     */
    public boolean postJoke(String joke) throws IOException, JSONException {
        String urlString = BASE_URL + POST_JOKE;
        Request request = new Request.Builder().url(urlString).build();
        Response response = mClient.newCall(request).execute();
        String result = response.body().string();

        JSONObject jsonObject = new JSONObject(result);
        return jsonObject.getBoolean(REQUEST_SUCCESS);
    }

    /**
     * "like"/upvote a joke
     * @param id joke ID returned from the server
     * @return true if succeeded, otherwise false
     * @throws IOException
     * @throws JSONException
     */
    public boolean upvoteJoke(int id) throws IOException, JSONException {
        String urlString = BASE_URL + UPVOTE_JOKE;
        Request request = new Request.Builder().url(urlString).build();
        Response response = mClient.newCall(request).execute();
        String result = response.body().toString();

        JSONObject jsonObject = new JSONObject(result);
        return jsonObject.getBoolean(REQUEST_SUCCESS);
    }

    /**
     * "dislike"/downvote a joke
     * @param id joke ID returned from the server
     * @return true if succeeded, otherwise false
     * @throws IOException
     * @throws JSONException
     */
    public boolean downvoteJoke(int id) throws IOException, JSONException {
        String urlString = BASE_URL + DOWNVOTE_JOKE;
        Request request = new Request.Builder().url(urlString).build();
        Response response = mClient.newCall(request).execute();
        String result = response.body().toString();

        JSONObject jsonObject = new JSONObject(result);
        return jsonObject.getBoolean(REQUEST_SUCCESS);
    }

}
