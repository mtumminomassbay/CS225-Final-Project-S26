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

    public GroupPhaseController() {
    }

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


            buttonGrid.add(buttonNode, x, y);

            // FIXME
            //
            // TeamInfoCardController card = loader.getController();

            // listTeamCards.add(card);                      //Add team in array list
            
            // cardContainer.getChildren().add(cardNode);    //<--- MAYBE MOVE TO DIFFERENT METHOD (Load card to Team View Menu)
        } catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
    }
    
    @FXML
    private void showGroup(int i) {
        groupInFocus = i;
        navigateTo(Screen.SINGLE_GROUP);
    }

    public static int getGroupInFocus() {
        return groupInFocus;
    }
}
