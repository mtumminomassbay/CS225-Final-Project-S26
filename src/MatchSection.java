import java.util.Random;

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
     * Creates one section of a match.
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
     * The Random object is passed in from Match
     * The section duration controls the number of scoring chances
     * A 45-minute half gets more chances than a 30-minute overtime
     * @param random the seeded Random object used for simulation
     */
    public void simulate(Random random) {
        // Prevent the same section from being simulated more than once
        if (isFinished()) {
            return;
        }

        // Longer sections get more scoring chances
        int scoringChances = Math.max(1, durationMinutes / 15);

        for (int i = 0; i < scoringChances; i++) {
            // Not every scoring chance becomes a goal
            if (random.nextDouble() < 0.35) {
                Team scoringTeam = chooseScoringTeam(random);

                if (scoringTeam.equals(team1)) {
                    team1Score++;
                } else {
                    team2Score++;
                }
            }
        }

        finished = true;
    }

    /**
     * Chooses which team scores based on ranking
     * @param random the seeded Random object used for simulation
     * @return the team that scores
     */
    private Team chooseScoringTeam(Random random) {
        double team1Strength = getStrengthFromRanking(team1);
        double team2Strength = getStrengthFromRanking(team2);

        double totalStrength = team1Strength + team2Strength;

        // Probability that team1 scores the goal.
        double team1Chance = team1Strength / totalStrength;

        if (random.nextDouble() < team1Chance) {
            return team1;
        } else {
            return team2;
        }
    }

    /**
     * Converts ranking into a strength value
     * Since a lower ranking number is better, subtracting from 212 gives
     * higher-ranked teams a larger strength value
     */
    private double getStrengthFromRanking(Team team) {
        return 212.0 - team.getRanking();
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
     * A section is finished once simulate() has been called
     * This uses a boolean so even a 0-0 section can still count
     * as completed
     */
    public boolean isFinished() {
        return finished;
    }
}
