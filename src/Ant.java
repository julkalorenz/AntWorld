import java.util.*;

/**
 * Has attributes like: name, strength, health, color, currentPosition, isDead, isBackHome and dotRepresentation.
 * Ant walks randomly, remembers its path and goes back home (home anthill) in special cases.
 * It can also take damage and die.
 */
public class Ant extends Thread {
    private final String name;
    private int strength;
    private int health;
    private final String color;
    protected WorldObject currentPosition;
    protected final Anthill home;
    protected boolean returnHome;
    private boolean isDead;
    private boolean isBackHome;
    protected final Dot dotRepresentation;

    /**
     * Constructor
     * @param n The name that will be given to an Ant object
     * @param s The strength of the ant, how many larvae it can carry
     * @param h The health of the ant
     * @param c The color
     * @param anthill The anthill that is a home of this ant
     */
    Ant(String n, int s, int h, String c, Anthill anthill){
        name = n;
        strength = s;
        health = h;
        color = c;
        home = anthill;
        home.addAnt(this);
        returnHome = false;
        isDead = false;
        isBackHome = false;
        dotRepresentation = new Dot((double) home.getCoordinates()[0], (double) home.getCoordinates()[1], c);
    }

    /**
     *
     * @return the graphical representation of an ant
     */
    public Dot getDotRepresentation() {
        return dotRepresentation;
    }

    /**
     *
     * @return the name of an ant
     */
    public String getAntName()  { return name; }

    /**
     *
     * @return an ant's strength
     */
    public int getStrength() { return strength; }

    /**
     *
     * @return an ant's health
     */
    public int getHealth() { return health; }

    /**
     *
     * @return an ant's color
     */
    public String getColor() { return color; }

    /**
     *
     * @return a string that is a description on an ant
     */
    public String getInformation() {
        return String.format("%s ant - health: %d", color, health);
    }

    /**
     * Function evoked by the attacking ant that will give damage (reduce health by a random amount) of an ant
     */
    public synchronized void getHit() {
        Random random = new Random();
        int damage = random.nextInt(health+1);
        health -= damage;
    }
    /**
     * Function handling the walking of an ant.
     * While an ant doesn't have to return home.
     * Chooses random neighbour of ant's currentPosition, and changes the currentPosition to that neighbour.
     * Adds each position to a path.
     * If an ant encounters a Stone it has to go back.
     * @throws InterruptedException
     */
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
                returnHome = true;
                break;
            }
            path.add(currentPosition);
            try {
                sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentPosition.removeAntAtObject(this);
        }
        if (!isDead){
            walkHome(path);
        }
    }

    /**
     * Walks back home on the remembered path.
     * @param path the path constructed by the walk() method
     */
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
        }
        if (!isDead){
            setIsBackHome(true);
        }
    }

    /**
     *
     * @return boolean value denoting if an ant is alive or dead
     */
    public boolean getIsDead() {
        return isDead;
    }

    /**
     * Changes value of isDead to true.
     * Removes the ant from its currentPosition and its anthill.
     * Hides the graphical representation.
     */
    public void die() {
        // drop larvae - blunderer, collector, worker
        // isDead true
        isDead = true;
        // delete ant at this object
        currentPosition.removeAntAtObject(this);
        // delete ant from ants world, and anthill
        home.removeAnt(this);
        dotRepresentation.setVisible(false);
    }

    /**
     * If an ant returned home safely this method will return true
     * @return boolean value denoting if an ant returned home.
     */
    public boolean getIsBackHome() {
        return isBackHome;
    }

    /**
     *
     * @param bool true if an ant returned home
     */
    public void setIsBackHome(boolean bool) {
        isBackHome = bool;
    }


    @Override
    public void run() {
        try {
            walk();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
