package input;

import entities.Player;
import game.Game;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {
    private Player player;
    private Game game;
    private boolean paused = false;

    public InputHandler(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
                player.setMovingLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                player.setMovingRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.shoot();
                break;
            case KeyEvent.VK_P:
                togglePause();
                break;
            case KeyEvent.VK_R:
                resetGame();
                break;
            // 추가 키 입력 처리 가능
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
                player.setMovingLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setMovingRight(false);
                break;
            // 추가 키 릴리즈 처리 가능
        }
    }

    private void togglePause() {
        paused = !paused;
        game.setPaused(paused);
    }

    private void resetGame() {
        game.reset();
    }
}
