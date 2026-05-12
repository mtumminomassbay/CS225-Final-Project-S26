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
        this.thirdPlace = null; // no third place match until semifinals have been completed
    }

    private BracketBranch buildBracket(List<Team> seededTeams) {
        List<BracketBranch> currentRound = new ArrayList<>();
        for (int i = 0; i < seededTeams.size(); i += 2) {
            currentRound.add(new BracketBranch(seededTeams.get(i), seededTeams.get(i + 1), seededTeams.size()));
        }

        while (currentRound.size() > 1) {
            List<BracketBranch> nextRound = new ArrayList<>();
            for (int i = 0; i < currentRound.size(); i += 2) {
                nextRound.add(new BracketBranch(currentRound.get(i), currentRound.get(i + 1), currentRound.size()));
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

    public String getCurrentRound() {
        if (getCompletedMatches() < 16) {
            return "Round of 32";
        } else if (getCompletedMatches() < 24) {
            return "Round of 16";
        } else if (getCompletedMatches() < 28) {
            return "Quarterfinals";
        } else if (getCompletedMatches() < 30) {
            return "Semifinals";
        } else if (getCompletedMatches() == 30) {
            return "Match for Third";
        } else if (getCompletedMatches() == 31) {
            return "Finals";
        } else {
            return "Completed";
        }
    }

    public int getTotalMatches() {
        return teams.size();
    }

    public int getCompletedMatches() {
        int totalMatches = bracketRoot.getMatchesSimulated();
        if (thirdPlace != null && thirdPlace.isFinished()) {
            totalMatches += 1;
        }

        return totalMatches;
    }
}


