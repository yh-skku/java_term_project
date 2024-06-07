package main;

import game.Game;
import utils.ScoreBoard;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        // 게임이 끝난 후 점수판을 표시
        while (menu.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 메뉴가 종료된 후에 게임을 시작
        if (menu.isSinglePlayer()) {
            Game game = new Game();
            game.start();

            // 게임이 끝난 후 점수판을 표시
            while (!game.isGameOver()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 사용자 이름을 입력받고 점수판을 표시
            ScoreBoard.readScores();
            new ScoreBoard(null, game.getScore());
        } else {
            // 멀티플레이 연결 (현재는 아무 것도 하지 않음)
        }
    }
}
