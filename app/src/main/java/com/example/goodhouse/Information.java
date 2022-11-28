package com.example.goodhouse;

import java.util.HashMap;

public class Information {
    private int score;
    HashMap noise;
    //Complaint[] complaint;

    public Information() {}

    public int getScore() {
        return score;
    }

    public void setScore() {
        this.score = score;
    }

    public Information(int score) {
        this.score = score;
        //this.noise = noise;
    }
}