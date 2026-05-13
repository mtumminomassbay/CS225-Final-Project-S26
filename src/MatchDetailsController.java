/**
 * Controller for Matchdetail.fxml
 * This screen shows the result of the match including the first half score, 
 * second half scores and penalties and overtime as needed. 
 * When the penalty takes places it also shows the kick by kick sequence of the penalty. 
 * @author Bandana Kadel
*/

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    @FXML private TextArea kickSequenceTextArea;

    

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
        
            String kickText = "";

            for (PenaltyShootout.KickResult kick: match.getPenalties().getKickResults()){
                kickText += kick.toString() + "\n";
            }

            kickSequenceTextArea.setText(kickText);
            
        } else {
            penaltiesLabel.setText("--");
            kickSequenceTextArea.setText("No penalty shootout for this match.");
        }

        // Winner
        if (match.getWinner() != null) {
            winnerLabel.setText("Winner: " + match.getWinner().getName());
        } else {
            winnerLabel.setText("Draw");
        }
    }

    @FXML
    private void backButton_clicked(ActionEvent e) {
        ((Stage)((Node)e.getSource()).getScene().getWindow()).close();
    }

    public static void setMatch(Match selectedMatch) {
        match = selectedMatch;
    }

    @FXML
    private void teamA_clicked() {
        showTeamInfo(match.getFirstTeam());
    }
    
    @FXML
    private void teamB_clicked() {
        showTeamInfo(match.getSecondTeam());
    }
    
    private void showTeamInfo(Team team) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/TeamInfoAdditional.fxml")
            );
    
            VBox root = loader.load();
    
            TeamInfoAdditionalController controller = loader.getController();
    
            controller.updateFromTeam(team);
    
            Stage stage = new Stage();
            stage.setTitle("Team Info");
            stage.setScene(new Scene(root));
            stage.show();
    
        } catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
    }
}