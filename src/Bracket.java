import java.util.ArrayList;
import java.util.List;

public class Bracket {
    private List<Team> teams;
    private BracketBranch bracketRoot; // Final Match is the root of the bracket tree
    private Match thirdPlace;


    public Bracket(List<Team> teams) {
        //if (teams.size() != 32) {
        //    throw new IllegalArgumentException("Bracket must be initialized with exactly 32 teams.");
        //}
        this.teams = getTeamsSorted(teams);  // seed teams 
        this.bracketRoot = buildBracket(this.teams); // build the bracket from seeded list
        this.thirdPlace = null; // no third place match untill semifinals have been completed
    }

    private BracketBranch buildBracket(List<Team> seededTeams) {
        List<BracketBranch> currentRound = new ArrayList<>();
        for (int i = 0; i < seededTeams.size(); i += 2) {
        currentRound.add(new BracketBranch(seededTeams.get(i), seededTeams.get(i + 1)));
        }

        while (currentRound.size() > 1) {
            List<BracketBranch> nextRound = new ArrayList<>();
            for (int i = 0; i < currentRound.size(); i += 2) {
                nextRound.add(new BracketBranch(currentRound.get(i), currentRound.get(i + 1)));
            }
            currentRound = nextRound;
        }
        return currentRound.get(0);

    }

    public List<Team> getTeamsSorted(List<Team> qualifiedTeams) {
    // Returns teams in the order provided by GroupStage.getAdvancingTeams()
    // Full bracket seeding logic can be added later if requested by frontend team
    return new ArrayList<>(qualifiedTeams);
}

    
    public Match simulateOneMatch() {
        if (bracketRoot.isFinished()) {
            return null;
        }

        BracketBranch leftSemi  = bracketRoot.getLeftBranch();
        BracketBranch rightSemi = bracketRoot.getRightBranch();

        if (leftSemi.isFinished() && rightSemi.isFinished() && thirdPlace == null) {
            Match leftMatch  = leftSemi.getMatch();
            Match rightMatch = rightSemi.getMatch();

            Team loser1 = leftMatch.getWinner().equals(leftMatch.getFirstTeam())
                ? leftMatch.getSecondTeam() : leftMatch.getFirstTeam();
            Team loser2 = rightMatch.getWinner().equals(rightMatch.getFirstTeam())
                ? rightMatch.getSecondTeam() : rightMatch.getFirstTeam();

            thirdPlace = new Match(loser1, loser2, false);
            thirdPlace.simulate();
            return thirdPlace;
        }

        if (thirdPlace != null && thirdPlace.isFinished()) {
            return bracketRoot.simulateOneMatch();
        }

        return bracketRoot.simulateOneMatch();
    }

    public boolean isFinished() {
        return bracketRoot.isFinished() && thirdPlace != null && thirdPlace.isFinished();
    }

    public BracketBranch getBracketRoot() {
        return bracketRoot;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Match> getMatches() {
        List<Match> matches = new ArrayList<>();
        collectMatches(bracketRoot, matches);
        if (thirdPlace != null) {
            matches.add(thirdPlace);
        }
        return matches;
    }

    private void collectMatches(BracketBranch branch, List<Match> matches) {
        if (branch == null) return;
        collectMatches(branch.getLeftBranch(), matches);
        collectMatches(branch.getRightBranch(), matches);
        if (branch.getMatch() != null) {
            matches.add(branch.getMatch());
        }
    }

    public Match getFinal() {
        return bracketRoot.getMatch();
    }

    public Match getThirdPlace() {
        return thirdPlace;
    }


}


