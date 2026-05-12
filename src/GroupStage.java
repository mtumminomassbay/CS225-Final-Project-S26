import javafx.print.Collation;

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


        ArrayList<Team> tempTeams = new ArrayList<>();

        for (int i = 0; i < teams.size(); i++) {
            int groupIndex = i / GROUP_COUNT;
            tempTeams.add(teams.get(i));

            if(i % GROUP_COUNT == GROUP_COUNT - 1) {
                char groupLetter = (char) ('A' + groupIndex);

                groups.add(new Group("Group " + groupLetter, tempTeams, 1));
                tempTeams = new ArrayList<>();

            }

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
