// Author: Chris Rabanales
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldCupTournament {
    // Singleton instance
    public static final WorldCupTournament instance = new WorldCupTournament();

    // Stage names + status
    public static final String GROUP_STAGE = "Group Stage";
    public static final String KNOCKOUT_STAGE = "Knockout Stage";
    public static final String COMPLETE = "Complete";

    private final List<Team> allTeams;
    private GroupStage groupStage;
    private Bracket bracket;

    private String currentStage;
    private Team champion;
    private Match currentlyViewedMatch;

    // Private constructor because this class is a singleton
    private WorldCupTournament() {
        TeamParser teamParser = new TeamParser();
        allTeams = teamParser.getTeams();

        resetTournament();
    }

    public static WorldCupTournament getInstance() {
        return instance;
    }

    // Getter methods
    public GroupStage getGroupStage() {
        return groupStage;
    }

    public Bracket getBracket() {
        return bracket;
    }

    public Bracket getKnockoutStage() {
        return bracket;
    }

    public List<Group> getGroups() {
        return groupStage.getGroups();
    }

    public List<Match> getGroupMatches() {
        List<Match> matches = new ArrayList<>();

        for (Group group : groupStage.getGroups()) {
            matches.addAll(group.getMatches());
        }

        return matches;
    }

    public List<Match> getKnockoutMatches() {
        if (bracket == null) {
            return new ArrayList<>();
        }

        return bracket.getMatches();
    }

    public Match getCurrentlyViewedMatch() {
        return currentlyViewedMatch;
    }

    public void setCurrentlyViewedMatch(Match currentlyViewedMatch) {
        this.currentlyViewedMatch = currentlyViewedMatch;
    }

    public Team getChampion() {
        return champion;
    }

    public Team getChampionIfComplete() {
        if (isTournamentComplete()) {
            return champion;
        }

        return null;
    }

    public String getCurrentStage() {
        return currentStage;
    }

    public String getCurrentRound() {
        if (currentStage.equals(GROUP_STAGE)) {
            return "Group Stage";
        }

        if (currentStage.equals(COMPLETE)) {
            return "Tournament Complete";
        }

        return "Knockout Stage";
    }

    // Returns how many matches have been completed
    public int getCompletedMatches() {
        return getCompletedGroupMatches() + getCompletedKnockoutMatches();
    }

    // Returns the total number of matches in the tournament
    public int getTotalMatches() {
        return getTotalGroupMatches() + getTotalKnockoutMatches();
    }

    // returns remaining matches
    public int getRemainingMatches() {
        return getTotalMatches() - getCompletedMatches();
    }

    // displays progress as text
    public String getProgressText() {
        return getCompletedMatches() + " / " + getTotalMatches()
                + " matches completed";
    }

    // Counts completed group-stage matches
    private int getCompletedGroupMatches() {
        int completed = 0;

        for (Match match : getGroupMatches()) {
            if (match.isFinished()) {
                completed++;
            }
        }

        return completed;
    }

    private int getTotalGroupMatches() {
        return getGroupMatches().size();
    }

    private int getCompletedKnockoutMatches() {
        if (bracket == null) {
            return 0;
        }

        int completed = 0;

        for (Match match : bracket.getMatches()) {
            if (match.isFinished()) {
                completed++;
            }
        }

        return completed;
    }

    private int getTotalKnockoutMatches() {
        return 32;
    }
    // following methods check if certain aspects of the program are completed before moving on.
    public boolean isGroupStageComplete() {
        return groupStage.isSimulated();
    }

    public boolean isKnockoutBracketCreated() {
        return bracket != null;
    }

    public boolean isTournamentComplete() {
        return currentStage.equals(COMPLETE);
    }

    public boolean isFinished() {
        return isTournamentComplete();
    }

    // Simulates the next available match
    public Match simulateOneMatch() {
        if (currentStage.equals(COMPLETE)) {
            return null;
        }

        if (currentStage.equals(GROUP_STAGE)) {
            groupStage.simulateGroupStage();
            createKnockoutStage();
            currentlyViewedMatch = null;
            return null;
        }

        Match match = bracket.simulateOneMatch();

        if (match != null) {
            currentlyViewedMatch = match;
        }

        if (bracket.isFinished()) {
            champion = bracket.getFinal().getWinner();
            currentStage = COMPLETE;
        }

        return match;
    }

    public Match simulateNextAvailableMatch() {
        return simulateOneMatch();
    }

    // Simulates the rest of the current round
    public void simulateRemainingCurrentRound() {
        if (currentStage.equals(COMPLETE)) {
            return;
        }

        if (currentStage.equals(GROUP_STAGE)) {
            groupStage.simulateGroupStage();
            createKnockoutStage();
            return;
        }

        while (!bracket.isFinished()) {
            bracket.simulateOneMatch();
        }

        champion = bracket.getFinal().getWinner();
        currentStage = COMPLETE;
    }

    // Simulates the rest of the tournament
    public void simulateRestOfTournament() {
        while (!isTournamentComplete()) {
            simulateOneMatch();
        }
    }

    // Creates the knockout bracket after the group stage
    private void createKnockoutStage() {
        List<Team> advancingTeams = groupStage.getAdvancingTeams();

        if (advancingTeams.size() != 32) {
            throw new IllegalStateException
                ("Bracket needs 32 teams, but GroupStage returned " 
                 + advancingTeams.size());
        }

        bracket = new Bracket(advancingTeams);
        currentStage = KNOCKOUT_STAGE;
    }

    // The following methods reset the entire tournnament,
    // the current group, and the current round.
    //TODO: these reset methods need testing against the frontend to make sure that old copies of groupStage/bracket don't stick around
    public void resetTournament() {
        groupStage = new GroupStage(allTeams);
        bracket = null;

        currentStage = GROUP_STAGE;
        champion = null;
    }
    
    public void resetCurrentGroup(int groupNumber) {
        //FIXME: waiting for implementation in GroupStage
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void resetCurrentRound() {
        if (currentStage.equals(GROUP_STAGE)) {
            groupStage = new GroupStage(allTeams);
        } else {
            createKnockoutStage();
            champion = null;
        }
    }

    public List<Team> getAllTeams() {
        return Collections.unmodifiableList(allTeams);
    }
}
