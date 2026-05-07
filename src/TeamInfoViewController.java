//package FinalProject.CS225-Final-Project-S26.src;

//Andrew Larrazabal
//Menu for the Team Info view

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class TeamInfoViewController extends BaseController {

    //ATTRIBUTES
    @FXML
    private FlowPane cardContainer;

    private ArrayList<TeamInfoCardController> listTeamCards = new ArrayList<>();

    TeamParser parser = new TeamParser();
    ArrayList<Team> teams = parser.getTeams();

    //INITIALIZE
    @Override
    protected void onLoad() {

        //TEST: teams object

        for (Team team: teams) {
            addTeamCard(team);
        }

        //TODO: a bit lost on how to sort the list alphabetically from an object

        /*
        TODO: Either use addTeamCard() from earlier to set up each country, or
                sort the Object List alphabetically first and then add them to the
                view in a different method (Start of code shown below)
        */

        // for (TeamInfoCardController card : listTeamCards) {
        //     setTeamView(card)
        // }
    }

    //METHODS
    public void addTeamCard(Team team) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/TeamInfoCard.fxml")
            );
            
            Node cardNode = loader.load();
        
            TeamInfoCardController card = loader.getController();

            card.setTeam(team);                           //Set up team in each card
            listTeamCards.add(card);                      //Add team in array list
            cardContainer.getChildren().add(cardNode);    //<--- MAYBE MOVE TO DIFFERENT METHOD (Load card to Team View Menu)

        } catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
    }

    //FIXME: add method to set up the team view if needed

    public ArrayList<TeamInfoCardController> getTeamList() {return listTeamCards;}

    public void setTeamList(ArrayList<TeamInfoCardController> list) {listTeamCards = list;}
}
