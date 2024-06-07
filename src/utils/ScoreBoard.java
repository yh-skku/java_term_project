package utils;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;

public class ScoreBoard extends JDialog {
    private JLabel nameLabel = new JLabel("PLAYER : ");
    private JTextField nameTextField = new JTextField(10); // 플레이어 이름을 적는 텍스트 필드
    private JButton okButton = new JButton("OK");
    private int score;
    private static ArrayList<Integer> scores = new ArrayList<>(); // 점수 저장
    private static ArrayList<String> nicknames = new ArrayList<>(); // 닉네임 저장

    public ScoreBoard(JFrame parent, int score) {
        super(parent, "Score Board", true);
        this.score = score;

        setSize(400, 100);
        setLayout(new FlowLayout());
        add(nameLabel);
        add(nameTextField);
        add(okButton);

        okButton.addActionListener(new ActionListener() { // OK 버튼이 눌리면
            public void actionPerformed(ActionEvent e) {
                scores.add(score);
                if (nameTextField.getText().isEmpty()) {
                    nicknames.add("이름없음");
                } else {
                    nicknames.add(nameTextField.getText());
                }
                sortScores();
                setVisible(false); // 입력창 닫기
                showScoreBoard(parent); // 점수판 표시
            }
        });

        setLocationRelativeTo(parent);
        setVisible(true); // 입력창 표시
    }

    private void sortScores() { // 점수를 내림차순으로 정렬
        int size = scores.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (scores.get(j) < scores.get(j + 1)) {
                    int tmp = scores.get(j);
                    String stmp = nicknames.get(j);

                    scores.set(j, scores.get(j + 1));
                    scores.set(j + 1, tmp);

                    nicknames.set(j, nicknames.get(j + 1));
                    nicknames.set(j + 1, stmp);
                }
            }
        }
        writeFile();
    }

    private void writeFile() {
        File file = new File("ranking.txt");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true)); // append mode
            if (file.isFile() && file.canWrite()) {
                for (int i = 0; i < scores.size() && i < 10; i++) {
                    bufferedWriter.write((i + 1) + "st" + "," + nicknames.get(i) + "," + scores.get(i)); // 파일에 등수를 씀
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void showScoreBoard(JFrame parent) {
        JDialog scoreDialog = new JDialog(parent, "Top Scores", true);
        scoreDialog.setSize(400, 300);
        scoreDialog.setLayout(new FlowLayout());

        JLabel scoreLabel = new JLabel("<html><h1>Top Scores</h1><ul>");
        for (int i = 0; i < scores.size() && i < 10; i++) {
            scoreLabel.setText(scoreLabel.getText() + "<li>" + (i + 1) + ". " + nicknames.get(i) + ": " + scores.get(i) + "</li>");
        }
        scoreLabel.setText(scoreLabel.getText() + "</ul></html>");

        scoreDialog.add(scoreLabel);
        scoreDialog.setLocationRelativeTo(parent);
        scoreDialog.setVisible(true); // 점수판 표시
    }

    // 점수 파일 읽기
    public static void readScores() {
        File file = new File("ranking.txt");
        if (file.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        scores.add(Integer.parseInt(parts[2]));
                        nicknames.add(parts[1]);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}