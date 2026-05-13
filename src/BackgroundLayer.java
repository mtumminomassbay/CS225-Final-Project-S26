import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
    Controller for Background.fxml

    BackgroundLayer is loaded at startup and remains the base layer for the
    entire length of the program.

    BackgroundLayer doesn't extend BaseController.
    @author Joey Barton
*/
public class BackgroundLayer {

    @FXML private AnchorPane rootPane;

    public void initialize() {
        Navigator.getInstance().setContentArea(rootPane);
        Navigator.getInstance().navigateTo(Screen.DASHBOARD);
    }
    
    @FXML
    private void home() {
        Navigator.getInstance().navigateTo(Screen.DASHBOARD);
    }

    @FXML
    private void openHelp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Help-Screen.fxml"));
            Parent root = loader.load();
            
            Stage helpStage = new Stage();
            helpStage.setTitle("Help");
            helpStage.setScene(new Scene(root));
            helpStage.show();


        } catch(IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
    }
}
