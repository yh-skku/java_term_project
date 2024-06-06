package entities;
import java.awt.Image;

public class Player extends Sprite {
	private Game game;

	public Player (Game game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		dx = 0;
    		dy = 0;
	}

	public void move() {
    		if ((dx < 0) && (x < 10)) {
			return;
		}
		if ((dx > 0) && (x > 940)) {
			return;
		}
		super.move();
	}
}
