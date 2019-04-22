import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Universe {
    private List<String> systems;
    private List<Location> locations;
    private List<TechLevel> actualLevels;
    private List<Resources> actualResources;
    private List<SolarSystem> solarSystems;
    private final int MAXSOLARSYSTEM = 10;

    private static Universe universe = new Universe();

    private Universe() {
        systems = new ArrayList<String>();
        locations = new ArrayList<Location>();
    }

    public static Universe getInstance() {
        return universe;
    }

    public static Universe getUniverse() {
        return universe;
    }

    public static void setUniverse(Universe universe) {
        Universe.universe = universe;
    }

    //universe creation
    public void generateSystemNames() {
        int max = solarSystemNames.size() - 1;
        int min = 0;
        Set<String> systemSet = new HashSet<>();

        while (systemSet.size() != MAXSOLARSYSTEM) {
            int num = (int) (Math.random() * ((max - min) + 1)) + min;
            systemSet.add(solarSystemNames.get(num));
        }
        systems = new ArrayList<>(systemSet);
    }

    public void scatterSystemLocations() {
        int max = 150;
        int min = 0;
        Set<Location> locationSet = new HashSet<Location>();
        while (locationSet.size() != MAXSOLARSYSTEM) {
            int x = (int) (Math.random() * ((max - min) + 1)) + min;
            int y = (int) (Math.random() * ((max - min) + 1)) + min;
            locationSet.add(new Location(x, y));
        }
        locations = new ArrayList<Location>(locationSet);
    }

    public void generateTechLevels() {
        List<TechLevel> possibleLevels = Arrays.asList(TechLevel.values());
        actualLevels = new ArrayList<TechLevel>();

        for (int i = 0; i < MAXSOLARSYSTEM; i++) {
            actualLevels.add(possibleLevels.get((int) (Math.random()
                                            * (TechLevel.values().length))));
        }
    }

    public void generateResources() {
        List<Resources> possibleResources = Arrays.asList(Resources.values());
        actualResources = new ArrayList<Resources>();

        for (int i = 0; i < MAXSOLARSYSTEM; i++) {
            actualResources.add(possibleResources.get((int) (Math.random()
                                            * (Resources.values().length))));
        }
    }

    public void generateUniverse() {
        generateSystemNames();
        scatterSystemLocations();
        generateTechLevels();
        generateResources();
        solarSystems = new ArrayList<>();

        Iterator<String> it = systems.iterator();
        Iterator<Location> loc = locations.iterator();
        String content = "";
        int i = 0;
        while (it.hasNext()) {
            String sys = it.next();
            Location locat = loc.next();
            content += "System name: " + sys + "\n";
            content += "Location: " + locat + "\n";
            content += "Tech Level: " + actualLevels.get(i).getName() + "\n";
            content += "Resources: " + actualResources.get(i).getName() + "\n \n";
            solarSystems.add(new SolarSystem(sys, locat,
                actualLevels.get(i), actualResources.get(i)));
            i++;
        }

        File file = new File("./Universe.txt");
        try (FileWriter fout = new FileWriter(file)) {
            fout.write(content);
            fout.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    //getters and setters
    public List<String> getSystems() {
        return new ArrayList<String>(systems);
    }

    public void setSystems(List<String> systems) {
        this.systems = systems;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<TechLevel> getActualLevels() {

        return actualLevels;
    }

    public void setActualLevels(List<TechLevel> actualLevels) {
        this.actualLevels = actualLevels;
    }

    public List<Resources> getActualResources() {
        return actualResources;
    }

    public void setActualResources(List<Resources> actualResources) {
        this.actualResources = actualResources;
    }

    public List<SolarSystem> getSolarSystems() {
        return solarSystems;
    }

    public List<String> getSolarSystemNames() {
        return solarSystemNames;
    }

    public int getMAXSOLARSYSTEM() {
        return MAXSOLARSYSTEM;
    }

    //A very long list of possible solar system names
    private ArrayList<String> solarSystemNames = new ArrayList<>(Arrays.asList(
        "Acamar",
        "Adahn",         // The alternate personality for The Nameless One in "Planescape: Torment"
        "Aldea",
        "Andevian",
        "Antedi",
        "Balosnee",
        "Baratas",
        "Brax",          // One of the heroes in Master of Magic
        "Bretel",        // This is a Dutch device for keeping your pants up.
        "Calondia",
        "Campor",
        "Capelle",       // The city I lived in while programming this game
        "Carzon",
        "Castor",        // A Greek demi-god
        "Cestus",
        "Cheron",
        "Courteney",     // After Courteney Cox…
        "Daled",
        "Damast",
        "Davlos",
        "Deneb",
        "Deneva",
        "Devidia",
        "Draylon",
        "Drema",
        "Endor",
        "Esmee",         // One of the witches in Pratchett's Discworld
        "Exo",
        "Ferris",        // Iron
        "Festen",        // A great Scandinavian movie
        "Fourmi",        // An ant, in French
        "Frolix",        // A solar system in one of Philip K. Dick's novels
        "Gemulon",
        "Guinifer",      // One way of writing the name of king Arthur's wife
        "Hades",         // The underworld
        "Hamlet",        // From Shakespeare
        "Helena",        // Of Troy
        "Hulst",         // A Dutch plant
        "Iodine",        // An element
        "Iralius",
        "Janus",         // A seldom encountered Dutch boy's name
        "Japori",
        "Jarada",
        "Jason",         // A Greek hero
        "Kaylon",
        "Khefka",
        "Kira",          // My dog's name
        "Klaatu",        // From a classic SF movie
        "Klaestron",
        "Korma",         // An Indian sauce
        "Kravat",        // Interesting spelling of the French word for "tie"
        "Krios",
        "Laertes",       // A king in a Greek tragedy
        "Largo",
        "Lave",          // The starting system in Elite
        "Ligon",
        "Lowry",         // The name of the "hero" in Terry Gilliam's "Brazil"
        "Magrat",        // The second of the witches in Pratchett's Discworld
        "Malcoria",
        "Melina",
        "Mentar",        // The Psilon home system in Master of Orion
        "Merik",
        "Mintaka",
        "Montor",        // A city in Ultima III and Ultima VII part 2
        "Mordan",
        "Myrthe",        // The name of my daughter
        "Nelvana",
        "Nix",           // An interesting spelling of a word meaning "nothing" in Dutch
        "Nyle",          // An interesting spelling of the great river
        "Odet",
        "Og",            // The last of the witches in Pratchett's Discworld
        "Omega",         // The end of it all
        "Omphalos",      // Greek for navel
        "Orias",
        "Othello",       // From Shakespeare
        "Parade",        // This word means the same in Dutch and in English
        "Penthara",
        "Picard",        // The enigmatic captain from ST:TNG
        "Pollux",        // Brother of Castor
        "Quator",
        "Rakhar",
        "Ran",           // A film by Akira Kurosawa
        "Regulas",
        "Relva",
        "Rhymus",
        "Rochani",
        "Rubicum",       // The river Ceasar crossed to get into Rome
        "Rutia",
        "Sarpeidon",
        "Sefalla",
        "Seltrice",
        "Sigma",
        "Sol",           // That's our own solar system
        "Somari",
        "Stakoron",
        "Styris",
        "Talani",
        "Tamus",
        "Tantalos",      // A king from a Greek tragedy
        "Tanuga",
        "Tarchannen",
        "Terosa",
        "Thera",         // A seldom encountered Dutch girl's name
        "Titan",         // The largest moon of Jupiter
        "Torin",         // A hero from Master of Magic
        "Triacus",
        "Turkana",
        "Tyrus",
        "Umberlee",      // A god from AD&D, which has a prominent role in Baldur's Gate
        "Utopia",        // The ultimate goal
        "Vadera",
        "Vagra",
        "Vandor",
        "Ventax",
        "Xenon",
        "Xerxes",        // A Greek hero
        "Yew",           // A city which is in almost all of the Ultima games
        "Yojimbo",       // A film by Akira Kurosawa
        "Zalkon",
        "Zuul"));
}
