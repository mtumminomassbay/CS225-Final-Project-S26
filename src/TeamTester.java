/**
 * TeamTester class.
 *
 * A lightweight class to test the output of the Team, and TeamParser classes
 *
 * @author Tristan Burchard
 */

import java.util.ArrayList;

public class TeamTester {
    public static void main(String[] args) throws Exception {
        TeamParser parser = new TeamParser();
        ArrayList<Team> teams = parser.getTeams();

        for (Team t : teams) {
            System.out.println(t);
            //FlagDownloader.download(t.getCode(), t.getIso2());
        }
    }
}
