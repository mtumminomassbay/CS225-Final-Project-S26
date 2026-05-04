import javafx.fxml.FXML;
import javafx.scene.control.Button;

/*
    Controller for dashboard.fxml
*/

public class DashboardController extends BaseController{

    @FXML private Button GroupStage_Button;
    @FXML private Button KnockoutPhase_Button;

    @Override
    protected void onLoad() {

    }

    @FXML
    private void GroupStageButton_clicked() {
        navigateTo(Screen.GROUP_STAGE);
    }

    @FXML
    private void KnockoutPhaseButton_clicked() {
        navigateTo(Screen.KNOCKOUT);
    }
    
}
