//package FinalProject.CS225-Final-Project-S26.src;

//Andrew Larrazabal
//Info menu view of the teams

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

public class TeamInfoViewController extends BaseController {

    //ATTRIBUTES
    @FXML
    private FlowPane cardContainer; // matches fx:id on the inner VBox

    private ArrayList<TeamInfoCard> teamCardList = new ArrayList<>();

    //INITIALIZE
    @Override
    protected void onLoad() {

        
        // TEST: load cards
        for (int i = 0; i < 48; i++) {
            addTeamCard();
        }
    }

    //METHODS
    public void addTeamCard() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("TeamInfoCard.fxml")
            );
            
            Node cardNode = loader.load();

            TeamInfoCard card = loader.getController();
            
            cardContainer.getChildren().add(cardNode);

        } catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
    }
}
