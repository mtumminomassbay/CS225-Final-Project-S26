import java.util.Map;
import java.util.List;

/**
 * @author Jonathan
 */
public class Group
{

    private String groupName;
    private List<Team> teams;
    private List<Match> matches;
    private Map<Team, GroupResults> groupResults;

    private int matchesPerTeamPair;
    private int matchesPlayed;

    public Group(String groupName, List<Team> teams, int matchesPerTeamPair) {
        this.groupName = groupName;
        this.teams = teams;
        this.matchesPerTeamPair = matchesPerTeamPair;
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
}
