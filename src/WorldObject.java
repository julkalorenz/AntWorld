import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * An object of the ant world, has coordinates, label, larvaeAmount, neighbours, keeps track of ants currently at the object.
 */
public class WorldObject {
    private final int label;
    private int coordinateX;
    private int coordinateY;
    private ArrayList<WorldObject> neighbours;
    private int larvaeAmount;
    private String objectType = "World object";
    private ArrayList<Ant> antsAtObject;
    private ArrayList<Ant> redAnts;
    private ArrayList<Ant> blueAnts;

    /**
     * Constructor
     * @param x x coordinate
     * @param y y coordinate
     * @param z label of the object
     */
    WorldObject(int x, int y, int z){
        coordinateX = x;
        coordinateY = y;
        label = z;
        Random rand = new Random();
        larvaeAmount = rand.nextInt(200) + 1;
        antsAtObject= new ArrayList<>();
        redAnts = new ArrayList<>();
        blueAnts = new ArrayList<>();
        neighbours = new ArrayList<>();
    }

    /**
     *
     * @return a pair of coordinates
     */
    public int[] getCoordinates() {
        return new int[]{coordinateX, coordinateY};
    }

    /**
     *
     * @return the label of an object
     */
    public int getLabel() { return label; }

    /**
     *
     * @return the larvae amount at an object
     */
    public synchronized int getLarvaeAmount(){
        return larvaeAmount;
    }

    /**
     *
     * @param amount amount of larvae to be assigned to an object
     */
    public synchronized void setLarvaeAmount(int amount){
        larvaeAmount = amount;
    }

    /**
     * Changes the amount of larvae at an object, can either increase or decrease the amount depending on the parameter.
     * @param change the value that larvae amount at an object will be changed by, can be positive or negative
     */
    public synchronized void changeLarvaeAmount(int change) {
        larvaeAmount += change;
    }

    /**
     *
     * @return a string that is the type of object
     */
    public String getObjectType() { return objectType; }

    /**
     *
     * @return an ArrayList of ants present at an object
     */
    public ArrayList<Ant> getAntsAtObject() {
        return antsAtObject;
    }

    /**
     *
     * @param ant an ant that will be added to the ArrayList of ants and to the ArrayList of blue/red ants
     */
    public void addAntAtObject(Ant ant) {
        antsAtObject.add(ant);
        if (Objects.equals(ant.getColor(), "red")){
            redAnts.add(ant);
        } else {
            blueAnts.add(ant);
        }
    }

    /**
     *
     * @param ant an ant that will be removed from the ArrayList of ants and from the ArrayList of blue/red ants
     */
    public void removeAntAtObject(Ant ant) {
        antsAtObject.remove(ant);
        if (Objects.equals(ant.getColor(), "red")){
            redAnts.remove(ant);
        } else {
            blueAnts.remove(ant);
        }
    }

    /**
     *
     * @return the ArrayList of blue ants
     */
    public ArrayList<Ant> getBlueAnts() {
        return blueAnts;
    }
    /**
     *
     * @return the ArrayList of red ants
     */
    public ArrayList<Ant> getRedAnts() {
        return redAnts;
    }

    /**
     *
     * @return the nr of ants present at this object
     */
    public int getNumberOfAntsAtObject() {
        return antsAtObject.size();
    }

    /**
     *
     * @return the ArrayList of WorldObjects that are the neighbours of an object
     */
    public ArrayList<WorldObject> getNeighbours(){
        return neighbours;
    }

    /**
     *
     * @param neighbour WorldObject
     */
    public void addNeighbour(WorldObject neighbour){
        neighbours.add(neighbour);
    }

    /**
     *
     * @return a string containing information about an object.
     */
    public String getInformation() {
        return String.format("%s (%d) - larvae amount : %d, red ants: %d, blue ants: %d", getObjectType(), getLabel(), getLarvaeAmount(), redAnts.size(), blueAnts.size());
    }
}
