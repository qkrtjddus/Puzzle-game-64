import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

public class Puzzle extends JFrame implements ActionListener {
    private JButton btn[];

    public static void main(String[] args) {
        new Puzzle();
    }

    public Puzzle() {
        setTitle("박성연의 64피스 퍼즐");
        setSize(800,600); // 창 크기 조절
        setLocation(200,100); // 창 위치 조절
        makeButton();
        setVisible(true);
    }

    void makeButton() {
        btn = new JButton[64]; // 64개 버튼으로 변경
        setLayout(new GridLayout(8, 8)); // 8x8 그리드로 변경

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < 64; i++) { // 0부터 63까지의 숫자 추가
            numbers.add(i);
        }
        Collections.shuffle(numbers); // 숫자들을 랜덤하게 섞음

        for (int i = 0; i < 64; i++) { // 섞인 숫자를 버튼 라벨로 설정
            String buttonText = numbers.get(i) == 63 ? "" : numbers.get(i).toString(); // 마지막 숫자를 빈 문자열로 설정
            btn[i] = new JButton(buttonText);
            add(btn[i]);
            btn[i].addActionListener(this);
            if (numbers.get(i) == 63) {
                btn[i].setEnabled(false); // 마지막 버튼 비활성화
            }
        }
    }

    private int[] nb = new int[4]; // 이웃 배열

    private void findNB(int id) {
        // 이웃 찾기 로직을 64개 퍼즐에 맞게 조정
        nb[0] = id - 8;
        if (nb[0] < 0) nb[0] = -1;

        nb[1] = id + 8;
        if (nb[1] >= 64) nb[1] = -1;

        nb[2] = id - 1;
        if (nb[2] < 0 || (nb[2] % 8 == 7)) nb[2] = -1;

        nb[3] = id + 1;
        if (nb[3] % 8 == 0) nb[3] = -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        int id;
        for (id = 0; id < 64; id++) {
            if (b == btn[id]) break;
        }

        findNB(id);

        for (int i = 0; i < 4; i++) {
            if (nb[i] >= 0 && !btn[nb[i]].isEnabled()) {
                JButton act, inact;
                act = btn[id];
                inact = btn[nb[i]];
                inact.setText(act.getText());
                act.setText("");
                act.setEnabled(false);
                inact.setEnabled(true);
                break;
            }
        }

        if (checkSuccess()){
            JOptionPane.showMessageDialog(this, "게임 성공! 다시 시작하려면 확인을 누르세요");
            resetGame();
        }
    }
    private boolean checkSuccess() {
        for (int i = 0; i < 63; i++) {
            if (!btn[i].getText().equals(String.valueOf(i))){
                return false;
            }
        }
        return true;
    }
    private void resetGame() {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < 64; i++) { // 0부터 63까지의 숫자 추가
            numbers.add(i);
        }
        Collections.shuffle(numbers); // 숫자들을 랜덤하게 섞음

        // 버튼 라벨 재설정 및 모든 버튼 활성화
        for (int i = 0; i < 64; i++) {
            btn[i].setEnabled(true); // 버튼을 활성화
            String buttonText = numbers.get(i) == 63 ? "" : numbers.get(i).toString(); // 마지막 숫자를 빈 문자열로 설정
            btn[i].setText(buttonText);
        }

        // GUI를 새로 그림
        repaint();
    }
}