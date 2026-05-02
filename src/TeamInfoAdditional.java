//package FinalProject.CS225-Final-Project-S26.src;

//Andrew Larrazabal
//Additional information whenever a user clicks on the team info card

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class TeamInfoAdditional extends BaseController {
    //ATTRIBUTES

    @FXML
    private Text countryNameAdditional;
    
    @FXML
    private Text rankNumber;

    @FXML
    private Text recordWins;

    @FXML
    private Text recordLosses;

    @FXML
    private Text stadiumName;

    @FXML
    private Text coachName;

    @FXML
    private TableView roster;

    private int wins;

    private int losses;

    //INITIALIZE
    @Override
    protected void onLoad() {}

    //METHODS

    //2 methods to update records
    //accumulated wins
    public void addWin() {
        wins++;
        recordWins.setText(Integer.toString(wins));
    }
    //accumulated losses
    public void addLoss() {
        losses++;
        recordLosses.setText(Integer.toString(losses));
    }

    //GETTERS
    public TableView getRoster() {
        return roster;
    }

    //SETTERS
    public void setCountryName(String country) {
        countryNameAdditional.setText(country);
    }

    public void setRank(String rank) {
        rankNumber.setText(rank);
    }

    public void setStadiumName(String name) {
        stadiumName.setText(name);
    }

    public void setCoach(String name) {
        coachName.setText(name);
    }
}
