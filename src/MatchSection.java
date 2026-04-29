

import jdk.jshell.spi.ExecutionControl;

/**
 * @author Jonathan
 */

public class MatchSection {
    private Team team1;
    private Team team2;

    private int durationMinutes;
    private int team1Score;
    private int team2Score;
    public MatchSection() {

        //probably should change this sometime
        this(null, null, 0);
    }
    public MatchSection(Team team1, Team team2, int durationMinutes) {
        this.team1 = team1;
        this.team2 = team2;
        this.durationMinutes = durationMinutes;
    }

    public void simulate() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getTeam1Score() {
        return team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }
}
