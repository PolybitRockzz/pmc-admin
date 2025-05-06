package config;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import menus.MainMenu;

public class GameLoop {

    // Game Trigger Booleans
    public static boolean mouseClicked = false;

    
    public static String SCREEN = "SPLASH";

    public static long lastTime = 0;

    public static AttributedString splashScreenSubtext = new AttributedString("Polybit  Productions");

    public static MainMenu mainMenu = null;

    public static int renderScreen(Graphics2D g2) {
        switch (SCREEN) {
            case "SPLASH":
                if (System.currentTimeMillis() - lastTime >= 7000 && lastTime != 0) {
                    lastTime = 0; SCREEN = "MAIN_MENU"; break;
                }
                lastTime = lastTime == 0 ? System.currentTimeMillis() : lastTime;
                float alpha = Math.min(1.0f, (System.currentTimeMillis() - lastTime) / 2000.0f);
                g2.setColor(new Color(245, 245, 245, (int) (alpha * 255)));
                int tileSize = Assets.GAME_WIDTH / (5 * 19);
                String logoMap = "1111011110111001111\n1001010010101101001\n1011010010100101101\n1110011110111100111";
                int x = (Assets.GAME_WIDTH - (tileSize * 19)) / 2;
                int y = (Assets.GAME_HEIGHT - (tileSize * 4)) / 2; y -= 24;
                for (int i = 0; i < logoMap.length(); i++) {
                    if (logoMap.charAt(i) == '\n') {
                        x = (Assets.GAME_WIDTH - (tileSize * 19)) / 2;
                        y += tileSize;
                        continue;
                    }
                    if (logoMap.charAt(i) == '1') {
                        g2.fillRect(x, y, tileSize, tileSize);
                    }
                    x += tileSize;
                }
                splashScreenSubtext.addAttribute(TextAttribute.FONT, Assets.BLOXAT_FONT.deriveFont(20f));
                splashScreenSubtext.addAttribute(TextAttribute.FONT, Assets.DELICATUS_FONT.deriveFont(20f), 7, 9);
                splashScreenSubtext.addAttribute(TextAttribute.FOREGROUND, new Color(245, 245, 245, (int) (alpha * 255)));
                g2.drawString(splashScreenSubtext.getIterator(), (Assets.GAME_WIDTH - 200) / 2 - 10, (Assets.GAME_HEIGHT - 50) / 2 + 60);
                break;
            case "MAIN_MENU":
                if (mainMenu == null) {
                    mainMenu = new MainMenu();
                }
                mainMenu.render(g2);
                break;
            case "GAME":
                // Game rendering logic
                break;
            case "EXIT":
                return 1;
            default:
                break;
        }
        return 0;
    }

}
