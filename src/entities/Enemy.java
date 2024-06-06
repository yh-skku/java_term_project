import java.awt.Image;

public class Enemy extends Sprite {
	private Game game;

	public Enemy (Game game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		dy = -3;
    dx = 0
	}

	public void move() {
    y += 30;
    if (y > 600) {
      game.gameOver();
    }
  }
  super.move();
}
