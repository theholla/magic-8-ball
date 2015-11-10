package com.epicodus.magic8ball.models;

import java.util.Random;

public class Magic8Ball {

    private String[] mResponses;

    public Magic8Ball() {
        mResponses = new String[]{"It is certain.", "It is decidedly so.",
                "Without a doubt.", "Yes, definitely.", "You may rely on it.",
                "As I see it, yes.", "Most likely.", "Outlook good.", "Yes.",
                "Signs point to yes.", "Reply hazy try again.", "Ask again later.",
                "Better not tell you now.", "Cannot predict now.", "Concentrate and ask again.",
                "Don't count on it.", "My reply is no.", "My sources say no.", "Outlook not so good.",
                "Very doubtful."};

    }

    public String getResponse() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(mResponses.length);
        return mResponses[index];
    }
}
