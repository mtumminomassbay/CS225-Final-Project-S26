public class BracketBranch {
    private Match match;
    private BracketBranch leftBranch;
    private BracketBranch rightBranch;

    public BracketBranch(Team team1, Team team2) {
        this.match = new Match(team1, team2, false);
        this.leftBranch = null;
        this.rightBranch = null;
    }

    public BracketBranch(BracketBranch left, BracketBranch right) {
        this.leftBranch = left;
        this.rightBranch = right;
        this.match = null;
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

    public boolean isFinished() {
        return match != null && match.isFinished();
    }

    public Team getWinner() {
        if (!isFinished()) return null;
        return match.getWinner();
    }

    public Match simulateOneMatch() {
        if (leftBranch != null && !leftBranch.isFinished()) {
            return leftBranch.simulateOneMatch();
        }
        if (rightBranch != null && !rightBranch.isFinished()) {
            return rightBranch.simulateOneMatch();
        }
        if ( match == null) {
            match = new Match(leftBranch.getWinner(), rightBranch.getWinner(), false);
        }

        match.simulate();
        return match;
    }

}
