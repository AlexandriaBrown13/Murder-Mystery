package com.loversvendetta;

public class Location extends SearchableClues {

    private String gameText;
    private final String howToSearch;

    public String getHowToSearch() {
        return howToSearch;
    }

    public Location(String name, int numberSelection, String howToSearch) {
        super(name, numberSelection);
        this.howToSearch = howToSearch;
    }

    public String getLocation() {
        return super.getName();
    }

    public String getGameText() {
        return gameText;
    }

    public void setGameText(String gameText) {
        this.gameText = gameText;
    }
}
