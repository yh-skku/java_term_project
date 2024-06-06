package game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;
import javax.swing.JFrame;
import input.InputHandler;
import entities.Player;
import graphics.Sprite;
import graphics.SpriteManager;

public class Game extends Canvas implements Runnable {
    private boolean running = false;
    private boolean paused = false;
    private boolean gameOver = false;
    private Thread gameThread;
    private Thread enemySpawnThread;
    private Thread collisionDetectionThread;
    private SpriteManager spriteManager;
    private int score = 0;
    private static final long ENEMY_SPAWN_INTERVAL = 2000; // 2초마다 적 생성

    public Game() {
        JFrame frame = new JFrame("Galaga Game");
        frame.setSize(GameSettings.WIDTH, GameSettings.HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);

        spriteManager = new SpriteManager(this);
        spriteManager.playerDeparted();
        this.addKeyListener(new InputHandler(spriteManager.getPlayer(), this));
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        gameThread = new Thread(this);
        enemySpawnThread = new Thread(new EnemySpawnTask());
        collisionDetectionThread = new Thread(new CollisionDetectionTask());
        gameThread.start();
        enemySpawnThread.start();
        collisionDetectionThread.start();
    }

    public synchronized void stop() {
        running = false;
        gameOver = true;
        render();
        try {
            gameThread.join();
            enemySpawnThread.join();
            collisionDetectionThread.join();
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
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        render();
        stop();
    }

    private void tick() {
        List<Sprite> sprites = spriteManager.getSprites();
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).move();
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
        List<Sprite> sprites = spriteManager.getSprites();
        for (Sprite sprite : sprites) {
            sprite.draw(g);
        }

        // 점수 그리기
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 20);

        // 게임 오버 메시지 그리기
        if (gameOver) {
            System.out.println("game over");
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", 370, 350);
        }

        g.dispose();
        bs.show();
    }

    public void addSprite(Sprite sprite) {
        spriteManager.getSprites().add(sprite);
    }

    public void removeSprite(Sprite sprite) {
        spriteManager.removeSprite(sprite);
    }

    public void addScore(int points) {
        score += points;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void reset() {
        spriteManager = new SpriteManager(this);
        gameOver = false;
        this.addKeyListener(new InputHandler(spriteManager.getPlayer(), this));
    }

    public void gameOver() {
        running = false;
        gameOver = true;
    }

    // 적 출현 작업을 처리하는 내부 클래스
    private class EnemySpawnTask implements Runnable {
        @Override
        public void run() {
            while (running && !gameOver) {
                try {
                    Thread.sleep(ENEMY_SPAWN_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!paused && !gameOver) {
                    spriteManager.enemyAppeared();
                }
            }
        }
    }

    // 충돌 감지 작업을 처리하는 내부 클래스
    private class CollisionDetectionTask implements Runnable {
        @Override
        public void run() {
            while (running && !gameOver) {
                List<Sprite> sprites = spriteManager.getSprites();
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
                try {
                    Thread.sleep(10); // 충돌 감지 주기 설정
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
