import java.awt.Image;

public class Bullet extends Sprite {
	private Game game;

	public Bullet (Game game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		dy = -5;
	}

	public void move() {
		super.move();  
		if (y < -100) { 
			game.removeSprite(this); 
		}
	}

	public void handleCollision(Sprite other) {
		if (other instanceof Enemy) {
			game.removeSprite(other);
			game.removeSprite(this);
			game.score += 10;
		}
	}
}
