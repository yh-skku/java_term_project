import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {
	
	private Image image;
	protected int x;
	protected int y;
	protected int dx;
	protected int dy;
	
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
	
	public void setDx() {
		this.dx = x;
	}
	
	public void setDy() {
		this.dy = y;
	}
	
	public boolean checkCollision(Sprite other) {
		Rectangle myRect = new Rectangle();
		Rectangle otherRect = new Rectangle();
		myRect.setBounds(x, y, getWidth(), getHeight());
		otherRect.setBounds(other.getX(), other.getY(), other.getWidth(),other.getHeight());
		return myRect.intersects(otherRect);
	}
	
	public void handleCollision(Sprite other) {
		
	}
}
