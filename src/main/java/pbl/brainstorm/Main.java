package pbl.brainstorm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/MainScreen.fxml"));

        StackPane pane = loader.load();

        Scene scene = new Scene(pane, 400, 400);

        stage.setScene(scene);

        stage.setTitle("Brainstorm");

        stage.setMaximized(true);
        
        stage.show();

    }

}
