package entities;

import java.awt.Image;
import graphics.Sprite;
import game.Game;

public class Bullet extends Sprite {
	private Game game;

	public Bullet(Game game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		setDy(-5); // 총알은 위로 이동
	}

	@Override
	public void move() {
		super.move();
		if (getY() < -100) {
			game.removeSprite(this);
		}
	}

	@Override
	public void handleCollision(Sprite other) {
		if (other instanceof Enemy) {
			game.removeSprite(other);
			game.removeSprite(this);
			game.addScore(10);
		}
	}
}
