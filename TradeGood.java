import java.util.Enumeration;
import java.util.Random;

public enum TradeGood {
    WATER(30, 0, 0, 2, 3, 4),
    Furs(250, 0, 0, 0, 10, 10),
    Food(100, 1, 0, 1, 5, 5),
    Ore(350, 2, 2, 3, 20, 10),
    Games(250, 3, 1, 6, -10, 5),
    Firearms(1250, 3, 1, 5, -75, 100),
    Medicine(650, 4, 1, 6, -20, 10),
    Machines(900, 4, 3, 5, -30, 5),
    Narcotics(3500, 5, 0, 5, -125, 150),
    Robots(5000, 6, 4, 7, -150, 100);

    private final double basePrice;

    private final int MTLP; // Minimum Tech Level to produce this resource
    private final int MTLU; // Minimum Tech Level to use this resource
    private final int TTP;  // Tech Level which produces the most of this item
    private final int IPL;  // Price increase per tech level
    private final int var;  // maximum percentage that the price can vary

    private boolean IE; // Radical price increase event
    private boolean CR; // Radical price decrease event
    private boolean ER; // Condition when resource is expensive
    private int MTL;    // Min price offered in space trade with random trader
    private int MTH;    // Max price offered in space trade with random trader

    private double currentPrice;

    TradeGood(double basePrice, int MTLP, int MTLU, int TTP, int IPL, int Var) {
        this.MTLP = MTLP;
        this.MTLU = MTLU;
        this.TTP = TTP;
        this.basePrice = basePrice;
        this.IPL = IPL;
        this.var = Var;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getMTLP() {
        return MTLP;
    }

    public int getMTLU() {
        return MTLU;
    }

    public int getTTP() {
        return TTP;
    }

    public int getIPL() {
        return IPL;
    }

    public int getVar() {
        return var;
    }

    private double getVariance() {
        double number = basePrice * (((double) (new Random().nextInt(var)) / 100));
        int random = new Random().nextInt(2);
        if (random == 0) {
            return -1 * number;
        }
        return number;
    }

    public String getNameLowercase() {
        return name().toLowerCase();
    }

    public void generatePrice(SolarSystem s) {
        currentPrice = basePrice + (IPL * (s.getTechLevel().getRank() - MTLP))
                                    + getVariance();
        if (currentPrice < 0) {
            System.out.println("ranks: " + s.getTechLevel().getRank() + ", " + MTLP);
        }
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public String toString() {
        return name() + ": $" + currentPrice;
    }
}
