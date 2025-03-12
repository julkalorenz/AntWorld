import java.util.ArrayList;

/**
 * The home of ants, it has attributes like: color, coordinates, array of ants.
 */
public class Anthill extends WorldObject{
    private final String color;
    private final String objectType = "Anthill";
    private ArrayList<Ant> ants = new ArrayList<>();

    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z label
     * @param col color of the anthill
     */
    Anthill(int x, int y, int z, String col){
        super(x, y, z);
        this.color = col;
        this.setLarvaeAmount(0);
    }

    /**
     *
     * @return the color of an anthill
     */
    public String getColor() { return color; }
    public String getObjectType() { return objectType; }

    /**
     *
     * @param ant an ant that will be added to the ArrayList of ants
     */
    public void addAnt(Ant ant) {
        ants.add(ant);
    }

    /**
     *
     * @param ant an ant that will be removed from the ArrayList of ants
     */
    public void removeAnt(Ant ant) {
        ants.remove(ant);
    }
    @Override
    public String getInformation() {
        return String.format("%s anthill - collected larvea amount: %d", color, getLarvaeAmount());
    }
}
