/**
 * @author Joshua Staub
 */
public enum StageMode {
    GROUP_STAGE,
    KNOCKOUT_STAGE,
    COMPLETE;

    @Override
    public String toString() {
        return switch (this) {
            case GROUP_STAGE -> "Group Stage";
            case KNOCKOUT_STAGE -> "Knockout Stage";
            case COMPLETE -> "Complete";
        };
    }
}