import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class GroupStage {
    private static final int GROUP_COUNT = 12;
    private static final int TEAMS_PER_GROUP = 4;
    private static final int TOTAL_GROUP_STAGE_TEAMS = GROUP_COUNT * TEAMS_PER_GROUP;
    private static final int ADVANCING_PER_GROUP = 2;

    private List<Team> teams;
    private List<Group> groups;
    private List<Team> advancingTeams;

    private boolean simulated;

    public GroupStage(List<Team> allTeams) {
        if (allTeams.size() < TOTAL_GROUP_STAGE_TEAMS) {
            throw new IllegalArgumentException(
                "Group stage needs at least 48 teams. Only found " + allTeams.size() + "."
            );
        }

        this.teams = new ArrayList<>(allTeams);

        this.teams.sort(Comparator.comparingInt(Team::getRanking));


        this.teams = new ArrayList<>(this.teams.subList(0, TOTAL_GROUP_STAGE_TEAMS));

        this.groups = new ArrayList<>();
        this.advancingTeams = new ArrayList<>();
        this.simulated = false;

        createGroups();
    }

    private void createGroups() {
        List<List<Team>> groupBuckets = new ArrayList<>();

        for (int i = 0; i < GROUP_COUNT; i++) {
            groupBuckets.add(new ArrayList<>());
        }

        for (int i = 0; i < teams.size(); i++) {
            int groupIndex = i % GROUP_COUNT;
            groupBuckets.get(groupIndex).add(teams.get(i));
        }

        for (int i = 0; i < GROUP_COUNT; i++) {
            char groupLetter = (char) ('A' + i);
            Group group = new Group("Group " + groupLetter, groupBuckets.get(i), 1);
            groups.add(group);
        }
    }

    public void simulateGroupStage() {
        if (simulated) {
            return;
        }

        for (Group group : groups) {
            group.simulateAllMatches();
            advancingTeams.addAll(group.getAdvancingTeams(ADVANCING_PER_GROUP));
        }

        simulated = true;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Team> getAdvancingTeams() {
        return advancingTeams;
    }

    public Group getGroupByName(String groupName) {
        for (Group group : groups) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }

        return null;
    }

    public boolean isSimulated() {
        return simulated;
    }
}
