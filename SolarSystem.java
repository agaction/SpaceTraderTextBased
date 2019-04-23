import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SolarSystem implements Serializable {
    private final String systemName;
    private Location location;
    private TechLevel techLevel;
    private Resources resources;
    private final List<TradeGood> listOfGoods;

    public SolarSystem(String systemName, Location location,
                                    TechLevel techLevel, Resources resources) {
        this.systemName = systemName;
        this.location = location;
        this.techLevel = techLevel;
        this.resources = resources;
        this.listOfGoods = findGoodsAvailabletoBuy();
    }

    public SolarSystem() {
        systemName = "";
        listOfGoods = null;
    }

    public List<TradeGood> findGoodsAvailabletoBuy() {
        List<TradeGood> list = new ArrayList<TradeGood>();
        for (TradeGood tg : TradeGood.values()) {
            if (techLevel.getRank() >= tg.getMTLP()){
                tg.generatePrice(this);
                list.add(tg);
            }
        }
        return list;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public TechLevel getTechLevel() {
        return techLevel;
    }

    public String getSystemName(){
        return systemName;
    }

    public void setTechLevel(TechLevel techLevel) {
        this.techLevel = techLevel;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public List<TradeGood> getListOfGoods() {
        return listOfGoods;
    }

    @Override
    public String toString() {
        String availableGoods = "";
        for (TradeGood t : listOfGoods) {
            availableGoods += t.name() + ", ";
        }
        availableGoods = availableGoods.replaceAll(", $", "");

        return systemName + "\n - Tech Level: " + techLevel + "\n - Resources: "
        + resources + "\n - Goods available to buy: " + availableGoods;
    }
}

