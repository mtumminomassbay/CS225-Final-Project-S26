/**
 * TeamTester class.
 *
 * A lightweight class to test the output of the Team, and TeamParser classes
 *
 * @author Tristan Burchard
 */

import java.util.*;

public class TeamTester {
    public static void main(String[] args) throws Exception {

        ArrayList<Team> list = new ArrayList<>();
        for (int i = 0; i < 48; i++) {
            list.add(new Team("Test"+i, 0, "","","",
                    "","","",""));
        }

        GroupStage groupStage = new GroupStage(list);

        List<Group> groups = groupStage.getGroups();
        List<Team> teams = groupStage.getTeams();
        System.out.println("Compiled.");
    }
}
