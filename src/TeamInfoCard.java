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
import javafx.stage.Stage;


public class TeamInfoCard extends BaseController {

    //ATTRIUBTES 
    @FXML
    private Text groupAssignment;

    @FXML
    private Text confederationName;

    @FXML
    private Label countryName;

    @FXML
    private ImageView flagImage;

    @FXML
    private AnchorPane cardPane;

    @FXML
    private Pane backgroundPane;

    private TeamInfoAdditional additionalInfo; //Additional team info object to update 

    //INITIALIZE
    @Override
    protected void onLoad() {
        try {
            FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/TeamInfoAdditional.fxml")
            );

            loader.load();

            additionalInfo = loader.getController();
            
        } catch (Exception e) {
            System.err.println("COULD NOT LOAD FXML: " + e.getMessage());
        }

    }

    //METHODS

    //When the Team Card gets clicked, open a new window with additional info about the team
    @FXML
    public void cardClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/TeamInfoAdditional.fxml")
            );

            VBox root = loader.load();

            additionalInfo = loader.getController();

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
    public TeamInfoAdditional getAdditionalInfo() {
        return additionalInfo;
    }

    public String getCountryName() {
        return countryName.getText();
    }

    //SETTERS
    public void setFlagImage(Image flag) {
        flagImage.setImage(flag);        //Set flag image for the Team Card
    }

    public void setBackgroundColor(Color color) {
        backgroundPane.setBackground(    //Set background color for team cards
            new Background( 
                new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)
            ));
    }

    public void setGroupAssignment(String group) {
        groupAssignment.setText(group);
    }

    public void setConfederation(String confedName) {
        confederationName.setText(confedName);
    }

    public void setCountry(String name) {
        countryName.setText(name);
        additionalInfo.setCountryName(name);
    }
}
