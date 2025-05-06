import java.awt.Color;
import javax.swing.JFrame;

import rendering.GameDisplay;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("PMC Admin - v0.0.1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(new Color(0, 0, 0));

        GameDisplay gameDisplay = new GameDisplay();
        frame.getContentPane().add(gameDisplay);

        frame.setVisible(true);

        gameDisplay.startGameThread();
    }

}