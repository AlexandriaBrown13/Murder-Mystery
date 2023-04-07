package com.loversvendetta;

import java.util.*;

public class Main {
    public static final int ENTER_HOUSE = 1;
    public static final int TALK_TO_WITNESS = 2;
    public static final int ARREST_SOMEONE = 3;
    public static final String PRESS_FOR_CLUES = "Enter 8 to view found clues.";
    public static final int SHOW_CLUES = 8;
    public static final int GO_BACK = 7;
    public static Boolean isGamePlaying = true;
    public static int weAreArresting;
    public static GameText houseText = new GameText(" \nYou follow your partner into the house and down a long narrow \n" +
            "hallway to the bedroom. There is a sense of stillness as you see the \n" +
            "body just laying there. The room is a mess, you can tell there was \n" +
            "some sort of struggle. Your partner steps to the doorway to allow \n" +
            "you to get a better look.", "\nYou never really got use to the aura death brings.\n" +
            "You know you need to get to work.");
    public static GameText talkToSuspectText = new GameText(" \nBefore approaching any suspects, you speak with one of the \n" +
            "cops that was taking notes earlier. You find out the name of the dead \n" +
            "man inside is Jared. They proceed give you a small rundown of\n" +
            "everyone else in the house tonight, knowing one of them did it.", "You already know the wife did it. The wife always \n" +
            "does it....... right?... RIGHT??");
    public static Location room = new Location("room", 1, "search the room");
    public static Location partner = new Location("partner", 2, "ask partner for updates");
    public static Location body = new Location("body", 3, "examine body");
    public static Suspect wife = new Suspect("Britney", 1, 1);
    public static Suspect wifeBFF = new Suspect("Paris", 2, 2);
    public static Suspect victimBrother = new Suspect("Kevin", 4, 4);
    public static Suspect mistress = new Suspect("Cora", 3, 3);
    public static int whoDidIt;
    public static Suspect killer;
    public static Location[] allLocations = {room, partner, body};
    public static Suspect[] allSuspects = {wife, wifeBFF, mistress, victimBrother};
    public static boolean wasException = true;
    public static Clue britneyRelation = new Clue("Britney is Jared's wife.");
    public static Clue parisRelation = new Clue("Paris is Britney's best friend.");
    public static Clue kevinRelation = new Clue("Kevin is Jared's brother.");
    public static Clue coraRelation = new Clue("Cora is Jared's wife.");


    public static void main(String[] args) {
        int lowerBound = 1;
        int upperBound = 4;
        int range = (upperBound - lowerBound) + 1;
        int random = (int) (Math.random() * range) + lowerBound;
        setStory(random);
        Scanner userInput = new Scanner(System.in);
        System.out.println("Welcome to Lover's Vendetta. You will play the role of a \n" +
                "detective and use your skills to gather clues, interview suspects \n" +
                "and solve the mystery. The story can slightly change on every play, \n" +
                "making 1 of the 4 suspects guilty! Will you be able to arrest the \n" +
                "killer?");
        while (isGamePlaying) {
            int userSelection = runStartMenu(userInput);
            if (userSelection == 1) {
                beOutside(userInput);
            }
        }
        System.out.println(" \nThank you for playing. Try playing again for a\n" +
                "different possible outcome!");

    }

    //TRAVELING
    public static void beOutside(Scanner userInput) {
        //below game text when first going outside
        System.out.println(" \nThe night was dark and cool when you pull up to the crime \n" +
                "scene. You notice two women sitting on the porch. One of them is \n" +
                "crying while the other one is trying to console her. There is a \n" +
                "heated argument between a man and a women over by an ambulance. \n" +
                "Peaking back at the front door, you see your partner trying to \n" +
                "wave you inside.");
        while (isGamePlaying) {
            ///this text comes up when first coming outside and also when comes back
            System.out.println("You can tell this is going to be a long night. \nWhat would you like to do?");
            int goToSelection = runOutSideMenu(userInput);

            if (goToSelection == ENTER_HOUSE) {
                enterHouse(userInput);
            } else if (goToSelection == TALK_TO_WITNESS) {
                talkToSuspects(userInput);
            } else if (goToSelection == ARREST_SOMEONE) {
                arrestSomeone(userInput);
            } else if (goToSelection == SHOW_CLUES) {
                printAllClues();
            } else if (goToSelection == GO_BACK) {
                isGamePlaying = false;
                break;
            } else makeValidSelection();
        }
    }

    public static void enterHouse(Scanner userInput) {
        while (isGamePlaying) {
            System.out.println(houseText.getStory());
            int searchSelection = runHouseMenu(userInput);
            if (searchSelection == body.getNumberSelection()) {
                body.searchClue();
            } else if (searchSelection == room.getNumberSelection()) {
                room.searchClue();
            } else if (searchSelection == partner.getNumberSelection()) {
                partner.searchClue();
            } else if (searchSelection == GO_BACK) {
                break;
            } else if (searchSelection == SHOW_CLUES) {
                printAllClues();
            } else {
                makeValidSelection();
            }
        }

    }

    public static void talkToSuspects(Scanner userInput) {
        System.out.println(talkToSuspectText.getStory());
        getSuspectRelations();
        while (isGamePlaying) {
            int talkToSelection = runSuspectMenu(userInput);
            if (talkToSelection == wife.getNumberSelection()) {
                wife.searchClue();
            } else if (talkToSelection == mistress.getNumberSelection()) {
                mistress.searchClue();
            } else if (talkToSelection == wifeBFF.getNumberSelection()) {
                wifeBFF.searchClue();
            } else if (talkToSelection == victimBrother.getNumberSelection()) {
                victimBrother.searchClue();
            } else if (talkToSelection == GO_BACK) {
                break;
            } else if (talkToSelection == SHOW_CLUES) {
                printAllClues();
            } else makeValidSelection();

        }

    }

    public static void arrestSomeone(Scanner userInput) {
        while (isGamePlaying) {
            System.out.println(" \nAh ha! After gathering some information, you now \n" +
                    "know who you need to arrest. Who gets a free ride in the cop car?");
            int arrestSelection = runArrestMenu(userInput);
            if (arrestSelection == GO_BACK) {
                break;
            } else if (arrestSelection == SHOW_CLUES) {
                printAllClues();
            } else if (arrestSelection == wife.getNumberSelection() ||
                    arrestSelection == mistress.getNumberSelection() ||
                    arrestSelection == wifeBFF.getNumberSelection() ||
                    arrestSelection == victimBrother.getNumberSelection()) {
                weAreArresting = arrestSelection;
                confirmArrestSomeone(userInput);
            } else makeValidSelection();
        }
    }

    public static void confirmArrestSomeone(Scanner userInput) {
        int areYouSureSelection = runAreYouSureMenu(userInput);
        if (areYouSureSelection == 1) {
            if (isThisCorrect()) {
                System.out.println(killer.getConfession());
                System.out.println(" \nWow! You really are a detective! You can \n" +
                        "now sit back and relax knowing you put the right \n" +
                        "person behind bars!");
                isGamePlaying = false;
            } else System.out.println("\nOops! You arrested the wrong person! \n" +
                    "Would you like to try again?");
            tryAgain(userInput);
        }
    }

    public static void tryAgain(Scanner userInput) {
        while (isGamePlaying) {
            int userSelection = runTryAgain(userInput);
            if (userSelection == 1) {
                arrestSomeone(userInput);
            } else if (userSelection == 2) {
                isGamePlaying = false;
                break;
            } else makeValidSelection();
        }
    }

    public static Boolean isThisCorrect() {
        return weAreArresting == whoDidIt;
    }

    // MENUS
    public static int runStartMenu(Scanner userInput) {
        System.out.println("Press 1 to start the game.");
        try {
            String userSelection = userInput.nextLine();
            return Integer.parseInt(userSelection);
        } catch (Exception ex) {
            System.out.println(" \nPlease make a numeric selection.\n ");
            wasException = true;
        }
        return 0;
    }

    public static int runOutSideMenu(Scanner userInput) {
        System.out.println("Enter " + ENTER_HOUSE + " to enter the house.");
        System.out.println("Enter " + TALK_TO_WITNESS + " to talk to the suspects.");
        System.out.println("Enter " + ARREST_SOMEONE + " to arrest someone.");
        System.out.println(PRESS_FOR_CLUES);
        System.out.println("Enter " + GO_BACK + " to quit.");
        try {
            String userSelection = userInput.nextLine();
            return Integer.parseInt(userSelection);
        } catch (Exception ex) {
            System.out.println(" \nPlease make a numeric selection.\n ");
            wasException = true;
        }
        return 9;
    }

    public static int runHouseMenu(Scanner userInput) {
        for (Location location : allLocations) {
            System.out.println("Enter " + location.getNumberSelection() + " to " + location.getHowToSearch() + ".");
        }
        System.out.println("Enter " + GO_BACK + " to go back outside.");
        System.out.println(PRESS_FOR_CLUES);
        try {
            String userSelection = userInput.nextLine();
            return Integer.parseInt(userSelection);
        } catch (Exception ex) {
            System.out.println(" \nPlease make a numeric selection.\n ");
            wasException = true;
        }
        return 9;
    }

    public static int runSuspectMenu(Scanner userInput) {
        for (Suspect suspect : allSuspects) {
            System.out.println("Enter " + suspect.getNumberSelection() + " to talk to " + suspect.getName() + ".");
        }
        System.out.println("Enter " + GO_BACK + " to stop talking.");
        System.out.println(PRESS_FOR_CLUES);
        try {
            String userSelection = userInput.nextLine();
            return Integer.parseInt(userSelection);
        } catch (Exception ex) {
            System.out.println(" \nPlease make a numeric selection.\n ");
            wasException = true;
        }
        return 9;
    }

    public static int runArrestMenu(Scanner userInput) {
        for (Suspect suspect : allSuspects) {
            System.out.println("Enter " + suspect.getNumberSelection() + " to arrest " + suspect.getName() + ".");
        }
        System.out.println("Enter " + GO_BACK + " to continue investigating.");
        System.out.println(PRESS_FOR_CLUES);
        try {
            String userSelection = userInput.nextLine();
            return Integer.parseInt(userSelection);
        } catch (Exception ex) {
            System.out.println(" \nPlease make a numeric selection.\n ");
            wasException = true;
        }
        return 9;
    }

    public static int runAreYouSureMenu(Scanner userInput) {
        String areYouSure = "Are you sure you want to arrest this suspect?";
        System.out.println(areYouSure);
        System.out.println("Enter 1 for yes.");
        System.out.println("Enter 2 for no.");
        try {
            String userSelection = userInput.nextLine();
            return Integer.parseInt(userSelection);
        } catch (Exception ex) {
            System.out.println(" \nPlease make a numeric selection.\n ");
            wasException = true;
        }
        return 9;
    }

    public static int runTryAgain(Scanner userInput) {
        System.out.println("Enter 1 to try again.");
        System.out.println("Enter 2 to quit.");
        try {
            String userSelection = userInput.nextLine();
            return Integer.parseInt(userSelection);
        } catch (Exception ex) {
            System.out.println(" \nPlease make a numeric selection.\n ");
            wasException = true;
        }
        return 9;
    }

    //other Methods

    public static void setStory(int random) {
        if (random == wife.getStoryNumber()) {
            wife.setKiller();
            setWifeStoryClues();
            killer = wife;
        } else if (random == mistress.getStoryNumber()) {
            mistress.setKiller();
            setMistressStoryClues();
            killer = mistress;
        } else if (random == wifeBFF.getStoryNumber()) {
            wifeBFF.setKiller();
            setWifeBFFStoryClues();
            killer = wifeBFF;
        } else if (random == victimBrother.getStoryNumber()) {
            victimBrother.setKiller();
            setVictimBrotherStoryClues();
            killer = victimBrother;
        }

        for (Suspect suspect : allSuspects) {
            if (suspect.isKiller()) {
                whoDidIt = suspect.getNumberSelection();
            }
        }

    }

    public static void printAllClues() {
        if (room.getFoundSize() < 1 &&
                body.getFoundSize() < 1 &&
                partner.getFoundSize() < 1 &&
                wife.getFoundSize() < 1 &&
                wifeBFF.getFoundSize() < 1 &&
                victimBrother.getFoundSize() < 1 &&
                mistress.getFoundSize() < 1
        ) {
            System.out.println("You haven't found any clues yet! Please look and ask around to find them.");
            System.out.println(" ");
        } else {
            System.out.println("\nThe clues you have found:");
            room.getFoundClues();
            body.getFoundClues();
            partner.getFoundClues();
            wife.getFoundClues();
            wifeBFF.getFoundClues();
            victimBrother.getFoundClues();
            mistress.getFoundClues();
            System.out.println(" ");
        }
    }

    public static void makeValidSelection() {
        if (wasException) {
            wasException = false;
        } else System.out.println(" \nPlease make a valid selection\n ");
    }

    public static void getSuspectRelations() {
        wife.getSpecificClue(britneyRelation);
        wifeBFF.getSpecificClue(parisRelation);
        victimBrother.getSpecificClue(kevinRelation);
        mistress.getSpecificClue(coraRelation);

    }

    public static void setWifeStoryClues() {
        Clue deathClue = new Clue("There are multiple stab wounds on the chest.");
        Clue partnerClue = new Clue("There are broken branches and a flatten bush outside the window.");
        Clue partnerClueTwo = new Clue("There was blood found in the bathroom sink.");
        Clue roomClue = new Clue("A broken mirror indicates a struggle.");
        Clue roomClueTwo = new Clue("The blood on the mirror shards show female DNA.");
        Clue roomClueThree = new Clue("A combat knife is under the bed.");
        Clue roomClueFour = new Clue("A drawer by the bed is open. An empty knife holster lays below it.");
        Clue wifeClue = new Clue("Britney had her arm bandaged up from the paramedics.");
        Clue wifeClueTwo = new Clue("You notice Britney's hand is stained red.");
        Clue wifeClueThree = new Clue("\"I know my husband was having an affair. I did not want to believe it.\" -Britney");
        Clue wifeClueFour = new Clue("\"Paris and I were at a paint and sip. I couldn't get all the paint off.\" - Britney");
        Clue mistressClue = new Clue("\"Jared was alive when I left the room.\" - Cora");
        Clue mistressClueTwo = new Clue("Jared and Cora have been having an affair for 6 months.");
        Clue mistressClueThree = new Clue("\"She was acting crazy, throwing stuff at Jared!\" - Cora");
        Clue brotherClue = new Clue("\"I know Cora was cheating, so I followed her to find out the other man.\" - Kevin");
        Clue brotherClueTwo = new Clue("Kevin climbed the tree to spy on Cora and Jared. The tree branch broke and he fell into a bush.");
        Clue bffClue = new Clue("There are red splatter stains on Paris's clothes.");
        Clue bffClueTwo = new Clue("\"Britney and I went to a paint and sip and came over to crash on her couch.\" - Paris");
        Clue bffClueThree = new Clue("Cora had to be jealous since Jared would not leave Britney. I would not be surprised if it was her\" - Paris");
        body.setClues(new Clue[]{deathClue});
        room.setClues(new Clue[]{roomClue, roomClueTwo, roomClueThree, roomClueFour});
        partner.setClues(new Clue[]{partnerClue, partnerClueTwo});
        wife.setClues(new Clue[]{wifeClue, wifeClueTwo, wifeClueThree, wifeClueFour});
        mistress.setClues(new Clue[]{mistressClueTwo, mistressClue, mistressClueThree});
        victimBrother.setClues(new Clue[]{brotherClue, brotherClueTwo});
        wifeBFF.setClues(new Clue[]{bffClue, bffClueTwo, bffClueThree});
        wife.setConfession("Ok I did it! When I came home, I caught Jared and Cora sleeping together! We started to argue and\n" +
                "he was pushing me around. I fell into the mirror and cut my arm. To save myself, I grabbed my knife from my\n" +
                "bedside table and kept stabbing until he stopped. It should have been Cora I did this to!");

    }

    public static void setMistressStoryClues() {
        Clue deathClue = new Clue("There are small red marks around Jared's neck.");
        Clue partnerClue = new Clue("We found Kevin locked in the bathroom.");
        Clue partnerClueTwo = new Clue("There was a shoe string in the trash can.");
        Clue partnerClueThree = new Clue("There was a positive pregnancy test in the trash can");
        Clue roomClue = new Clue("A broken closet door indicates a struggle.");
        Clue roomClueTwo = new Clue("There is a shoe in the closet with a missing shoe string.");
        Clue roomClueThree = new Clue("The window is open.");
        Clue wifeClue = new Clue("I found a positive pregnancy test and pretended to Jared it was mine and he was happy to have another child with me.\" - Britney");
        Clue wifeClueTwo = new Clue("You notice Britney's clothes had red stained marks.");
        Clue wifeClueThree = new Clue("\"I am not pregnant, so I knew then my husband was having an affair.\" -Britney");
        Clue wifeClueFour = new Clue("\"Paris and I were at a paint and sip. I couldn't get all the paint off.\" - Britney");
        Clue mistressClue = new Clue("\"I started the affair because Kevin spends too much time fishing.\" - Cora");
        Clue mistressClueTwo = new Clue("Jared and Cora have been having an affair for 6 months.");
        Clue mistressClueThree = new Clue("\"I told Jared I was pregnant with his baby and we were going to run away together.\" - Cora");
        Clue mistressClueFour = new Clue("\"We heard Britney coming upstairs so i hid in the closet.");
        Clue brotherClue = new Clue("\"I know Cora was cheating, so I followed her to find out the other man.\" - Kevin");
        Clue brotherClueTwo = new Clue("Kevin climbed through the window and into the bathroom, found the pregnancy test, and overheard Jared & Cora's conversation.");
        Clue brotherClueThree = new Clue("\"I now know my wife has been sleeping with my brother, but I wouldn't kill him for that!\" - Kevin");
        Clue bffClue = new Clue("There are red splatter stains on Paris's clothes.");
        Clue bffClueTwo = new Clue("\"Britney and I went to a paint and sip and came over to crash on her couch.\" - Paris");
        Clue bffClueThree = new Clue("Cora had to be jealous since Jared would not leave Britney.\" - Paris");
        Clue bffClueFour = new Clue("Britney came downstairs crying because she realized Jared got another girl pregnant\" - Paris");
        body.setClues(new Clue[]{deathClue});
        room.setClues(new Clue[]{roomClue, roomClueTwo, roomClueThree});
        partner.setClues(new Clue[]{partnerClue, partnerClueTwo, partnerClueThree});
        wife.setClues(new Clue[]{wifeClue, wifeClueTwo, wifeClueThree, wifeClueFour});
        mistress.setClues(new Clue[]{mistressClueTwo, mistressClue, mistressClueThree, mistressClueFour});
        victimBrother.setClues(new Clue[]{brotherClue, brotherClueTwo, brotherClueThree});
        wifeBFF.setClues(new Clue[]{bffClue, bffClueTwo, bffClueThree, bffClueFour});
        mistress.setConfession("Ok, I did it! Britney came home and I quickly hid in the closet. Britney came inside and\n" +
                "was telling Jared she was also pregnant. He was so excited by this news. After she left the room he\n" +
                "told me that he would rather have his wife's baby and I need to pretend I am pregnant by my husband.\n" +
                "I asked him about running away together just as he promised but wanted nothing to do with it anymore.\n" +
                "I was so angry that after he was walking away I took the shoe string I planned on using on Britney, \n" +
                "and used it on Jared. He put up a fight and tried to throw me into the closet to get me off, but I\n" +
                "ended up winning this time.");

    }

    public static void setVictimBrotherStoryClues() {
        Clue deathClue = new Clue("There are large red marks around Jared's neck.");
        Clue deathClueTwo = new Clue("There is dead skin under Jared's fingernails.");
        Clue partnerClueTwo = new Clue("There was a rope in the trash can.");
        Clue partnerClueThree = new Clue("There was a positive pregnancy test in the trash can");
        Clue roomClue = new Clue("A broken closet door indicates a struggle.");
        Clue roomClueTwo = new Clue("The room has a very fishy smell to it.");
        Clue roomClueThree = new Clue("The window is open.");
        Clue wifeClue = new Clue("Jared said he found a pregnancy test and how happy he was to have another child with me.\" - Britney");
        Clue wifeClueTwo = new Clue("You notice Britney's clothes had red stained marks.");
        Clue wifeClueThree = new Clue("\"I am not pregnant, so I knew then my husband was having an affair.\" -Britney");
        Clue wifeClueFour = new Clue("\"Paris and I were at a paint and sip. I couldn't get all the paint off.\" - Britney");
        Clue mistressClue = new Clue("\"I started the affair because Kevin spends too much time fishing.\" - Cora");
        Clue mistressClueTwo = new Clue("Jared and Cora have been having an affair for 6 months.");
        Clue mistressClueThree = new Clue("\"I told Jared I was pregnant with his baby and we were going to run away together.\" - Cora");
        Clue brotherClue = new Clue("\"I know Cora was cheating, so I followed her to find out the other man.\" - Kevin");
        Clue brotherClueTwo = new Clue("Kevin climbed through the window and into the bathroom, found the pregnancy test, and overheard Jared & Cora's conversation.");
        Clue brotherClueThree = new Clue("\"I now know my wife has been sleeping with my brother, but I wouldn't kill him for that!\" - Kevin");
        Clue brotherClueFour = new Clue("There are scratch marks on Kevin's arm.");
        Clue bffClue = new Clue("There are red splatter stains on Paris's clothes.");
        Clue bffClueTwo = new Clue("\"Britney and I went to a paint and sip and came over to crash on her couch.\" - Paris");
        Clue bffClueThree = new Clue("Cora had to be jealous since Jared would not leave Britney.\" - Paris");
        Clue bffClueFour = new Clue("Britney came downstairs crying because she realized Jared got another girl pregnant.\" - Paris");
        body.setClues(new Clue[]{deathClue, deathClueTwo});
        room.setClues(new Clue[]{roomClue, roomClueTwo, roomClueThree});
        partner.setClues(new Clue[]{partnerClueTwo, partnerClueThree});
        wife.setClues(new Clue[]{wifeClue, wifeClueTwo, wifeClueThree, wifeClueFour});
        mistress.setClues(new Clue[]{mistressClueTwo, mistressClue, mistressClueThree});
        victimBrother.setClues(new Clue[]{brotherClue, brotherClueTwo, brotherClueThree, brotherClueFour});
        wifeBFF.setClues(new Clue[]{bffClue, bffClueTwo, bffClueThree, bffClueFour});
        victimBrother.setConfession("Ok I did it! After following my wife to the house, I climbed the tree and \n" +
                "came in through the window. I went into the bathroom to spy and saw this positive pregnancy test\n" +
                "in the trash. I then heard Cora talking to Jared stating how she is pregnant and with his baby.\n" +
                "My own brother, I couldn't believe it! He needed to go and this baby is going to be mine. I\n" +
                "snuck downstairs and grabbed my fishing rope from my truck and used it on him. He put up a \n" +
                "fight, but I clearly came up on top.");
    }

    public static void setWifeBFFStoryClues() {
        Clue deathClue = new Clue("There are multiple stab wounds on the back.");
        Clue deathClueTwo = new Clue("There are also glass shards in the back of the head.");
        Clue partnerClue = new Clue("There are broken branches and a flatten bush outside the window.");
        Clue partnerClueTwo = new Clue("There was blood found in the bathroom sink.");
        Clue roomClue = new Clue("A broken mirror indicates a struggle.");
        Clue roomClueTwo = new Clue("The blood on the mirror shards show female DNA.");
        Clue roomClueFive = new Clue("A broken vase is by the bed, matching the glass shards on head.");
        Clue roomClueThree = new Clue("A bloody kitchen knife is under the bed.");
        Clue roomClueFour = new Clue("The kitchen knife matches the knife set in the kitchen.");
        Clue wifeClue = new Clue("Britney had her arm bandaged up from the paramedics.");
        Clue wifeClueTwo = new Clue("You notice Britney's hand is stained red.");
        Clue wifeClueThree = new Clue("\"I know my husband was having an affair. I did not want to believe it.\" -Britney");
        Clue wifeClueFour = new Clue("\"Paris and I were at a paint and sip. I couldn't get all the paint off.\" - Britney");
        Clue mistressClue = new Clue("\"Jared was alive when I left the room.\" - Cora");
        Clue mistressClueTwo = new Clue("Jared and Cora have been having an affair for 6 months.");
        Clue mistressClueThree = new Clue("\"She was acting crazy, her and Jared starting arguing when she caught us!\" - Cora");
        Clue brotherClue = new Clue("\"I know Cora was cheating, so I followed her to find out the other man.\" - Kevin");
        Clue brotherClueTwo = new Clue("Kevin climbed the tree to spy on Cora and Jared. The tree branch broke and he fell into a bush.");
        Clue bffClue = new Clue("There are red splatter stains on Paris's clothes.");
        Clue bffClueTwo = new Clue("\"Britney and I went to a paint and sip and came over to crash on her couch.\" - Paris");
        Clue bffClueThree = new Clue("Cora had to be jealous since Jared would not leave Britney.\" - Paris");
        Clue bffClueFour = new Clue("I am glad Jared is dead, he always beat Britney when he was mad. I am glad she is safe now.");
        body.setClues(new Clue[]{deathClue, deathClueTwo});
        room.setClues(new Clue[]{roomClue, roomClueTwo, roomClueThree, roomClueFour, roomClueFive});
        partner.setClues(new Clue[]{partnerClue, partnerClueTwo});
        wife.setClues(new Clue[]{wifeClue, wifeClueTwo, wifeClueThree, wifeClueFour});
        mistress.setClues(new Clue[]{mistressClueTwo, mistressClue, mistressClueThree});
        victimBrother.setClues(new Clue[]{brotherClue, brotherClueTwo});
        wifeBFF.setClues(new Clue[]{bffClue, bffClueTwo, bffClueThree, bffClueFour});
        wifeBFF.setConfession("Ok I did it! After coming into the house, I heard yelling upstairs. Cora came running\n" +
                "downstairs. I heard banging around and knew Jared had to have been beating on Britney. I grabbed a\n" +
                "knife from the kitchen and ran upstairs. I smacked the back of his head with a vase to get him dazed\n" +
                "and proceeded to stab his back until he stopped.");

    }

}