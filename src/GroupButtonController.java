import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Iterator;
import javafx.scene.layout.GridPane;

/**
Controller for the buttons to access a single group view for a group
 @author Lior Sapir
 */
public class GroupButtonController extends BaseController {

    public static final int FLAG_WIDTH = 30;

    @FXML private Label groupNameLabel;
    @FXML private GridPane grid;
    
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

        //add labels showing teams in the group to the button
        Iterator<Team> teams = group.getTeams().iterator();
        for (int i = 1; i < grid.getRowCount(); ++i) {
            for (int j = 0; j < grid.getColumnCount(); ++j) {
                Team currentTeam = teams.next();
                Label label = new Label(currentTeam.getCode());
                ImageView flag = new ImageView(currentTeam.getFlagPath());

                flag.setPreserveRatio(true);
                flag.setFitWidth(FLAG_WIDTH);
                label.setGraphic(flag);
                label.getStyleClass().add("team-label");
                grid.add(label, j, i);
            }
        }
    }
}
