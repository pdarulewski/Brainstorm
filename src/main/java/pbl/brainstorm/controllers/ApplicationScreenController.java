package pbl.brainstorm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class ApplicationScreenController {

    private MainController mainController;

    public void setMainController(MainController mainController) {

        this.mainController = mainController;

    }

    @FXML
    private StackPane applicationScreen;

    final ContextMenu cm = new ContextMenu();

    @FXML
    private void handleMousePressed(MouseEvent event) {

        if (event.isSecondaryButtonDown()) {

            if (!cm.getItems().isEmpty()) {

                cm.getItems().clear();

            }

            final MenuItem addNewNode = new MenuItem("Add a new node...");
            final MenuItem deleteNode = new MenuItem("Delete an existing node...");

            cm.getItems().addAll(addNewNode, deleteNode);

            cm.show(applicationScreen, event.getScreenX(), event.getScreenY());

        }

        if (event.isPrimaryButtonDown()) {

            cm.hide();

        }

    }

}
