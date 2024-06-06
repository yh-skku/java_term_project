package game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import input.InputHandler;
import entities.Player;
import graphics.Sprite;
import graphics.Renderer;


public class Game extends Canvas implements Runnable {
    private boolean running = false;
    private boolean paused = false;
    private boolean gameOver = false;
    private Thread thread;
    private Player player;
    private List<Sprite> sprites = new ArrayList<>();
    private int score = 0;

    public Game() {
        JFrame frame = new JFrame("Galaga Game");
        frame.setSize(GameSettings.WIDTH, GameSettings.HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);

        player = new Player(this, GameSettings.playerImage, GameSettings.WIDTH / 2, GameSettings.HEIGHT - 50);
        sprites.add(player);

        this.addKeyListener(new InputHandler(player, this));
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            bs = this.getBufferStrategy();
        }

        long lastTickTime = System.currentTimeMillis(); // 마지막 로직 업데이트 시간

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                if (!paused && !gameOver) {
                    tick();
                }
                delta--;
            }
            if (running && !paused && !gameOver) {
                render();
            }
                tick(); // 게임 로직 업데이트
                delta--;
            }

            // 일정 간격마다 렌더링
            if (System.currentTimeMillis() - lastTickTime >= 1000 / amountOfTicks) {
                Renderer renderer = new Renderer(bs);
                renderer.render();
                lastTickTime = System.currentTimeMillis(); // 마지막 로직 업데이트 시간 갱신
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).move();
        }
        // 충돌 처리 로직
        for (int i = 0; i < sprites.size(); i++) {
            for (int j = i + 1; j < sprites.size(); j++) {
                Sprite s1 = sprites.get(i);
                Sprite s2 = sprites.get(j);
                if (s1.checkCollision(s2)) {
                    s1.handleCollision(s2);
                    s2.handleCollision(s1);
                }
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        // 배경 이미지 그리기
        g.drawImage(GameSettings.backgroundImage, 0, 0, GameSettings.WIDTH, GameSettings.HEIGHT, null);

        // 스프라이트 그리기
        for (Sprite sprite : sprites) {
            sprite.draw(g);
        }

        // 게임 오버 메시지 그리기
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", GameSettings.WIDTH / 2 - 150, GameSettings.HEIGHT / 2);
        }

        g.dispose();
        bs.show();
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    public void addScore(int points) {
        score += points;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void reset() {
        player = new Player(this, GameSettings.playerImage, GameSettings.WIDTH / 2, GameSettings.HEIGHT - 50);
        sprites.clear();
        sprites.add(player);
        gameOver = false;
        this.addKeyListener(new InputHandler(player, this));
    }

    public void gameOver() {
        gameOver = true;
        stop();
    }
}
