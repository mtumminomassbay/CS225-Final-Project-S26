import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SingleGroupController extends BaseController {

    public static final int FLAG_WIDTH = 30;

    @FXML private VBox teamsList;
    @FXML private Label groupLabel;
    @FXML private GridPane matchGrid;

    private static Group currentGroup;

    @Override
    protected void onLoad() {
        groupLabel.setText(currentGroup.getGroupName());

        List<Team> teams = currentGroup.getTeams();
        for (Team team : teams) {
            addTeamPane(team);
        }

        Iterator<Match> matches = currentGroup.getMatches().iterator();
        int rowIndex = matchGrid.getRowCount();

        for (int i = 0; i < matchGrid.getColumnCount(); ++i) {
            for (int j = 0; j < matchGrid.getRowCount(); ++j) {
                addMatchPane(i, 3 - Math.abs(rowIndex), matches.next());
                --rowIndex;
            }
            --rowIndex;
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

    private void addMatchPane(int x, int y, Match match) {
        ImageView flag1 = new ImageView(match.getFirstTeam().getFlagPath());
        flag1.setPreserveRatio(true);
        flag1.setFitWidth(FLAG_WIDTH);

        Text vs = new Text("vs");
        HBox.setMargin(vs, new Insets(0, 10, 0, 10));

        ImageView flag2 = new ImageView(match.getSecondTeam().getFlagPath());
        flag2.setPreserveRatio(true);
        flag2.setFitWidth(FLAG_WIDTH);

        HBox box = new HBox(flag1, vs, flag2);
        box.setAlignment(Pos.CENTER);
        box.setOnMouseClicked(evt -> matchClicked(match));
        box.setCursor(Cursor.HAND);
        GridPane.setHalignment(box, HPos.CENTER);

        box.getStyleClass().add("match-cell");
        matchGrid.add(box, x, y);
    }

    private void matchClicked(Match match) {
        MatchDetailsController.setMatch(match);
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/matchdetails.fxml")
            );

        AnchorPane root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Match Details");
        stage.setScene(new Scene(root));
        stage.show();
        }
        catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
    }

    @FXML
    private void backToGroupStage() {
        navigateTo(Screen.GROUP_STAGE);
    }

    public static void setGroup(Group group) {
        currentGroup = group;
    }
}
