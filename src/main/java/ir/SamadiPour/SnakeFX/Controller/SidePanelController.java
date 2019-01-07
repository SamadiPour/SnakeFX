package ir.SamadiPour.SnakeFX.Controller;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.effects.JFXDepthManager;
import ir.SamadiPour.SnakeFX.Setting.Setting;
import ir.SamadiPour.SnakeFX.Setting.SpeedLevel;
import ir.SamadiPour.SnakeFX.logic.Game;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class SidePanelController implements ChangeListener {

    @FXML
    JFXColorPicker headColorPicker;
    @FXML
    JFXColorPicker tailColorPicker;
    @FXML
    JFXSlider speedSlider;
    @FXML
    ImageView snakeImage;

    public void initialize() {
        //"this" refer to class listener
        speedSlider.valueProperty().addListener(this);
        headColorPicker.setValue(Color.FORESTGREEN);
        tailColorPicker.setValue(Color.LIMEGREEN);
        speedSlider.setFocusTraversable(false);
        speedSlider.setOnMouseReleased(event -> Game.getGamePane().requestFocus());
        headColorPicker.setFocusTraversable(false);
        tailColorPicker.setFocusTraversable(false);
        JFXDepthManager.setDepth(snakeImage, 2);
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (Math.floor((Double) oldValue) != Math.floor((Double) newValue)) {
            SpeedLevel.setSpeed((int) speedSlider.getValue());
            Game.createNewKeyFrame();
        }
    }

    public void setTailColor() {
        if (Setting.tailColor != tailColorPicker.getValue())
            Setting.tailColor = tailColorPicker.getValue();
    }

    public void setHeadColor() {
        if (Setting.headColor != headColorPicker.getValue())
            Setting.headColor = headColorPicker.getValue();
    }
}