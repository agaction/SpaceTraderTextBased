import java.io.Serializable;

public class Player implements Serializable {

    private String characterName;
    private int pilotSkill;
    private int fighterSkill;
    private int traderSkill;
    private int engineerSkill;
    private String difficulty;

    private double credits;
    private Spaceship ship;
    private int fuelLevel;
    private Universe universe;
    private SolarSystem system;

    public Player() {

    }

    public Player(String name, int pilot, int fighter, int trader, int engineer,
                                                        String difficulty) {
        characterName = name;
        pilotSkill = pilot;
        fighterSkill = fighter;
        traderSkill = trader;
        engineerSkill = engineer;
        difficulty = difficulty;

        credits = 1000.0;
        this.ship = Spaceship.Gnat;
        fuelLevel = ship.getParsecs();
    }

    @Override
    public String toString() {
        return "Player " + characterName + " with pilot skill of " + pilotSkill
                + ", fighter skill of " + fighterSkill
                + ", trader skill of " + traderSkill
                + ", and engineer skill of " + engineerSkill;
    }

    //getters and setters
    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getPilotSkill() {
        return pilotSkill;
    }

    public void setPilotSkill(int pilotSkill) {
        this.pilotSkill = pilotSkill;
    }

    public int getFighterSkill() {
        return fighterSkill;
    }

    public void setFighterSkill(int fighterSkill) {
        this.fighterSkill = fighterSkill;
    }

    public int getTraderSkill() {
        return traderSkill;
    }

    public void setTraderSkill(int traderSkill) {
        this.traderSkill = traderSkill;
    }

    public int getEngineerSkill() {
        return engineerSkill;
    }

    public void setEngineerSkill(int engineerSkill) {
        this.engineerSkill = engineerSkill;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public Spaceship getShip() {
        return ship;
    }

    public void setShip(Spaceship ship) {
        this.ship = ship;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public SolarSystem getSystem(){
        return system;
    }

    public void setSystem(SolarSystem system) {
        this.system = system;
    }
}
