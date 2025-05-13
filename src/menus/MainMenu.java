package menus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;

import config.Assets;
import config.GameLoop;

public class MainMenu {

    long lastTime = 0;

    AttributedString menuTitle = new AttributedString(Assets.GAME_NAME);
    AttributedString menuVersion = new AttributedString(Assets.GAME_VERSION);
    AttributedString menuSubtitle = new AttributedString("A  Morally  Gray  Simulator");
    AttributedString[] menuOptions = {
        new AttributedString("Start Game"),
        new AttributedString("Options"),
        new AttributedString("Exit")
    };
    
    public MainMenu() {
        lastTime = System.currentTimeMillis();

        menuTitle.addAttribute(TextAttribute.FONT, Assets.BLOXAT_FONT.deriveFont(150f));
        menuTitle.addAttribute(TextAttribute.FONT, Assets.DELICATUS_FONT.deriveFont(150f), 3, 4);
        menuTitle.addAttribute(TextAttribute.FOREGROUND, new Color(245, 245, 245));

        menuVersion.addAttribute(TextAttribute.FONT, Assets.DELICATUS_FONT.deriveFont(30f));
        if (Assets.GAME_VERSION.contains("BETA")) {
            menuVersion.addAttribute(TextAttribute.FOREGROUND, new Color(144, 238, 144));
        } else {
            menuVersion.addAttribute(TextAttribute.FOREGROUND, new Color(245, 245, 245));
        }

        menuSubtitle.addAttribute(TextAttribute.FONT, Assets.DELICATUS_FONT.deriveFont(30f));
        menuSubtitle.addAttribute(TextAttribute.FOREGROUND, new Color(245, 245, 245));

        for (int i = 0; i < menuOptions.length; i++) {
            menuOptions[i].addAttribute(TextAttribute.FONT, Assets.DELICATUS_FONT.deriveFont(25f));
            menuOptions[i].addAttribute(TextAttribute.FOREGROUND, new Color(245, 245, 245));
        }
    }

    public void render(Graphics2D g2) {
        int hoveringOption = -1;

        g2.setColor(new Color(245, 245, 245));

        g2.drawString(
            menuTitle.getIterator(),
            (Assets.GAME_WIDTH - (int) g2.getFontMetrics(Assets.BLOXAT_FONT.deriveFont(150f)).getStringBounds(Assets.GAME_NAME, g2).getWidth()) / 2,
            (Assets.GAME_HEIGHT - 50) / 2 - 60
        );

        g2.drawString(
            menuVersion.getIterator(),
            (Assets.GAME_WIDTH - (int) g2.getFontMetrics(Assets.BLOXAT_FONT.deriveFont(150f)).getStringBounds(Assets.GAME_NAME, g2).getWidth() + 10) / 2,
            (Assets.GAME_HEIGHT - 50) / 2 - 175
        );

        g2.drawString(
            menuSubtitle.getIterator(),
            (Assets.GAME_WIDTH - (int) g2.getFontMetrics(Assets.DELICATUS_FONT.deriveFont(30f)).getStringBounds("A  Morally  Gray  Simulator", g2).getWidth()) / 2,
            (Assets.GAME_HEIGHT - 50) / 2 - 20
        );

        for (int i = 0; i < menuOptions.length; i++) {
            TextLayout layout = new TextLayout(menuOptions[i].getIterator(), g2.getFontRenderContext());
            int strWidth = (int) layout.getBounds().getWidth();
            int strHeight = (int) layout.getBounds().getHeight();

            PointerInfo mouseInfo = MouseInfo.getPointerInfo();
            if (mouseInfo != null) {
                int mouseX = (int) mouseInfo.getLocation().getX();
                int mouseY = (int) mouseInfo.getLocation().getY();
                if (mouseX >= Assets.WIDTH_PADDING + (Assets.GAME_WIDTH - strWidth) / 2 - 10
                && mouseX <= Assets.WIDTH_PADDING + (Assets.GAME_WIDTH + strWidth) / 2 + 10
                && mouseY >= Assets.HEIGHT_PADDING + (Assets.GAME_HEIGHT + 250) / 2 + (i * 50) - strHeight - 10
                && mouseY <= Assets.HEIGHT_PADDING + (Assets.GAME_HEIGHT + 250) / 2 + (i * 50) + strHeight + 10) {
                    menuOptions[i].addAttribute(TextAttribute.FOREGROUND, new Color(15 + Assets.CURRENT_BG_DELTA, 15 + Assets.CURRENT_BG_DELTA, 15 + Assets.CURRENT_BG_DELTA));
                    g2.fillRect((Assets.GAME_WIDTH - strWidth) / 2 - 10, (Assets.GAME_HEIGHT + 250) / 2 + (i * 50) - strHeight - 10, strWidth + 22, strHeight + 22);
                    hoveringOption = i;
                } else {
                    menuOptions[i].addAttribute(TextAttribute.FOREGROUND, new Color(245, 245, 245));
                }
            }

            g2.drawString(
                menuOptions[i].getIterator(),
                (Assets.GAME_WIDTH - strWidth) / 2,
                (Assets.GAME_HEIGHT + 250) / 2 + (i * 50)
            );

            if (hoveringOption == 2 && GameLoop.mouseClicked) {
                GameLoop.SCREEN = "EXIT";
            }
        }
    }

}
