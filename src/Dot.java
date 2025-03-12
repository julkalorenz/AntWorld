import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static java.lang.Math.abs;

/**
 * This class is a graphical representation of an Ant.
 * It is a dot that moves from one point to another.
 * Each ant has its dot representation.
 */
public class Dot extends JComponent{
    static final int SIZE = 7;
    private double currentX;
    private double currentY;
    private double targetX;
    private double targetY;
    private String color;
    private double[] stepDistance;
    Timer timer = new Timer(200, e -> {
        moveDot();
        repaint();
    });

    /**
     *
     * @param x1 current coordinate x
     * @param y1 current coordinate y
     * @param col the color
     */
    Dot(double x1, double y1, String col){
        this.currentX = x1;
        this.currentY = y1;
        this.color = col;
    }

    /**
     *
     * @param x the target x coordinate
     * @param y the target y coordinate
     */
    public void setTargetPosition(double x, double y) {
        this.targetX = x;
        this.targetY = y;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (Objects.equals(color, "red")){
            g.setColor(Color.red);
        } else if (Objects.equals(color, "blue")) {
            g.setColor(Color.blue);
        }
        g.fillOval((int) currentX, (int) currentY, SIZE, SIZE);
    }

    /**
     * a step is the change of the coordinates that will be made
     * @return a pair of doubles that correspond to the size of a step in the x and y direction
     */
    public double[] getStepDistance() {
        double stepX = (targetX - currentX) / 30;
        double stepY = (targetY - currentY) / 30;
        return new double[]{stepX, stepY};
    }

    /**
     * While the target position is not reached it changes the current position step by step
     */
    private void moveDot() {
        if (abs(targetX - currentX) >= 2) {
            currentX += stepDistance[0];
        }
        if (abs(targetY - currentY) >= 2) {
            currentY += stepDistance[1];
        }
        if (abs(targetX - currentX) <= 2 && abs(targetY - currentY) <= 2) {
            timer.stop();
        }
    }

    /**
     * Function that makes the dot move step bby step from one point to another.
     * @param x the target coordinate x
     * @param y the target coordinate y
     */
    public void dotWalk(double x, double y) {
        this.targetX = x;
        this.targetY = y;
        this.stepDistance = getStepDistance();
        timer.start();
    }

}
