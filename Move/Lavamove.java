
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lavamove extends JPanel implements ActionListener {

    private int antennaeY1 = 30; // 첫 번째 안테나 초기 위치
    private int antennaeY2 = 30; // 두 번째 안테나 초기 위치
    private boolean moveUp = true; // 이동 방향 플래그
    private int eyeX = 235; // 눈동자 초기 x 좌표
    private int mouthHeight = 20; // 입 초기 높이
    private boolean increaseMouth = true; // 입 크기 조절 플래그
    private int eyeOffset = 0; // 눈 위치의 초기 오프셋 (움직임 범위 조절용)
    private boolean eyeMoveRight = true; // 눈동자 이동 방향 플래그

    private Timer timer; // 애니메이션용 타이머

    private int shiftX = 600; // 초기 수평 이동 거리

    public Lavamove() {
        timer = new Timer(20, this); // 20밀리초마다 타이머 이벤트 발생
        timer.start(); // 타이머 바로 시작
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경 이미지 그리기
        ImageIcon backgroundImage = new ImageIcon("잔디.png");
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

        // Draw character with updated coordinates for shifting

        // 몸체
        g.setColor(Color.yellow);
        g.fillOval(80 + shiftX, 50, 200, 500);

        // 눈
        g.setColor(Color.black);
        g.drawArc(170 + shiftX, 17, 77, 80, 0, 180);

        // 눈동자
        g.setColor(new Color(255, 187, 0));
        g.fillOval(eyeX + shiftX, 55, 30, 30);

        // 머리
        g.setColor(Color.white);
        g.fillOval(140 + shiftX, 180, 40, 150);
        g.fillOval(180 + shiftX, 180, 40, 150);

        // 머리카락
        g.setColor(Color.black);
        g.fillOval(160 + eyeOffset + shiftX, 230, 20, 50);
        g.fillOval(185 + eyeOffset + shiftX, 230, 20, 50);

        // 볼
        g.setColor(new Color(255, 187, 0));
        g.fillOval(220 + shiftX, 350, 40, 40);
        g.fillOval(110 + shiftX, 350, 40, 40);

        // 입
        g.setColor(Color.black);
        g.fillOval(160 + shiftX, 400, 20, 30);
        g.fillOval(180 + shiftX, 400, 20, 30);
        g.fillOval(160 + shiftX, 450, 40, mouthHeight); // 높이가 변하는 입

        // 추가 요소 (직사각형)
        g.setColor(Color.yellow);
        g.fillRoundRect(200 + shiftX, 400, 240, 150, 70, 70);
        g.setColor(new Color(255, 187, 0));
        g.fillRoundRect(280 + shiftX, 400, 30, 150, 0, 0);
        g.setColor(new Color(255, 187, 0));
        g.fillRoundRect(340 + shiftX, 400, 30, 150, 0, 0);
        g.setColor(new Color(255, 187, 0));
        g.fillRoundRect(400 + shiftX, 400, 30, 150, 0, 0);
        g.setColor(new Color(255, 187, 0));
        g.fillRoundRect(410 + shiftX, 400, 30, 150, 70, 70);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 왼쪽으로 이동
        shiftX--;

        // 안테나 위치 조정
        if (moveUp) {
            antennaeY1--;
            antennaeY2--;
            if (antennaeY1 < 220) {
                moveUp = false;
            }
        } else {
            antennaeY1++;
            antennaeY2++;
            if (antennaeY1 > 230) {
                moveUp = true;
            }
        }

        // 눈동자 위치 조정 (수평 이동)
        if (eyeX == 235) {
            eyeX = 230;
        } else {
            eyeX = 235;
        }

        // 입 크기 조정 (입 열림/닫힘 애니메이션)
        if (increaseMouth) {
            mouthHeight++;
            if (mouthHeight > 70) {
                increaseMouth = false;
            }
        } else {
            mouthHeight--;
            if (mouthHeight < 20) {
                increaseMouth = true;
            }
        }

        // 눈 위치 조정 (수평 이동)
        if (eyeMoveRight) {
            eyeOffset += 2;
            if (eyeOffset > 10) { // 움직임 범위 제한
                eyeMoveRight = false;
            }
        } else {
            eyeOffset -= 2;
            if (eyeOffset < -10) { // 움직임 범위 제한 (왼쪽 방향으로)
                eyeMoveRight = true;
            }
        }

        repaint(); // 패널을 다시 그리도록 요청
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Lava Character");
            frame.setSize(1200, 800); // Adjusted width to accommodate initial position
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new Lavamove();
            frame.add(panel);

            frame.setVisible(true);
        });
    }
}

