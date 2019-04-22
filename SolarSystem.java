import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SolarSystem implements Serializable {
    private final String systemName;
    private Location location;
    private TechLevel techLevel;
    private Resources resources;
    private final List<TradeGood> listofResources;

    public SolarSystem(String systemName, Location loc, TechLevel tl, Resources pol) {
        this.systemName = systemName;
        this.location = loc;
        this.techLevel = tl;
        this.resources = pol;
        this.location = loc;
        this.listofResources = findGoodsAvailabletoBuy();
    }

    public SolarSystem() {
        systemName = "";
        listofResources = null;
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

    public List<TradeGood> getListofResources() {
        return listofResources;
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
}

