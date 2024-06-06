package graphics;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {

	private Image image;
	protected int x;
	protected int y;
	protected int dx;
	protected int dy;


	boolean flag = false;
	
	public Sprite(Image image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	public void move() {
		x += dx;
		y += dy;
	}

	public int getWidth() {
		return image.getWidth(null);
	}

	public int getHeight() {
		return image.getHeight(null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public boolean checkCollision(Sprite other) {
		Rectangle myRect = new Rectangle(x, y, getWidth(), getHeight());
		Rectangle otherRect = new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight());
		return myRect.intersects(otherRect);
	}

	public void handleCollision(Sprite other) {
		// 기본 충돌 처리 로직
	}
}
