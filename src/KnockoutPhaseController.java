/*
    Controller for knockout.fxml
*/
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class KnockoutPhaseController extends BaseController{
    // TODO: no longer needed thanks to top left home button
    /*@FXML private Button Dashboard_button;*/

    @FXML private ImageView trophyImage;

    // TODO: maybe use vboxes and just get the children
    @FXML private VBox roundOf32Left;
    @FXML private VBox roundOf32Right;
    @FXML private VBox roundOf16Left;
    @FXML private VBox roundOf16Right;
    @FXML private VBox quarterfinalsLeft;
    @FXML private VBox quarterfinalsRight;
    @FXML private VBox semifinalsLeft;
    @FXML private VBox semifinalsRight;
    @FXML private VBox finals;

    @Override
    protected void onLoad() {
        try {
            trophyImage.setImage(new Image(getClass().getResourceAsStream("/images/trophy.png")));
        } catch (NullPointerException e) {
            System.err.println("COULD NOT LOAD IMAGE: " + e.getMessage());
        }

    }

    // TODO: no longer needed thanks to top left home button
    /*@FXML
    private void Dashboard_button_clicked() {
        navigateTo(Screen.DASHBOARD);
    }*/

}
