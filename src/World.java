import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * The ants world, has vertices(WorldObjects) and edges(paths betweend objects).
 * Has arrays of ants and their anthills.
 */
public class World extends JPanel {
    static final int WORLD_WIDTH = 800;
    static final int WORLD_HEIGHT = 500;
    static final int VERTEX_SIZE = 15;

    private final int[][] vertices = {
            {20, 50}, {275, 35}, {390, 43}, {575, 55}, {720, 25},
            {168, 138}, {287, 111}, {430, 160}, {569, 175}, {666, 171},
            {300, 235}, {470, 200}, {74, 300}, {180, 348}, {400, 250},
            {750, 250}, {400, 378}, {483, 310}, {589, 388}, {82, 420},
            {211, 390}, {481, 473}, {704, 415}
    };
    private final int[][] neighbourhoodAdjacencyList = {
            {1, 5, 12}, {0, 5, 2}, {1, 3, 6, 7}, {2, 4, 8, 7}, {3, 9, 15},
            {0, 2, 6}, {5, 2, 7, 10}, {2, 3, 6, 8, 10, 11, 14}, {3, 7, 9, 11}, {4, 8, 15},
            {6, 7, 16, 13, 14}, {7, 8}, {0, 13, 19}, {10, 12, 16, 19, 20}, {7, 16, 17, 10},
            {4, 9, 18, 22}, {10, 13, 14, 21}, {14, 18, 21}, {15, 17, 21, 22}, {12, 13, 20},
            {13, 19, 21}, {16, 17, 18, 20, 22}, {15, 18, 21}
    };
    private ArrayList<Anthill> anthills = new ArrayList<>();
    private final Anthill redAnthill = new Anthill(150, 225, 100, "red");
    private final Anthill blueAnthill = new Anthill(650, 225, 200, "blue");
    private ArrayList<WorldObject> worldObjects;
    private ArrayList<Ant> ants = new ArrayList<>();

    /**
     * Constructor, adds ants and objects and their graphical representations.
     */
    World(){
        worldObjects = addWorldObjects();
        addAnts();
        for (Ant a : ants){
            this.add(a.getDotRepresentation());
            a.start();
        }
        this.setPreferredSize(new Dimension(WORLD_WIDTH, WORLD_HEIGHT));
        this.setLayout(new OverlayLayout(this));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Draws the anthills, stones, leaves and paths between them.
     * @param g
     */
    public void draw(Graphics g) {
        int label = 0;
        for (WorldObject obj : worldObjects) {
            if (label >= worldObjects.size() - 2) {
                break;
            }
            g.setColor(Color.darkGray);
            for (WorldObject neigh : obj.getNeighbours()){
                g.drawLine(obj.getCoordinates()[0], obj.getCoordinates()[1], neigh.getCoordinates()[0], neigh.getCoordinates()[1]);
            }
            label++;
        }
        for (Anthill anthill : anthills) {
            g.setColor(Color.darkGray);
            for (WorldObject neigh : anthill.getNeighbours()) {
                g.drawLine(anthill.getCoordinates()[0], anthill.getCoordinates()[1], neigh.getCoordinates()[0], neigh.getCoordinates()[1]);
            }
        }

        label = 0;
        for (WorldObject obj : worldObjects) {
            if (label >= worldObjects.size() - 2){
                break;
            }
            if (Objects.equals(obj.getObjectType(), "Stone")) {
                g.setColor(Color.gray);
            } else {
                g.setColor(Color.green);
            }
            g.fillOval(obj.getCoordinates()[0] - 7, obj.getCoordinates()[1] - 7, VERTEX_SIZE, VERTEX_SIZE);
            g.setColor(Color.darkGray);
            g.drawString(""+obj.getLabel(), obj.getCoordinates()[0], obj.getCoordinates()[1] - 10);
            label++;
        }
        for (Anthill anthill : anthills) {
            if (Objects.equals(anthill.getColor(), "red")){
                g.setColor(Color.red);
            } else if (Objects.equals(anthill.getColor(), "blue")){
                g.setColor(Color.blue);
            }
            g.fillOval(anthill.getCoordinates()[0] - 17, anthill.getCoordinates()[1] - 17, 35, 35);
        }
    }

    /**
     * Creates leaves and stones randomly with a certain probability.
     * Add neighbours to all the objects based on the neighbourhoodAdjacencyList.
     * @return an ArrayList of WorldObjects
     */
    public ArrayList<WorldObject> addWorldObjects(){
        ArrayList<WorldObject> objects = new ArrayList<>();
        Random rand = new Random();
        double leafProb = 0.8; // generating leaves and stones randomly
        int vertexLabel = 0;
        for (int[] vertex : vertices){
            double randomValue = rand.nextDouble();
            if (randomValue < leafProb) {
                objects.add(new Leaf(vertex[0], vertex[1], vertexLabel));
            } else {
                objects.add(new Stone(vertex[0], vertex[1], vertexLabel));
            }
            vertexLabel++;
        }
        for (int i = 0; i < neighbourhoodAdjacencyList.length; i++){
            WorldObject object = objects.get(i);
            for (int index : neighbourhoodAdjacencyList[i]){
                object.addNeighbour(objects.get(index));
            }
        }
        redAnthill.addNeighbour(objects.get(0));
        redAnthill.addNeighbour(objects.get(5));
        redAnthill.addNeighbour(objects.get(10));
        redAnthill.addNeighbour(objects.get(12));
        redAnthill.addNeighbour(objects.get(13));
        blueAnthill.addNeighbour(objects.get(9));
        blueAnthill.addNeighbour(objects.get(11));
        blueAnthill.addNeighbour(objects.get(14));
        blueAnthill.addNeighbour(objects.get(18));
        blueAnthill.addNeighbour(objects.get(15));
        objects.add(redAnthill);
        objects.add(blueAnthill);
        anthills.add(redAnthill);
        anthills.add(blueAnthill);
        return objects;
    }

    /**
     * Creates and adds ants.
     */
    public void addAnts() {
        for (int i = 0; i < 10; i++) {
            ants.add(new Collector(""+i, 50, 20, "red", redAnthill));
        }
        for (int i = 10; i < 15; i ++) {
            ants.add(new Soldier(""+i, 50, 20, "red", redAnthill));
        }
        for (int i = 15; i < 20; i++){
            ants.add(new Blunderer(""+i, 50, 20, "red", redAnthill));
        }
        for (int i = 0; i < 15; i++) {
            ants.add(new Worker(""+i, 50, 20, "blue", blueAnthill));
        }
        for (int i = 15; i<20; i++) {
            ants.add(new Drone(""+i, 50, 20,"blue", blueAnthill));
        }
    }

    /**
     * Collects information from all objects and ants.
     * @return a string containing all information about the world.
     */
    public String getInformationAboutWorld() {
        String information = "";
        for (WorldObject obj : worldObjects) {
            information += obj.getInformation();
            information += "\n";
        }
        for (Ant ant : ants) {
            information += ant.getInformation();
            information += "\n";
        }
        return information;
    }
}
