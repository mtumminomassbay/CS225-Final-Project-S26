import java.util.ArrayList;

public class TeamTester {
    public static void main(String[] args) {
        TeamParser parser = new TeamParser();
        ArrayList<Team> teams = parser.getTeams();

        for (Team t : teams) {
            System.out.println(t);
        }
    }
}
