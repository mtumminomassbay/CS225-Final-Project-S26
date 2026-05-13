import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The penaltyShootout class represents the penalty kick portion of a match
 *
 * A penalty shootout only happens when:
 * - the match does not allow ties, and
 * - the teams remain tied after regulation time and overtime
 *
 *  This class stores both teams and the number of penalty goals scored by each team.
 *  @author Jasper Carr
 *
 */
public class PenaltyShootout {

    //Joey Barton additions
    /*
        This class holds the result of a single Penalty Kick
    */
    public static class KickResult {

        private final Team team;
        private final boolean scored;


        private final int round;

        public KickResult(Team team, boolean scored, int round) {
            this.team = team;
            this.scored = scored;
            this.round = round;
        }

        public Team getTeam() {return team;}
        public boolean isScored() { return scored;}
        public int getRound() { return round; }

        @Override
        public String toString() {
            return "Round " + round + " | " + team.getName() + ": " + (scored ? "Scored" : "Missed");
        }
    }


    private Team team1;
    private Team team2;

    private int team1Goals;
    private int team2Goals;

    private boolean finished;

    private final List<KickResult> kickResults;

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

        // The shootout has not been completed yet
        this.finished = false;

        this.kickResults = new ArrayList<>();
    }

    /**
     * Simulates the penalty shootout
     * The Random object is passed in from Match
     * This keeps the match simulation connected to the same seed
     * Each team first takes 5 penalty kicks
     * If still tied, the shootout continues one kick at a time
     * until one team wins
     * @param random the seeded Random object used for simulation
     */
    public void simulate(Random random) {
        // Prevent the same penalty shootout from being simulated more than once
        if (isFinished()) {
            return;
        }

        // Standard penalty shootout: each team gets 5 kicks
        for (int i = 0; i < 5; i++) {
            // if (penaltyScored(team1, random)) {
            //     team1Goals++;
            // }

            // if (penaltyScored(team2, random)) {
            //     team2Goals++;
            // }

            performKick(team1, i + 1, random);
            performKick(team2, i + 1, random);
        }

        int round = 6;
        // Sudden death: continue until one team wins
        while (team1Goals == team2Goals) {
            // boolean team1Scored = penaltyScored(team1, random);
            // boolean team2Scored = penaltyScored(team2, random);

            // if (team1Scored) {
            //     team1Goals++;
            // }

            // if (team2Scored) {
            //     team2Goals++;
            // }

            performKick(team1, round, random);
            performKick(team2, round, random);
            round++;
        }

        finished = true;
    }

    private void performKick(Team team, int round, Random random) {
        boolean scored = penaltyScored(team, random);
        kickResults.add(new KickResult(team, scored, round));

        if(scored) {
            if(team == team1) {
                team1Goals++;
            } else {
                team2Goals++;
            }
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

    public List<KickResult> getKickResults() { return this.kickResults;}
}
