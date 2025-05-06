package rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import javax.swing.JPanel;

import config.Assets;
import config.GameLoop;

public class GameDisplay extends JPanel implements Runnable {

    Thread gameThread;
    long lastTime_flicker;
    int flakeIndex = 0;

    Color backgroundColor = new Color(15, 15, 15);
    Color crtFlakeColor = new Color(120, 120, 120, 7);

    int frameRate = 0;
    long lastTime_frameRate;
    
    public GameDisplay() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        if (width / height >= 16 / 9) {
            Assets.GAME_WIDTH = width;
            Assets.GAME_HEIGHT = (int) (width * 9 / 16);
            Assets.HEIGHT_PADDING = (height - Assets.GAME_HEIGHT) / 2;
            Assets.WIDTH_PADDING = 0;
        } else {
            Assets.GAME_HEIGHT = height;
            Assets.GAME_WIDTH = (int) (height * 16 / 9);
            Assets.WIDTH_PADDING = (width - Assets.GAME_WIDTH) / 2;
            Assets.HEIGHT_PADDING = 0;
        }

        System.out.println("Game Width: " + Assets.GAME_WIDTH);
        System.out.println("Game Height: " + Assets.GAME_HEIGHT);

        this.setBounds((width - Assets.GAME_WIDTH) / 2, (height - Assets.GAME_HEIGHT) / 2, Assets.GAME_WIDTH, Assets.GAME_HEIGHT);
        this.setPreferredSize(new Dimension(Assets.GAME_WIDTH, Assets.GAME_HEIGHT));
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {}
            @Override public void mousePressed(java.awt.event.MouseEvent e) {
                GameLoop.mouseClicked = true;
            }
            @Override public void mouseReleased(java.awt.event.MouseEvent e) {
                GameLoop.mouseClicked = false;
            }
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {}
            @Override public void mouseExited(java.awt.event.MouseEvent e) {}
        });

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.setName("PMC Admin Game Thread");
        gameThread.setPriority(Thread.NORM_PRIORITY);
        lastTime_flicker = System.currentTimeMillis();
        lastTime_frameRate = System.currentTimeMillis();
        gameThread.start();
    }

    public void update() {
        // Update game logic here
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw game elements here
        if (System.currentTimeMillis() - lastTime_flicker > 1000 / 10) {
            int variantColor = (int) (Math.random() * 4) - 2;
            Assets.CURRENT_BG_DELTA = variantColor;
            backgroundColor = new Color(15 + variantColor, 15 + variantColor, 15 + variantColor);
            lastTime_flicker = System.currentTimeMillis();
            flakeIndex += 2; flakeIndex %= 150;
        }
        g2.setColor(backgroundColor);
        g2.fillRect(0, 0, Assets.GAME_WIDTH, Assets.GAME_HEIGHT);

        int status = GameLoop.renderScreen(g2);
        if (status == 1) {
            gameThread = null;
            return;
        }

        int flakeStart = flakeIndex;
        g2.setColor(crtFlakeColor);
        if (flakeStart > 80) {
            g2.fillRect(0, 0, Assets.GAME_WIDTH, flakeStart - 80);
        }
        while (flakeStart + 15 < Assets.GAME_WIDTH) {
            g2.fillRect(0, flakeStart, Assets.GAME_WIDTH, 70);
            flakeStart += 150;
        }

        frameRate++;
        if (System.currentTimeMillis() - lastTime_frameRate > 1000) {
            Assets.FRAME_RATE = frameRate;
            frameRate = 0;
            lastTime_frameRate = System.currentTimeMillis();
        }
        AttributedString fpsText = new AttributedString("FPS: " + Assets.FRAME_RATE);
        fpsText.addAttribute(TextAttribute.FONT, Assets.DELICATUS_FONT.deriveFont(16f));
        fpsText.addAttribute(TextAttribute.FOREGROUND, new Color(144, 238, 144));
        g2.drawString(fpsText.getIterator(), 10, 20);

        // Dispose of the graphics object
        g2.dispose();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();
        }
        System.exit(0);
    }

}
