import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * An information window containing all the information about the world, updated using the refresh button.
 */
public class InformationWindow extends JFrame {
    private World world;
    private JTextArea textArea = new JTextArea();

    /**
     * Constructor,
     * it has a panel with the text and the refresh button that updates the information displayed
     * @param w the World from which this will take information
     */
    InformationWindow(World w) {
        world = w;
        JPanel panel = new JPanel(new BorderLayout());
        textArea.setText(getInfo());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> textArea.setText(getInfo()));
        panel.add(refreshButton, BorderLayout.SOUTH);
        try {
            BufferedImage icon = ImageIO.read(new File("images/info.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        this.getContentPane().add(panel);
        this.setTitle("Information window");
        this.setDefaultCloseOperation((EXIT_ON_CLOSE));
        this.setVisible(true);
        this.setSize(350,485);
    }

    /**
     *
     * @return a string that is all information about the world
     */
    public String getInfo() {
        return world.getInformationAboutWorld();
    }
}
