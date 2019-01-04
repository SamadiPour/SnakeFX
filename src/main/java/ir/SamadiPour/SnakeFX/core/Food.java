package ir.SamadiPour.SnakeFX.core;

import animatefx.animation.BounceIn;
import animatefx.animation.Hinge;
import com.jfoenix.effects.JFXDepthManager;
import ir.SamadiPour.SnakeFX.Main;
import ir.SamadiPour.SnakeFX.setting.ScreenSize;
import ir.SamadiPour.SnakeFX.setting.Setting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle {
    private Rectangle food;

    public Food() {
        food = new Rectangle(ScreenSize.BLOCK_SIZE, ScreenSize.BLOCK_SIZE, Color.BLUE);
        food.setFill(Color.BLUE);
        JFXDepthManager.setDepth(food, 1);
        generateFood();
    }

    public Rectangle getFood() {
        return food;
    }

    public void generateFood() {
        do {
            food.setTranslateX(Setting.randomCoordinateX());
            food.setTranslateY(Setting.randomCoordinateY());
        } while (!checkFood());
        new BounceIn(food).play();
    }

    public void destroyFood() {
        new Hinge(food).play();
    }

    private boolean checkFood() {
        for (int i = 0; i < Main.player.getSnakeSize(); i++) {
            if (Main.player.getSnakePart(i).getTranslateX() == food.getTranslateX() &
                    Main.player.getSnakePart(i).getTranslateY() == food.getTranslateY()) {
                return false;
            }
        }
        return true;
    }
}
