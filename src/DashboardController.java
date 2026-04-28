import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DashboardController {

    @FXML private Button Test_Button;

    private void initialize() {

    }

    @FXML
    private void TestButton_clicked() {
        Navigator.getInstance().navigateTo(Screen.TEST);
    }
    
}
