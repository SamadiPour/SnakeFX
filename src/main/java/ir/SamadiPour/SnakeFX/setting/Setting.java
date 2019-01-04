package ir.SamadiPour.SnakeFX.setting;

import javafx.scene.paint.Color;

public enum Setting {
    MAX_SCORE_COUNT(80),
    COLUMN_COUNT(20),
    ROW_COUNT(15);

    final Integer value;
    Setting(final Integer value) {
        this.value=value;
    }

    public Integer get() {
        return value;
    }
    //--------------------------------------------------------------------
    public static int AppHeight(){
        return ROW_COUNT.get() * ScreenSize.BLOCK_SIZE;
    }

    public static int AppWidth(){
        return COLUMN_COUNT.get() * ScreenSize.BLOCK_SIZE;
    }

    public static int randomCoordinateX(){
        return (int) (Math.random() * (AppWidth() - ScreenSize.BLOCK_SIZE)) / ScreenSize.BLOCK_SIZE * ScreenSize.BLOCK_SIZE;
    }
    public static int randomCoordinateY(){
        return (int) (Math.random() * (AppHeight() - ScreenSize.BLOCK_SIZE)) / ScreenSize.BLOCK_SIZE * ScreenSize.BLOCK_SIZE;
    }
    //--------------------------------------------------------------------
    public static Color headColor = Color.FORESTGREEN;
    public static Color tailColor = Color.LIMEGREEN;

}
