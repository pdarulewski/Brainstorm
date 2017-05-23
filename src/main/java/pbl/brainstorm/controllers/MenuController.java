package pbl.brainstorm.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MenuController {

    private MainController mainController;

    @FXML
    private Pane menuPane;

    public void setMainController(MainController mainController) {

        this.mainController = mainController;

    }

    @FXML
    public void exitApp() {

        Platform.exit();

    }

    @FXML
    public void startBrainstorming() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/ApplicationScreen.fxml"));

        Pane pane = null;

        try {

            pane = loader.load();

        } catch (IOException ex) {

            ex.printStackTrace();

        }

        ApplicationScreenController appController = loader.getController();

        appController.setMainController(mainController);

        mainController.setScreen(pane);

    }

    @FXML
    private void handleEnter(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {

            startBrainstorming();

        }

    }

    @FXML
    private void handleMouseClicked(MouseEvent event) {

        menuPane.requestFocus();

    }

}
