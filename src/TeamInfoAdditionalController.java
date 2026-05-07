//package FinalProject.CS225-Final-Project-S26.src;

//Andrew Larrazabal
//Additional information whenever a user clicks on the team

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

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

    private Image flagImage;

    private int wins;

    private int losses;

    private int ties;

    //INITIALIZE
    @Override
    protected void onLoad() {}

    //METHODS

    //All methods to update record
    public void updateRecord() {
        record.setText(String.valueOf(wins) + " - " + String.valueOf(ties) + " - " + String.valueOf(losses));
    }

    public void addWin() {wins++;}
    public void addLoss() {losses++;}
    public void addTie() {ties++;}

    //GETTERS
    // public TableView getRoster() {return roster;}
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

    // public void setRoster(TableView roster) {
    //     this.roster = roster;
    // }
}
