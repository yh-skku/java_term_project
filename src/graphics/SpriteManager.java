package graphics;

import game.GameSettings;
import game.Game;
import entities.Player;
import entities.Bullet;
import entities.Enemy;
import java.util.ArrayList;
import java.util.Random;

public class SpriteManager {
    private ArrayList<Sprite> sprites = new ArrayList<>();
    private Player player;
    private Game game;

    public SpriteManager(Game game) {
        this.game = game;
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    public void playerDeparted() {
        player = new Player(game, GameSettings.playerImage, 500, 600);
        sprites.add(player);
    }

    public void enemyAppeared() {
        Enemy enemy = new Enemy(game, GameSettings.enemyImage, new Random().nextInt(950), 0);
        sprites.add(enemy);
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }
}
