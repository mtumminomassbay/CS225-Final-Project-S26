import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelpController extends BaseController{

    @FXML private Label helpText_Label;

    private static final String HELP_CONTENT = "This application lets you simulate the entire 2026 FIFA World Cup, from the group stage through to the championship.\n\n" +
                                                "The Team Info screen displays all 48 qualified countries competing. Teams are listed alphabetically and each card shows the country name, flag, confederation, and group assignment. " +
                                                "Click on any team card to open a detailed view with additional information including the team's coach, home stadium, and FIFA ranking. " + 
                                                "Multiple team detail windows can be open at the same time, allowing you to compare teams side by side.\n\n " + 
                                                "The Group Stage screen shows all 12 groups of 4 teams competing. Every team plays each other team in their group exactly once. A win is worth " + 
                                                "3 points, a draw is worth 1 point, and a loss is worth 0 points.\n\nFrom the full stage view, you can see all 12 groups at a glance and track " + 
                                                "overall tournament progress. Click on any group to enter the single group view, where you can see individual match results, current standings, and which teams have advanced " + 
                                                "or been eliminated. Click on any completed match to view its full match details.\n\nOnce the group stage is complete, 32 teams advance to the knockout bracket. From this point, " + 
                                                "every match is win or lose, there are no draws. The bracket begins at the Round of 32 and progresses through the Round of 16, Quarterfinals, Semifinals, " + 
                                                "and the Final. The two semifinal losers play each other in a Third Place match before the Final.\n\nIf a knockout match is tied at the end of regulation, the game goes " + 
                                                "to a 30-minute overtime. If the score remains tied after overtime, the match is decided by a penalty shootout. The match detail view will show exactly how each game was decided.\n\n" + 
                                                "Click on any match in the bracket to view its full details. Click on any team to view their team info. ";
    

    @Override
    protected void onLoad() {
        helpText_Label.setText(HELP_CONTENT);
    }


}
