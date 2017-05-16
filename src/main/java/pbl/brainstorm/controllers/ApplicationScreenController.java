package pbl.brainstorm.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

        TextField tf = new TextField("Name a node");
        moveTextField(tf, cm.getX(), cm.getY());
        applicationScreen.getChildren().add(tf);
    }

    private void handleEnterInTextField(KeyEvent event, TextField tf) {
        if (event.getCode() == KeyCode.ENTER) {
            refreshingNode(tf);
        }
    }
    
    //TODO: showing the circles

    private void refreshingNode(TextField tf) {

        Text text = createText(tf.getText(), 10);

        tf.setLayoutX(text.getBoundsInLocal().getHeight());

        double textWidth = text.getBoundsInLocal().getHeight();
        double textHeight = text.getBoundsInLocal().getHeight();

        final Circle circle = createCircle(textWidth / 2 + 15,
                cm.getX() - textWidth / 2 - 15, cm.getY() - textWidth / 2 - 15);
        Group group = new Group(circle, tf);

        applicationScreen.getChildren().add(group);

        moveTextField(tf, cm.getX() - textHeight / 2, cm.getY() - textHeight / 2);

    }

    private void moveTextField(TextField tf, double x, double y) {
        tf.relocate(x, y);
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
