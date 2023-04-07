package com.loversvendetta;

public class Suspect extends SearchableClues {

    private final int storyNumber;
    private boolean isKiller = false;
    private String confession;

    public Suspect(String name, int numberSelection, int storyNumber) {
        super(name, numberSelection);
        this.storyNumber = storyNumber;
    }


    public int getStoryNumber() {
        return storyNumber;
    }

    public boolean isKiller() {
        return isKiller;
    }

    public void setKiller(){
        isKiller = true;
    }

    public String getConfession() {
        return confession;
    }

    public void setConfession(String confession) {
        this.confession = confession;
    }
}

