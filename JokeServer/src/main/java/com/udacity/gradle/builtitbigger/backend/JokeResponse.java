package com.udacity.gradle.builtitbigger.backend;

/**
 * Created by Owen LaRosa on 10/12/16.
 */

public class JokeResponse {

    private String text;
    private int id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
