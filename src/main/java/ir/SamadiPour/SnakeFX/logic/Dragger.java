package ir.SamadiPour.SnakeFX.logic;

import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * @author SamadiPour
 */
public class Dragger {

    private static double xOffSet = 0;
    private static double yOffSet = 0;

    public static void makeStageDraggable(Node parent, Stage stage) {
        parent.setOnMousePressed((event) -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        parent.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() - xOffSet);
            stage.setY(event.getScreenY() - yOffSet);
            stage.setOpacity(0.8f);
        });
        parent.setOnDragDone((event) -> {
            stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((event) -> {
            stage.setOpacity(1.0f);
        });
    }
}
