import java.io.IOException;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/*
    Controller for group-stage.fxml
*/
public class GroupPhaseController extends BaseController {
    @FXML private GridPane buttonGrid;

    @Override
    protected void onLoad(){
        Iterator<Group> groups = worldCup.getGroupStage().getGroups().iterator();

        for (int i = 0; i < buttonGrid.getRowCount(); ++i) {
            for (int j = 0; j < buttonGrid.getColumnCount(); ++j) {
                addGroupButton(j, i, groups.next());
            }
        }
    }

    private void addGroupButton(int x, int y, Group group) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("GroupButton.fxml")
            );
            
            Node buttonNode = loader.load();
           ((GroupButtonController)loader.getController()).setGroup(group);

            buttonGrid.add(buttonNode, x, y);
        } catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
    }

    private void makeThirdPlaceLeaderboard() {
        List<Team> teams = worldCup.getGroupStage().getThirdPlaceAdvancingTeams();

        for (int i = 0; i < teams.size(); ++i) {
            Label teamLabel = new Label(teams.get(i).getCode());
            ImageView flag = new ImageView(teams.get(i).getFlagPath());
            flag.setPreserveRatio(true);
            flag.setFitWidth(FLAG_WIDTH);
            teamLabel.setGraphic(flag);
            teamLabel.getStyleClass().add("team-label");
            leaderboardGrid.add(teamLabel, 0, i + 1);

            Label scoreLabel = new Label(teams.get(i).getTeamResults().getPoints() + "");
            scoreLabel.getStyleClass().add("score-label");
            GridPane.setHalignment(scoreLabel, HPos.RIGHT);
            GridPane.setMargin(scoreLabel, new Insets(0, 10, 0, 0));
            leaderboardGrid.add(scoreLabel, 1, i + 1);
        }
    }
}
