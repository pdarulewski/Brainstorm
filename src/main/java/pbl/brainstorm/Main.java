package pbl.brainstorm;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pbl.brainstorm.dialogs.DialogUtils;

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

        stage.setTitle("bStorm");

        stage.setOnCloseRequest((t) -> {

            Optional<ButtonType> result = DialogUtils.confirmationDialog();

            if (result.get() == ButtonType.OK) {

                Platform.exit();
                System.exit(0);

            }

            t.consume();

        });

        stage.show();

    }
}
