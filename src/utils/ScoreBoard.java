package utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class ScoreBoard extends JDialog {
    private JLabel nameLabel = new JLabel("PLAYER : ");
    private JTextField nameTextField = new JTextField(10); // 플레이어 이름을 적는 텍스트 필드
    private JButton okButton = new JButton("OK");
    private JButton quitButton = new JButton("quit");
    private int score;
    private static ArrayList<Integer> scores = new ArrayList<>(); // 점수 저장
    private static ArrayList<String> nicknames = new ArrayList<>(); // 닉네임 저장

    public ScoreBoard(JFrame parent, int score) {
        super(parent, "Score Board", true);
        this.score = score;

        setSize(400, 100);
        setLayout(new FlowLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(okButton);

        add(inputPanel, BorderLayout.CENTER);

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
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false)); // overwrite mode
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
        scoreDialog.setLayout(new BorderLayout());

        StringBuilder sb = new StringBuilder();
        sb.append("<html><div style='text-align: center; margin-top: 20px;'><h1>Top Scores</h1></div>")
                .append("<ul style='list-style: none; padding: 0; margin: 20px;'>");
        for (int i = 0; i < scores.size() && i < 10; i++) {
            sb.append("<li style='display: flex; justify-content: flex-start; margin-bottom: 5px;'>")
                    .append("<span style='flex: 1; text-align: left;'>")
                    .append(i + 1).append(". </span>")
                    .append("<span style='flex: 3;'>")
                    .append(nicknames.get(i)).append(": ").append(scores.get(i))
                    .append("</span></li>");
        }
        sb.append("</ul></html>");

        JLabel scoreLabel = new JLabel(sb.toString(), JLabel.CENTER);
        scoreDialog.add(scoreLabel, BorderLayout.CENTER);

        // quit 버튼 추가
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.SOUTHEAST;

        JButton quitButton = new JButton("quit");
        quitButton.addActionListener(new ActionListener() { // quit 버튼이 눌리면
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // 프로그램 종료
            }
        });

        buttonPanel.add(quitButton, gbc);
        scoreDialog.add(buttonPanel, BorderLayout.SOUTH);

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