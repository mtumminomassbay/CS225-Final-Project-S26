import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
Controller for the buttons to access a single group view for a group

 */
public class GroupButtonController extends BaseController {

    @FXML private Label groupNameLabel;
    
    private Group group;

    @Override
    protected void onLoad() {
    }

    @FXML
    private void buttonClicked() {
        SingleGroupController.setGroup(group);
        navigateTo(Screen.SINGLE_GROUP);
        
    }

    public void setGroup(Group group) {
        this.group = group;
        groupNameLabel.setText(group.getGroupName());
    }
}
