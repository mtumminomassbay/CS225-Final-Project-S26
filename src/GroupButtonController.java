import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
Controller for the buttons to access a single group view for a group

 */
public class GroupButtonController extends BaseController {

    @FXML private Label groupNameLabel;

    @Override
    protected void onLoad() {

    }

    @FXML
    private void buttonClicked() {
        navigateTo(Screen.SINGLE_GROUP);
    }

    public void setGroupLabelText(String text) {
        groupNameLabel.setText(text);
    }
}
