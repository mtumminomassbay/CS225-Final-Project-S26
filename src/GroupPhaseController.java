import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/*
    Controller for group-stage.fxml
*/
public class GroupPhaseController extends BaseController {
    @FXML private ComboBox<String> groupComboBox;

    @FXML private TableView<GroupResults> standingsTable;
    @FXML private TableColumn<GroupResults, String> teamColumn;
    @FXML private TableColumn<GroupResults, Integer> rankingColumn;
    @FXML private TableColumn<GroupResults, Integer> winsColumn;
    @FXML private TableColumn<GroupResults, Integer> tiesColumn;
    @FXML private TableColumn<GroupResults, Integer> lossesColumn;
    @FXML private TableColumn<GroupResults, Integer> goalsForColumn;
    @FXML private TableColumn<GroupResults, Integer> goalsAgainstColumn;
    @FXML private TableColumn<GroupResults, Integer> goalDifferenceColumn;
    @FXML private TableColumn<GroupResults, Integer> pointsColumn;

    @FXML private ListView<String> matchListView;
    @FXML private TextArea summaryText;

    @FXML private Button simulateButton;
    @FXML private Button backButton;

    private GroupStage groupStage;

    @Override
    protected void onLoad() {
        TeamParser parser = new TeamParser();
        groupStage = new GroupStage(parser.getTeams());

        setupTable();
        loadGroupNames();

        groupComboBox.getSelectionModel().selectFirst();

        showSelectedGroup();
        updateSummaryText();
    }

    private void setupTable() {
        teamColumn.setCellValueFactory(data ->
            new ReadOnlyStringWrapper(data.getValue().getTeam().getName())
        );

        rankingColumn.setCellValueFactory(data ->
            new ReadOnlyObjectWrapper<>(data.getValue().getTeam().getRanking())
        );

        winsColumn.setCellValueFactory(data ->
            new ReadOnlyObjectWrapper<>(data.getValue().getWins())
        );

        tiesColumn.setCellValueFactory(data ->
            new ReadOnlyObjectWrapper<>(data.getValue().getTies())
        );

        lossesColumn.setCellValueFactory(data ->
            new ReadOnlyObjectWrapper<>(data.getValue().getLosses())
        );

        goalsForColumn.setCellValueFactory(data ->
            new ReadOnlyObjectWrapper<>(data.getValue().getGoalsFor())
        );

        goalsAgainstColumn.setCellValueFactory(data ->
            new ReadOnlyObjectWrapper<>(data.getValue().getGoalsAgainst())
        );

        goalDifferenceColumn.setCellValueFactory(data ->
            new ReadOnlyObjectWrapper<>(data.getValue().getGoalDifference())
        );

        pointsColumn.setCellValueFactory(data ->
            new ReadOnlyObjectWrapper<>(data.getValue().getPoints())
        );
    }

    private void loadGroupNames() {
        List<String> groupNames = new ArrayList<>();

        for (Group group : groupStage.getGroups()) {
            groupNames.add(group.getGroupName());
        }

        groupComboBox.setItems(FXCollections.observableArrayList(groupNames));
    }

    @FXML
    private void groupSelected() {
        showSelectedGroup();
    }

    @FXML
    private void simulateButton_clicked() {
        groupStage.simulateGroupStage();
        showSelectedGroup();
        updateSummaryText();
    }

    @FXML
    private void backButton_clicked() {
        navigateTo(Screen.DASHBOARD);
    }

    private void showSelectedGroup() {
        String selectedGroupName = groupComboBox.getSelectionModel().getSelectedItem();

        if (selectedGroupName == null) {
            return;
        }

        Group selectedGroup = groupStage.getGroupByName(selectedGroupName);

        if (selectedGroup == null) {
            return;
        }

        standingsTable.setItems(
            FXCollections.observableArrayList(selectedGroup.getSortedResults())
        );

        matchListView.setItems(
            FXCollections.observableArrayList(getMatchStrings(selectedGroup))
        );
    }

    private List<String> getMatchStrings(Group group) {
        List<String> matchStrings = new ArrayList<>();

        for (Match match : group.getMatches()) {
            String text;

            if (match.isFinished()) {
                text = match.getFirstTeam().getName()
                    + " " + match.getFirstTeamScore()
                    + " - "
                    + match.getSecondTeamScore()
                    + " " + match.getSecondTeam().getName();
            } else {
                text = match.getFirstTeam().getName()
                    + " vs "
                    + match.getSecondTeam().getName();
            }

            matchStrings.add(text);
        }

        return matchStrings;
    }

   private void updateSummaryText() {
    StringBuilder builder = new StringBuilder();

    builder.append("Welcome to the group stage!\n\n");
    builder.append("We are using ").append(groupStage.getTeams().size()).append(" teams ");
    builder.append("split into ").append(groupStage.getGroups().size()).append(" groups.\n\n");

    if (!groupStage.isSimulated()) {
        builder.append("No matches have been played yet.\n");
        builder.append("Click \"Simulate Group Stage\" to play every group match and see who moves on.");
    } else {
        builder.append("The group stage is complete!\n");
        builder.append(groupStage.getAdvancingTeams().size()).append(" teams have qualified for the knockout rounds.\n\n");

        builder.append("Teams moving on:\n");

        for (Team team : groupStage.getAdvancingTeams()) {
            builder.append("• ").append(team.getName()).append("\n");
        }
    }

    summaryText.setText(builder.toString());
    }
}
