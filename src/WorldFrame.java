import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WorldFrame extends JFrame {

    protected JPanel world = new World();
    WorldFrame(){
        this.setTitle("Ant world");
        try {
            BufferedImage icon = ImageIO.read(new File("images/icon_ant.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        this.setDefaultCloseOperation((EXIT_ON_CLOSE));
        this.setResizable(false);
        this.add(world);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        new InformationWindow((World) world);
    }

    public static void main(String[] args) {
        new WorldFrame();
    }
}
