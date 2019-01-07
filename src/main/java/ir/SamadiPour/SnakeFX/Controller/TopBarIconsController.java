package ir.SamadiPour.SnakeFX.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import ir.SamadiPour.SnakeFX.Main;
import ir.SamadiPour.SnakeFX.Setting.Setting;
import ir.SamadiPour.SnakeFX.logic.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TopBarIconsController implements Initializable {
    @FXML
    private JFXButton infoBtn;

    @FXML
    private JFXButton highScoreBtn;

    @FXML
    private JFXButton minimizeBtn;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private HBox hBoxPane;

    @FXML
    void closeBtnClicked(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void highScoreClicked(MouseEvent event) {
        Game.setGamePause(true);
        Game.getTimeline().pause();
        Game.getCurrentGame().showHighScoreDialog();
    }

    @FXML
    void infoBtnClicked(MouseEvent event) {
        Game.setGamePause(true);
        Game.getTimeline().pause();
        Game.setInDialog(true);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        //body
        ImageView imageView = new ImageView("images/hint.png");
        imageView.setFitWidth(Setting.AppWidth() - 150);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-background-color: transparent");
        dialogLayout.setBody(imageView);
        JFXDialog dialog = new JFXDialog(Main.stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        //button
        JFXButton ok = new JFXButton("OK");
        ok.setButtonType(JFXButton.ButtonType.RAISED);
        ok.getStyleClass().add("pink-btn");
        dialogLayout.setActions(ok);
        ok.setOnMouseClicked(event1 -> {
            dialog.close();
            Game.setInDialog(false);
        });
        dialog.show();
    }

    @FXML
    void minimizeBtnClicked(MouseEvent event) {
        Game.setGamePause(true);
        Game.getTimeline().pause();
        Stage stage = (Stage)((JFXButton)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hBoxPane.setMinWidth(Setting.AppWidth() + 280);
        hBoxPane.setPrefWidth(Setting.AppWidth() + 280);
        closeBtn.setFocusTraversable(false);
        minimizeBtn.setFocusTraversable(false);
        infoBtn.setFocusTraversable(false);
        highScoreBtn.setFocusTraversable(false);
    }
}
