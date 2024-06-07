package input;

import entities.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import game.Game;
import game.GameState;

public class InputHandler extends KeyAdapter {
    private Player player;
    private Game game;

    public InputHandler(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // 게임 상태에 따른 키 입력 처리
        if (game.getGameState() == GameState.RUNNING) {
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
                    game.setPaused(true); // P 키로 일시 정지
                    break;
                // 추가 키 입력 처리 가능
            }
        } else if (game.getGameState() == GameState.PAUSED) {
            if (key == KeyEvent.VK_P) {
                game.setPaused(false); // P 키로 다시 시작
            }
        } else if (game.getGameState() == GameState.GAME_OVER) {
            if (key == KeyEvent.VK_ENTER) {
                game.reset(); // 엔터 키로 게임 재시작
            }
        } else if (game.getGameState() == GameState.MENU) {
            if (key == KeyEvent.VK_ENTER) {
                game.startGame(); // 엔터 키로 게임 시작
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        // 게임 상태에 따른 키 릴리즈 처리
        if (game.getGameState() == GameState.RUNNING) {
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
    }
}
