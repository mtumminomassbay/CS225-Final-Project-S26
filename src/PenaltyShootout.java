/**
 * The penaltyShootout class represents the penalty kick portion of a match
 *
 * A penalty shootout only happens when:
 * - the match does not allow ties, and
 * - the teams remain tied after regulation time and overtime
 *
 *  This class stores both teams and the number of penalty goals scored by each team.
 *  Placeholder results used for now. Weighted/random simulation logic to be added later.
 *  Author: Jasper Carr
 *
 */
public class PenaltyShootout {

    private Team team1;
    private Team team2;

    private int team1Goals;
    private int team2Goals;

    private boolean finished;

    /**
     * Creates a penalty shootout between two teams.
     *
     * @param team1 the first team in the shootout
     * @param team2 the second team in the shootout
     */
    public PenaltyShootout(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;

        // Scores start at 0 before the shootout is simulated
        this.team1Goals = 0;
        this.team2Goals = 0;

        // The shootout has not been completed yet
        this.finished = false;
    }

    /**
     * Simulates the penalty shootout
     *
     * Placeholder logic
     */
    public void simulate() {
        this.team1Goals = 5;
        this.team2Goals = 4;
        this.finished = true;
    }

    /**
     * Returns true if the penalty shootout has been simulated
     *
     * @return true if finished, false otherwise
     */
    public boolean isFinished() {
        return finished;
    }

    public int getFirstTeamGoals() {
        return team1Goals;
    }

    public int getSecondTeamGoals() {
        return team2Goals;
    }

    public Team getFirstTeam() {
        return team1;
    }

    public Team getSecondTeam() {
        return team2;
    }

    /**
     * Returns the winner of the penalty shootout
     *
     * If the shootout has not been simulated yet, this returns null
     *
     * @return the winning team, or null if the shootout is not finished
     */
    public Team getWinner() {
        if (!finished) {
            return null;
        }

        if (team1Goals > team2Goals) {
            return team1;
        } else {
            return team2;
        }
    }
}
