import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SingleGroupTeamInfoController extends BaseController {
    
    @FXML private ImageView flag;
    @FXML private Text teamName;
    @FXML private Text rank;
    @FXML private Text wins;
    @FXML private Text draws;
    @FXML private Text losses;
    @FXML private Text goalsFor;
    @FXML private Text goalsAgainst;
    @FXML private Text points;

    private Team team;

    @Override
    protected void onLoad() {
    }

    public void setTeam(Team team) {
        this.team = team;
        flag.setImage(new Image(team.getFlagPath()));
        teamName.setText(team.getName());
        rank.setText(team.getRanking() + "");
        System.out.println(team.getRecord());
        // wins.setText(team.getRecord().);

    }
}
