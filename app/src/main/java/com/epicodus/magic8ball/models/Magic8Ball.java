package com.epicodus.magic8ball.models;

import java.util.Random;

public class Magic8Ball {

    private String[] mResponses;

    public Magic8Ball(String[] responses) {
        mResponses = responses;
    }

    public String getResponse() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(mResponses.length);
        return mResponses[index];
    }
}
