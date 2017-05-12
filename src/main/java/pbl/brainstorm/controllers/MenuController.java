package pbl.brainstorm.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

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

            StackPane pane = null;

            try {

                pane = loader.load();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

            ApplicationScreenController appController = loader.getController();

            appController.setMainController(mainController);

            //mainController.resizeAnchorPane(applicationPane.getWidth(), applicationPane.getHeight());
            //mainController.resizeAnchorPane(1000.0, 1000.0);
            // TODO: Try to make MainScreen as big as ApplicationScreen.
            final Circle mainNode = createMainNode(100);
            final Text mainText = createText(s, 15);

            Group group = new Group(mainNode, mainText);

            pane.getChildren().add(group);
            
            //mainNode.relocate(mainNode.getBoundsInParent().getWidth() / 2, mainNode.getBoundsInParent().getHeight() / 2);
            
            centerText(mainText, mainNode.getRadius());

            mainController.setScreen(pane);

        }

    }

    private Circle createMainNode(double radius) {

        final Circle circle = new Circle(radius);

        circle.setStroke(Color.RED);
        circle.setStrokeWidth(10);
        circle.setStrokeType(StrokeType.INSIDE);
        circle.setFill(Color.ANTIQUEWHITE);
        circle.relocate(0, 0);
        
        return circle;
    }

    private Text createText(String name, int fontSize) {

        final Text text = new Text(name);

        text.setFont(new Font(fontSize));
        text.setBoundsType(TextBoundsType.VISUAL);

        return text;
    }

    private void centerText(Text text, double radius) {

        double width = text.getBoundsInLocal().getWidth();
        double height = text.getBoundsInLocal().getHeight();

        text.relocate(radius - width / 2, radius - height / 2);

    }

}
