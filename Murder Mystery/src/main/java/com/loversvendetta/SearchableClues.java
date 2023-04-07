package com.loversvendetta;

import java.util.ArrayList;
import java.util.List;

public class SearchableClues {
    private final String name;
    List<Clue> clues = new ArrayList<>();
    List<Clue> foundClues = new ArrayList<>();
    private final String youFound = "!!You have found a clue!";
    private final int numberSelection;

    public SearchableClues(String name, int numberSelection) {
        this.name = name;
        this.numberSelection = numberSelection;
    }

    public String getName() {
        return name;
    }

    public int getNumberSelection() {
        return numberSelection;
    }

    public void searchClue() {
        if (foundClues.size() == clues.size()) {
            System.out.println("Hmm... There are no more clues to find here.\n ");
        } else if (clues.size() > 0) {
            for (Clue clue : clues) {
                if (!clue.isFound()) {
                    clue.nowFound();
                    foundClues.add(clue);
                    break;
                }
            }
      } else
                System.out.println("Hmm... There are no more clues to find here.\n ");
    }

    public void getFoundClues() {
        for (Clue clue : clues) {
            if (clue.isFound()) {
                System.out.println(clue.getClue());
            }
        }
    }

    public void getSpecificClue(Clue clue) {
        if (!clue.isFound())
            clue.nowFound();
    }

    public void setClues(Clue[] cluesArray) {
        for (Clue clue : cluesArray) {
            clues.add(clue);
        }
    }

    public void setSingleClues(Clue clue) {
        clues.add(clue);
    }

    public int getFoundSize() {
        int counter = 0;
        for (Clue clue : clues) {
            if (clue.isFound()) {
                counter++;
            }
        }
        return counter;
    }

    public String getYouFound() {
        return youFound;
    }

}
