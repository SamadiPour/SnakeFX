package ir.SamadiPour.SnakeFX.Scoreboard;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import ir.SamadiPour.SnakeFX.logic.Game;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.FlowPane;

public class HighScoreTreeTable {
    private JFXTreeTableColumn<Score, String> playerNameColumn;
    private JFXTreeTableColumn<Score, String> scoreColumn;

    public HighScoreTreeTable() {
        playerNameColumn = new JFXTreeTableColumn<>("Player Name");
        playerNameColumn.setPrefWidth(200);
        playerNameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Score, String> param) -> {
            if (playerNameColumn.validateValue(param))
                return new SimpleStringProperty(param.getValue().getValue().getPlayerName());
            else return playerNameColumn.getComputedValue(param);
        });

        scoreColumn = new JFXTreeTableColumn<>("Score");
        scoreColumn.setPrefWidth(180);
        scoreColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Score, String> param) -> {
            if (scoreColumn.validateValue(param))
                return param.getValue().getValue().getScoreProperty();
            else return scoreColumn.getComputedValue(param);
        });
    }

    public FlowPane getHighScoreTreeTable() {
        // data
        ObservableList<Score> scoreList = Game.getHighScoreManager().getHighScoreList();
        // build tree
        final TreeItem<Score> root = new RecursiveTreeItem<>(scoreList, RecursiveTreeObject::getChildren);
        JFXTreeTableView<Score> treeView = new JFXTreeTableView<>(root);
        treeView.setShowRoot(false);
        treeView.getColumns().setAll(playerNameColumn, scoreColumn);
        treeView.setPrefHeight(490);

        FlowPane main = new FlowPane();
        main.getChildren().add(treeView);
        return main;
    }
}