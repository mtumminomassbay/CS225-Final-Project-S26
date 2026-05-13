//package FinalProject.CS225-Final-Project-S26.src;

//Andrew Larrazabal
//Additional information whenever a user clicks on the team

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TeamInfoAdditionalController extends BaseController {
    
    //ATTRIBUTES
    @FXML
    private Label countryNameAdditional;
    
    @FXML
    private Text rankNumber;

    @FXML
    private Text record;

    @FXML
    private Text stadiumName;

    @FXML
    private Text coachName;

    // @FXML
    // private TableView roster;  //Future project to display roster in additional info

    @FXML
    private ImageView flagImageView;

    //INITIALIZE
    @Override
    protected void onLoad() {}

    //METHODS
    public void updateFromTeam(Team team) {
        setCountryName(team.getName());
        setFlagImage(new Image(team.getFlagPath()));
        setRank(String.valueOf(team.getRanking()));
        setStadiumName(team.getHomeStadium());
        setCoach(team.getHeadCoach());
        updateRecord(team.getTeamResults());
    }

    public void updateRecord(TeamResults results) {
        record.setText(results.getWins() + " - " + results.getTies() + " - " + results.getLosses());
    }

    //GETTERS
    // public TableView getRoster() {return roster;}
    public String getCountryName() {return countryNameAdditional.getText();}
    public String getRank() {return rankNumber.getText();}
    public String getStadiumName() {return stadiumName.getText();}
    public String getCoach() {return coachName.getText();}
    public ImageView getFlag() {return flagImageView;}

    //SETTERS
    public void setCountryName(String country) {
        if (country.length() >= 20) {
            countryNameAdditional.setFont(new Font(15));
            countryNameAdditional.setStyle("-fx-font-weight: bold;");
        }

        countryNameAdditional.setText(country);
    }

    public void setFlagImage(Image flag) {
        flagImageView.setImage(flag);
    }

    public void setRank(String rank) {
        rankNumber.setText(rank);
    }

    public void setStadiumName(String name) {
        if (name.length() >= 30) {
            stadiumName.setFont(new Font(10));
        }
        stadiumName.setText(name);
    }

    public void setCoach(String name) {
        coachName.setText(name);
    }

    public boolean equals(TeamInfoAdditionalController other) {
        return (countryNameAdditional.getText().equals(other.getCountryName()) &&
            rankNumber.getText().equals(other.getRank()) &&
            stadiumName.getText().equals(other.getStadiumName()) &&
            coachName.getText().equals(other.getCoach())
        );
    }

    @Override
    public String toString() {
        String s = "";
        s += "Country: " + countryNameAdditional.getText() +
             "\nRank: #" + rankNumber.getText() +
             "\nHome Stadium: " + stadiumName.getText() + 
             "\nCoach: " + coachName.getText() +
             "\nRecord: " + record.getText();
        return s;
    }

    // public void setRoster(TableView roster) {
    //     this.roster = roster;
    // }
}
