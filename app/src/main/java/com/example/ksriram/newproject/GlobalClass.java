package com.example.ksriram.newproject;

import android.app.Application;

public class GlobalClass extends Application {
    private String score;
    private String email;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
