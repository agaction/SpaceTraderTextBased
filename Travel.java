import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class Travel implements Serializable {

    public static int calcDistance(SolarSystem sys1, SolarSystem sys2) {
        int difX = sys1.getLocation().getX() - sys2.getLocation().getX();
        int difY = sys1.getLocation().getY() - sys2.getLocation().getY();
        double distance = Math.sqrt(difX * difX + difY * difY);
        return (int) distance;
    }

    public static List<SolarSystem> getTravelList(Player p, Spaceship ship,
                                                List<SolarSystem> currPlanets) {
        List<SolarSystem> travelList = new ArrayList<>();
        for (int i = 0; i < currPlanets.size(); i++) {
            int distance = calcDistance(currPlanets.get(i), p.getSystem());
            if (distance != 0 && distance < 5 * ship.getParsecs()//magic number?
                        && ship.getFuelLevel() > distance / 5) { //magic number?
                travelList.add(currPlanets.get(i));
            } else {
                //System.out.println(calcDistance(currPlanets.get(i), p.getSystem()));
            }
        }
        return travelList;
    }

    public static void warp(Player p, Spaceship ship, SolarSystem s) {
        int distance = calcDistance(p.getSystem(), s);
        int parsecs = distance / 7; //magic number?

        if (ship.getFuelLevel() < parsecs && ship.getFuelLevel() < 0){
            System.out.println("You cannot travel to here, "
                + "there is not enough fuel! Please try again.");
        } else{
            p.setSystem(s);
            ship.setFuelLevel(ship.getFuelLevel() - parsecs);
            System.out.println("You have traveled to the system "
                + s.getSystemName() + "! You have " + ship.getFuelLevel()
                + " fuel remaining.");
        }
    }
}
