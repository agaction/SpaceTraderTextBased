import java.io.*;
import java.util.Scanner;
import java.util.Map;
import java.util.List;

public class SpaceTraderDriver {

    final static int skillsTotal = 16;
    final static String prompt = "What would you like to do?";

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
     * Method to bring up help menu
     */
    public static void helpMenu() {
        System.out.println("HELP MENU\n-------------------------------\n"
            + "'c' - See Credits\n"
            + "'ch' - See Cargo Hold\n"
            + "'b' - Buy Goods\n"
            + "'s' - Sell Goods\n"
            + "'m' - See map of nearby systems\n"
            + "'w' - Warp to another system\n"
            + "'h' - Help menu\n"
            + "'exit' - Exit game\n"
            + "-------------------------------");
    }

    /**
     * Helper method to check if a user-inputted good is in the list of
     * available goods (case insensitive) to buy on a system
     * @param  good the good name to check for
     * @param  listGoods the list of possible goods
     * @return true if the list contains the select good, false otherwise
     */
    public static boolean containsGood(String good, List<TradeGood> listGoods) {
        for (TradeGood tg : listGoods) {
            if (tg.getNameLowercase().equals(good.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to convert the name of a good to its corresponding
     * TradeGood Enum value, or return null if the good is not found in that
     * System's list of goods.
     * @param good the good name to check for
     * @param  listGoods the list of possible goods
     * @return corresponding TradeGood if the list contains the select good,
     * null otherwise.
     */
    public static TradeGood nameToGood(String good, List<TradeGood> listGoods) {
        for (TradeGood tg : listGoods) {
            if (tg.getNameLowercase().equals(good.toLowerCase())) {
                return tg;
            }
        }
        return null;
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
        SolarSystem system = universe.getSolarSystems().get(0);
        player.setSystem(system);
        Spaceship ship = player.getShip();
        Transaction market = new Transaction();

        System.out.println("Welcome to Space Trader!");
        System.out.println("At any time, enter 'h' to bring up a help menu "
            + "with all available commands.");
        System.out.println("You are on the system " + system.getSystemName()
            + ". " + prompt);

        String input = sc.next();
        while (true) {
            switch(input) {

            //see current credits
            case "c":
            System.out.println("You currently have " + player.getCredits()
                                + " credits. " + prompt);
            input = sc.next();
            break;

            //see current cargo holds
            case "ch":
            System.out.println("You currently have " + ship.getQuantity()
                                + " goods in holds, and can hold "
                                + ship.getCargoMax() + " goods at maximum.");
            if (ship.getQuantity() != 0) {
                for (Map.Entry<TradeGood, Integer> entry :
                                        ship.getCargoList().entrySet()) {
                        System.out.println(entry.getKey() + " - "
                                                + entry.getValue());
                }
            }
            System.out.println(prompt);
            input = sc.next();
            break;

            //buy goods
            case "b":
            System.out.println("You currently have " + player.getCredits()
                + " credits. What would you like to buy? Please enter"
                + " a good name and a quantity to buy, separated by a"
                + " whitespace. Example: 'Water 5'\nAvailable Goods:");
            for (TradeGood t : system.getListOfGoods()) {
                System.out.println(t);
            }

            sc.nextLine(); //check if needed
            boolean contBuying = true;
            String goodName;
            int quantity = 0;
            TradeGood buyGood;
            boolean invalid2;
            do {
                invalid2 = false;
                checkExit(sc);
                if (sc.hasNext("q")) {
                    System.out.println("Quitting from buy. ");
                    sc.nextLine();
                    contBuying = false;
                    continue; // I normally consider this bad practice,
                              // but it was not worth the time restructuring the
                              // loop to avoid this or a continue statement
                }

                String buyInput = sc.nextLine();
                String[] buyArr = buyInput.split(" ");
                goodName = buyArr[0];
                buyGood = nameToGood(goodName, system.getListOfGoods());
                if (buyGood == null) {
                    System.out.println("Error: Input must have a valid good"
                        + " that can be bought on this system."
                        + " Please try again.");
                    invalid2 = true;
                } else if (buyArr.length < 2) {
                    System.out.println("Error: you must input a good name "
                                    + "and a quantity. Please try again.");
                    invalid2 = true;
                }
                if (!invalid2) {
                    try {
                        quantity = Integer.parseInt(buyArr[1]);
                        if (quantity <= 0) {
                            System.out.println("Error: quantity must be "
                                + "positive. Please try again.");
                            invalid2 = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: quantity must be a valid "
                            + "integer. Please try again.");
                        invalid2 = true;
                    }
                }

                if(!invalid2) {
                    market.buy(player, buyGood, quantity);
                }
            } while (contBuying);

            System.out.println(prompt);
            input = sc.next();
            break;

            //sell goods
            case "s":
            System.out.println("You currently have " + player.getCredits()
                + " credits.");
            if (ship.getQuantity() != 0) {
                for (Map.Entry<TradeGood, Integer> entry : ship.getCargoList().entrySet()) {
                    System.out.println(entry.getKey() + " - "
                        + entry.getValue());
                }
            System.out.println("What would you like to sell?\n"
                + "Please enter the name of a good and a quantity "
                + "to sell, separated by a whitespace. "
                + "Example: ROBOTS 10");
            }
            input = sc.next();
            break;

            //map
            case "m":
            System.out.println("This is not implemented yet. What else "
                + "would you like to do?");
            input = sc.next();
            break;

            //warp
            case "w":
            System.out.println("This is not implemented yet. What else "
                + "would you like to do?");
            input = sc.next();
            break;

            //help menu
            case "h":
            helpMenu();
            System.out.println(prompt);
            input = sc.next();
            break;

            //exit
            case "exit":
            System.out.println("Exiting game.");
            System.exit(0);
            break;

            //invalid input
            default:
            System.out.println("Invalid input. Please Try again.");
            input = sc.next();
            break;
            }
        }
    }
}
