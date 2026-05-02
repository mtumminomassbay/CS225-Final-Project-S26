/**
 * The Match class represents one soccer game between two teams.
 *
 * A Match is made up of smaller parts:
 * - First half
 * - Second half
 * - Overtime, if ties are not allowed
 * - Penalty shootout, if the game is still tied after overtime
 *
 * The allowTies field determines whether the match can end in a tie.
 * GroupStage matches allow ties, while KnockoutStage matches do not
 * Author: Jasper Carr
 *
 */
public class Match {

    private Team team1;
    private Team team2;
    private boolean allowTies;

    private MatchSection firstHalf;
    private MatchSection secondHalf;
    private MatchSection overtime;
    private PenaltyShootout penalties;

    /**
     * Creates a match between two teams.
     *
     * @param team1 the first team in the match
     * @param team2 the second team in the match
     * @param allowTies true if the match can end tied, false for knockout
     */
    public Match(Team team1, Team team2, boolean allowTies) {
        this.team1 = team1;
        this.team2 = team2;
        this.allowTies = allowTies;

        // A soccer match has two 45 minute halves
        this.firstHalf = new MatchSection(team1, team2, 45);
        this.secondHalf = new MatchSection(team1, team2, 45);

        // Overtime and penalties only happen if needed
        this.overtime = null;
        this.penalties = null;
    }

    /**
     * Simulates the match
     *
     * Uses placeholder results through MatchSection
     * Can be randomized later
     */
    public void simulate() {
        // Simulate the two regulation halves
        firstHalf.simulate();
        secondHalf.simulate();

        // If ties are allowed, the match can end after regulation
        if (allowTies) {
            return;
        }

        // If ties are not allowed and the score is tied,
        // the match must go into overtime
        if (isTied()) {
            penalties = new PenaltyShootout(team1, team2);
            penalties.simulate();
        }
    }

    public Team getFirstTeam() {
        return team1;
    }

    public Team getSecondTeam() {
        return team2;
    }

    public int getFirstTeamScore() {
        int score = firstHalf.getFirstTeamScore() + secondHalf.getFirstTeamScore();

        if (overtime != null) {
            score += overtime.getFirstTeamScore();
        }

        return score;
    }

    public int getSecondTeamScore() {
        int score = firstHalf.getSecondTeamScore() + secondHalf.getSecondTeamScore();

        if (overtime != null) {
            score += overtime.getSecondTeamScore();
        }

        return score;
    }

    public boolean isFinished() {
        return firstHalf.isFinished() && secondHalf.isFinished();
    }

    public MatchSection getFirstHalf() {
        return firstHalf;
    }

    public MatchSection getSecondHalf() {
        return secondHalf;
    }

    public MatchSection getOvertime() {
        return overtime;
    }

    public PenaltyShootout getPenalties() {
        return penalties;
    }

    /**
     * Returns the winner of the match
     *
     * If the match is tied and ties are allowed, there is no winner,
     * so this method returns null
     */
    public Team getWinner() {
        if (getFirstTeamScore() > getSecondTeamScore()) {
            return team1;
        }

        if (getSecondTeamScore() > getFirstTeamScore()) {
            return team2;
        }

        if (penalties != null) {
            if (penalties.getFirstTeamGoals() > penalties.getSecondTeamGoals()) {
                return team1;
            } else {
                return team2;
            }
        }

        //GroupStage tie
        return null;
    }

    public boolean isTied() {
        return getFirstTeamScore() == getSecondTeamScore();
    }
}
