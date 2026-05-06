import java.util.ArrayList;
import java.util.List;

public class Bracket {
    private List<Team> teams;
<<<<<<< HEAD
    private BracketBranch bracketRoot; // Final Match is the root of the bracket tree
=======
    private BracketBranch bracketRoot;
>>>>>>> ebdb4753feb49930b80e79af5b9dc31f76d84d3f
    private Match thirdPlace;


    public Bracket(List<Team> teams) {
<<<<<<< HEAD
        this.teams = getTeamsSorted(teams);  // seed teams 
        this.bracketRoot = buildBracket(this.teams); // build the bracket from seeded list
        this.thirdPlace = null; // no third place match untill semifinals have been completed
=======
        this.teams = getTeamsSorted(teams);
        this.bracketRoot = buildBracket(this.teams);
        this.thirdPlace = null;
>>>>>>> ebdb4753feb49930b80e79af5b9dc31f76d84d3f
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
<<<<<<< HEAD
            Team group1Winner = qualifiedTeams.get(i);
            Team group1RunnerUp = qualifiedTeams.get(i + 1);
            Team group2Winner = qualifiedTeams.get(i + 2);
            Team group2RunnerUp = qualifiedTeams.get(i + 3);

            sorted.add(group1Winner);
            sorted.add(group1RunnerUp);
            sorted.add(group2Winner);
            sorted.add(group2RunnerUp);
=======
            Team groupAWinner = qualifiedTeams.get(i);
            Team groupARunnerUp = qualifiedTeams.get(i + 1);
            Team groupBWinner = qualifiedTeams.get(i + 2);
            Team groupBRunnerUp = qualifiedTeams.get(i + 3);

            sorted.add(groupAWinner);
            sorted.add(groupBRunnerUp);
            sorted.add(groupBWinner);
            sorted.add(groupARunnerUp);
>>>>>>> ebdb4753feb49930b80e79af5b9dc31f76d84d3f
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


