
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.util.TimerTask;

public class Luffymove extends JPanel {

    private int armAngle = 0;
    private int eyeOffset = 0;
    private BufferedImage backgroundImage; // 배경 이미지

    private int moveDistance = 800; // Total distance to move (adjust as needed)
    private int numSteps = 30; // Number of steps for movement
    private int currentStep = 0; // Current step count
    private int characterXOffset = 100; // Initial X offset of the character (adjusted left by 100)
    private int characterYOffset = 200; // Adjust this value to move the character down

    private Timer animationTimer;
    private Timer eyeArmTimer;

    public Luffymove(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;

        // Start eye and arm animation for 5 seconds
        startEyeArmAnimation(5000);
    }

    private void startEyeArmAnimation(int duration) {
        // Initialize eye and arm animation timer
        eyeArmTimer = new Timer();
        eyeArmTimer.scheduleAtFixedRate(new TimerTask() {
            private long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                // Calculate elapsed time
                long elapsedTime = System.currentTimeMillis() - startTime;

                // Check if animation duration has passed
                if (elapsedTime >= duration) {
                    eyeArmTimer.cancel(); // Stop timer after duration
                } else {
                    // Update eye and arm animations
                    armAngle += 10;
                    eyeOffset = (int) (5 * Math.sin(Math.toRadians(armAngle)));
                    repaint();
                }
            }
        }, 0, 100); // Adjust timer delay as needed
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // Draw character
        // Ears
        g.setColor(Color.pink);
        g.fillOval(55 + characterXOffset, 55 + characterYOffset, 25, 25);
        g.fillOval(170 + characterXOffset, 55 + characterYOffset, 25, 25);

        // Feet
        g.setColor(new Color(102, 0, 51));
        g.fillOval(90 + characterXOffset, 290 + characterYOffset, 30, 25);
        g.fillOval(140 + characterXOffset, 290 + characterYOffset, 30, 25);

        // Body
        g.setColor(Color.pink);
        g.fillOval(70 + characterXOffset, 178 + characterYOffset, 120, 120);
        g.fillOval(60 + characterXOffset, 200 + characterYOffset, 140, 100);

        g.setColor(Color.white);
        g.fillOval(90 + characterXOffset, 215 + characterYOffset, 80, 90);

        // Arms
        g.setColor(Color.pink);
        int armY = 200 + characterYOffset + (int) (10 * Math.sin(Math.toRadians(armAngle)));
        g.fillOval(45 + characterXOffset, armY, 60, 40); // Enlarged arm size
        g.fillOval(155 + characterXOffset, armY, 60, 40); // Enlarged arm size

        // Face
        g.setColor(Color.pink);
        g.fillOval(50 + characterXOffset, 50 + characterYOffset, 150, 150);
        g.fillOval(40 + characterXOffset, 110 + characterYOffset, 170, 90);

        // Cheeks
        g.setColor(new Color(241, 95, 95));
        g.fillOval(70 + characterXOffset, 120 + characterYOffset, 20, 10);
        g.fillOval(160 + characterXOffset, 120 + characterYOffset, 20, 10);

        // Nose
        g.setColor(new Color(102, 0, 51));
        g.fillOval(110 + characterXOffset, 110 + characterYOffset, 30, 20);
        g.fillOval(111 + characterXOffset, 113 + characterYOffset, 28, 20);

        // Eyes
        g.setColor(Color.black);
        g.fillOval(90 + eyeOffset + characterXOffset, 100 + characterYOffset, 12, 12);
        g.fillArc(150 + eyeOffset + characterXOffset, 100 + characterYOffset, 15, 10, -50, 280);

        // Eye highlights
        g.setColor(Color.white);
        g.fillOval(92 + eyeOffset + characterXOffset, 102 + characterYOffset, 4, 4);
        g.fillOval(113 + eyeOffset + characterXOffset, 116 + characterYOffset, 4, 4);

        // Teeth
        g.setColor(Color.white);
        g.fillRoundRect(115 + characterXOffset, 145 + characterYOffset, 20, 10, 10, 10);

        // Mouth
        g.setColor(Color.black);
        g.fillArc(105 + characterXOffset, 140 + characterYOffset, 40, 8, 130, 280);

        // Inner ears
        g.setColor(Color.white);
        g.fillArc(63 + characterXOffset, 64 + characterYOffset, 15, 15, 55, 180);
        g.fillArc(170 + characterXOffset, 64 + characterYOffset, 15, 15, 310, 180);
    }

    public void startAnimation() {
        // Start movement after eye and arm animation (after 5 seconds)
        TimerTask moveTask = new TimerTask() {
            @Override
            public void run() {
                // Initialize movement timer
                animationTimer = new Timer();
                animationTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (currentStep < numSteps) {
                            // Incremental step size calculation
                            int stepSize = moveDistance / numSteps;
                            characterXOffset += stepSize;
                            currentStep++;
                            repaint();
                        } else {
                            // Stop animation after reaching the desired movement
                            animationTimer.cancel();
                        }
                    }
                }, 0, 100); // Adjust timer delay as needed
            }
        };
        new Timer().schedule(moveTask, 5000); // 5 seconds delay for movement start
    }

    public static void main(String[] args) {
        // Load background image
        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File("북극.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Luffy on Background");
        Luffymove panel = new Luffymove(backgroundImage);
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start animation
        panel.startAnimation();
    }
}

