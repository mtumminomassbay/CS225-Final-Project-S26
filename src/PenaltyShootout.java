public class PenaltyShootout {

    private Team team1;
    private Team team2;

    private int team1Goals;
    private int team2Goals;

    public PenaltyShootout(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public void simulate() {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public int getTeam1Goals() {
        return team1Goals;
    }

    public int getTeam2Goals() {
        return team2Goals;
    }
}
