package ir.SamadiPour.SnakeFX.logic;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ir.SamadiPour.SnakeFX.Main;

public class KeyboardHandler {

    public static void onPressHandler(KeyEvent e) {
        if (!Main.player.isMoved() | Game.isInDialog())
            return;
        if (validMovingKey(e.getCode())) {
            if (!Game.isGamePause())
                MovementHandler(e);
            else return;
            Main.player.setMoved(false);
        }
    }

    public static void onReleaseHandler(KeyEvent e) {
        if (Game.isInDialog())
            return;
        if (e.getCode() == KeyCode.R) {
            Game.getCurrentGame().restart();
            e.consume();
        }
        if (e.getCode() == KeyCode.P && !Game.isGamePause()) {
            Game.setGamePause(true);
            Game.getTimeline().pause();
            e.consume();
        } else if (e.getCode() == KeyCode.P) {
            Game.setGamePause(false);
            Game.getTimeline().play();
            e.consume();
        }
    }

    private static void MovementHandler(KeyEvent e) {
        switch (e.getCode()) {
            case UP:
                if (Direction.getDirection() != Direction.DirectionEnum.DOWN)
                    Direction.setDirection(Direction.DirectionEnum.UP);
                e.consume();
                break;
            case DOWN:
                if (Direction.getDirection() != Direction.DirectionEnum.UP)
                    Direction.setDirection(Direction.DirectionEnum.DOWN);
                e.consume();
                break;
            case LEFT:
                if (Direction.getDirection() != Direction.DirectionEnum.RIGHT)
                    Direction.setDirection(Direction.DirectionEnum.LEFT);
                e.consume();
                break;
            case RIGHT:
                if (Direction.getDirection() != Direction.DirectionEnum.LEFT)
                    Direction.setDirection(Direction.DirectionEnum.RIGHT);
                e.consume();
                break;
        }
    }

    private static boolean validMovingKey(KeyCode code) {
        return code == KeyCode.UP || code == KeyCode.DOWN || code == KeyCode.LEFT || code == KeyCode.RIGHT;
    }
}
