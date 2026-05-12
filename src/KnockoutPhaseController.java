/*
    Controller for knockout.fxml
*/
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class KnockoutPhaseController extends BaseController{
    @FXML private Button Dashboard_button;
    @FXML private Button simulationControlsToggleButton;
    @FXML private Pane simulationControlsDrawer;
    @FXML private SimulationController simulationControlsController;

    @Override
    protected void onLoad() {
        hideSimulationControlsDrawer();
        simulationControlsToggleButton.setText("Simulation Controls");
        simulationControlsToggleButton.setOnAction(event -> toggleSimulationControlsDrawer());

        simulationControlsController.configureForKnockoutStage();
        simulationControlsController.setOnSimulationChanged(this::refreshKnockoutView);
    }
    
    @FXML
    private void toggleSimulationControlsDrawer() {
        boolean shouldShow = !simulationControlsDrawer.isVisible();

        simulationControlsDrawer.setVisible(shouldShow);
        simulationControlsDrawer.setManaged(shouldShow);
        simulationControlsToggleButton.setText(shouldShow ? "Hide Controls" : "Simulation Controls");
    }

    private void hideSimulationControlsDrawer() {
        simulationControlsDrawer.setVisible(false);
        simulationControlsDrawer.setManaged(false);
    }

    private void refreshKnockoutView() {
        if (!worldCup.isGroupStageComplete()) {
            return;
        }
    }

    @FXML
    private void Dashboard_button_clicked() {
        navigateTo(Screen.DASHBOARD);
    }
}
