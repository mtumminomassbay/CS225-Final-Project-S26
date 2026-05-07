import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/*
    Controller for group-stage.fxml
*/
public class GroupPhaseController extends BaseController {

    @FXML private GridPane buttonGrid;
    private static int groupInFocus;

<<<<<<< Gabe-FrontEnd
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
=======
    public GroupPhaseController() {
    }
>>>>>>> main

    @Override
    protected void onLoad(){
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 3; ++j) {
                addGroupButton(i, j);
            }
        }
    }

    private void addGroupButton(int x, int y) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("GroupButton.fxml")
            );
            
            Node buttonNode = loader.load();

<<<<<<< Gabe-FrontEnd
        // Start with no group selected, so group-specific buttons stay disabled.
        simulationControlsController.configureForGroupStage(selectedGroupNumber);
=======

            buttonGrid.add(buttonNode, x, y);

            // FIXME
            //
            // TeamInfoCardController card = loader.getController();

            // listTeamCards.add(card);                      //Add team in array list
            
            // cardContainer.getChildren().add(cardNode);    //<--- MAYBE MOVE TO DIFFERENT METHOD (Load card to Team View Menu)
        } catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
>>>>>>> main
    }
    
    @FXML
    private void showGroup(int i) {
<<<<<<< Gabe-FrontEnd
        selectedGroupNumber = i;
        simulationControlsController.configureForGroupStage(selectedGroupNumber);
        System.out.println("Selected Group " + i);
        //TODO: open single group view for corresponding group
=======
        groupInFocus = i;
        navigateTo(Screen.SINGLE_GROUP);
    }

    public static int getGroupInFocus() {
        return groupInFocus;
>>>>>>> main
    }
}
