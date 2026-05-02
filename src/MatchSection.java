/**
 * The MatchSection class represents one timed section of a soccer match.
 *
 * Examples of match sections include:
 * - First half
 * - Second half
 * - Overtime
 *
 * Each MatchSection stores the two teams, the length of the section,
 * and the number of goals scored by each team during that section
 * Author: Jasper Carr
 *
 */
public class MatchSection {

    private Team team1;
    private Team team2;
    private int durationMinutes;

    private int team1Score;
    private int team2Score;
    private boolean finished;

    /**
     * Creates one section of a match
     *
     * @param team1 the first team in the match section
     * @param team2 the second team in the match section
     * @param durationMinutes how long this section lasts in minutes
     */
    public MatchSection(Team team1, Team team2, int durationMinutes) {
        this.team1 = team1;
        this.team2 = team2;
        this.durationMinutes = durationMinutes;

        // Scores start at 0 before the section is simulated
        this.team1Score = 0;
        this.team2Score = 0;

        this.finished = false;
    }

    /**
     * Simulates this section of the match
     *
     * At the moment this uses placeholder results instead of random logic
     * Will be replaced later
     */
    public void simulate() {
        // Placeholder result:
        // Team 1 scores 1 goal and Team 2 scores 0 goals
        this.team1Score = 1;
        this.team2Score = 0;

        this.finished = true;
    }

    public Team getFirstTeam() {
        return team1;
    }

    public Team getSecondTeam() {
        return team2;
    }

    public int getFirstTeamScore() {
        return team1Score;
    }

    public int getSecondTeamScore() {
        return team2Score;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * A section is finished once simulate() has been called.
     *
     * This uses a boolean so even a 0-0 section can still count
     * as completed.
     */
    public boolean isFinished() {
        return finished;
    }
}
