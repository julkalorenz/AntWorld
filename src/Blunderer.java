import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A red blunderer ant, it's an ant that collects larvae but can lose some while returning home.
 */
public class Blunderer extends Collector {
    // red ant - collects larvea just like collector, while returning home drops random amount of larvea with certain probability
    /**
     * Constructor
     * @param n The name that will be given to an Ant object
     * @param s The strength of the ant, how many larvae it can carry
     * @param h The health of the ant
     * @param c The color
     * @param anthill The anthill that is a home of this ant
     */
    Blunderer(String n, int s, int h, String c, Anthill anthill){
        super(n, s, h, c, anthill);
    }
    @Override
    public String getInformation() {
        if (this.getIsDead()){
            return String.format("Blunderer: %s ant - health: %d DEAD", getColor(), getHealth());
        }
        if (this.getIsBackHome()) {
            return String.format("Blunderer: %s ant - health: %d. Returned home safely!", getColor(), getHealth());
        }
        return String.format("Blunderer: %s ant - health: %d", getColor(), getHealth());
    }

    /**
     * @see Collector
     * The same logic as Collector ant but while returning home can lose some amount of larvae with a certain probability.
     * @param path the path constructed by the walk() method
     */
    @Override
    public void walkHome(ArrayList<WorldObject> path) {
        Collections.reverse(path);
        for (WorldObject obj : path) {
            if (getHealth() == 0) {
                die();
                break;
            }
            try {
                sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentPosition.removeAntAtObject(this);
            currentPosition = obj;
            dotRepresentation.dotWalk(currentPosition.getCoordinates()[0], currentPosition.getCoordinates()[1]);
            currentPosition.addAntAtObject(this);
            double rand = Math.random();
            if (rand < 0.5) {
                if (collectedLarvae < 1) {
                    continue;
                }
                Random random = new Random();
                int randLarveaAmount = random.nextInt(this.collectedLarvae);
                currentPosition.changeLarvaeAmount(randLarveaAmount);
                this.collectedLarvae -= randLarveaAmount;
            }
        }
        if (!getIsDead()){
            home.changeLarvaeAmount(collectedLarvae);
            this.setCollectedLarvae(0);
            setIsBackHome(true);
        }
    }

}
