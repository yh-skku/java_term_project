package game;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class GameSettings {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static BufferedImage playerImage;
    public static BufferedImage enemyImage;
    public static BufferedImage bulletImage;
    public static BufferedImage backgroundImage;

    static {
        try {
            playerImage = ImageIO.read(GameSettings.class.getResource("/images/spaceship.png"));
            enemyImage = ImageIO.read(GameSettings.class.getResource("/images/monster.png"));
            bulletImage = ImageIO.read(GameSettings.class.getResource("/images/bullet.png"));
            backgroundImage = ImageIO.read(GameSettings.class.getResource("/images/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
