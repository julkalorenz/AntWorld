import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * A red collector ant that collects larvae and goes back if strength == collected larvae.
 */
public class Collector extends Ant{
    // red ant - collects random amount of larvea at its currentPosition
    protected int collectedLarvae;

    /**
     * Constructor
     * sets the collectedLarvae to 0
     * @param n The name that will be given to an Ant object
     * @param s The strength of the ant, how many larvae it can carry
     * @param h The health of the ant
     * @param c The color
     * @param anthill The anthill that is a home of this ant
     */
    Collector(String n, int s, int h, String c, Anthill anthill){
        super(n, s, h, c, anthill);
        this.collectedLarvae = 0;
    }
    @Override
    public String getInformation() {
        if (this.getIsDead()){
            return String.format("Collector: %s ant - health: %d DEAD", getColor(), getHealth());
        }
        if (this.getIsBackHome()) {
            return String.format("Collector: %s ant - health: %d. Returned home safely!", getColor(), getHealth());
        }
        return String.format("Collector: %s ant - health: %d", getColor(), getHealth());
    }

    /**
     *
     * @param amount amount of larvae that collectedLarvae will be set to
     */
    public void setCollectedLarvae(int amount) {
        collectedLarvae = amount;
    }

    /**
     * Function to collect random amount of larvae from a WorldObject.
     * Changes the collectedLarvae and larvaeAmount at the WorldObject.
     * @param object the position from which larvae will be collected
     */
    public synchronized void collect(WorldObject object) {
        // collects a random amount of larvea from an object
        Random rand = new Random();
        int larveaAmountAtVertex = object.getLarvaeAmount();
        if (larveaAmountAtVertex < 1 ){
            return;
        }
        int randAmount = rand.nextInt(larveaAmountAtVertex+1);
        if (randAmount > object.getLarvaeAmount()) {
            randAmount = 0;
        }
        if (randAmount + collectedLarvae > getStrength()){
            randAmount = getStrength() - collectedLarvae;
        }
        collectedLarvae += randAmount;
        object.changeLarvaeAmount(-randAmount);
    }

    /**
     * @see Ant
     * The same logic as in the class Ant but also collects larvae from currentPosition,
     * and if the collectedLarvae == strength the ant goes back
     */
    @Override
    public void walk() {
        Random rand = new Random();
        ArrayList<WorldObject> path = new ArrayList<>();
        ArrayList<WorldObject> neighbours;
        currentPosition = home;
        path.add(currentPosition);
        while (!returnHome) {
            if (getHealth() == 0) {
                die();
                break;
            }
            neighbours = currentPosition.getNeighbours();
            int randomIndex = rand.nextInt(neighbours.size());
            currentPosition = neighbours.get(randomIndex);
            currentPosition.addAntAtObject(this);
            dotRepresentation.dotWalk(currentPosition.getCoordinates()[0], currentPosition.getCoordinates()[1]);
            collect(currentPosition);
            if (Objects.equals(currentPosition.getObjectType(), "Stone")){
                returnHome = true;
                break;
            }
            if (getStrength() == collectedLarvae) {
                returnHome = true;
                break;
            }
            path.add(currentPosition);
            try {
                sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            collect(currentPosition);
            currentPosition.removeAntAtObject(this);
        }
        if (!getIsDead()){
            walkHome(path);
        }
    }

    /**
     * If an ant reaches its home it drops off the larvae, i.e. changes the larvaeAmount at the anthill.
     * @param path the path constructed by the walk() method
     */
    @Override
    public void walkHome(ArrayList<WorldObject> path) {
        super.walkHome(path);
        home.changeLarvaeAmount(collectedLarvae);
        this.setCollectedLarvae(0);
    }

    /**
     * Changes value of isDead to true.
     * Removes the ant from its currentPosition and its anthill.
     * Hides the graphical representation.
     * Also, the ant drops the larvae it has collected, i.e. changes the larvaeAmount at the position of its death.
     */
    @Override
    public void die(){
        super.die();
        currentPosition.changeLarvaeAmount(collectedLarvae);
        this.setCollectedLarvae(0);
    }
}
