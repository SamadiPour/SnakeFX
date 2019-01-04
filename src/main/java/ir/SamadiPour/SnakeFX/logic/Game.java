package ir.SamadiPour.SnakeFX.logic;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.effects.JFXDepthManager;
import ir.SamadiPour.SnakeFX.Main;
import ir.SamadiPour.SnakeFX.core.Food;
import ir.SamadiPour.SnakeFX.setting.ScreenSize;
import ir.SamadiPour.SnakeFX.setting.Setting;
import ir.SamadiPour.SnakeFX.setting.SpeedLevel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Game {
    //--------------------------------------------------------------------------------
    //Game status: gameRunning for losing or winning and gamePause for Pause
    private static boolean gameRunning = false;
    private static boolean gamePause = false;
    private static Pane gamePane = new Pane();
    private static Game game = new Game();
    private static Food food = new Food();

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
    //--------------------------------------------------------------------------------
    //game functions


    public static Pane getGamePane() {
        return gamePane;
    }

    public void addNewFood() {
        gamePane.getChildren().remove(food.getFood());
        food = new Food();
        gamePane.getChildren().add(food.getFood());
    }

    public static Pane createContent() {
        gamePane.setPrefSize(Setting.AppWidth() - 11, Setting.AppHeight() - 11);
        //main game works
        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / SpeedLevel.getSpeed()), event -> Game.getCurrentGame().gameLoop());
        //making loop for first time with indefinite time
        Game.init(frame);
        //adding content to display
        gamePane.getChildren().addAll(food.getFood());
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
    public void stop() {
        food.destroyFood();
        setGameRunning(false);
        timeline.stop();
    }

    //stop game and clear all
    private void fullStop() {
        setGameRunning(false);
        timeline.stop();
        Main.player.clearSnake();
        Score.resetScore();
    }

    //basic setting and running game. before start we should fullStop Game
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
        if (Score.getScore() == Setting.MAX_SCORE_COUNT.get()) {
            showSnackbar("Victory!");
            stop();
        }
    }

    //--------------------------------------------------------------------------------
    //Game Thread for playing.
    private static Timeline timeline = new Timeline();

    static Timeline getTimeline() {
        return timeline;
    }

    public static void init(KeyFrame frame) {
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
    public void gameLoop() {
        //stop loop if it stopped
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
        }
    }

    //---------------------------------------------------------------
    //SnackBar
    private static JFXSnackbar snackbar;

    public static void setSnackbar(JFXSnackbar snackbar) {
        Game.snackbar = snackbar;
    }

    public static void showSnackbar(String text) {
        snackbar.show(text,2500);
    }
}
