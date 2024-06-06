package input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        // 키 입력 처리
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        // 키 릴리즈 처리
    }
}
