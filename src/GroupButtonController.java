import javafx.fxml.FXML;

/**
Controller for the buttons to access a single group view for a group

 */
public class GroupButtonController extends BaseController {
    @Override
    protected void onLoad() {

    }

    @FXML
    private void buttonClicked() {
        navigateTo(Screen.SINGLE_GROUP);
    }
}
