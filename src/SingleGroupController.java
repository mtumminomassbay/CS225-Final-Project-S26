import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SingleGroupController extends BaseController {

    @FXML private VBox teamsList;
    @FXML private Label groupLabel;

    private static Group currentGroup;

    @Override
    protected void onLoad() {
        groupLabel.setText(currentGroup.getGroupName());
        List<Team> teams = currentGroup.getTeams();
        for (Team team : teams) {
            addTeamPane(team);
        }
    }

    private void addTeamPane(Team team) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("SingleGroupTeamInfo.fxml")
            );
            
            Node teamNode = loader.load();
            ((SingleGroupTeamInfoController)loader.getController()).setTeam(team);

            teamsList.getChildren().add(teamNode);
        } catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
    }

    public static void setGroup(Group group) {
        currentGroup = group;
    }
}
