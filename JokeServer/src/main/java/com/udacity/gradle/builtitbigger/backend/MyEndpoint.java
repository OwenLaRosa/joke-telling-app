/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.udacity.gradle.builtitbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

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
    static int nextKey = 0;

    private static LinkedHashMap<Integer, String> jokes;
    static {
        jokes = new LinkedHashMap<Integer, String>();
        jokes.put(0, "Why did the ghost go into the bar?\n\nFor the Boos!");
        jokes.put(1, "Knock Knock!\n\nWho's there?\n\nLong pause...\n\nJava");
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
    @ApiMethod(name = "joke")
    public Joke getJoke() {
        Joke response = new Joke();
        String joke = null;
        int id = 0;
        while (true) {
            // make sure we get a joke, try again if there's a concurrency issue
            Exception exception = null;
            try {
                id = keys.get(new Random().nextInt() % keys.size());
                joke = jokes.get(id);
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

}
