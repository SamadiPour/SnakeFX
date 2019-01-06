package ir.SamadiPour.SnakeFX.Scoreboard;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Score extends RecursiveTreeObject<Score> implements Serializable {

    transient private SimpleStringProperty scoreString = new SimpleStringProperty();
    private String playerName;
    private int score;

    public Score() {
        score = 0;
        scoreString.set(String.valueOf(score));
    }

    public Score(int score, String name) {
        this.score = score;
        playerName = name;
        scoreString.set(String.valueOf(score));
    }

    //-------------------------------------------------------------------
    //setter and getter
    public int getScore() {
        return score;
    }

    public String getPlayerName() {
        return playerName;
    }

    //-------------------------------------------------------------------
    //some functions
    public void increase() {
        score++;
        scoreString.set(String.valueOf(score));
    }

    public void resetScore() {
        score = 0;
        scoreString.set(String.valueOf(score));
    }

    public SimpleStringProperty getScoreProperty() {
        if (scoreString == null)
            scoreString = new SimpleStringProperty(String.valueOf(score));
        return scoreString;
    }
}
