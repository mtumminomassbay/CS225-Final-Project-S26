public class BracketBranch {
    private Match match;
    private BracketBranch leftBranch;
    private BracketBranch rightBranch;
    private final int BRACKET_STAGE;
    private int matchesSimulated = 0;

    public BracketBranch(Team team1, Team team2, int bracketStage) {
        this.match = new Match(team1, team2, false);
        this.leftBranch = null;
        this.rightBranch = null;
        this.BRACKET_STAGE = bracketStage;

        team1.setStage(bracketStageToLabel(BRACKET_STAGE));
        team2.setStage(bracketStageToLabel(BRACKET_STAGE));
    }

    public BracketBranch(BracketBranch left, BracketBranch right, int bracketStage) {
        this.leftBranch = left;
        this.rightBranch = right;
        this.match = null;
        this.BRACKET_STAGE = bracketStage;
    }

    public Match getMatch() {
        return match;
    }

    public BracketBranch getLeftBranch() {
        return leftBranch;
    }

    public BracketBranch getRightBranch() {
        return rightBranch;
    }

    public int getBracketStage() {
        return BRACKET_STAGE;
    }

    public boolean isFinished() {
        return match != null && match.isFinished();
    }

    public Team getWinner() {
        if (!isFinished()) return null;
        return match.getWinner();
    }

    public int getMatchesSimulated() {
        return matchesSimulated;
    }

    public Match simulateOneMatch(boolean dryRun) {
        if (match != null) {
            if (match.isFinished()) {
                return null;
            }

            if (!dryRun) {
                matchesSimulated++;
                match.simulate();
                match.getWinner().setStage(bracketStageToLabel(BRACKET_STAGE / 2));

                if (BRACKET_STAGE == 4) {
                    match.getLoser().setStage(bracketStageToLabel(3));
                }
            }

            return match;
        }

        Match result = null;
        // Keep track of the total number of matches simulated in this branch, then simulate the child branch with the smaller
        // number of finished matches. This ensures that each round (round of 32, of 16, etc) is fully completed before moving
        // on to the next.
        if (!dryRun) {
            matchesSimulated++;
        }

        if (leftBranch != null && rightBranch != null) {
            if (!leftBranch.isFinished() && leftBranch.matchesSimulated <= rightBranch.matchesSimulated) {
                result = leftBranch.simulateOneMatch(dryRun);
            } else if (!rightBranch.isFinished()) {
                result = rightBranch.simulateOneMatch(dryRun);
            }
        }

        if (!dryRun && leftBranch.isFinished() && rightBranch.isFinished()) {
            match = new Match(leftBranch.getWinner(), rightBranch.getWinner(), false);
        }

        return result;
    }

    public static String bracketStageToLabel(int bracketStage) {
        return switch (bracketStage) {
            case 1 -> "Champion";
            case 2 -> "Finals";
            case 3 -> "Match for Third";
            case 4 -> "Semifinals";
            case 8 -> "Quarterfinals";
            default -> "Round of " + bracketStage;
        };
    }
}
