/*
    Controller for knockout.fxml
*/
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class KnockoutPhaseController extends BaseController{
    private Button Dashboard_button;

    @Override
    protected void onLoad() {

    }
    

    @FXML
    private void Dashboard_button_clicked() {
        navigateTo(Screen.DASHBOARD);
    }
}
