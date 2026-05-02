/*
    Controller for knockout.fxml
*/

import javafx.fxml.FXML;

public class KnockoutPhaseController extends BaseController{

    @FXML private SimulationController simulationControlsController;

    // TODO: Replace this placeholder with real bracket state from the backend.
    private final boolean bracketReady = true;

    @Override
    protected void onLoad() {
        simulationControlsController.configureForKnockoutStage(bracketReady);
    }
    
}
