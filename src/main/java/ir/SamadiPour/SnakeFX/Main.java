package ir.SamadiPour.SnakeFX;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXSnackbar;
import ir.SamadiPour.SnakeFX.core.Snake;
import ir.SamadiPour.SnakeFX.logic.Game;
import ir.SamadiPour.SnakeFX.logic.KeyboardHandler;
import ir.SamadiPour.SnakeFX.setting.ScreenSize;
import ir.SamadiPour.SnakeFX.setting.Setting;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static final Snake player = new Snake();

    @Override
    public void start(Stage primaryStage) throws Exception{
        //MainPanel
        BorderPane borderPane = new BorderPane();
        //Nice black decorator
        JFXDecorator decorator = new JFXDecorator(primaryStage,borderPane, false,false,true);
        //set needed panels
        borderPane.setLeft(FXMLLoader.load(getClass().getResource("/layout/SidePanelView.fxml")));
        Pane pane = Game.createContent();
        pane.getChildren().add(player.getSnakeBody());
        borderPane.setCenter(pane);
        borderPane.getCenter().setFocusTraversable(true);
        //creating window
        Scene scene = new Scene(decorator, Setting.AppWidth() + 280, Setting.AppHeight() + ScreenSize.BLOCK_SIZE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        //keyboard listener
        scene.setOnKeyPressed(KeyboardHandler::onPressHandler);
        scene.setOnKeyReleased(KeyboardHandler::onReleaseHandler);
        //adding stylesheet
        scene.getStylesheets().add("/stylesheet/stylesheet.css");
        //pass pane to Snackbar
        Game.setSnackbar(new JFXSnackbar((Pane) borderPane.getCenter()));
        primaryStage.show();
        //setting icon
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));
        //starting game
        Game.getCurrentGame().start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
