package entities;

import java.awt.Image;
import graphics.Sprite;
import game.Game;

public class Enemy extends Sprite {
	private Game game;

	public Enemy(Game game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		setDy(4); // 적은 아래로 이동
		setDx(0);
	}

	@Override
	public void move() {
		y += dy;
		if (y > 650) { // 화면 하단에 도달하면
			game.gameOver();
		}
	}
}
