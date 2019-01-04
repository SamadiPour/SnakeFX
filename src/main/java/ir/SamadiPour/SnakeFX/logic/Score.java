package ir.SamadiPour.SnakeFX.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Score {

    private static IntegerProperty score = new SimpleIntegerProperty();

    public static void increase() {
        score.setValue(score.getValue() + 1);
    }

    static int getScore() {
        return score.getValue();
    }

    static void resetScore() {
        score.setValue(0);
    }

    public static IntegerProperty scoreProperty() {
        return score;
    }
}
