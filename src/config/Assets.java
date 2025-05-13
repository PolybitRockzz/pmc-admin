package config;

import java.awt.Font;
import java.io.File;
import java.awt.FontFormatException;
import java.io.IOException;

public class Assets {

    public static final String GAME_NAME = "PMC Admin";
    public static final String GAME_VERSION = "v0.1.0 BETA";

    public static int GAME_WIDTH = 1280;
    public static int GAME_HEIGHT = 720;

    public static int WIDTH_PADDING = 0;
    public static int HEIGHT_PADDING = 0;

    public static int CURRENT_BG_DELTA = 0;

    public static int FRAME_RATE = 0;

    public static Font DELICATUS_FONT;
    public static Font BLOXAT_FONT = null;

    static {
        try {
            DELICATUS_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("./assets/fonts/Delicatus.ttf"));
            BLOXAT_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("./assets/fonts/Bloxat.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            DELICATUS_FONT = new Font("Serif", Font.PLAIN, 12);
            BLOXAT_FONT = new Font("Serif", Font.PLAIN, 12);
        }
    }

}
