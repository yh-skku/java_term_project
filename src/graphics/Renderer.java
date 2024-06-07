package graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.ArrayList;

import game.Game;
import game.GameSettings;

public class Renderer {
    private BufferStrategy bs;

    public Renderer(BufferStrategy bufferStrategy) {
        this.bs = bufferStrategy;
    }

    public void render(Game game) {
        if (bs == null) {
            return;
        }
        Graphics g = bs.getDrawGraphics();

        // 배경 이미지 그리기
        g.drawImage(GameSettings.backgroundImage, 0, 0, GameSettings.WIDTH, GameSettings.HEIGHT, null);

        // 스프라이트 그리기
        List<Sprite> sprites;
        synchronized (game.getSpriteManager()) {
            sprites = new ArrayList<>(game.getSpriteManager().getSprites());
        }
        for (Sprite sprite : sprites) {
            sprite.draw(g);
        }

        // 점수 그리기
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + Integer.toString(game.getScore()), 10, 20);

        // 게임 오버 메시지 그리기
        if (game.getGameover()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", 400, 350);
        }

        g.dispose();
        bs.show();
    }
}
