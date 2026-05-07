//package FinalProject.CS225-Final-Project-S26.src;

//Andrew Larrazabal
//Team Info Card that's displayed on the Team View

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class TeamInfoCardController extends BaseController {

    //ATTRIUBTES 
    @FXML
    private Text groupAssignment;

    @FXML
    private Text confederationName;

    @FXML
    private Label countryName;

    @FXML
    private ImageView flagImageView;

    @FXML
    private AnchorPane cardPane;

    @FXML
    private Pane backgroundPane;

    private Team team;          //Team object for each individual card

    //INITIALIZE
    @Override
    protected void onLoad() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/TeamInfoAdditional.fxml")
            );

            loader.load();
            
        } catch (Exception e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }

    }

    //METHODS

    @FXML
    public void cardClicked() {    //When the Team Card gets clicked, open a new window with additional info about the team
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/TeamInfoAdditional.fxml")
            );

            VBox root = loader.load();

            TeamInfoAdditionalController add_info_controller = loader.getController();
            
            //Add team info into additional controller class
            add_info_controller.setCountryName(team.getName());
            add_info_controller.setFlagImage(new Image(team.getFlagPath()));
            add_info_controller.setRank(team.getRanking());
            add_info_controller.setStadiumName(team.getHomeStadium());
            add_info_controller.setCoach(team.getHeadCoach());

            //Create a new window
            Stage stage = new Stage();
            stage.setTitle("Team Info");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }
    }

    //GETTERS
    public ImageView getFlag() {return flagImageView;}
    public String getCountryName() {return countryName.getText();}

    //SETTERS
    public void setTeam(Team team) {
        this.team = team;

        if (team.getName().length() >= 20) {
            countryName.setFont(new Font(15));
        }

        countryName.setText(team.getName());
        confederationName.setText(team.getRegion());
        flagImageView.setImage(new Image(team.getFlagPath()));

        setBackgroundColor(team.getColor());
    }

    public void setCountryName(String name) {
        countryName.setText(name);           //Set country name for both Team Card and additional window
    }
    public void setFlagImage(Image flag) {
        flagImageView.setImage(flag);        //Set flag image for both Team Card and additional window
    }

    public void setBackgroundColor(String color) {
        String country = team.getName();
        if ( //Change Text color to white for darker backgrounds (Could be improved)
            country.equals("England")
        ) {   //Change color of text with dark backgrounds
            countryName.setTextFill(Color.BLACK);
        }
        backgroundPane.setStyle("-fx-background-color: " + color + ";");
    }
}
