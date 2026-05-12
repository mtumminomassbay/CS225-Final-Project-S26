import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GroupStage {
    private static final int GROUP_COUNT = 12;
    private static final int TEAMS_PER_GROUP = 4;
    private static final int TOTAL_GROUP_STAGE_TEAMS = GROUP_COUNT * TEAMS_PER_GROUP;
    private static final int ADVANCING_PER_GROUP = 2;
    private static final int THIRD_PLACE_ADVANCING = 8;

    private List<Team> teams;
    private List<Group> groups;
    private List<Team> advancingTeams;
    private int currentGroup = 0;

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
            Team t = teams.get(i);
            int groupIndex = i % GROUP_COUNT;
            groupBuckets.get(groupIndex).add(t);

            //Tristan to set group letter to Team
            char groupLetter = (char) ('A' + groupIndex);
            t.setGroup("Group " + groupLetter);
            //System.out.println("Setting group for: " + t.getName() + " " + t.getGroup());
        }

        for (int i = 0; i < GROUP_COUNT; i++) {
            char groupLetter = (char) ('A' + i);
            Group group = new Group("Group " + groupLetter, groupBuckets.get(i), 1);
            groups.add(group);
        }
    }

    private void finalizeGroupStage() {
        simulated = true;

        ArrayList<GroupResults> thirdPlaceTeams = new ArrayList<>();
        for (Group group : groups) {
            advancingTeams.addAll(group.getAdvancingTeams(ADVANCING_PER_GROUP));
            thirdPlaceTeams.add(group.getSortedResults().get(2));
        }

        Collections.sort(thirdPlaceTeams);
        advancingTeams.addAll(thirdPlaceTeams.subList(0, THIRD_PLACE_ADVANCING).stream().map(GroupResults::getTeam).toList());
    }

    public Match simulateOneMatch() {
        if (simulated) {
            return null;
        }

        Group group = groups.get(currentGroup);
        Match match = group.simulateOneMatch();

        if (group.isCompleted()) {
            currentGroup++;
            if (currentGroup == groups.size()) {
                finalizeGroupStage();
            }
        }
        return match;
    }

    public void simulateGroupStage() {
        while (!simulated) {
            simulateOneMatch();
        }
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
