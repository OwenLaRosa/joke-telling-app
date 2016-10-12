/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.udacity.gradle.builtitbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builtitbigger.gradle.udacity.com",
                ownerName = "backend.builtitbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    // unique key to be used for referencing jokes
    private static int nextKey = 0;

    private static LinkedHashMap<Integer, Joke> jokes;
    static {
        jokes = new LinkedHashMap<Integer, Joke>();
        jokes.put(0, new Joke("Why did the ghost go into the bar?\n\nFor the Boos!"));
        jokes.put(1, new Joke("Knock Knock!\n\nWho's there?\n\nLong pause...\n\nJava"));
        nextKey = jokes.keySet().size();
    }
    private static ArrayList<Integer> keys;
    static {
        keys = new ArrayList<Integer>();
        keys.add(0);
        keys.add(1);
    }

    /**
     * Get a randomly selected joke
     * @return A (not) random Halloween joke
     */
    @ApiMethod(name = "getJoke", path = "get_joke")
    public JokeResponse getJoke() {
        JokeResponse response = new JokeResponse();
        String joke = null;
        int id = 0;
        while (true) {
            // make sure we get a joke, try again if there's a concurrency issue
            Exception exception = null;
            try {
                id = keys.get(new Random().nextInt() % keys.size());
                joke = jokes.get(id).text;
            } catch (IndexOutOfBoundsException e) {
                exception = e;
            }
            // verify that the index and key both exist
            if (joke != null && exception == null) {
                // if so, we have a joke, otherwise try again
                break;
            }
        }
        response.setText(joke);
        response.setId(id);

        return response;
    }

    @ApiMethod(name = "postJoke", path = "post_joke", httpMethod = ApiMethod.HttpMethod.POST)
    public SuccessResponse postJoke(@Named("joke") String joke) {
        SuccessResponse response = new SuccessResponse();

        if (joke.equals("")) {
            response.setSuccess(false);
        } else {
            // ensure the key doesn't change because of race conditions
            int key = nextKey;
            nextKey++;
            // add the joke
            jokes.put(key, new Joke(joke));
            keys.add(key);
            response.setSuccess(true);
        }

        return response;
    }

    @ApiMethod(name = "upVote", path = "up_vote", httpMethod = ApiMethod.HttpMethod.PUT)
    public SuccessResponse upVote(@Named("id") int id) {
        SuccessResponse response = new SuccessResponse();

        Joke joke = jokes.get(id);
        if (joke != null) {
            joke.upVote();
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
        }

        return response;
    }

    @ApiMethod(name = "downVote", path = "down_vote", httpMethod = ApiMethod.HttpMethod.PUT)
    public SuccessResponse downVote(@Named("id") int id) {
        SuccessResponse response = new SuccessResponse();

        Joke joke = jokes.get(id);
        if (joke != null) {
            if (joke.downVote() <= 0) {
                jokes.remove(id);
            }
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
        }

        return response;
    }

}
