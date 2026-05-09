import java.io.IOException;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/*
    Controller for group-stage.fxml
*/
public class GroupPhaseController extends BaseController {
    
    @FXML private GridPane buttonGrid;

    private static GroupStage groupStage;

    @Override
    protected void onLoad(){
        if (groupStage == null) {
            groupStage = new GroupStage(new TeamParser().getTeams());
        }

        Iterator<Group> groups = groupStage.getGroups().iterator();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j) {
                addGroupButton(j, i, groups.next());
            }
        }
    }

    private void addGroupButton(int x, int y, Group group) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("GroupButton.fxml")
            );
            
            Node buttonNode = loader.load();
           ((GroupButtonController)loader.getController()).setGroup(group);

            buttonGrid.add(buttonNode, x, y);
        } catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
    }
    
    @FXML
    private void showGroup() {
        navigateTo(Screen.SINGLE_GROUP);
    }
}
