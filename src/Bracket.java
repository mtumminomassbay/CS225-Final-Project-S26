import java.util.ArrayList;
import java.util.List;

public class Bracket {
    private List<Team> teams;
    private BracketBranch bracketRoot;
    private Match thirdPlace;


    public Bracket(List<Team> teams) {
        this.teams = getTeamsSorted(teams);
        this.bracketRoot = buildBracket(this.teams);
        this.thirdPlace = null;
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
        List<Team> sorted = new ArrayList<>();
        for (int i = 0; i < qualifiedTeams.size(); i += 4) {
            Team groupAWinner = qualifiedTeams.get(i);
            Team groupARunnerUp = qualifiedTeams.get(i + 1);
            Team groupBWinner = qualifiedTeams.get(i + 2);
            Team groupBRunnerUp = qualifiedTeams.get(i + 3);

            sorted.add(groupAWinner);
            sorted.add(groupBRunnerUp);
            sorted.add(groupBWinner);
            sorted.add(groupARunnerUp);
        }
        return sorted;
    }

    public Match simulateOneMatch() {
        if (bracketRoot.isFinished()) {
            if (thirdPlace != null && !thirdPlace.isFinished()) {
                thirdPlace.simulate();
                return thirdPlace;
            }
        
            return null;
        } 
        
        return bracketRoot.simulateOneMatch();
    }

    public boolean isFinished() {
        return bracketRoot.isFinished() && (thirdPlace == null || thirdPlace.isFinished());
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
}


