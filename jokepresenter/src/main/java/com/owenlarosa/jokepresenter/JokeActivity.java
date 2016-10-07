package com.owenlarosa.jokepresenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_EXTRA = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        // get joke from the intent and display it onscreen
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(JOKE_EXTRA)) {
            String joke = intent.getStringExtra(JOKE_EXTRA);
            TextView jokeTextView = (TextView) findViewById(R.id.joke_textview);
            jokeTextView.setText(joke);
        }
    }
}
