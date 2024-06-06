import game.GameSettings;
import java.util.ArrayList;

class SpriteManager {
    private ArrayList sprites = new ArrayList();
    private Sprite player;
    private Game game;
    
    class SpriteManager(Game game) {
        this.game = game;
    }

    public void removeSprite(Sprite sprite) {
        sprite.flag = true;
        sprites.remove(sprite);
    }

    public void playerDeparted() {
        player = new Player(game, GameSettings.playerImage, 500, 650);
    }

    public void fire() {
        Bullet bullet = new Bullet(game, GameSettings.bulletImage, player.getX() + 10, 620);
        sprites.add(bullet);
    }

    public void enemyAppeared() {
        Enemy enemy = new Enemy(game, GameSettings.enemyImage, new Random().nextInt(950), 0);
        sprites.add(enemy);
    }
}
