/*
    Controller for knockout.fxml
*/
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

public class KnockoutPhaseController extends BaseController{
    // TODO: no longer needed thanks to top left home button
    /*@FXML
    private Button Dashboard_button;*/

    @FXML
    private ImageView trophyImage;

    /* This list holds the labels for the team names in the bracket,
       starting from the top leftmost team in the left round of 32 section,
       going down, then right. The last label in the list is the bottom rightmost
       label in the right round of 32 section. */
    @FXML
    private List<Label> teamLabels;

    // All 64 team labels, each should be named "tl" then the index of where
    // it will be in the list (0 to 63)
    @FXML
    private Label tl0, tl1, tl2, tl3, tl4, tl5, tl6, tl7, tl8, tl9, tl10;
    @FXML
    private Label tl11, tl12, tl13, tl14, tl15, tl16, tl17, tl18, tl19, tl20;
    @FXML
    private Label tl21, tl22, tl23, tl24, tl25, tl26, tl27, tl28, tl29, tl30;
    @FXML
    private Label tl31, tl32, tl33, tl34, tl35, tl36, tl37, tl38, tl39, tl40;
    @FXML
    private Label tl41, tl42, tl43, tl44, tl45, tl46, tl47, tl48, tl49, tl50;
    @FXML
    private Label tl51, tl52, tl53, tl54, tl55, tl56, tl57, tl58, tl59, tl60;
    @FXML
    private Label tl61, tl62, tl63;

    @Override
    protected void onLoad() {
        // TODO: should probably add a try catch to this
        trophyImage.setImage(new Image(getClass().getResourceAsStream("/images/trophy.png")));

        // Add labels for all teams to list
        teamLabels = List.of(tl0, tl1, tl2, tl3, tl4, tl5, tl6, tl7, tl8, tl9, tl10,
                tl11, tl12, tl13, tl14, tl15, tl16, tl17, tl18, tl19, tl20,
                tl21, tl22, tl23, tl24, tl25, tl26, tl27, tl28, tl29, tl30,
                tl31, tl32, tl33, tl34, tl35, tl36, tl37, tl38, tl39, tl40,
                tl41, tl42, tl43, tl44, tl45, tl46, tl47, tl48, tl49, tl50,
                tl51, tl52, tl53, tl54, tl55, tl56, tl57, tl58, tl59, tl60,
                tl61, tl62, tl63);
    }

    // TODO: no longer needed thanks to top left home button
    /*@FXML
    private void Dashboard_button_clicked() {
        navigateTo(Screen.DASHBOARD);
    }*/

}
