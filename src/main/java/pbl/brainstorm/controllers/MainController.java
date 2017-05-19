package pbl.brainstorm.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane mainPane;

    public void setScreen(Pane pane) {

        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);

    }

    public void initialize() {

        loadMenuScreen();

    }

    public void loadMenuScreen() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/MenuScreen.fxml"));

        Pane pane = null;

        try {

            pane = loader.load();

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        MenuController menuController = loader.getController();

        menuController.setMainController(this);

        setScreen(pane);

    }
}
