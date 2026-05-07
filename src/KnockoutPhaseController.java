/*
    Controller for knockout.fxml
*/
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.fxml.FXML;

public class KnockoutPhaseController extends BaseController{
    private Button Dashboard_button;

    @FXML private SimulationController simulationControlsController;

    // TODO: Replace this placeholder with real bracket state from the backend.
    private final boolean bracketReady = true;

    @Override
    protected void onLoad() {
        simulationControlsController.configureForKnockoutStage(bracketReady);
    }
    

    @FXML
    private void Dashboard_button_clicked() {
        navigateTo(Screen.DASHBOARD);
    }
}
