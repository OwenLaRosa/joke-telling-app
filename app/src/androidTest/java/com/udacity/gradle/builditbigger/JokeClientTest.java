package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import com.example.Joke;
import com.example.JokeClient;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Owen LaRosa on 10/13/16.
 */

public class JokeClientTest extends AndroidTestCase {

    private JokeClient mClient;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mClient = new JokeClient();
    }

    public void testGetJoke() {
        Joke joke = null;
        try {
            joke = mClient.getJoke();
        } catch (IOException e) {
            assertTrue("IOException occurred", false);
        } catch (JSONException e) {
            assertTrue("JSONException occurred", false);
        } finally {
            if (joke != null ) {
                assertFalse(joke.text.equals(""));
            } else {
                assertTrue("getJoke Returned null", false);
            }
        }
    }

    public void testPostJoke() {
        Boolean success = null;
        try {
            success = mClient.postJoke("Is this a joke?");
        } catch (IOException e) {
            assertTrue("IOException occurred", false);
        } catch (JSONException e) {
            assertTrue("JSONException occurred", false);
        } finally {
            if (success != null) {
                assertTrue("Successfully posted joke", success);
            } else {
                assertTrue("postJoke Returned null", false);
            }
        }
    }

    public void testUpvoteJoke() {
        Boolean success = null;
        try {
            success = mClient.upvoteJoke(0);
        } catch (IOException e) {
            assertTrue("IOException occurred", false);
        } catch (JSONException e) {
            assertTrue("JSONException occurred", false);
        } finally {
            if (success != null) {
                assertTrue("Successfully upvoted joke", success);
            } else {
                assertTrue("upvoteJoke Returned null", false);
            }
        }
    }

    public void testDownvoteJoke() {
        Boolean success = null;
        try {
            success = mClient.downvoteJoke(0);
        } catch (IOException e) {
            assertTrue("IOException occurred", false);
        } catch (JSONException e) {
            assertTrue("JSONException occurred", false);
        } finally {
            if (success != null) {
                assertTrue("Successfully downvoted joke", success);
            } else {
                assertTrue("downvoteJoke Returned null", false);
            }
        }
    }

}
