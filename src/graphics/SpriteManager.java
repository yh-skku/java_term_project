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
        playerDeparted();
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    public void playerDeparted() {
        player = new Player(game, GameSettings.playerImage, 500, 650);
        sprites.add(player);
    }

    public void fire() {
        Bullet bullet = new Bullet(game, GameSettings.bulletImage, player.getX() + 10, 620);
        sprites.add(bullet);
    }

    public void enemyAppeared() {
        Enemy enemy = new Enemy(game, GameSettings.enemyImage, new Random().nextInt(950), 0);
        sprites.add(enemy);
    }

    // 스프라이트 목록을 가져오는 메서드 추가
    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public Player getPlayer() {
        return player;
    }

}
