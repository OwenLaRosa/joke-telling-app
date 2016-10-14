package com.owenlarosa.jokepresenter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.JokeClient;
import com.melnykov.fab.FloatingActionButton;

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_TEXT_EXTRA = "joke_text";
    public static final String JOKE_ID_EXTRA = "joke_id";

    FloatingActionButton upvoteButton;
    FloatingActionButton downvoteButton;

    private JokeClient mClient = new JokeClient();
    private int jokeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        // get joke from the intent and display it onscreen
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(JOKE_TEXT_EXTRA) && intent.hasExtra(JOKE_ID_EXTRA)) {
            String joke = intent.getStringExtra(JOKE_TEXT_EXTRA);
            TextView jokeTextView = (TextView) findViewById(R.id.joke_textview);
            jokeTextView.setText(joke);
            jokeId = intent.getIntExtra(JOKE_ID_EXTRA, 0);
        }
        upvoteButton = (FloatingActionButton) findViewById(R.id.upvote_joke_button);
        downvoteButton = (FloatingActionButton) findViewById(R.id.downvote_joke_button);
    }

    public void upvoteJoke(View view) {
        disableVotingButtons();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mClient.upvoteJoke(jokeId);
                } catch (Exception e) {
                    // do nothing
                }
            }
        });
    }

    public void downvoteJoke(View view) {
        disableVotingButtons();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mClient.downvoteJoke(jokeId);
                } catch (Exception e) {
                    // do nothing
                }
            }
        });
    }

    private void disableVotingButtons() {
        // disable user interaction
        upvoteButton.setEnabled(false);
        downvoteButton.setEnabled(false);
        // show a lighter color when button is disabled
        upvoteButton.setColorNormalResId(R.color.green_light);
        downvoteButton.setColorNormalResId(R.color.red_light);
    }

}
