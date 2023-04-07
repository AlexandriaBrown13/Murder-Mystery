package com.loversvendetta;

public class GameText {
    private final String beginningStory;
    private final String repeatedText;
    private boolean didStoryRun;

    public GameText(String beginningStory, String repeatedText) {
        this.beginningStory = beginningStory;
        this.repeatedText = repeatedText;
        didStoryRun = false;
    }

    public String getStory() {
        if (!didStoryRun) {
            didStoryRun = true;
            return beginningStory + repeatedText;
        } else return repeatedText;
    }
}
