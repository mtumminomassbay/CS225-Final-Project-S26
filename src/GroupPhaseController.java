import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/*
    Controller for group-stage.fxml
*/
public class GroupPhaseController extends BaseController {

    public static final int FLAG_WIDTH = 30;

    @FXML private GridPane buttonGrid;
    @FXML private GridPane leaderboardGrid;
    @FXML private SimulationController simulationControlsController;

    @Override
    protected void onLoad(){
        Iterator<Group> groups = worldCup.getGroupStage().getGroups().iterator();

        for (int i = 0; i < buttonGrid.getRowCount(); ++i) {
            for (int j = 0; j < buttonGrid.getColumnCount(); ++j) {
                addGroupButton(j, i, groups.next());
            }
        }

        worldCup.getGroupStage().simulateGroupStage();
        if (worldCup.getGroupStage().isSimulated()) {
            makeThirdPlaceLeaderboard();
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

        for (int i = 1; i < teams.size() + 1; ++i) {
            Label teamLabel = new Label(teams.get(i).getCode());
            ImageView flag = new ImageView(teams.get(i).getFlagPath());
            flag.setPreserveRatio(true);
            flag.setFitWidth(FLAG_WIDTH);
            teamLabel.setGraphic(flag);
            leaderboardGrid.add(teamLabel, 0, i);

            Label scoreLabel = new Label(teams.get(i).getGroup().getGroupResults(teams.get(i)).getPoints() + "");
            leaderboardGrid.add(scoreLabel, 1, i);
        }
    }
    
    @FXML
    private void showGroup() {
        simulationControlsController.configureForGroupStage();
        navigateTo(Screen.SINGLE_GROUP);
    }
}
