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

    @FXML
    private void handleMousePressed(MouseEvent event) {

        if (event.isSecondaryButtonDown()) {

            final ContextMenu cm = new ContextMenu();

            final MenuItem addNewNode = new MenuItem("Add a new node...");
            final MenuItem deleteNode = new MenuItem("Delete an existing node...");

            cm.getItems().addAll(addNewNode, deleteNode);

            cm.show(applicationScreen, event.getScreenX(), event.getScreenY());

            // TODO: The menu should disappear when mouse is clicked somewhere else on the pane.
        }

    }

}
