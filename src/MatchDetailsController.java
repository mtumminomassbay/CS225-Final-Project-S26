//Bandana Kadel
/*this screen shows the details like team A name , team B name, scores by 1st half
score by second half, OT, and penalties if needed. It also shows the place and time the game is being
played at. 
*/


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MatchDetailsController extends BaseController {

    @FXML private Label teamANameLabel;
    @FXML private Label teamBNameLabel;
    @FXML private Label scoreLabel;
    @FXML private Label firstHalfLabel;
    @FXML private Label secondHalfLabel;
    @FXML private Label extraTimeLabel;
    @FXML private Label penaltiesLabel;
    @FXML private Label winnerLabel;
    @FXML private Button backButton;

    @Override
    protected void onLoad() {
        
        TeamParser parser = new TeamParser();
        GroupStage groupStage = new GroupStage(parser.getTeams());
        Match match = groupStage.getGroups().get(0).getMatches().get(0);

        
        match.simulate();

        // Show teams names and the scores
        teamANameLabel.setText(match.getFirstTeam().getName());
        teamBNameLabel.setText(match.getSecondTeam().getName());
        scoreLabel.setText(match.getFirstTeamScore() + " - " + match.getSecondTeamScore());

        // Show score at half time
        firstHalfLabel.setText(match.getFirstHalf().getFirstTeamScore() + " - " + match.getFirstHalf().getSecondTeamScore());
        secondHalfLabel.setText(match.getSecondHalf().getFirstTeamScore() + " - " + match.getSecondHalf().getSecondTeamScore());

        // Show if the match goes to OT nad the score after OT
        if (match.getOvertime() != null) {
            extraTimeLabel.setText(match.getOvertime().getFirstTeamScore() + " - " + match.getOvertime().getSecondTeamScore());
        } else {
            extraTimeLabel.setText("--");
        }

        // Show penalties shootout scores
        if (match.getPenalties() != null) {
            penaltiesLabel.setText(match.getPenalties().getFirstTeamGoals() + " - " + match.getPenalties().getSecondTeamGoals());
        } else {
            penaltiesLabel.setText("- -");
        }

        // Show winner of the match or if it is draw
        Team winner = match.getWinner();
        winnerLabel.setText(winner != null ? "Winner: " + winner.getName() : "Draw");
    }

    @FXML
    private void backButton_clicked() {
        navigateTo(Screen.DASHBOARD);
    }
}