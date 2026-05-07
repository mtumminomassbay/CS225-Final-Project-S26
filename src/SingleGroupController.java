import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SingleGroupController extends BaseController {

    @FXML private VBox teamsList;

    @Override
    protected void onLoad() {
        for (int i = 0; i < 4; ++i) {
            addTeamPane();
        }
    }

    private void addTeamPane() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("SingleGroupTeamInfo.fxml")
            );
            
            Node teamNode = loader.load();


            teamsList.getChildren().add(teamNode);

            // FIXME
            //
            // TeamInfoCardController card = loader.getController();

            // listTeamCards.add(card);                      //Add team in array list
            
            // cardContainer.getChildren().add(cardNode);    //<--- MAYBE MOVE TO DIFFERENT METHOD (Load card to Team View Menu)
        } catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
    }

    
}
