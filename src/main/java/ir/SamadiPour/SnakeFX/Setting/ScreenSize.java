package ir.SamadiPour.SnakeFX.Setting;

import javafx.stage.Screen;

public class ScreenSize {
    public static final int BLOCK_SIZE;

    static {
        switch ((int) Screen.getPrimary().getBounds().getHeight()) {
            case 2160:
                BLOCK_SIZE = 133;
                break;
            case 1440:
                BLOCK_SIZE = 85;
                break;
            case 1080:
                BLOCK_SIZE = 60;
                break;
            case 1024:
                BLOCK_SIZE = 58;
                break;
            case 900:
                BLOCK_SIZE = 52;
                break;
            case 800:
                BLOCK_SIZE = 46;
                break;
            case 768:
                BLOCK_SIZE = 45;
                break;
            case 720:
                BLOCK_SIZE = 42;
                break;
            default:
                BLOCK_SIZE = 32;
        }
    }

}
