package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.test.AndroidTestCase;

import com.example.JokeClient;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Owen LaRosa on 10/10/16.
 */

public class AsyncTaskTest extends AndroidTestCase {

    private JokeClient jokeClient;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        jokeClient = new JokeClient();
    }

    public void testAsyncTask() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String joke = "";
                try {
                    joke = jokeClient.getJoke();
                } catch (IOException e) {
                    assertTrue("IOException occurred" ,false);
                } catch (JSONException e) {
                    assertTrue("JSON Exception occurred", false);
                }
                assertFalse("Joke is not empty", joke.equals(""));
            }
        });
    }

}
