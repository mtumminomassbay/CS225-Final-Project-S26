/*
    Controller for knockout.fxml
*/
import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class KnockoutPhaseController extends BaseController {
    private List<AnchorPane> matchCards;
    @FXML
    private Button Dashboard_button;
    
    @FXML
    private Button simulateRound_Button;

    @FXML
    private Button simulateTournament_Button;

    @FXML private Pane simulationControlsDrawer;
    @FXML private SimulationController simulationControlsController;
    @FXML private Button simulationControlsToggleButton;


    // TODO: maybe use vboxes and just get the children
    @FXML private VBox roundof32Left;
    @FXML private VBox roundof32Right;
    @FXML private VBox roundof16Left;
    @FXML private VBox roundof16Right;
    @FXML private VBox quarterfinalsLeft;
    @FXML private VBox quarterfinalsRight;
    @FXML private VBox semifinalsLeft;
    @FXML private VBox semifinalsRight;
    @FXML private VBox finals;

    //match boxes for round of 32 (top to bottom, left then right)
    @FXML private AnchorPane r32match1;
    @FXML private AnchorPane r32match2;
    @FXML private AnchorPane r32match3;
    @FXML private AnchorPane r32match4;
    @FXML private AnchorPane r32match5;
    @FXML private AnchorPane r32match6;
    @FXML private AnchorPane r32match7;
    @FXML private AnchorPane r32match8;
    @FXML private AnchorPane r32match9;
    @FXML private AnchorPane r32match10;
    @FXML private AnchorPane r32match11;
    @FXML private AnchorPane r32match12;
    @FXML private AnchorPane r32match13;
    @FXML private AnchorPane r32match14;
    @FXML private AnchorPane r32match15;
    @FXML private AnchorPane r32match16;

    //matches boxes for round of 16 (top to bottom, left then right)
    @FXML private AnchorPane r16match1;
    @FXML private AnchorPane r16match2;
    @FXML private AnchorPane r16match3;
    @FXML private AnchorPane r16match4;
    @FXML private AnchorPane r16match5;
    @FXML private AnchorPane r16match6;
    @FXML private AnchorPane r16match7;
    @FXML private AnchorPane r16match8;

    //match boxes for quarterfinals (top to bottom, left then right)
    @FXML private AnchorPane qfmatch1;
    @FXML private AnchorPane qfmatch2;
    @FXML private AnchorPane qfmatch3;
    @FXML private AnchorPane qfmatch4;
    
    //match boxes for semifinals (top to bottom, left then right)
    @FXML private AnchorPane sfmatch1;
    @FXML private AnchorPane sfmatch2;

    //final match and third place match
    @FXML private AnchorPane finalmatch;
    @FXML private AnchorPane thirdplacematch;

    @FXML private ImageView trophyImage;

    @Override
    protected void onLoad() {
        Image trophy = new Image(getClass().getResourceAsStream("/images/trophy.png"));
        trophyImage.setImage(trophy);

        matchCards = List.of(
            r32match1, r32match2, r32match3, r32match4, r32match5, r32match6, r32match7, r32match8,
            r32match9, r32match10, r32match11, r32match12, r32match13, r32match14, r32match15, r32match16,

            r16match1, r16match2, r16match3, r16match4,
            r16match5, r16match6, r16match7, r16match8,

            qfmatch1, qfmatch2, 
            qfmatch3, qfmatch4,

            sfmatch1, 
            sfmatch2,

            finalmatch,
            thirdplacematch
        );

        connectMatchCards();
        loadKnockoutMatches();

        hideSimulationControlsDrawer();
        simulationControlsToggleButton.setText("Simulation Controls");
        simulationControlsToggleButton.setOnAction(event -> toggleSimulationControlsDrawer());

        simulationControlsController.configureForKnockoutStage();
        simulationControlsController.setOnSimulationChanged(this::refreshKnockoutView);
    }

    private void hideSimulationControlsDrawer() {
        simulationControlsDrawer.setVisible(false);
        simulationControlsDrawer.setManaged(false);
    }

    @FXML
    private void toggleSimulationControlsDrawer() {
        boolean shouldShow = !simulationControlsDrawer.isVisible();

        simulationControlsDrawer.setVisible(shouldShow);
        simulationControlsDrawer.setManaged(shouldShow);
        simulationControlsToggleButton.setText(shouldShow ? "Hide Controls" : "Simulation Controls");
    }

    private void refreshKnockoutView() {
        loadKnockoutMatches();
    }
    
    private void connectMatchCards() {
        List<Match> matches = WorldCupTournament.getInstance().getKnockoutMatches();
        for (int i = 0; i < matches.size() && i < matchCards.size(); i++) {

            Match match = matches.get(i);
            AnchorPane card = matchCards.get(i);

            card.setCursor(Cursor.HAND);
            card.setOnMouseClicked(event -> matchClicked(match));
        }
        System.out.println("Knockout matches found: " + matches.size());
        System.out.println("Match cards found: " + matchCards.size());
    }

    private void matchClicked(Match match) {
        MatchDetailsController.setMatch(match);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/matchdetails.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Match Details");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println(
                "COULD NOT LOAD FXML: " + e.getMessage()
            );
        }
    }

    @FXML
    private void Dashboard_button_clicked() {
        navigateTo(Screen.DASHBOARD);
    }
    
    @FXML
    private void simulateRoundClicked() {
        WorldCupTournament.getInstance().simulateRemainingCurrentRound();
        loadKnockoutMatches();
    }

    @FXML
    private void simulateTournamentClicked() {
        worldCup.simulateRestOfTournament();
        loadKnockoutMatches();
    }

    private void loadKnockoutMatches() {
        loadMatchesIntoVBox(roundof32Left,32, 8);
        loadMatchesIntoVBox(roundof32Right,32, 8);

        loadMatchesIntoVBox(roundof16Left, 16, 4);
        loadMatchesIntoVBox(roundof16Right, 16, 4);

        loadMatchesIntoVBox(quarterfinalsLeft, 8, 2);
        loadMatchesIntoVBox(quarterfinalsRight, 8, 2);

        loadMatchesIntoVBox(semifinalsLeft, 4, 1);
        loadMatchesIntoVBox(semifinalsRight, 4, 1);

        loadMatchesIntoVBox(finals, 2, 2);
    }

    private void loadMatchesIntoVBox(VBox roundBox, int bracketRound, int count) {
        List<Match> matches = List.of();
        if (worldCup.isGroupStageComplete()) {
            matches = worldCup.getBracket().getMatchesForRound(bracketRound);
            if (bracketRound == 2) {
                matches.add(worldCup.getBracket().getThirdPlace());
            }
        }

        int matchIndex = 0;
        for (Node node : roundBox.getChildren()) {
            if (node instanceof AnchorPane card && matchIndex < count) {
                Match match;
                if (matchIndex < matches.size()) {
                    match = matches.get(matchIndex);
                } else {
                    match = null;
                }

                updateMatchCard(card, match);

                card.setCursor(Cursor.HAND);
                if (match != null) {
                    card.setOnMouseClicked(event -> matchClicked(match));
                } else {
                    card.setOnMouseClicked(null);
                }

                matchIndex++;
            }
        }
    }

    private void updateMatchCard(AnchorPane card, Match match) {
        Label team1 = getCardLabel(card, 0);
        Label team2 = getCardLabel(card, 1);
        Label score = getCardLabel(card, 2);

        if (team1 != null) {
            team1.setText(match == null ? "???" : match.getFirstTeam().getCode());
        }

        if (team2 != null) {
            team2.setText(match == null ? "???" : match.getSecondTeam().getCode());
        }

        if (score != null) {
            score.setText(match != null && match.isFinished()
                ? match.getFirstTeamScore() + " - " + match.getSecondTeamScore()
                : "vs");
        }
    }

    private Label getCardLabel(AnchorPane card, int index) {
        int current = 0;

        for (Node child : card.getChildren()) {
            if (child instanceof Label label) {
                if (current == index) {
                    return label;
                }
                current++;
            }
        }
        return null;
    }
}
