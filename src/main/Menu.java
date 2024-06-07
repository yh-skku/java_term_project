package main;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Menu extends JFrame {
    private boolean singlePlayer = true;
    private final Image backgroundImage;

    public Menu() {
        setTitle("Menu");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 파일 시스템 경로를 사용하여 이미지 로드
        this.backgroundImage = new ImageIcon("assets/images/background.png").getImage();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

                g.setFont(new Font("Arial", Font.BOLD, 80));
                g.setColor(Color.YELLOW);
                FontMetrics fm = g.getFontMetrics();
                String title = "Galaga";
                int titleWidth = fm.stringWidth(title);
                g.drawString(title, (getWidth() - titleWidth) / 2, 200);

                g.setFont(new Font("Arial", Font.BOLD, 40));
                fm = g.getFontMetrics();
                String single = "Single Player";
                String multi = "Multi Player";
                int singleWidth = fm.stringWidth(single);
                int multiWidth = fm.stringWidth(multi);
                g.setColor(singlePlayer ? Color.WHITE : Color.GRAY);
                g.drawString(single, (getWidth() - singleWidth) / 2, 350);
                g.setColor(singlePlayer ? Color.GRAY : Color.WHITE);
                g.drawString(multi, (getWidth() - multiWidth) / 2, 450);

                g.setFont(new Font("Arial", Font.PLAIN, 20));
                g.setColor(Color.WHITE);
                String instruction = "Use UP/DOWN arrows to choose and SPACE to start";
                int instructionWidth = fm.stringWidth(instruction);
                g.drawString(instruction, (getWidth() - instructionWidth) / 2, 650);
            }
        };

        add(panel);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                        singlePlayer = !singlePlayer;
                        panel.repaint();
                        break;
                    case KeyEvent.VK_SPACE:
                        dispose(); // 메뉴 화면 종료
                        break;
                }
            }
        });

        setVisible(true);

    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }
}