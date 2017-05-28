package pbl.brainstorm.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    private MainController mainController;

    @FXML
    private Pane menuPane;
    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label emptyProblemField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField portField;

    public void setMainController(MainController mainController) {

        this.mainController = mainController;

    }

    @FXML
    public void exitApp() {

        Platform.exit();

    }

    @FXML
    public void startBrainstorming() {

        if (!addressField.getText().isEmpty() && !portField.getText().isEmpty()) {

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/ApplicationScreen.fxml"));

            Pane pane = null;

            try {

                pane = loader.load();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

            ApplicationScreenController appController = loader.getController();

            appController.setMainController(mainController);

            appController.setMenuController(this);
            
            appController.setAddress(addressField.getText());
            
            appController.setPort(Integer.valueOf(portField.getText()));

            mainController.setScreen(pane);

            Stage stage = (Stage) pane.getScene().getWindow();

            stage.setMaximized(true);
        }

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
