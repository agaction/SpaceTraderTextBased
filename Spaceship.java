import java.util.HashMap;
import java.util.Map;

public enum Spaceship {
    FLEA("Flea", 20, 10, 2),
    Gnat("Gnat", 15, 15, 7),
    Firefly("Firefly", 17, 20, 7),
    Mosquito("Mosquito", 13, 15, 25),
    Bumblebee("Bumblebee", 15, 25, 10),
    Beetle("Beetle", 14, 50, 5),
    Hornet("Hornet", 16, 20, 16),
    Grasshopper("Grasshopper", 15, 30, 12),
    Termite("Termite", 13, 60, 20),
    Wasp("Wasp", 14, 35, 20);

    private String name;
    private int hullStrength;
    private int parsecs;
    private int fuelLevel;
    private int cargoMax;
    private Integer quantity;
    private HashMap<TradeGood, Integer> cargoList;
    private int sizeCargoList;

    Spaceship(String name, int parsecs, int cargoMax, int hullStrength){
        this.name = name;
        this.parsecs = parsecs;
        this.fuelLevel = parsecs;
        this.cargoMax = cargoMax;
        this.hullStrength = hullStrength;
        quantity = 0;
        cargoList = new HashMap<TradeGood, Integer>();
    }

    public boolean canBuy(int quantity){
        return this.quantity + quantity <= cargoMax;
    }

    public void addToList(TradeGood t, int quantity) {
        this.quantity += quantity;
        cargoList.put(t, quantity);
    }

    public void remove(TradeGood t, int quantity) {
        int currQuantity = cargoList.get(t);
        if (quantity >= currQuantity) {
            cargoList.remove(t);
        } else {
            cargoList.put(t, currQuantity - quantity);
        }
        this.quantity -= quantity;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParsecs(int parsecs) {
        this.parsecs = parsecs;
    }

    public int getParsecs(){
        return parsecs;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public int getCargoMax() {
        return cargoMax;
    }

    public void setCargoMax(int cargoMax) {
        this.cargoMax = cargoMax;
    }

    public int getHullStrength() {
        return hullStrength;
    }

    public void setHullStrength(int hullStrength) {
        this.hullStrength = hullStrength;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCargoList(HashMap<TradeGood, Integer> cargoList) {
        this.cargoList = cargoList;
    }

    public void setCargoLoad(HashMap<TradeGood, Integer> map) {
        cargoList = map;
    }

    public Map<TradeGood, Integer> getCargoList() {
        return cargoList;
    }
}
