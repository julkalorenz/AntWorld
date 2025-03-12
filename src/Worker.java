import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * A blue worker ant, can collect larvae and attack red ants.
 */
public class Worker extends Ant{
    // blue ant - collects larvae, attacks red ants
    private int collectedLarvae;
    Worker(String n, int s, int h, String c, Anthill anthill){
        super(n, s, h, c, anthill);
        this.collectedLarvae = 0;
    }
    @Override
    public String getInformation() {
        if (this.getIsDead()){
            return String.format("Worker: %s ant - health: %d DEAD", getColor(), getHealth());
        }
        if (this.getIsBackHome()) {
            return String.format("Worker: %s ant - health: %d. Returned home safely!", getColor(), getHealth());
        }
        return String.format("Worker: %s ant - health: %d", getColor(), getHealth());
    }

    /**
     *
     * @param amount amount of larvae collectedLarvae will be set to
     */
    public void setCollectedLarvae(int amount) {
        collectedLarvae = amount;
    }

    /**
     * @see Collector#collect(WorldObject)
     * @param object
     */
    public void collect(WorldObject object) {
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
     * @see Soldier#attack(Ant)
     * @param otherAnt
     */
    public void attack(Ant otherAnt) {
        otherAnt.getHit();
    }

    /**
     * @see Ant#walk()
     * Also, can attack and collect larvae.
     * Goes back if it attacks or collectedLarvae == strength.
     * @throws InterruptedException
     */
    @Override
    public void walk() throws InterruptedException {
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
            if (Objects.equals(currentPosition.getObjectType(), "Stone")){
                if (!currentPosition.getRedAnts().isEmpty()) {
                    Ant otherAnt = currentPosition.getRedAnts().get(0);
                    attack(otherAnt);
                }
                returnHome = true;
                break;
            } else {
                // if Leaf - there is a 30% chance an ant will discover another ant and attack them
                double randomValue = rand.nextDouble();
                double attackProb  = 0.3;
                if (randomValue < attackProb){
                    if (!currentPosition.getRedAnts().isEmpty()) {
                        Ant otherAnt = currentPosition.getRedAnts().get(0);
                        attack(otherAnt);
                        returnHome = true;
                        break;
                    }
                }
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
     * @see Collector#walkHome(ArrayList)
     * @param path the path constructed by the walk() method
     */
    @Override
    public void walkHome(ArrayList<WorldObject> path) {
        super.walkHome(path);
        home.changeLarvaeAmount(collectedLarvae);
        this.setCollectedLarvae(0);
    }

    /**
     * @see Collector#die()
     */
    @Override
    public void die(){
        super.die();
        currentPosition.changeLarvaeAmount(collectedLarvae);
        this.setCollectedLarvae(0);
    }
}
