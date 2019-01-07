package ir.SamadiPour.SnakeFX;

import com.jfoenix.controls.JFXSnackbar;
import ir.SamadiPour.SnakeFX.Objects.Snake;
import ir.SamadiPour.SnakeFX.Setting.ScreenSize;
import ir.SamadiPour.SnakeFX.Setting.Setting;
import ir.SamadiPour.SnakeFX.logic.Dragger;
import ir.SamadiPour.SnakeFX.logic.Game;
import ir.SamadiPour.SnakeFX.logic.KeyboardHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static final Snake player = new Snake();
    public static StackPane stackPane = new StackPane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        stackPane.getChildren().add(borderPane);
        stackPane.getStyleClass().add("stack-pane");
        Dragger.makeStageDraggable(stackPane, primaryStage);

        borderPane.setLeft(FXMLLoader.load(getClass().getResource("/layout/SidePanelView.fxml")));
        Pane pane = Game.createContent();
        pane.getChildren().add(player.getSnakeBody());
        borderPane.setCenter(pane);
        borderPane.getCenter().setFocusTraversable(true);

        borderPane.setTop(FXMLLoader.load(getClass().getResource("/layout/TopBarIcons.fxml")));

        Scene scene = new Scene(stackPane, Setting.AppWidth() + 276, Setting.AppHeight() + ScreenSize.BLOCK_SIZE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        scene.setOnKeyPressed(KeyboardHandler::onPressHandler);
        scene.setOnKeyReleased(KeyboardHandler::onReleaseHandler);
        Game.setSnackbar(new JFXSnackbar((Pane) borderPane.getCenter()));

        scene.getStylesheets().add(Main.class.getResource("/stylesheet/stylesheet.css").toExternalForm());
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        Label score = new Label();
        score.textProperty().bind(Game.getScore().getScoreProperty());
        score.getStyleClass().add("score-text");
        score.setFont(new Font("Roboto", ScreenSize.BLOCK_SIZE * 7));
        score.setAlignment(Pos.CENTER);
        score.setTranslateX(score.getTranslateX() + 135);
        score.toFront();
        stackPane.getChildren().add(score);

        primaryStage.show();
        Game.getCurrentGame().start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
