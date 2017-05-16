package pbl.brainstorm.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import static javafx.geometry.Pos.CENTER;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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

        final TextField tf = new TextField("Name a node");
        moveTextField(tf, cm.getX(), cm.getY());
        applicationScreen.getChildren().add(tf);

        tf.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    refreshingNode(tf);
                }
            }
        });

    }

    private void refreshingNode(final TextField tf) {

        Text text = createText(tf.getText(), 10);

        tf.setAlignment(CENTER);
        //TODO: ustawic dlugosc textfielda automatycznie
        //tf.setPrefWidth(text.getBoundsInLocal().getWidth());
        double tfWidth = tf.getWidth();
        double tfHeight = tf.getHeight();

        final Circle circle = createCircle(tfWidth / 2,
                cm.getX() - tfWidth / 2, cm.getY() - tfWidth / 2);
        
        Group group = new Group(circle, tf);

        applicationScreen.getChildren().add(group);
        moveTextField(tf, cm.getX() - tfWidth / 2, cm.getY() - tfHeight / 2);
        //tf.setVisible(false);
        

    }

    private void moveTextField(TextField tf, double x, double y) {
        tf.relocate(x, y);
    }

    private Circle createCircle(double radius, double x, double y) {
        final Circle circle = new Circle(radius);
        circle.setFill(Color.LIGHTBLUE);
        circle.relocate(x, y);
        drawLine(cm.getX(), cm.getY(), 500, 500);
        return circle;
    }

    private Text createText(String name, int fontSize) {
        final Text text = new Text(name);
        text.setFont(new Font(fontSize));
        text.setBoundsType(TextBoundsType.VISUAL);
        return text;
    }

    private void drawLine(double startX, double startY, double endX, double endY) {
        Line line = new Line();
        line.setStroke(Color.LIGHTGRAY);
        line.setStrokeWidth(3);
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        applicationScreen.getChildren().add(line);
    }
}
