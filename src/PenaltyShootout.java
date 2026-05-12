import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The penaltyShootout class represents the penalty kick portion of a match
 * A penalty shootout only happens when:
 * - the match does not allow ties, and
 * - the teams remain tied after regulation time and overtime
 *  This class stores both teams and the number of penalty goals scored by each team.
 *  Author: Jasper Carr
 *
 */
public class PenaltyShootout {

    private Team team1;
    private Team team2;

    private int team1Goals;
    private int team2Goals;

    /*
     * Stores the result of each penalty kick.
     * true means the kick was scored.
     * false means the kick was missed.
     */
    private List<Boolean> team1PenaltySequence;
    private List<Boolean> team2PenaltySequence;

    private boolean finished;

    /**
     * Creates a penalty shootout between two teams
     * @param team1 the first team in the shootout
     * @param team2 the second team in the shootout
     */
    public PenaltyShootout(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;

        // Scores start at 0 before the shootout is simulated
        this.team1Goals = 0;
        this.team2Goals = 0;

        // Penalty sequences start empty before the shootout is simulated
        this.team1PenaltySequence = new ArrayList<>();
        this.team2PenaltySequence = new ArrayList<>();

        // The shootout has not been completed yet
        this.finished = false;
    }

    /**
     * Simulates the penalty shootout
     * Each team first takes 5 penalty kicks.
     * If still tied, the shootout continues one kick at a time
     * until one team wins.
     * Each kick is saved in the penalty sequence:
     * true = scored
     * false = missed
     * @param random the seeded Random object used for simulation
     */
    public void simulate(Random random) {
        // Prevent the same penalty shootout from being simulated more than once
        if (isFinished()) {
            return;
        }

        // Standard penalty shootout: each team gets 5 kicks
        for (int i = 0; i < 5; i++) {
            boolean team1Scored = penaltyScored(team1, random);
            boolean team2Scored = penaltyScored(team2, random);

            recordPenaltyKick(team1Scored, team2Scored);
        }

        // Sudden death: continue until one team wins
        while (team1Goals == team2Goals) {
            boolean team1Scored = penaltyScored(team1, random);
            boolean team2Scored = penaltyScored(team2, random);

            recordPenaltyKick(team1Scored, team2Scored);
        }

        finished = true;
    }

    /**
     * Records one round of penalty kicks.
     * Each round includes one kick for team1 and one kick for team2.
     * @param team1Scored true if team1 scored its kick
     * @param team2Scored true if team2 scored its kick
     */
    private void recordPenaltyKick(boolean team1Scored, boolean team2Scored) {
        team1PenaltySequence.add(team1Scored);
        team2PenaltySequence.add(team2Scored);

        if (team1Scored) {
            team1Goals++;
        }

        if (team2Scored) {
            team2Goals++;
        }
    }


    /**
     * Determines whether a team scores a penalty
     * Better-ranked teams get a slightly higher chance to score
     * @param team the team taking the penalty
     * @param random the seeded Random object used for simulation
     * @return true if the penalty is scored, false otherwise
     */
    private boolean penaltyScored(Team team, Random random) {
        // Ranking bonus is higher for better-ranked teams
        double rankingBonus = (211.0 - team.getRanking()) / 211.0;

        // Base chance is 65%, with up to 25% added based on ranking
        double scoreChance = 0.65 + (rankingBonus * 0.25);

        return random.nextDouble() < scoreChance;
    }

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
     * Returns the first team's kick-by-kick penalty sequence.
     * true = scored
     * false = missed
     * @return first team's penalty sequence
     */
    public List<Boolean> getFirstTeamPenaltySequence() {
        return team1PenaltySequence;
    }

    /**
     * Returns the second team's kick-by-kick penalty sequence.
     * true = scored
     * false = missed
     * @return second team's penalty sequence
     */
    public List<Boolean> getSecondTeamPenaltySequence() {
        return team2PenaltySequence;
    }

    /**
     * Returns the first team's penalty sequence as display text.
     * O means scored.
     * X means missed.
     * @return first team's penalty sequence text
     */
    public String getFirstTeamPenaltySequenceText() {
        return getPenaltySequenceText(team1PenaltySequence);
    }

    /**
     * Returns the second team's penalty sequence as display text.
     * O means scored.
     * X means missed.
     * @return second team's penalty sequence text
     */
    public String getSecondTeamPenaltySequenceText() {
        return getPenaltySequenceText(team2PenaltySequence);
    }


    /**
     * Converts a penalty sequence into display text.
     * @param sequence the list of penalty kick results
     * @return display text using O for scored and X for missed
     */
    private String getPenaltySequenceText(List<Boolean> sequence) {
        StringBuilder result = new StringBuilder();

        for (boolean scored : sequence) {
            if (scored) {
                result.append("O ");
            } else {
                result.append("X ");
            }
        }

        return result.toString().trim();
    }

    /**
     * Returns the penalty shootout result as display text.
     * @return penalty result string
     */
    public String getPenaltyResult() {
        return team1.getName() + " " + team1Goals
                + " - "
                + team2Goals + " " + team2.getName();
    }

    /**
     * Returns the winner of the penalty shootout
     * If the shootout has not been simulated yet, this returns null
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
