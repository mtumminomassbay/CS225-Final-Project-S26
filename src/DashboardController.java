import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController extends BaseController{

    @FXML private Button GroupStage_Button;
    @FXML private Button KnockoutPhase_Button;
    @FXML private Button TeamInfo_Button;
    @FXML private Button MatchDetails_Button;
    @FXML private Label statusLabel;
    @FXML private Label currentStageLabel;
    @FXML private Label groupsCompletedLabel;

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

    @FXML
    private void TeamInfoButton_clicked() {
        navigateTo(Screen.TEAM_INFO);
    }

    @FXML
    private void MatchDetailsButton_clicked() {
        navigateTo(Screen.MATCH_DETAILS);
    }
}