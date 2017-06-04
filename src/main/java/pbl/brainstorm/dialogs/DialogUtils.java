package pbl.brainstorm.dialogs;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DialogUtils {

    public static Optional<ButtonType> confirmationDialog() {
        
        Alert closeAlert = new Alert(Alert.AlertType.CONFIRMATION);

        closeAlert.setTitle("Quit");
        closeAlert.setHeaderText("Do you want to quit the application?");
        closeAlert.setContentText("Confirm your choice, please.");

        return closeAlert.showAndWait();

    }

}
