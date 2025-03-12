/**
 * A WorldObject that is a stone.
 */
public class Stone extends WorldObject{
    private final String objectType = "Stone";
    Stone(int x, int y, int z) {
        super(x, y, z);
    }
    public String getObjectType() { return objectType; }

}
