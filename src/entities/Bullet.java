import java.awt.Image;

public class Bullet extends Sprite {
	private SpriteMager sm;
	
	public Bullet (SpriteManager sm, Image image, int x, int y) {
		super(image, x, y);
		this.sm = sm;
		dy = -5;
	}

	public void move() {
		super.move();  
		if (y < -100) { 
			sm.removeSprite(this); 
		}
	}

	public void handleCollision(Sprite other) {
		if (other instanceof Enemy) {
			sm.removeSprite(other);
			sm.removeSprite(this);
		}
	}
}
