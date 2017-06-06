package pbl.brainstorm.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pbl.brainstorm.IdeaNode;
import pbl.brainstorm.dao.Dao;
import pbl.brainstorm.dao.IdeaNodeListDaoFactory;
import pbl.brainstorm.dialogs.DialogUtils;

public class MenuController implements Initializable {

    private MainController mainController;

    private List<IdeaNode> list;

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

    @FXML
    private Button loadButton;

    public void setMainController(MainController mainController) {

        this.mainController = mainController;

    }

    @FXML
    public void exitApp() {

        Optional<ButtonType> result = DialogUtils.confirmationDialog();

        if (result.get() == ButtonType.OK) {

            Platform.exit();
            System.exit(0);

        }

    }

    @FXML
    public void startBrainstorming() {

        if (!addressField.getText().isEmpty() && !portField.getText().isEmpty()) {

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/ApplicationScreen.fxml"));

            Pane pane = null;

            try {

                pane = loader.load();

            } catch (IOException ex) {

                System.err.println(ex);

            }

            ApplicationScreenController appController = loader.getController();

            appController.setAddress(addressField.getText());

            appController.setPort(Integer.valueOf(portField.getText()));

            appController.setList(list);

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
    private void handleMouseClicked() {

        menuPane.requestFocus();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        list = new ArrayList<>();

    }

    @FXML
    private void handleLoad() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose a state to load:");

        File outputFile = fileChooser.showOpenDialog(menuPane.getScene().getWindow());

        if (outputFile != null) {

            try (Dao<List<IdeaNode>> dao = IdeaNodeListDaoFactory.getFileDao(outputFile.getAbsolutePath());) {

                list = dao.read();

            } catch (Exception ex) {

                System.err.println(ex);

            }

            startBrainstorming();

        }

    }

}
