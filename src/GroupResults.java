/**
 * @author Jonathan
 */
public class GroupResults implements Comparable<GroupResults> {
    private Team team;

    private int wins;
    private int losses;
    private int ties;

    private int goalsFor;
    private int goalsAgainst;

    public GroupResults(Team team) {
        this.team = team;
    }

    public void addWin() {
        wins++;
    }

    public void addTie() {
        ties++;
    }

    public void addLoss() {
        losses++;
    }

    public void addResult(int scored, int conceded) {
        goalsFor += scored;
        goalsAgainst += conceded;

        if (scored > conceded) {
            addWin();
        } else if (scored < conceded) {
            addLoss();
        } else {
            addTie();
        }
    }

    public Team getTeam() {
        return team;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }

    public int getPoints() {
        return wins * 3 + ties;
    }

    @Override
    public int compareTo(GroupResults other) {
        int pointCompare = Integer.compare(other.getPoints(), this.getPoints());
        if (pointCompare != 0) {
            return pointCompare;
        }

        int goalDifferenceCompare = Integer.compare(other.getGoalDifference(), this.getGoalDifference());
        if (goalDifferenceCompare != 0) {
            return goalDifferenceCompare;
        }

        int goalsForCompare = Integer.compare(other.getGoalsFor(), this.getGoalsFor());
        if (goalsForCompare != 0) {
            return goalsForCompare;
        }

        int rankingCompare = Integer.compare(this.team.getRanking(), other.team.getRanking());
        if (rankingCompare != 0) {
            return rankingCompare;
        }

        return this.team.getName().compareTo(other.team.getName());
    }
}
