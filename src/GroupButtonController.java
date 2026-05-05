import javafx.fxml.FXML;

public class GroupButtonController extends BaseController {
    @Override
    protected void onLoad() {

    }

    @FXML
    private void buttonClicked() {
        navigateTo(Screen.SINGLE_GROUP);
    }
}
