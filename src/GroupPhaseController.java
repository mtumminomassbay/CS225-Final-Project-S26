import javafx.fxml.FXML;
import javafx.scene.control.Button;

/*
    Controller for group-stage.fxml
*/

public class GroupPhaseController extends BaseController{

    @FXML private Button group1;
    @FXML private Button group2;
    @FXML private Button group3;
    @FXML private Button group4;
    @FXML private Button group5;
    @FXML private Button group6;
    @FXML private Button group7;
    @FXML private Button group8;
    @FXML private Button group9;
    @FXML private Button group10;
    @FXML private Button group11;
    @FXML private Button group12;
    @FXML private SimulationController simulationControlsController;

    // 0 means no group has been selected yet.
    private int selectedGroupNumber = 0;

    @Override
    protected void onLoad(){
        group1.setOnAction(e -> showGroup(1));
        group2.setOnAction(e -> showGroup(2));
        group3.setOnAction(e -> showGroup(3));
        group4.setOnAction(e -> showGroup(4));
        group5.setOnAction(e -> showGroup(5));
        group6.setOnAction(e -> showGroup(6));
        group7.setOnAction(e -> showGroup(7));
        group8.setOnAction(e -> showGroup(8));
        group9.setOnAction(e -> showGroup(9));
        group10.setOnAction(e -> showGroup(10));
        group11.setOnAction(e -> showGroup(11));
        group12.setOnAction(e -> showGroup(12));

        // Start with no group selected, so group-specific buttons stay disabled.
        simulationControlsController.configureForGroupStage(selectedGroupNumber);
    }
    
    @FXML
    private void showGroup(int i) {
        selectedGroupNumber = i;
        simulationControlsController.configureForGroupStage(selectedGroupNumber);
        System.out.println("Selected Group " + i);
        //TODO: open single group view for corresponding group
    }
}
