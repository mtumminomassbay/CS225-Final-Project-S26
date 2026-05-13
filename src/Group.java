import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jonathan
 */
public class Group {
    private String groupName;
    private List<Team> teams;
    private List<Match> matches;

    private int matchesPerTeamPair;
    private int matchesPlayed;
    private boolean completed;

    public Group(String groupName, List<Team> teams, int matchesPerTeamPair) {
        this.groupName = groupName;
        this.teams = teams;
        this.matchesPerTeamPair = matchesPerTeamPair;

        this.matches = new ArrayList<>();
        this.matchesPlayed = 0;
        this.completed = false;

        for (Team team : teams) {
            team.setGroup(this);
        }

        createMatches();
    }

    private void createMatches() {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                for (int k = 0; k < matchesPerTeamPair; k++) {
                    Match match = new Match(teams.get(i), teams.get(j), true);
                    matches.add(match);
                }
            }
        }
    }

    public Match getNextMatch() {
        if (completed) {
            return null;
        }

        return matches.get(matchesPlayed);
    }

    public Match simulateOneMatch() {
        if (completed) {
            return null;
        }

        Match match = getNextMatch();
        match.simulate();
        matchesPlayed++;

        if (matchesPlayed == matches.size()) {
            completed = true;
        }
        return match;
    }

    public List<TeamResults> getSortedResults() {
        List<TeamResults> results = new ArrayList<>(teams.stream().map(Team::getTeamResults).toList());
        Collections.sort(results);
        return results;
    }

    public List<Team> getAdvancingTeams(int numberOfTeams) {
        List<TeamResults> results = getSortedResults();
        List<Team> advancingTeams = new ArrayList<>();

        for (int i = 0; i < numberOfTeams && i < results.size(); i++) {
            advancingTeams.add(results.get(i).getTeam());
        }

        return advancingTeams;
    }

    public int getMatchesPerTeamPair() {
        return matchesPerTeamPair;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public String getGroupName() {
        return groupName;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return groupName;
    }
}
