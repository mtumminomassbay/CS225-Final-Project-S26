//Joey Barton
/*To add a new Screen:
  1. Add an entry here
  2. Create the FMXL file in the resources folder
  3. Create the controller in the src folder
*/
public enum Screen {

    DASHBOARD ("dashboard.fxml", "Dashboard"),
    GROUP_STAGE ("group-stage.fxml", "Group Stage"),
    KNOCKOUT ("knockout.fxml", "Knockout Rounds"),
    MATCH_DETAILS ("match-details.fxml", "Match Details"),
    TEAM_INFO ("team-info.fxml", "Team Info"),

    TEST ("Testfxml.fxml", "Test");


    private String fxmlFile;
    private String displayName;

    Screen(String fxmlFile, String displayName) {
        this.fxmlFile = fxmlFile;
        this.displayName = displayName;
    }

    public String getFxmlFile() { return this.fxmlFile;}
    public String getDisplayName() { return this.displayName;}
}
