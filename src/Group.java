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
    private Map<Team, GroupResults> groupResults;

    private int matchesPerTeamPair;
    private int matchesPlayed;
    private boolean completed;

    public Group(String groupName, List<Team> teams, int matchesPerTeamPair) {
        this.groupName = groupName;
        this.teams = teams;
        this.matchesPerTeamPair = matchesPerTeamPair;

        this.matches = new ArrayList<>();
        this.groupResults = new LinkedHashMap<>();
        this.matchesPlayed = 0;
        this.completed = false;

        for (Team team : teams) {
            groupResults.put(team, new GroupResults(team));
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

    public void simulateAllMatches() {
        if (completed) {
            return;
        }

        for (Match match : matches) {
            if (!match.isFinished()) {
                match.simulate();
                recordMatchResult(match);
                matchesPlayed++;
            }
        }

        completed = true;
    }

    private void recordMatchResult(Match match) {
        Team team1 = match.getFirstTeam();
        Team team2 = match.getSecondTeam();

        int team1Score = match.getFirstTeamScore();
        int team2Score = match.getSecondTeamScore();

        groupResults.get(team1).addResult(team1Score, team2Score);
        groupResults.get(team2).addResult(team2Score, team1Score);
    }

    public List<GroupResults> getSortedResults() {
        List<GroupResults> results = new ArrayList<>(groupResults.values());
        Collections.sort(results);
        return results;
    }

    public List<Team> getAdvancingTeams(int numberOfTeams) {
        List<GroupResults> results = getSortedResults();
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

    public GroupResults getGroupResults(Team team) {
        return groupResults.get(team);
    }

    public String getGroupName() {
        return groupName;
    }

    public boolean isCompleted() {
        return completed;
    }
}
