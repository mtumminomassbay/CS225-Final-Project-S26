/* Author: Chris Rabanales */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WorldCupTournament {

    private static final int THIRD_PLACE_ADVANCE = 8;

    private List<Group> groups;

    // group stage matches
    private List<Match> groupMatches;
    private List<Group> groupForMatch;

    // knockout stage matches
    private List<Match> knockoutMatches;
    private List<Team> roundWinners;

    // tracks the current match
    private int groupMatchIndex;
    private int knockoutMatchIndex;

    private Team champion;

    // constructor:
    public WorldCupTournament() {
        this.groups = groups;

        groupMatches = new ArrayList<>();
        groupForMatch = new ArrayList<>();

        knockoutMatches = new ArrayList<>();
        roundWinners = new ArrayList<>();

        groupMatchIndex = 0;
        knockoutMatchIndex = 0;
        champion = null;

        createGroupMatches();
    }

    // getter methods:
    public Team getChampion() {
        return champion;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Match> getGroupMatches() {
        return groupMatches;
    }

    public List<Match> getKnockoutMatches() {
        return knockoutMatches;
    }

    private int getTotalPoints(Team team) {
        for (Group group : groups) {
            if (group.getTeams().contains(team)) {
                return getPoints(group, team);
            }
        }

        return 0;
    }

    private int getPoints(Group group, Team team) {
        int points = 0;

        for (int i = 0; i < groupMatches.size(); i++) {
            Match match = groupMatches.get(i);

            if (groupForMatch.get(i) != group || !match.isFinished()) {
                continue;
            }

            boolean teamPlayed =
                    match.getFirstTeam().equals(team) ||
                            match.getSecondTeam().equals(team);

            if (!teamPlayed) {
                continue;
            }

            if (match.isTied()) {
                points += 1;
            } else if (team.equals(match.getWinner())) {
                points += 3;
            }
        }

        return points;
    }

    private List<Team> getAdvancingTeams() {
        List<Team> advancingTeams = new ArrayList<>();
        List<Team> thirdPlaceTeams = new ArrayList<>();

        for (Group group : groups) {
            List<Team> sortedTeams = new ArrayList<>(group.getTeams());

            sortedTeams.sort((team1, team2) -> compareTeams(group, team1, team2));

            advancingTeams.add(sortedTeams.get(0));
            advancingTeams.add(sortedTeams.get(1));
            thirdPlaceTeams.add(sortedTeams.get(2));
        }

        thirdPlaceTeams.sort(this::compareThirdPlaceTeams);

        for (int i = 0; i < THIRD_PLACE_ADVANCE; i++) {
            advancingTeams.add(thirdPlaceTeams.get(i));
        }

        advancingTeams.sort(Comparator.comparingInt(Team::getRanking));

        return advancingTeams;
    }

    private void createGroupMatches() {
        for (Group group : groups) {
            List<Team> groupTeams = group.getTeams();

            for (int i = 0; i < groupTeams.size(); i++) {
                for (int j = i + 1; j < groupTeams.size(); j++) {
                    Match match = new Match(groupTeams.get(i), groupTeams.get(j), true);

                    groupMatches.add(match);
                    groupForMatch.add(group);
                }
            }
        }
    }

    // simulates the next match in the schedule
    public Match simulateOneMatch() {
        if (champion != null) {
            return null;
        }

        // Simulates group stage matches first.
        // Once group stage is complete, starts knockout stage.
        if (!isGroupStageFinished()) {
            Match match = groupMatches.get(groupMatchIndex);
            match.simulate();

            groupMatchIndex++;

            if (isGroupStageFinished()) {
                startKnockoutStage();
            }

            return match;
        }

        // Simulates knockout stage matches.
        Match match = knockoutMatches.get(knockoutMatchIndex);
        match.simulate();

        roundWinners.add(match.getWinner());
        knockoutMatchIndex++;

        if (knockoutMatchIndex >= knockoutMatches.size()) {
            if (roundWinners.size() == 1) {
                champion = roundWinners.get(0);
            } else {
                createKnockoutRound(roundWinners);
            }
        }

        return match;
    }

    private void startKnockoutStage() {
        List<Team> advancingTeams = getAdvancingTeams();
        createKnockoutRound(advancingTeams);
    }

    private void createKnockoutRound(List<Team> roundTeams) {
        List<Team> teamsForRound = new ArrayList<>(roundTeams);

        knockoutMatches.clear();
        roundWinners.clear();

        knockoutMatchIndex = 0;

        for (int i = 0; i < teamsForRound.size() / 2; i++) {
            Team team1 = teamsForRound.get(i);
            Team team2 = teamsForRound.get(teamsForRound.size() - 1 - i);

            knockoutMatches.add(new Match(team1, team2, false));
        }
    }

    private int compareTeams(Group group, Team team1, Team team2) {
        int pointsCompare = Integer.compare(
                getPoints(group, team2),
                getPoints(group, team1)
        );

        if (pointsCompare != 0) {
            return pointsCompare;
        }

        return Integer.compare(team1.getRanking(), team2.getRanking());
    }

    private int compareThirdPlaceTeams(Team team1, Team team2) {
        int pointsCompare = Integer.compare(
                getTotalPoints(team2),
                getTotalPoints(team1)
        );

        if (pointsCompare != 0) {
            return pointsCompare;
        }

        return Integer.compare(team1.getRanking(), team2.getRanking());
    }

    // when there is a winner, it will return true
    public boolean isFinished() {
        return champion != null;
    }

    // returns true when the group stage matches are all complete
    public boolean isGroupStageFinished() {
        return groupMatchIndex >= groupMatches.size();
    }
}
