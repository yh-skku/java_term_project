package graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import game.GameSettings;
public class Renderer {
    private BufferStrategy bufferStrategy;

    public Renderer(BufferStrategy bufferStrategy) {
        this.bufferStrategy = bufferStrategy;
    }

    public void render() {
        Graphics g = bufferStrategy.getDrawGraphics();
        // 배경 이미지 그리기
        g.drawImage(GameSettings.playerImage, 0, 0, GameSettings.WIDTH, GameSettings.HEIGHT, null);

        // 여기에 다른 그리기 작업 추가

        g.dispose();
        bufferStrategy.show();
    }
}
