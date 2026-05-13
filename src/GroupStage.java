import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author David Hagbi, Joshua Staub
 */
public class GroupStage {
    private static final int GROUP_COUNT = 12;
    private static final int TEAMS_PER_GROUP = 4;
    private static final int TOTAL_GROUP_STAGE_TEAMS = GROUP_COUNT * TEAMS_PER_GROUP;
    private static final int ADVANCING_PER_GROUP = 2;
    private static final int THIRD_PLACE_ADVANCING = 8;

    private List<Team> teams;
    private List<Group> groups;
    private List<Team> advancingTeams;
    private List<Team> thirdPlaceAdvancingTeams;
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
        this.thirdPlaceAdvancingTeams = new ArrayList<>();
        this.simulated = false;

        createGroups();
    }

    private void createGroups() {
        ArrayList<Team> tempTeams = new ArrayList<>();

        for (int i = 0; i < teams.size(); i++) {
            int groupIndex = i / TEAMS_PER_GROUP;
            tempTeams.add(teams.get(i));

            if(i % TEAMS_PER_GROUP == TEAMS_PER_GROUP - 1) {
                char groupLetter = (char) ('A' + groupIndex);

                groups.add(new Group("Group " + groupLetter, tempTeams, 1));
                tempTeams = new ArrayList<>();
            }
        }
    }

    private void finalizeGroupStage() {
        simulated = true;

        ArrayList<TeamResults> thirdPlaceTeams = new ArrayList<>();
        for (Group group : groups) {
            advancingTeams.addAll(group.getAdvancingTeams(ADVANCING_PER_GROUP));
            thirdPlaceTeams.add(group.getSortedResults().get(2));
        }

        Collections.sort(thirdPlaceTeams);
        thirdPlaceAdvancingTeams = thirdPlaceTeams.subList(0, THIRD_PLACE_ADVANCING).stream().map(TeamResults::getTeam).toList();
        advancingTeams.addAll(thirdPlaceAdvancingTeams);
    }

    public List<Team> getThirdPlaceAdvancingTeams() {
        return thirdPlaceAdvancingTeams;
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

    public Match getNextMatch() {
        if (simulated) {
            return null;
        }

        Group group = groups.get(currentGroup);
        return group.getNextMatch();
    }

    public void simulateGroupStage() {
        while (!simulated) {
            simulateOneMatch();
        }
    }

    public Group simulateNextGroup() {
        if (simulated) {
            return null;
        }

        int savedGroup = currentGroup;
        while (currentGroup == savedGroup) {
            simulateOneMatch();
        }

        return groups.get(savedGroup);
    }

    public Group getCurrentGroup() {
        if (simulated) {
            return null;
        }
        return groups.get(currentGroup);
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
