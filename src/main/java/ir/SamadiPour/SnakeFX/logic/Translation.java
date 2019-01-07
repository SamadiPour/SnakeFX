package ir.SamadiPour.SnakeFX.logic;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import ir.SamadiPour.SnakeFX.Main;
import ir.SamadiPour.SnakeFX.Setting.ScreenSize;
import ir.SamadiPour.SnakeFX.Setting.Setting;

class Translation {

    static void DirectionHandle(Node tail) {
        switch (Direction.getDirection()) {
            case UP:
                tail.setTranslateX(Main.player.getSnakePart(0).getTranslateX());
                if (Main.player.getSnakePart(0).getTranslateY() - ScreenSize.BLOCK_SIZE < 0)
                    tail.setTranslateY(Setting.AppHeight() - ScreenSize.BLOCK_SIZE);
                else
                    tail.setTranslateY(Main.player.getSnakePart(0).getTranslateY() - ScreenSize.BLOCK_SIZE);
                break;
            case DOWN:
                tail.setTranslateX(Main.player.getSnakePart(0).getTranslateX());
                if (Main.player.getSnakePart(0).getTranslateY() + ScreenSize.BLOCK_SIZE >= Setting.AppHeight())
                    tail.setTranslateY(0);
                else
                    tail.setTranslateY(Main.player.getSnakePart(0).getTranslateY() + ScreenSize.BLOCK_SIZE);
                break;
            case LEFT:
                if (Main.player.getSnakePart(0).getTranslateX() - ScreenSize.BLOCK_SIZE < 0)
                    tail.setTranslateX(Setting.AppWidth() - ScreenSize.BLOCK_SIZE);
                else
                    tail.setTranslateX(Main.player.getSnakePart(0).getTranslateX() - ScreenSize.BLOCK_SIZE);
                tail.setTranslateY(Main.player.getSnakePart(0).getTranslateY());
                break;
            case RIGHT:
                if (Main.player.getSnakePart(0).getTranslateX() + ScreenSize.BLOCK_SIZE >= Setting.AppWidth())
                    tail.setTranslateX(0);
                else
                    tail.setTranslateX(Main.player.getSnakePart(0).getTranslateX() + ScreenSize.BLOCK_SIZE);
                tail.setTranslateY(Main.player.getSnakePart(0).getTranslateY());
                break;
        }
        keepHeadColor(tail);
    }

    private static void keepHeadColor(Node tail) {
        ((Rectangle) tail).setFill(Setting.headColor);
        //head & head with one tail counts 1 size
        //set new added and other nodes color
        if (Main.player.getSnakeSize() > 1 || !tail.equals(Main.player.getSnakePart(0)))
            for (Node node : Main.player.getSnake()) {
                ((Rectangle) node).setFill(Setting.tailColor);
            }
    }
}