package ir.SamadiPour.SnakeFX.Setting;


public class SpeedLevel {
    private static int Speed = 10;

    public static int getSpeed() {
        return Math.max(1, Speed);
    }

    public static void setSpeed(int speed) {
        Speed = speed;
    }
}