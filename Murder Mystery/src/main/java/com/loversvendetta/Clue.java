package com.loversvendetta;

import java.util.*;


public class Clue {

    private final String clue;
    private boolean isFound;

    public Clue(String clue) {
        this.clue = clue;
        isFound = false;
    }

    public String getClue() {
        return clue;
    }

    public boolean isFound() {
        return isFound;
    }

    public void nowFound() {
        isFound = true;
        System.out.println("!!!You found a clue!\n" + clue);
    }

}