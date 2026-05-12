import java.util.Optional;
import java.util.function.Supplier;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;

/*
    Controller for simulation-controls.fxml.

    This controller only manages the reusable UI controls. The mock service at
    the bottom stands in for real simulation work until the backend is ready.

    Gabriel Ferreira
*/
public class SimulationController {
    private final static double SIMULATION_DELAY = 300;

    @FXML private Label currentStageLabel;
    @FXML private Label currentRoundLabel;
    @FXML private Label nextMatchLabel;
    @FXML private Label progressLabel;
    @FXML private Button simulateNextMatchButton;
    @FXML private Button simulateCurrentGroupButton;
    @FXML private Button simulateCurrentRoundButton;
    @FXML private Button simulateRestOfTournamentButton;
    @FXML private Button autoPlayButton;
    @FXML private Button pauseButton;
    @FXML private Button resetTournamentButton;
    @FXML private ComboBox<String> speedComboBox;
    @FXML private Label statusLabel;

    private WorldCupTournament worldCup;

    private boolean actionRunning = false;
    private boolean autoPlayRunning = false;
    private int autoPlayMatchesThisRun = 0;
    private Timeline actionTimeline;
    private Timeline autoPlayTimeline;
    private StageMode lastStage;

    @FXML
    private void initialize() {
        worldCup = WorldCupTournament.getInstance();
        lastStage = worldCup.getCurrentStage();

        // TODO: These speeds are placeholder UI choices until we finalize settings.
        speedComboBox.getItems().addAll("Slow (2 sec)", "Normal (1 sec)", "Fast (0.5 sec)");
        speedComboBox.setValue("Normal (1 sec)");
        speedComboBox.setOnAction(event -> restartAutoPlayWithNewSpeed());

        simulateNextMatchButton.setOnAction(event ->
            runAction("Simulating next match", true, this::simulateNextMatch));
        simulateCurrentGroupButton.setOnAction(event ->
            runAction("Simulating current group", true, this::simulateCurrentGroup));
        simulateCurrentRoundButton.setOnAction(event ->
            runAction("Simulating current round", true, this::simulateCurrentRound));
        simulateRestOfTournamentButton.setOnAction(event ->
            runAction("Simulating rest of tournament", true, this::simulateRestOfTournament));
        autoPlayButton.setOnAction(event -> startAutoPlay());
        pauseButton.setOnAction(event -> stopAutoPlay("Auto play paused."));
        resetTournamentButton.setOnAction(event -> confirmAndResetTournament());

        refreshLabels();
        refreshButtonStates();
    }

    public void configureForGroupStage() {
        // Parent screens call this after loading or after a group is selected.
        refreshLabels();
        refreshButtonStates();
    }

    public void configureForKnockoutStage() {
        refreshLabels();
        refreshButtonStates();
    }

    private void advanceTournamentStage() {
        lastStage = worldCup.getCurrentStage();
        if (worldCup.getCurrentStage() == StageMode.GROUP_STAGE) {
            configureForKnockoutStage();
        }
    }

    private String simulateNextMatch() {
        Match match = worldCup.simulateOneMatch();
        return match.toString();
    }

    private String simulateCurrentGroup() {
        Group group = worldCup.simulateNextGroup();
        return group.getGroupName() + " completed.";
    }

    private String simulateCurrentRound() {
        String result = worldCup.getCurrentStage() + " simulated.";
        worldCup.simulateRemainingCurrentRound();
        return result;
    }

    private String simulateRestOfTournament() {
        String result = "Entire tournament simulated.";
        worldCup.simulateRestOfTournament();
        return result;
    }

    private void runAction(String runningMessage, boolean useDelay, Supplier<String> action) {
        if (actionRunning) {
            return;
        }
        actionRunning = true;

        if (useDelay) {
            statusLabel.setText(runningMessage + "...");
            refreshButtonStates();
        }

        // Timeline keeps the UI responsive without using Thread.sleep().
        EventHandler<ActionEvent> eventHandler = event -> {
            String result = action.get();
            if (lastStage != worldCup.getCurrentStage()) {
                advanceTournamentStage();
            }

            actionRunning = false;
            refreshLabels();
            statusLabel.setText(result);
            refreshButtonStates();
        };

        if (useDelay) {
            actionTimeline = new Timeline(new KeyFrame(Duration.millis(SIMULATION_DELAY), eventHandler));
            actionTimeline.play();
        } else {
            eventHandler.handle(null);
        }
    }

    private void startAutoPlay() {
        autoPlayRunning = true;
        autoPlayMatchesThisRun = 0;
        statusLabel.setText("Auto play running...");
        autoPlayTimeline = buildAutoPlayTimeline();
        autoPlayTimeline.play();
        refreshButtonStates();
    }

    private Timeline buildAutoPlayTimeline() {
        // Each Timeline tick simulates one placeholder match.
        Timeline timeline = new Timeline(new KeyFrame(getSelectedDelay(), event -> autoPlayNextMatch()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    private void autoPlayNextMatch() {
        if (worldCup.isTournamentComplete()) {
            stopAutoPlay("Tournament complete!");
            return;
        }

        autoPlayMatchesThisRun++;
        runAction("", false, this::simulateNextMatch);
    }

    private void restartAutoPlayWithNewSpeed() {
        if (!autoPlayRunning) {
            return;
        }

        if (autoPlayTimeline != null) {
            autoPlayTimeline.stop();
        }

        autoPlayTimeline = buildAutoPlayTimeline();
        autoPlayTimeline.play();
        statusLabel.setText("Auto play speed updated.");
    }

    private void stopAutoPlay(String message) {
        if (autoPlayTimeline != null) {
            autoPlayTimeline.stop();
            autoPlayTimeline = null;
        }

        autoPlayRunning = false;
        statusLabel.setText(message);
        refreshButtonStates();
    }

    private void confirmAndResetTournament() {
        if (autoPlayRunning) {
            stopAutoPlay("Auto play stopped before reset.");
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reset Tournament");
        alert.setHeaderText("Reset the tournament?");
        alert.setContentText("This will reset the entire tournament.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            worldCup.resetTournament();
            refreshLabels();
            statusLabel.setText("Tournament reset.");
            refreshButtonStates();
            configureForGroupStage();
        }
    }

    private void refreshLabels() {
        currentStageLabel.setText("Stage: " + worldCup.getCurrentStage());
        currentRoundLabel.setText(getRoundLabel());
        nextMatchLabel.setText("Next match: " + "TODO"); //TODO
        progressLabel.setText("Progress: " + worldCup.getCompletedMatches() + " / " + worldCup.getTotalMatches() + " matches");
    }

    private void refreshButtonStates() {
        // Keep invalid actions disabled so users cannot start two simulations at once.
        boolean cantRunSimulation = actionRunning || autoPlayRunning || worldCup.isTournamentComplete();

        simulateNextMatchButton.setDisable(cantRunSimulation);
        simulateCurrentGroupButton.setDisable(cantRunSimulation || worldCup.getCurrentStage() != StageMode.GROUP_STAGE);
        simulateCurrentRoundButton.setDisable(cantRunSimulation);
        simulateRestOfTournamentButton.setDisable(cantRunSimulation);
        autoPlayButton.setDisable(cantRunSimulation);
        pauseButton.setDisable(actionRunning || !autoPlayRunning);
        resetTournamentButton.setDisable(actionRunning);
        speedComboBox.setDisable(actionRunning);
    }

    private Duration getSelectedDelay() {
        // TODO: If speed settings become user preferences, load them from storage.
        String speed = speedComboBox.getValue();

        if ("Slow (2 sec)".equals(speed)) {
            return Duration.seconds(2);
        }

        if ("Fast (0.5 sec)".equals(speed)) {
            return Duration.millis(500);
        }

        return Duration.seconds(1);
    }

    private String getRoundLabel() {
        if (worldCup.getCurrentStage() == StageMode.GROUP_STAGE) {
            return "Current Group: " + worldCup.getGroupStage().getCurrentGroup().getGroupName();
        }

        return "Current Round: " + worldCup.getBracket().getCurrentRound();
    }
}
