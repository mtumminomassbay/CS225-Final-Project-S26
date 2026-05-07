import java.util.Optional;
import java.util.function.Supplier;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

    private enum StageMode {
        GROUP_STAGE,
        KNOCKOUT_STAGE
    }

    @FXML private Label currentStageLabel;
    @FXML private Label selectedContextLabel;
    @FXML private Label nextMatchLabel;
    @FXML private Label progressLabel;
    @FXML private Button simulateNextMatchButton;
    @FXML private Button simulateCurrentGroupButton;
    @FXML private Button simulateCurrentRoundButton;
    @FXML private Button simulateRestOfTournamentButton;
    @FXML private Button autoPlayButton;
    @FXML private Button pauseButton;
    @FXML private Button resetCurrentContextButton;
    @FXML private Button resetTournamentButton;
    @FXML private ComboBox<String> speedComboBox;
    @FXML private Label statusLabel;

    // TODO: Replace this limit with a real "no more matches left" backend check.
    private static final int AUTO_PLAY_PLACEHOLDER_LIMIT = 5;

    // TODO: Replace this mock service with the real tournament simulation service.
    private final MockSimulationService mockService = new MockSimulationService();

    private StageMode stageMode = StageMode.GROUP_STAGE;
    private int selectedGroupNumber = 0;
    private boolean bracketReady = false;
    private boolean actionRunning = false;
    private boolean autoPlayRunning = false;
    private int autoPlayMatchesThisRun = 0;
    private Timeline actionTimeline;
    private Timeline autoPlayTimeline;

    @FXML
    private void initialize() {
        // TODO: These speeds are placeholder UI choices until we finalize settings.
        speedComboBox.getItems().addAll("Slow (2 sec)", "Normal (1 sec)", "Fast (0.5 sec)");
        speedComboBox.setValue("Normal (1 sec)");
        speedComboBox.setOnAction(event -> restartAutoPlayWithNewSpeed());

        simulateNextMatchButton.setOnAction(event -> simulateNextMatch());
        simulateCurrentGroupButton.setOnAction(event -> simulateCurrentGroup());
        simulateCurrentRoundButton.setOnAction(event -> simulateCurrentRound());
        simulateRestOfTournamentButton.setOnAction(event -> simulateRestOfTournament());
        autoPlayButton.setOnAction(event -> startAutoPlay());
        pauseButton.setOnAction(event -> stopAutoPlay("Auto play paused."));
        resetCurrentContextButton.setOnAction(event -> resetCurrentContext());
        resetTournamentButton.setOnAction(event -> confirmAndResetTournament());

        refreshLabels();
        refreshButtonStates();
    }

    public void configureForGroupStage(int groupNumber) {
        // Parent screens call this after loading or after a group is selected.
        stageMode = StageMode.GROUP_STAGE;
        selectedGroupNumber = groupNumber;
        bracketReady = false;
        resetCurrentContextButton.setText("Reset Current Group");
        refreshLabels();
        refreshButtonStates();
    }

    public void configureForKnockoutStage(boolean bracketReady) {
        // TODO: bracketReady should come from real tournament state later.
        stageMode = StageMode.KNOCKOUT_STAGE;
        selectedGroupNumber = 0;
        this.bracketReady = bracketReady;
        resetCurrentContextButton.setText("Reset Current Round");
        refreshLabels();
        refreshButtonStates();
    }

    private void simulateNextMatch() {
        runMockAction("Simulating next match", () ->
            mockService.simulateNextMatch(getStageName(), getContextName())
        );
    }

    private void simulateCurrentGroup() {
        runMockAction("Simulating current group", () ->
            mockService.simulateCurrentGroup(selectedGroupNumber)
        );
    }

    private void simulateCurrentRound() {
        runMockAction("Simulating current round", mockService::simulateCurrentRound);
    }

    private void simulateRestOfTournament() {
        runMockAction("Simulating rest of tournament", mockService::simulateRestOfTournament);
    }

    private void resetCurrentContext() {
        runMockAction("Resetting current context", () -> {
            if (stageMode == StageMode.GROUP_STAGE) {
                return mockService.resetCurrentGroup(selectedGroupNumber);
            }

            return mockService.resetCurrentRound();
        });
    }

    private void runMockAction(String runningMessage, Supplier<String> action) {
        if (actionRunning) {
            return;
        }

        actionRunning = true;
        statusLabel.setText(runningMessage + "...");
        refreshButtonStates();

        // Timeline keeps the UI responsive without using Thread.sleep().
        actionTimeline = new Timeline(new KeyFrame(Duration.millis(400), event -> {
            String result = action.get();
            actionRunning = false;
            refreshLabels();
            statusLabel.setText(result);
            refreshButtonStates();
        }));
        actionTimeline.play();
    }

    private void startAutoPlay() {
        if (!canUseAutoPlay()) {
            return;
        }

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
        if (autoPlayMatchesThisRun >= AUTO_PLAY_PLACEHOLDER_LIMIT) {
            stopAutoPlay("Auto play stopped after the placeholder limit.");
            return;
        }

        autoPlayMatchesThisRun++;
        String result = mockService.simulateNextMatch(getStageName(), getContextName());
        refreshLabels();
        statusLabel.setText("Auto play: " + result);

        if (autoPlayMatchesThisRun >= AUTO_PLAY_PLACEHOLDER_LIMIT) {
            stopAutoPlay("Auto play stopped after " + AUTO_PLAY_PLACEHOLDER_LIMIT + " placeholder matches.");
        }
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
        // TODO: Update this message when reset uses real tournament data.
        alert.setContentText("This placeholder reset will clear the mock simulation progress.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            mockService.resetTournament();
            refreshLabels();
            statusLabel.setText("Tournament reset with placeholder data.");
            refreshButtonStates();
        }
    }

    private void refreshLabels() {
        // TODO: Replace these labels with live match and progress data.
        currentStageLabel.setText("Stage: " + getStageName());
        selectedContextLabel.setText("Selected: " + getContextName());
        nextMatchLabel.setText("Next match: " + mockService.getNextMatchPlaceholder(getContextName()));
        progressLabel.setText("Progress: " + mockService.getProgressPlaceholder());
    }

    private void refreshButtonStates() {
        // Keep invalid actions disabled so users cannot start two simulations at once.
        boolean groupSelected = selectedGroupNumber > 0;
        boolean knockoutCanSimulate = stageMode == StageMode.KNOCKOUT_STAGE && bracketReady;
        boolean simulationRunning = actionRunning || autoPlayRunning;

        simulateNextMatchButton.setDisable(simulationRunning || (stageMode == StageMode.KNOCKOUT_STAGE && !bracketReady));
        simulateCurrentGroupButton.setDisable(simulationRunning || stageMode != StageMode.GROUP_STAGE || !groupSelected);
        simulateCurrentRoundButton.setDisable(simulationRunning || !knockoutCanSimulate);
        simulateRestOfTournamentButton.setDisable(simulationRunning || (stageMode == StageMode.KNOCKOUT_STAGE && !bracketReady));
        autoPlayButton.setDisable(simulationRunning || !canUseAutoPlay());
        pauseButton.setDisable(actionRunning || !autoPlayRunning);
        resetCurrentContextButton.setDisable(simulationRunning || !canResetCurrentContext());
        resetTournamentButton.setDisable(actionRunning);
        speedComboBox.setDisable(actionRunning);
    }

    private boolean canUseAutoPlay() {
        return stageMode == StageMode.GROUP_STAGE || bracketReady;
    }

    private boolean canResetCurrentContext() {
        if (stageMode == StageMode.GROUP_STAGE) {
            return selectedGroupNumber > 0;
        }

        return bracketReady;
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

    private String getStageName() {
        if (stageMode == StageMode.GROUP_STAGE) {
            return "Group Stage";
        }

        return "Knockout Stage";
    }

    private String getContextName() {
        // TODO: Replace these context strings with selected group/round model data.
        if (stageMode == StageMode.GROUP_STAGE) {
            if (selectedGroupNumber > 0) {
                return "Group " + selectedGroupNumber;
            }

            return "No group selected";
        }

        if (bracketReady) {
            return "Current knockout round";
        }

        return "Bracket not ready";
    }

    private static class MockSimulationService {
        // TODO: Remove this class after connecting the real backend simulation logic.
        private int simulatedMatches = 0;

        private String simulateNextMatch(String stageName, String contextName) {
            simulatedMatches++;
            return "Mock match " + simulatedMatches + " simulated for " + contextName + ".";
        }

        private String simulateCurrentGroup(int groupNumber) {
            simulatedMatches += 3;
            return "Mock Group " + groupNumber + " completed.";
        }

        private String simulateCurrentRound() {
            simulatedMatches += 4;
            return "Mock knockout round completed.";
        }

        private String simulateRestOfTournament() {
            simulatedMatches += 8;
            return "Mock rest of tournament completed.";
        }

        private String resetCurrentGroup(int groupNumber) {
            simulatedMatches = Math.max(0, simulatedMatches - 3);
            return "Mock Group " + groupNumber + " reset.";
        }

        private String resetCurrentRound() {
            simulatedMatches = Math.max(0, simulatedMatches - 4);
            return "Mock knockout round reset.";
        }

        private void resetTournament() {
            simulatedMatches = 0;
        }

        private String getNextMatchPlaceholder(String contextName) {
            return "Placeholder Match " + (simulatedMatches + 1) + " - " + contextName;
        }

        private String getProgressPlaceholder() {
            return simulatedMatches + " placeholder matches simulated";
        }
    }
}
