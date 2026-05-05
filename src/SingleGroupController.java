import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SingleGroupController extends BaseController {

    @FXML private Label label;

    @Override
    protected void onLoad() {
        label.setText("Group " + GroupPhaseController.getGroupInFocus());
    }
}
