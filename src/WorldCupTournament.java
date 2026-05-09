// Author: Chris Rabanales
import java.util.ArrayList;
import java.util.List;

public class WorldCupTournament {

    // Singleton instance
    public static final WorldCupTournament instance = new WorldCupTournament();

    // Stage names + status
    private static final String GROUP_STAGE = "Group Stage";
    private static final String KNOCKOUT_STAGE = "Knockout Stage";
    private static final String COMPLETE = "Complete";

    private GroupStage groupStage;
    private Bracket bracket;

    private String currentStage;
    private Team champion;

    // Private constructor because this class is a singleton
    private WorldCupTournament() {
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
            return null;
        }

        Match match = bracket.simulateOneMatch();

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
    public void resetTournament() {
        TeamParser parser = new TeamParser();

        groupStage = new GroupStage(parser.getTeams());
        bracket = null;

        currentStage = GROUP_STAGE;
        champion = null;
    }
    
    public void resetCurrentGroup(int groupNumber) {
        resetTournament();
    }

    public void resetCurrentRound() {
        resetTournament();
    }
}
