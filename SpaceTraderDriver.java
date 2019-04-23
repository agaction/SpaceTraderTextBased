import java.io.*;
import java.util.Scanner;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

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
            + "'i' - Player Information\n"
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
     * Helper method to check if a user-inputted system is in the list of
     * available systems (case insensitive) to travel to
     * @param  target  target system
     * @param  systems list of available systems
     * @return true if target is in list, false otherwise
     */
    public static boolean containsSystem(String target,
                                                    List<SolarSystem> systems) {
        for (SolarSystem s : systems) {
            if (s.getSystemName().toLowerCase().equals(target.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to convert the name of a system to its corresponding
     * SolarSystem, or return null if the system is not found in that
     * System's list of goods.
     * @param  target  target system
     * @param  systems list of available systems
     * @return correct SolarSystem if found, null otherwise
     */
    public static SolarSystem nameToSystem(String target,
                                                    List<SolarSystem> systems) {
        for (SolarSystem s : systems) {
            if (s.getSystemName().toLowerCase().equals(target.toLowerCase())) {
                return s;
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
        SolarSystem startSystem = universe.getSolarSystems().get(0);
        player.setSystem(startSystem);
        Spaceship ship = player.getShip();
        Transaction market = new Transaction();

        System.out.println("Welcome to Space Trader!");
        System.out.println("At any time, enter 'h' to bring up a help menu "
            + "with all available commands.");
        System.out.println("You are on the system " + startSystem.getSystemName()
            + ". " + prompt);

        String input = sc.next();
        while (true) {
            switch(input) {

            //see player information
            case "i":
            System.out.print("PLAYER INFORMATION:\n You are on the planet "
                                + player.getSystem().getSystemName()
                                + ". You have " + player.getCredits()
                                + " credits, and " + ship.getFuelLevel()
                                + " fuel remaining for travel. ");
            System.out.println("You currently have " + ship.getQuantity()
                                + " goods in holds, and can hold "
                                + ship.getCargoMax() + " goods at maximum. "
                                + "These are the goods you have in hold:");
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

            //see current cargo holds
            case "ch":

            System.out.println(prompt);
            input = sc.next();
            break;

            //buy goods
            case "b":
            System.out.println("You currently have " + player.getCredits()
                + " credits. What would you like to buy? Please enter"
                + " a good name and a quantity to buy, separated by a"
                + " whitespace. Example: 'Water 5'\nAvailable Goods:");
            for (TradeGood t : player.getSystem().getListOfGoods()) {
                System.out.println(t);
            }

            sc.nextLine(); //read empty newline character
            boolean contBuying = true;
            String goodName;
            int quantity = 0;
            TradeGood buyGood;
            boolean invalid2;
            do {
                invalid2 = false;
                checkExit(sc);
                if (sc.hasNext("q")) {
                    System.out.println("Quitting from buy.");
                    sc.nextLine();
                    contBuying = false;
                    continue; // I normally consider this bad practice,
                              // but it was not worth the time restructuring the
                              // loop to avoid this or a break statement
                }

                String buyInput = sc.nextLine();
                String[] buyArr = buyInput.split(" ");
                goodName = buyArr[0];
                buyGood = nameToGood(goodName,
                                        player.getSystem().getListOfGoods());
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
                + " credits. Cargo: ");
            if (ship.getQuantity() != 0) {
                for (Map.Entry<TradeGood, Integer> entry :
                                            ship.getCargoList().entrySet()) {
                    System.out.println(entry.getKey() + " - "
                        + entry.getValue());
                }
            System.out.println("What would you like to sell?\n"
                + "Please enter the name of a good and a quantity "
                + "to sell, separated by a whitespace. "
                + "Example: ROBOTS 10");
            } else {
                System.out.println("EMPTY - you have no goods to sell!");
                System.out.println("What else would you like to do?");
                input = sc.next();
                break; //Is there a better way? probably...
            }

            sc.nextLine(); //read empty newline character
            boolean contSelling = true;
            String goodNameSell;
            int quantitySell = 0;
            TradeGood sellGood;
            boolean invalid3;
            do {
                invalid3 = false;
                checkExit(sc);
                if (sc.hasNext("q")) {
                    System.out.println("Quitting from sell.");
                    sc.nextLine();
                    contSelling = false;
                    continue; // I normally consider this bad practice,
                              // but it was not worth the time restructuring the
                              // loop to avoid this or a break statement
                }

                String sellInput = sc.nextLine();
                String[] sellArr = sellInput.split(" ");
                goodNameSell = sellArr[0];
                sellGood = nameToGood(goodNameSell,
                                        player.getSystem().getListOfGoods());
                if (sellGood == null) {
                    System.out.println("Error: Input must have a valid good"
                        + " that can be sold on this system."
                        + " Please try again.");
                    invalid3 = true;
                } else if (sellArr.length < 2) {
                    System.out.println("Error: you must input a good name "
                                    + "and a quantity. Please try again.");
                    invalid3 = true;
                }
                if (!invalid3) {
                    try {
                        quantitySell = Integer.parseInt(sellArr[1]);
                        if (quantitySell <= 0) {
                            System.out.println("Error: quantity must be "
                                + "positive. Please try again.");
                            invalid3 = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: quantity must be a valid "
                            + "integer. Please try again.");
                        invalid3 = true;
                    }
                }

                if(!invalid3) {
                    market.sell(player, sellGood, quantitySell);
                }
            } while (contSelling);

            System.out.println(prompt);
            input = sc.next();
            break;

            //galaxy map of nearby systems
            case "m":
            System.out.println("Nearby systems to travel to: ");
            List<SolarSystem> nearbySystems = Travel.getTravelList(player, ship,
                                                    universe.getSolarSystems());
            System.out.println("Size: " + nearbySystems.size());
            for (SolarSystem s : nearbySystems) {
                System.out.println(s);
            }
            System.out.println(prompt);
            input = sc.next();
            break;

            //warp
            case "w":
            System.out.println("What system would you like to warp to? "
                + "Please enter a valid system listed on the nearby map. "
                + "If you need to see the map, press 'q' to quit, and then 'm'"
                + " to pull up the map.");

            if (sc.hasNext("q")) {
                System.out.println("Quitting from warp.");
                break;
            } else {
                List<SolarSystem> nearbySystemsT = Travel.getTravelList(player,
                                            ship, universe.getSolarSystems());
                String target = sc.next();
                SolarSystem targetSystem = nameToSystem(target, nearbySystemsT);
                if (targetSystem == null) {
                    System.out.println("Error: Input must be a valid system"
                        + " that can be travelled to. Please try again.");
                } else {
                    Travel.warp(player, ship, targetSystem);
                    System.out.println("Restocking fuel automatically...");
                    player.setCredits(player.getCredits() -
                                    (ship.getParsecs() - ship.getFuelLevel()));
                    ship.setFuelLevel(ship.getParsecs());
                    System.out.println("You now have " + player.getCredits()
                        + " credits remaining.");
                    System.out.println(prompt);
                }
            }
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
