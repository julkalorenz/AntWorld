/**
 * Just a regular blue ant, walks around.
 */
public class Drone extends Ant{
    // blue ant - just a regular ant
    Drone(String n, int s, int h, String c, Anthill anthill){
        super(n, s, h, c, anthill);
    }
    @Override
    public String getInformation() {
        if (this.getIsDead()){
            return String.format("Drone: %s ant - health: %d DEAD", getColor(), getHealth());
        }
        if (this.getIsBackHome()) {
            return String.format("Drone: %s ant - health: %d. Returned home safely!", getColor(), getHealth());
        }
        return String.format("Drone: %s ant - health: %d", getColor(), getHealth());
    }
}
