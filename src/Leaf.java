/**
 * WorldObject that is a leaf.
 */
public class Leaf extends WorldObject{
    private final String objectType = "Leaf";
    Leaf(int x, int y, int z){
        super(x, y, z);
    }
    public String getObjectType() { return objectType; }

}
