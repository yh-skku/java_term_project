package entities;
import java.awt.Image;
import graphics.Sprite;
import game.Game;

public class Player extends Sprite {
	private Game game;
	private boolean movingLeft = false;
	private boolean movingRight = false;

	public Player(Game game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		dx = 0;
		dy = 0;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	@Override
	public void move() {
		if (movingLeft) {
			dx = -5;
		} else if (movingRight) {
			dx = 5;
		} else {
			dx = 0;
		}

		if ((dx < 0) && (x < 10)) {
			return;
		}
		if ((dx > 0) && (x > 940)) {
			return;
		}
		super.move();
	}

	public void shoot() {
		// 총알 발사 로직 구현
	}
}
