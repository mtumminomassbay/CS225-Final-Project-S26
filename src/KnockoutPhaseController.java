/*
    Controller for knockout.fxml
*/
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class KnockoutPhaseController extends BaseController{
    @FXML
    private Button Dashboard_button;

    @FXML
    private ImageView trophyImage;

    @Override
    protected void onLoad() {
        trophyImage.setImage(new Image(getClass().getResourceAsStream("/images/trophy.png")));
    }


    @FXML
    private void Dashboard_button_clicked() {
        navigateTo(Screen.DASHBOARD);
    }
}
