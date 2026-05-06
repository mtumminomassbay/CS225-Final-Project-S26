//Joey Barton

/*
    Screen is an enum that maps each screen in the app to its FXML file.

    The Navigator uses these Screen values to know which FXML file to load.
*/
/*To add a new Screen:
  1. Add an entry here
  2. Create the .fxml file in the resources folder
  3. Create the Controller.java in the src folder
*/

/*
    Example of how to navigate to a new screen

    navigateTo(Screen.GROUP_STAGE);

*/
public enum Screen {

    DASHBOARD ("dashboard.fxml", "Dashboard"),
    GROUP_STAGE ("group-stage.fxml", "Group Stage"),
    KNOCKOUT ("knockout.fxml", "Knockout Rounds"),
    MATCH_DETAILS ("match-details.fxml", "Match Details"),
    TEAM_INFO ("TeamInfoView.fxml", "Team Info");

    private String fxmlFile;
    private String displayName;

    Screen(String fxmlFile, String displayName) {
        this.fxmlFile = fxmlFile;
        this.displayName = displayName;
    }

    public String getFxmlFile() { return this.fxmlFile;}
    public String getDisplayName() { return this.displayName;}
}
