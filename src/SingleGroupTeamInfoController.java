import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SingleGroupTeamInfoController extends BaseController {
    
    @FXML private ImageView flag;
    @FXML private Text teamName;
    @FXML private Text rank;
    @FXML private Text wins;
    @FXML private Text draws;
    @FXML private Text losses;
    @FXML private Text goalsFor;
    @FXML private Text goalsAgainst;
    @FXML private Text points;

    private Team team;

    @Override
    protected void onLoad() {
    }

    //FIXME: This can be changed to just have a Team as a parameter if the backend makes Team have a reference to its Group
    public void setTeam(Group group, Team team) {
        this.team = team;

        flag.setImage(new Image(team.getFlagPath()));
        teamName.setText(team.getName());
        rank.setText(team.getRanking() + "");

        GroupResults results = group.getGroupResults(team);

        wins.setText(results.getWins() + "");
        draws.setText(results.getTies() + "");
        losses.setText(results.getLosses() + "");
        goalsFor.setText(results.getGoalsFor() + "");
        goalsAgainst.setText(results.getGoalsAgainst() + "");
        points.setText(results.getPoints() + "");

        teamName.getStyleClass().add("standings-team-name");
        points.getStyleClass().add("standings-points");
        rank.getStyleClass().add("standings-stat");
        wins.getStyleClass().add("standings-stat");
        draws.getStyleClass().add("standings-stat");
        losses.getStyleClass().add("standings-stat");
        goalsFor.getStyleClass().add("standings-stat");
        goalsAgainst.getStyleClass().add("standings-stat");
    }

    @FXML
    private void clicked() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/TeamInfoAdditional.fxml")
            );

            VBox root = loader.load();

            TeamInfoAdditionalController add_info_controller = loader.getController();
            
            //Add team info into additional controller class
            add_info_controller.setCountryName(team.getName());
            add_info_controller.setFlagImage(new Image(team.getFlagPath()));
            add_info_controller.setRank(String.valueOf(team.getRanking()));
            add_info_controller.setStadiumName(team.getHomeStadium());
            add_info_controller.setCoach(team.getHeadCoach());

            //Create a new window
            Stage stage = new Stage();
            stage.setTitle("Team Info");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
    }
}
