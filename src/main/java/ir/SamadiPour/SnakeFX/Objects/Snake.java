package ir.SamadiPour.SnakeFX.Objects;

import animatefx.animation.Pulse;
import com.jfoenix.effects.JFXDepthManager;
import ir.SamadiPour.SnakeFX.Setting.ScreenSize;
import ir.SamadiPour.SnakeFX.Setting.Setting;
import ir.SamadiPour.SnakeFX.logic.Game;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class Snake {
    private boolean moved = false;

    //we create a group and get it's children and make change. group will be add directly to pane
    private Group snakeBody = new Group();
    private ObservableList<Node> snake = snakeBody.getChildren();

    //getters
    public boolean isMoved() {
        return moved;
    }

    public Node getSnakePart(int i) {
        return snake.get(i);
    }

    public ObservableList<Node> getSnake() {
        return snake;
    }

    public int getSnakeSize() {
        return snake.size();
    }

    public Group getSnakeBody() {
        return snakeBody;
    }

    //setters and other functions
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public void addSnake(int i, Node node) {
        snake.add(i, node);
    }

    public void addSnake(Node node) {
        snake.add(node);
    }

    public void clearSnake() {
        snake.clear();
    }

    public Node removeSnake(int i) {
        return snake.remove(i);
    }

    public void eatFood(Node tail) {
        Rectangle rect = new Rectangle(ScreenSize.BLOCK_SIZE, ScreenSize.BLOCK_SIZE);
        rect.setTranslateX(tail.getTranslateX());
        rect.setTranslateY(tail.getTranslateY());
        rect.setFill(Setting.headColor);

        JFXDepthManager.setDepth(rect, 2);
        Game.getScore().increase();
        Game.getCurrentGame().checkVictory();
        addSnake(rect);

        //eating effect
        new Thread(() -> {
            for (Node node : snake) {
                new Pulse(node).setSpeed(2).play();
            }
        }).start();
    }

    public void eatSnake(Node tail) {
        for (Node rect : getSnake()) {
            if (rect != tail & (tail.getTranslateX() == rect.getTranslateX() &
                    tail.getTranslateY() == rect.getTranslateY())) {
                Rectangle head = (Rectangle) snake.get(1);
                head.setFill(Setting.headColor);
                Game.getCurrentGame().gameOver();
                break;
            }
        }
    }
}
