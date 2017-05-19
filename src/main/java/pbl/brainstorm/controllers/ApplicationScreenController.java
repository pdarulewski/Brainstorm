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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class ApplicationScreenController {

    private MainController mainController;

    public void setMainController(MainController mainController) {

        this.mainController = mainController;

    }

    @FXML
    private Pane applicationScreen;

    final ContextMenu cm = new ContextMenu();

    final MenuItem addNewNode = new MenuItem("Add a new node...");

    @FXML
    private void handleMousePressed(final MouseEvent event) {

        if (event.isSecondaryButtonDown()) {

            if (!cm.getItems().isEmpty()) {

                cm.getItems().clear();

            }

            addNewNode.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {

                    final TextField tf = new TextField();

                    tf.setPromptText("Add a new item...");

                    tf.requestFocus();

                    tf.relocate(event.getSceneX(), event.getSceneY());

                    applicationScreen.getChildren().add(tf);

                    tf.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

                        public void handle(final KeyEvent keyEvent) {

                            if (keyEvent.getCode() == KeyCode.ENTER) {

                                refreshingNode(tf, event);

                                applicationScreen.requestFocus();

                            }
                        }

                    });

                }

            });

            cm.getItems().add(addNewNode);

            cm.show(applicationScreen, event.getScreenX(), event.getScreenY());

        }

        if (event.isPrimaryButtonDown()) {

            cm.hide();

        }
    }

    private void refreshingNode(final TextField tf, MouseEvent event) {

        Text text = createText(tf.getText(), 15);

        double textWidth = text.getLayoutBounds().getWidth();

        final Circle circle = createCircle(textWidth / 2 + 15, event.getScreenX(), event.getScreenY());

        Group group = new Group(circle, text);

        applicationScreen.getChildren().add(group);

        moveText(text, event.getScreenX(), event.getScreenY());

        tf.setVisible(false);

    }

    private void moveText(Text text, double x, double y) {

        double width = text.getBoundsInLocal().getWidth();
        double height = text.getBoundsInLocal().getHeight();

        text.relocate(x - width / 2, y - height / 2);

    }

    private Circle createCircle(double radius, double x, double y) {

        final Circle circle = new Circle(radius);

        circle.setFill(Color.LIGHTBLUE);
        circle.relocate(x - radius, y - radius);

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
