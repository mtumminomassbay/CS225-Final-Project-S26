import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class WorldCupController {

    @FXML private AnchorPane rootPane;

    public void initialize() {
        Navigator.getInstance().setContentArea(rootPane);
        Navigator.getInstance().navigateTo(Screen.DASHBOARD);
    }
    
}
