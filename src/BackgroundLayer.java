//Joey Barton

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/*
    Controller for Background.fxml

    BackgroundLayer is loaded at startup and remains the base layer for the
    entire length of the program.

    BackgroundLayer doesn't extend BaseController.
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
}
