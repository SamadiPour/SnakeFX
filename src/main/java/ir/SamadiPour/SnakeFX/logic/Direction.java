package ir.SamadiPour.SnakeFX.logic;

import java.util.Random;

class Direction {

    public enum DirectionEnum {
        UP, DOWN, LEFT, RIGHT
    }

    private static DirectionEnum direction;

    static {
        direction = new Direction().getRandomDirection();
    }

    static DirectionEnum getDirection() {
        return direction;
    }

    static void setDirection(DirectionEnum d) {
        direction = d;
    }

    DirectionEnum getRandomDirection() {
        return DirectionEnum.values()[new Random().nextInt(4)];
    }
}
