package game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import input.InputHandler;
import entities.Player;
import graphics.Sprite;
import graphics.SpriteManager;

public class Game extends Canvas implements Runnable {
    private boolean running = false;
    private Thread gameThread;
    private Thread enemySpawnThread;
    private Thread collisionDetectionThread;
    private SpriteManager spriteManager;
    private int score = 0;
    private static final long ENEMY_SPAWN_INTERVAL = 2000; // 2초마다 적 생성
    private GameState gameState = GameState.MENU; // 초기 상태를 메뉴로 설정
    private JFrame frame;
    private JPanel menuPanel;
    private JPanel gamePanel;
    private JButton startButton;

    public Game() {
        frame = new JFrame("Galaga Game");
        frame.setSize(GameSettings.WIDTH, GameSettings.HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new CardLayout());

        spriteManager = new SpriteManager(this);
        spriteManager.playerDeparted();
        // 메뉴 패널 생성
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(Color.BLACK);

        // 게임 시작 버튼 생성
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // 버튼을 메뉴 패널에 추가
        menuPanel.add(startButton);

        // 게임 패널 생성 및 설정
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.add(this, BorderLayout.CENTER);

        // 메뉴 패널과 게임 패널을 프레임에 추가
        frame.add(menuPanel, "Menu");
        frame.add(gamePanel, "Game");

        frame.setVisible(true);

        // 게임 캔버스에 키 리스너 추가
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
                if (gameState == GameState.RUNNING) {
                    tick();
                }
                delta--;
            }
            render();
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

        if (gameState == GameState.MENU) {
            renderMenu(g);
        } else {
            renderGame(g);
        }

        g.dispose();
        bs.show();
    }

    private void renderMenu(Graphics g) {
        // 메뉴 렌더링은 패널과 버튼으로 처리되므로 따로 구현 필요 없음
    }

    private void renderGame(Graphics g) {
        g.drawImage(GameSettings.backgroundImage, 0, 0, GameSettings.WIDTH, GameSettings.HEIGHT, null);

        List<Sprite> sprites = spriteManager.getSprites();
        for (Sprite sprite : sprites) {
            sprite.draw(g);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 20);

        if (gameState == GameState.GAME_OVER) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", 370, 350);
        }
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
        this.gameState = paused ? GameState.PAUSED : GameState.RUNNING;
    }

    public void reset() {
        spriteManager = new SpriteManager(this);
        gameState = GameState.MENU;
        this.addKeyListener(new InputHandler(spriteManager.getPlayer(), this));
        // 메뉴 패널을 다시 표시하고 캔버스를 숨김
        ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Menu");
    }

    public void startGame() {
        gameState = GameState.RUNNING;
        // 메뉴 패널을 숨기고 캔버스를 표시
        ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Game");
        this.setVisible(true);
        this.requestFocus();
        start();
    }

    public void gameOver() {
        gameState = GameState.GAME_OVER;
    }

    public GameState getGameState() {
        return gameState;
    }

    // 적 출현 작업을 처리하는 내부 클래스
    private class EnemySpawnTask implements Runnable {
        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(ENEMY_SPAWN_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (gameState == GameState.RUNNING) {
                    spriteManager.enemyAppeared();
                }
            }
        }
    }

    // 충돌 감지 작업을 처리하는 내부 클래스
    private class CollisionDetectionTask implements Runnable {
        @Override
        public void run() {
            while (running) {
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
