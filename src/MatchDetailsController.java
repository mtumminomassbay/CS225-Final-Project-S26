import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**This class shows the result of the match including the first half score,
second half scores and penalties and overtime as needed.
@author Bandana Kadel
*/
public class MatchDetailsController extends BaseController {

    @FXML private Label roundLabel;
    @FXML private Label teamANameLabel;
    @FXML private Label teamBNameLabel;
    @FXML private Label teamARankLabel;
    @FXML private Label teamBRankLabel;
    @FXML private ImageView teamAFlag;
    @FXML private ImageView teamBFlag;
    @FXML private Label scoreLabel;
    @FXML private Label firstHalfLabel;
    @FXML private Label secondHalfLabel;
    @FXML private Label extraTimeLabel;
    @FXML private Label penaltiesLabel;
    @FXML private Label winnerLabel;
    @FXML private Button backButton;

    private static Match match;

    @Override
    protected void onLoad() {
        if (match == null) {
            match = worldCup.getCurrentlyViewedMatch();
        }

        if (match == null) {
            return;
        }

        Team teamA = match.getFirstTeam();
        Team teamB = match.getSecondTeam();

        // Basic match information
        roundLabel.setText(worldCup.getCurrentStage().toString());

        // Team information
        teamANameLabel.setText(teamA.getName());
        teamBNameLabel.setText(teamB.getName());
        teamARankLabel.setText("#" + teamA.getRanking());
        teamBRankLabel.setText("#" + teamB.getRanking());

        // Team flags
        teamAFlag.setImage(new Image(teamA.getFlagPath()));
        teamBFlag.setImage(new Image(teamB.getFlagPath()));

        // Final score
        scoreLabel.setText(match.getFirstTeamScore() + " - " + match.getSecondTeamScore());

        // Half scores
        firstHalfLabel.setText(match.getFirstHalf().getFirstTeamScore() + " - "
                + match.getFirstHalf().getSecondTeamScore());

        secondHalfLabel.setText(match.getSecondHalf().getFirstTeamScore() + " - "
                + match.getSecondHalf().getSecondTeamScore());

        // Overtime score
        if (match.getOvertime() != null) {
            extraTimeLabel.setText(match.getOvertime().getFirstTeamScore() + " - "
                    + match.getOvertime().getSecondTeamScore());
        } else {
            extraTimeLabel.setText("--");
        }

        // Penalty score
        if (match.getPenalties() != null) {
            penaltiesLabel.setText(match.getPenalties().getFirstTeamGoals() + " - "
                    + match.getPenalties().getSecondTeamGoals());
        } else {
            penaltiesLabel.setText("--");
        }

        // Winner
        if (match.getWinner() != null) {
            winnerLabel.setText("Winner: " + match.getWinner().getName());
        } else {
            winnerLabel.setText("Draw");
        }
    }

    //close window
    @FXML
    private void backButton_clicked(ActionEvent e) {
        ((Stage)((Node)e.getSource()).getScene().getWindow()).close();
    }

    public static void setMatch(Match selectedMatch) {
        match = selectedMatch;
    }
}