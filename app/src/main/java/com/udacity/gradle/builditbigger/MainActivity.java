package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.JokeClient;
import com.owenlarosa.jokepresenter.JokeActivity;

import org.json.JSONException;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private JokeClient mJokeClient = new JokeClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetJokeTask extends AsyncTask<Void, Void, String> {

        private Exception error;

        @Override
        protected String doInBackground(Void... params) {
            String joke = "";
            try {
                joke = mJokeClient.getJoke();
            } catch (Exception e) {
                error = e;
            }
            return joke;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (error != null) {
                showError(error);
            } else {
                // successfully fetched joke, show detail screen
                tellJoke(s);
            }
        }
    }

    public void jokeButtonClicked(View view) {
        new GetJokeTask().execute();
    }

    private void tellJoke(String joke) {
        // get a joke and show it in the activity
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_EXTRA, joke);
        startActivity(intent);
    }

    private void showError(Exception e) {
        String errorMessage = "";
        if (e instanceof IOException) {
            errorMessage = getString(R.string.download_error);
        } else if (e instanceof JSONException) {
            errorMessage = getString(R.string.parsing_error);
        } else {
            errorMessage = getString(R.string.unknown_error);
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }


}
