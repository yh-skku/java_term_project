package game;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.File;
public class GameSettings {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static BufferedImage playerImage;
    public static BufferedImage enemyImage;
    public static BufferedImage bulletImage;
    public static BufferedImage backgroundImage;

    static {
        try {

            playerImage = ImageIO.read(new File("assets/images/background.png"));
            enemyImage = ImageIO.read(new File("assets/images/monster.png"));
            bulletImage = ImageIO.read(new File("assets/images/bullet.png"));
            backgroundImage = ImageIO.read(new File("assets/images/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
