package utils;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class ScoreBoard extends JDialog { //순위를 처리할 클래스
    private JLabel NameLabel = new JLabel("PLAYER : ");
    private JTextField Nametf = new JTextField(10);//플레이어 이름을 적는 텍스트필드
    private JButton okButton = new JButton("OK");
    int s;
    ArrayList<Integer> score = new ArrayList<>(); // 점수 저장
    ArrayList<String> nickname = new ArrayList<>(); //닉네임 저장
    public ScoreBoard() {

        setSize(400,100);
        setLayout(new FlowLayout());
        add(NameLabel);
        add(Nametf);
        add(okButton);
        setModal(true);

        okButton.addActionListener(new ActionListener() {  //ok가 눌리면
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                score.add(s);
                if(Nametf.getText() == null) {nickname.add("이름없음");}
                else {
                    nickname.add(Nametf.getText()+" ");} //사용자가 입력한 닉네임 저장
                sort();

            }
        });

    }
    void set_s(int s) {
        this.s = s;
    }

    void sort() { //순위를 내림차순으로 정렬
        int size = score.size();

        for(int i=0; i<size; i++) {
            for(int j=0; j<size-1; j++) {
                if(score.get(j) < score.get(j+1)) {
                    int tmp = score.get(j);
                    String stmp = nickname.get(j);

                    score.set(j,score.get(j+1));
                    score.set(j+1,tmp);

                    nickname.set(j,nickname.get(j+1));
                    nickname.set(j+1,stmp);}  }

        }

        write_file();
    }

    void write_file() {
        File file = new File("ranking.txt");
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            if(file.isFile() && file.canWrite()){

                for(int i=0;i<10;i++) {  //최대 10등까지만 저장
                    if(score.size()==i) break; // 플레이를 10번 이하로 했을 때 인덱스 오버플로우 방지
                    bufferedWriter.write(i+1+"st"+","+nickname.get(i)+","+score.get(i)); // 파일에 등수를 씀
                    bufferedWriter.newLine();
                }

                bufferedWriter.close();
            }
        }catch (IOException e) {
            System.out.println(e);
        }


    }


}
