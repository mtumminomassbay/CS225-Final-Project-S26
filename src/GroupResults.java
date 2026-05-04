/**
 * @author Jonathan
 */

public class GroupResults {
    private Team team;
    private int wins;
    private int losses;
    private int ties;


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

    public Team getTeam() {
        return team;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    public int getWins() {
        return wins;
    }
}
