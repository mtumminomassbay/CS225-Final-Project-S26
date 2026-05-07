import java.util.List;

public class BracketTester {
    public static void main(String[] args) {
        // Load teams and simulate group stage to get proper 32 advancing teams
        TeamParser parser = new TeamParser();
        GroupStage groupStage = new GroupStage(parser.getTeams());
        groupStage.simulateGroupStage();

        List<Team> advancing = groupStage.getAdvancingTeams();
        System.out.println("Advancing teams: " + advancing.size());

        Bracket bracket = new Bracket(advancing);

        // Simulate one match at a time
        while (!bracket.isFinished()) {
            Match match = bracket.simulateOneMatch();
            if (match != null) {
                System.out.println(match);
            }
        }

        // Print final results
        System.out.println("\n--- RESULTS ---");
        System.out.println("3rd Place: " + bracket.getThirdPlace().getWinner().getName());
        System.out.println("Champion:  " + bracket.getFinal().getWinner().getName());
    }
}