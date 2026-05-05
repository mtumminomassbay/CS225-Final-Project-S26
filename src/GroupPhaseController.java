import javafx.fxml.FXML;
import javafx.scene.control.Button;

/*
    Controller for group-stage.fxml
*/

public class GroupPhaseController extends BaseController{

    @FXML private Button group1;
    @FXML private Button group2;
    @FXML private Button group3;
    @FXML private Button group4;
    @FXML private Button group5;
    @FXML private Button group6;
    @FXML private Button group7;
    @FXML private Button group8;
    @FXML private Button group9;
    @FXML private Button group10;
    @FXML private Button group11;
    @FXML private Button group12;
    private static int groupInFocus;

    public GroupPhaseController() {
    }

    @Override
    protected void onLoad(){
        if (group1 != null) {
            group1.setOnAction(e -> showGroup(1));
            group2.setOnAction(e -> showGroup(2));
            group3.setOnAction(e -> showGroup(3));
            group4.setOnAction(e -> showGroup(4));
            group5.setOnAction(e -> showGroup(5));
            group6.setOnAction(e -> showGroup(6));
            group7.setOnAction(e -> showGroup(7));
            group8.setOnAction(e -> showGroup(8));
            group9.setOnAction(e -> showGroup(9));
            group10.setOnAction(e -> showGroup(10));
            group11.setOnAction(e -> showGroup(11));
            group12.setOnAction(e -> showGroup(12));
        }
    }
    
    @FXML
    private void showGroup(int i) {
        groupInFocus = i;
        navigateTo(Screen.SINGLE_GROUP);
    }

    public static int getGroupInFocus() {
        return groupInFocus;
    }
}
