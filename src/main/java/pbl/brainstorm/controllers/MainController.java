package pbl.brainstorm.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MainController {
    
    @FXML
    private AnchorPane mainAnchorPane;
    
    public void setScreen(Pane pane) {
        
        mainAnchorPane.getChildren().clear();
        mainAnchorPane.getChildren().add(pane);
        
    }
    
    public void initialize() {
        
        loadMenuScreen();
        
    }
    
    public void loadMenuScreen() {
        
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/MenuScreen.fxml"));
        
        Pane pane = null;
        
        try {
            
            pane = loader.load();
            
        } catch (IOException ex) {
            
            ex.printStackTrace();
        }
        
        MenuController menuController = loader.getController();
        
        menuController.setMainController(this);
        
        setScreen(pane);
        
    }
    
    public void resizeAnchorPane(double a, double b) {
        
        mainAnchorPane.setPrefSize(a, b); // FIXME: Not working!
        
    }
    
}
