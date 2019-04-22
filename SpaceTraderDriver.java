import java.io.*;
import java.util.Scanner;

public class SpaceTraderDriver {

    final static int skillsTotal = 16;

    /**
     * Method to check if the user wishes to exit the game
     * @param sc The scanner being used to take user input
     */
    public static void checkExit(Scanner sc) {
        if (sc.hasNext("exit")) {
            System.out.println("Exiting game.");
            System.exit(0);
        }
    }

    /**
     * Driver program for the game. Uses system I/O to play Space Trader.
     * @param args Standard main method argument
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Space Trader! If at any time, "
                            + "you wish to end the game, enter 'exit'");

        Scanner sc = new Scanner(System.in);

        //Player name
        System.out.print("Please enter your player name: ");
        checkExit(sc);
        String name = sc.next();
        sc.nextLine(); //read empty newline character
        System.out.println("Your name is " + name);

        //Player skills
        System.out.println("Please select your skill assignments for "
            + "Pilot Skill, Fighter Skill, Trader Skill, and Engineer Skill. "
            + "Your total skill points must add to 16. Example: '10 0 4 2'");

        boolean validSkills = false;
        int[] skills = new int[4];
        do {
            checkExit(sc);
            String skillsLine = sc.nextLine();
            String[] skillsString = skillsLine.trim().split(" ");

            int i;
            int sum = 0;
            boolean invalid = false;
            for (i = 0; i < skillsString.length && i < 4 && !invalid; i++) {
                try {
                    skills[i] = Integer.parseInt(skillsString[i]);
                    if (skills[i] < 0) {
                        System.out.println("Error: inputs must be positive. "
                                            + "Please try again.");
                        invalid = true;
                    }
                    sum += skills[i];
                } catch (NumberFormatException e) {
                    System.out.println("Error: input must be valid integers, "
                                        + "separated by whitespaces. "
                                        + "Please try again.");
                    invalid = true;
                }
            }

            if (invalid) {
                continue;
            } else if (i != 4) {
                System.out.println("Error: input must have four numbers. "
                                    + "Please try again.");
            } else if (sum == skillsTotal) {
                validSkills = true;
                System.out.println("Your skills are: \nPilot - " + skills[0]
                    + "\nFighter - " + skills[1] + "\nTrader - " + skills[2]
                    + "\nEngineer - " + skills[3]);
            } else {
                System.out.println("Error: Your total skills must add to 16. "
                                    + "Please try again.");
            }
        } while (!validSkills);

        //Game difficulty
        System.out.println("Please select your difficulty: Easy, Medium, "
                                                            + "or Hard");
        checkExit(sc);
        String difficulty = sc.next();

        //Initialize player and game
        Player player = new Player(name, skills[0], skills[1], skills[2],
                                    skills[3], difficulty);
        Universe universe = Universe.getInstance();
        universe.generateUniverse();
        System.out.println("Welcome to Space Trader!");
    }
}
