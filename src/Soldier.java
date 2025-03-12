import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * A red soldier ant, walks around and attacks blue ants.
 */
public class Soldier extends Ant{
    // red ant
    Soldier(String n, int s, int h, String c, Anthill anthill){
        super(n, s, h, c, anthill);
    }
    @Override
    public String getInformation() {
        if (this.getIsDead()){
            return String.format("Soldier: %s ant - health: %d DEAD", getColor(), getHealth());
        }
        if (this.getIsBackHome()) {
            return String.format("Soldier: %s ant - health: %d. Returned home safely!", getColor(), getHealth());
        }
        return String.format("Soldier: %s ant - health: %d", getColor(), getHealth());
    }

    /**
     * Evokes the getHit() method on the ant it attacks
     * @param otherAnt the ant that will get hit
     */
    public void attack(Ant otherAnt) {
        otherAnt.getHit();
    }

    /**
     *  @see Ant
     * The same logic as in class Ant, also if it attacks it has to go back.
     * If the ant is on the Stone it always tries to attack. If it's on a Leaf it has a 30% chance to discover the hiding ant.
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
            if (Objects.equals(currentPosition.getObjectType(), "Stone")) {
                // if Stone - always attack
                if (!currentPosition.getBlueAnts().isEmpty()) {
                    Ant otherAnt = currentPosition.getBlueAnts().get(0);
                    attack(otherAnt);
                }
                returnHome = true;
                break;
            } else {
                // if Leaf - there is a 30% chance an ant will discover another ant and attack them
                double randomValue = rand.nextDouble();
                double attackProb  = 0.3;
                if (randomValue < attackProb){
                    if (!currentPosition.getBlueAnts().isEmpty()) {
                        Ant otherAnt = currentPosition.getBlueAnts().get(0);
                        attack(otherAnt);
                        returnHome = true;
                        break;
                    }
                }
            }
            path.add(currentPosition);
            try {
                sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentPosition.removeAntAtObject(this);
        }
        if (!getIsDead()){
            walkHome(path);
        }
    }
}

