package pbl.brainstorm.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import pbl.brainstorm.controllers.*;

public class ApplicationScreenController {

    private MainController mainController;

    public void setMainController(MainController mainController) {

        this.mainController = mainController;

    }

    @FXML
    private Pane applicationScreen;

    final ContextMenu cm = new ContextMenu();

    @FXML
    private void handleMousePressed(MouseEvent event) {

        if (event.isSecondaryButtonDown()) {

            if (!cm.getItems().isEmpty()) {

                cm.getItems().clear();

            }

            final MenuItem addNewNode = new MenuItem("Add a new node...");
            addNewNode.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    addNewNodePressed(cm);
                }
            });

            final MenuItem deleteNode = new MenuItem("Delete an existing node...");

            cm.getItems().addAll(addNewNode, deleteNode);

            cm.show(applicationScreen, event.getScreenX(), event.getScreenY());

        }

        if (event.isPrimaryButtonDown()) {

            cm.hide();

        }
    }

    @FXML
    private void addNewNodePressed(ContextMenu cm) {

        String s = "Name a node...";
        Text text = createText(s, 10);
        double textWidth = text.getLayoutBounds().getWidth();
        final Circle circle = createCircle(textWidth / 2 + 15,
                cm.getAnchorX() - textWidth / 2 - 15, cm.getAnchorY() - textWidth / 2 - 15);
        Group group = new Group(circle, text);
        
        applicationScreen.getChildren().add(group);

        moveText(text, cm.getAnchorX() - text.getBoundsInLocal().getWidth() / 2, cm.getAnchorY()- text.getBoundsInLocal().getHeight() / 2);
    }

    private void moveText(Text text, double x, double y) {

        text.relocate(x, y);

    }

    private Circle createCircle(double radius, double x, double y) {

        final Circle circle = new Circle(radius);

        circle.setFill(Color.LIGHTBLUE);
        circle.relocate(x, y);

        return circle;
    }

    private Text createText(String name, int fontSize) {

        final Text text = new Text(name);

        text.setFont(new Font(fontSize));
        text.setBoundsType(TextBoundsType.VISUAL);

        return text;
    }

}
