import game.GameSettings;

class SpriteManager {
    private ArrayList enemies = new ArrayList();
    private ArrayList bullets = new ArrayList();
    private Sprite player;
    private Game game;
    
    class SpriteManager(Game game) {
        this.game = game;
    }

    public void playerDeparted() {
        player = new Player(game, GameSettings.playerImage, 500, 650);
    }

    public void fire() {
        Bullet bullet = new Bullet(game, GameSettings.bulletImage, player.getX() + 10, 620);
        bullets.add(bullet);
    }

    public void enemyAppeared() {
        Enemy enemy = new Enemy(game, GameSettings.enemyImage, new Random().nextInt(950), 0);
        enemiese.add(enemy);
    }
}
