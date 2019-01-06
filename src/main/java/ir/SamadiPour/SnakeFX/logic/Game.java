package ir.SamadiPour.SnakeFX.logic;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import ir.SamadiPour.SnakeFX.Main;
import ir.SamadiPour.SnakeFX.Objects.Food;
import ir.SamadiPour.SnakeFX.Scoreboard.HighScoreManager;
import ir.SamadiPour.SnakeFX.Scoreboard.HighScoreTreeTable;
import ir.SamadiPour.SnakeFX.Scoreboard.Score;
import ir.SamadiPour.SnakeFX.Setting.ScreenSize;
import ir.SamadiPour.SnakeFX.Setting.Setting;
import ir.SamadiPour.SnakeFX.Setting.SpeedLevel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Game {

    //--------------------------------------------------------------------------------
    //Game status: gameRunning for losing or winning and gamePause for Pause
    private static HighScoreManager highScoreManager = new HighScoreManager();
    private static HighScoreTreeTable highScoreTreeTable = new HighScoreTreeTable();
    private static boolean gameRunning = false;
    private static boolean gamePause = false;
    private static boolean inDialog = false;
    private static boolean highestScore = false;
    private static Pane gamePane = new Pane();
    private static Game game = new Game();
    private static Food food = new Food();
    private static Score score = new Score();
    private static String lastPlayerName;

    public static Game getCurrentGame() {
        return game;
    }

    public Food getFood() {
        return food;
    }

    static boolean isGamePause() {
        return gamePause;
    }

    static void setGamePause(boolean gamePause) {
        Game.gamePause = gamePause;
    }

    private static boolean isGameRunning() {
        return gameRunning;
    }

    private static void setGameRunning(boolean gameRunning) {
        Game.gameRunning = gameRunning;
    }

    public static Pane getGamePane() {
        return gamePane;
    }

    public static Score getScore() {
        return score;
    }

    public static boolean isInDialog() {
        return inDialog;
    }

    public static HighScoreManager getHighScoreManager() {
        return highScoreManager;
    }

    //--------------------------------------------------------------------------------
    //game functions

    public static Pane createContent() {
        gamePane.setPrefSize(Setting.AppWidth() - 11, Setting.AppHeight() - 11);
        //main game works
        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / SpeedLevel.getSpeed()),
                event -> Game.getCurrentGame().gameLoop());
        //making loop for first time with indefinite time
        Game.init(frame);
        //adding content to display
        gamePane.getChildren().add(food.getFood());
        return gamePane;
    }

    //start new game
    void restart() {
        fullStop();
        addNewFood();
        start();
        Direction.setDirection(new Direction().getRandomDirection());
    }

    //stop game and keep current status
    public void gameOver() {
        food.destroyFood();
        setGameRunning(false);
        if (highScoreManager.isScore(score.getScore())) {
            showSnackbar("Why did you eat yourself? :(( \n Good game anyway *___*");
            showScoreRecordDialog();
        } else
            showSnackbar("unfortunately you didn't break any record :((");
        timeline.stop();
        highestScore = false;
    }

    private void showScoreRecordDialog() {
        inDialog = true;
        //dialog box layout
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        //title
        Text text = new Text("Enter your name:");
        text.setFill(Color.valueOf("#F78C7B"));
        text.getStyleClass().add("custom-font-header");
        dialogLayout.setHeading(text);
        //body
        JFXTextField textField = new JFXTextField();
        textField.getStyleClass().add("jfx-text-field");
        if (lastPlayerName != null)
            textField.setText(lastPlayerName);
        dialogLayout.setBody(textField);
        //button
        JFXButton ok = new JFXButton("Submit");
        ok.setButtonType(JFXButton.ButtonType.RAISED);
        ok.getStyleClass().add("pink-btn");
        JFXButton cancel = new JFXButton("Cancel");
        cancel.setButtonType(JFXButton.ButtonType.RAISED);
        cancel.getStyleClass().add("pink-btn");
        dialogLayout.setActions(ok, cancel);
        //main dialog
        JFXDialog dialog = new JFXDialog(Main.stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        ok.setOnMouseClicked(event -> {
            if (textField.getText() != null) {
                if (textField.getText().length() < 25 & !textField.getText().equals("")) {
                    textField.getStyleClass().remove("wrong-credentials");
                    highScoreManager.addScore(score.getScore(), textField.getText());
                    lastPlayerName = textField.getText();
                    inDialog = false;
                    dialog.close();
                    showHighScoreDialog();
                } else {
                    textField.getStyleClass().add("wrong-credentials");
                }
            } else {
                textField.getStyleClass().add("wrong-credentials");
            }
        });
        cancel.setOnMouseClicked(event -> {
            inDialog = false;
            dialog.close();
        });
        dialog.show();
        textField.requestFocus();
    }

    private void showHighScoreDialog() {
        inDialog = true;
        //dialog box layout
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        //title
        Text text = new Text("SnakeFX HighScores so far!");
        text.setFill(Color.valueOf("#F78C7B"));
        text.getStyleClass().add("custom-font-header");
        dialogLayout.setHeading(text);
        //body
        dialogLayout.setBody(highScoreTreeTable.getHighScoreTreeTable());
        //button
       JFXButton ok = new JFXButton("OK");
        ok.setButtonType(JFXButton.ButtonType.RAISED);
        ok.getStyleClass().add("pink-btn");
        dialogLayout.setActions(ok);
        //main dialog
        JFXDialog dialog = new JFXDialog(Main.stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        ok.setOnMouseClicked(event -> {
            dialog.close();
            inDialog = false;
        });
        dialog.show();
    }

    //stop game and clear all
    private void fullStop() {
        setGameRunning(false);
        timeline.stop();
        Main.player.clearSnake();
        score.resetScore();
        highestScore = false;
    }

    //basic Setting and running game. before start we should fullStop Game
    public void start() {
        Rectangle head = new Rectangle(ScreenSize.BLOCK_SIZE, ScreenSize.BLOCK_SIZE);
        head.setTranslateX(Setting.randomCoordinateX());
        head.setTranslateY(Setting.randomCoordinateY());
        head.setFill(Setting.headColor);
        JFXDepthManager.setDepth(head, 1);
        Main.player.addSnake(head);
        food.generateFood();
        timeline.play();
        setGameRunning(true);
    }

    public void checkVictory() {
        if (score.getScore() == Setting.MAX_SCORE_COUNT.get()) {
            showSnackbar("How the hell did you finished it?!!");
            gameOver();
        }
    }

    private void addNewFood() {
        gamePane.getChildren().remove(food.getFood());
        food = new Food();
        gamePane.getChildren().add(food.getFood());
    }

    //--------------------------------------------------------------------------------
    //Game Thread for playing.
    private static Timeline timeline = new Timeline();

    static Timeline getTimeline() {
        return timeline;
    }

    private static void init(KeyFrame frame) {
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    //create new keyframe with same actions when speed changed
    public static void createNewKeyFrame() {
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.0 / SpeedLevel.getSpeed()), timeline.getKeyFrames().get(0).getOnFinished());
        timeline.stop();
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    //Main Game Thread
    private void gameLoop() {
        //gameOver loop if it stopped
        if (!Game.isGameRunning()) return;
        //remove node from behind
        boolean toRemove = Main.player.getSnakeSize() > 1;
        Node tail = toRemove ? Main.player.removeSnake(Main.player.getSnakeSize() - 1) : Main.player.getSnakePart(0);
        //make move in direction
        Translation.DirectionHandle(tail);
        //in order to not move in 2 direction and same time
        Main.player.setMoved(true);
        //add head
        if (toRemove)
            Main.player.addSnake(0, tail);
        //check if snake eat itself
        Main.player.eatSnake(tail);
        //check if snake is on the wall
        Translation.SideHandle(tail);
        //check if snake eat food
        if (tail.getTranslateX() == food.getFood().getTranslateX() &&
                tail.getTranslateY() == food.getFood().getTranslateY()) {
            Main.player.eatFood(tail);
            food.generateFood();
            if (highScoreManager.isHigherThanHighest(score.getScore()) & !highestScore) {
                showSnackbar("You broke highest score (*_*)");
                highestScore = true;
            }
        }
    }

    //---------------------------------------------------------------
    //SnackBar
    private static JFXSnackbar snackbar;

    public static void setSnackbar(JFXSnackbar snackbar) {
        Game.snackbar = snackbar;
    }

    public static void showSnackbar(String text) {
        snackbar.show(text, 3000);
    }
}
