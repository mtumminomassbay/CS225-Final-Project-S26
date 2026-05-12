import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DashboardController extends BaseController{

    @FXML private Button GroupStage_Button;
    @FXML private Button KnockoutPhase_Button;
    @FXML private Button TeamInfo_Button;
    @FXML private Button MatchDetails_Button;
    @FXML private Label statusLabel;
    @FXML private Label currentStageLabel;
    @FXML private Label groupsCompletedLabel;
    @FXML private ImageView AllStar_ImageView;

    private List<File> AllStarsList = new ArrayList<>();

    private int lastIndex = -1;
    
    private Random rand = new Random();

    @Override
    protected void onLoad() {
        fillAllStarList();
        displayRandomAllStar();

        statusLabel.setText("Status: " + worldCup.getCompletedMatches() + " / " + worldCup.getTotalMatches() + " matches");
        currentStageLabel.setText("Current Stage: " + worldCup.getCurrentStage());
        groupsCompletedLabel.setText("Remaining Matches: " + worldCup.getRemainingMatches());
    }

    @FXML
    private void GroupStageButton_clicked() {
        navigateTo(Screen.GROUP_STAGE);
    }

    @FXML
    private void KnockoutPhaseButton_clicked() {
        navigateTo(Screen.KNOCKOUT);
    }

    @FXML
    private void TeamInfoButton_clicked() {
        navigateTo(Screen.TEAM_INFO);
    }

    @FXML
    private void MatchDetailsButton_clicked() {
        navigateTo(Screen.MATCH_DETAILS);
    }

    private void fillAllStarList() {

        File folder = new File("resources/AllStars");
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles != null) {
            for(File file : listOfFiles) {
                if(file.isFile() && file.getName().endsWith(".png")) {
                    AllStarsList.add(file);
                }
            }
        }
    }

    private void displayRandomAllStar() {
        if(!AllStarsList.isEmpty()) {
            int index;
            do {
                index = rand.nextInt(AllStarsList.size());
            } while(index == lastIndex);

            lastIndex = index;
            File randomFile = AllStarsList.get(index);
            Image img = new Image(randomFile.toURI().toString(), true);

            AllStar_ImageView.setImage(img);
            
            
        }
    }
}
