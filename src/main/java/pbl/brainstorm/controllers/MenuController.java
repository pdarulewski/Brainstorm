package pbl.brainstorm.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class MenuController {

    private MainController mainController;

    @FXML
    private TextField problemField;

    @FXML
    private Pane applicationPane;

    public void setMainController(MainController mainController) {

        this.mainController = mainController;

    }

    @FXML
    public void exitApp() {

        Platform.exit();

    }

    @FXML
    public void startBrainstorming() {

        if (!problemField.getText().isEmpty()) {

            String s = problemField.getText();

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/ApplicationScreen.fxml"));

            Pane pane = null;

            try {

                pane = loader.load();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

            ApplicationScreenController appController = loader.getController();

            appController.setMainController(mainController);

            //mainController.resizeAnchorPane(applicationPane.getWidth(), applicationPane.getHeight());
            
            // TODO: Try to make MainScreen as big as ApplicationScreen.

            mainController.setScreen(pane);

        }

    }

}
