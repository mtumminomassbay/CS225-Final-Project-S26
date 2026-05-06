import java.util.List;

public class BracketTester {
    public static void main(String[] args) {
        TeamParser parser = new TeamParser();
        List<Team> teams = parser.getTeams();

        teams = teams.subList(0, 32);
        Bracket bracket = new Bracket(teams);

        while (!bracket.isFinished()) {
            System.out.println(bracket.simulateOneMatch().toString());
        }

        System.out.println("-----");
        for (Match match : bracket.getMatches()) {
            System.out.println(match.toString());
        }

        System.out.println("\n\n");
    }
}
