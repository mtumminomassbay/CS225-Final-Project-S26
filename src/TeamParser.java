import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class TeamParser {
    private ArrayList<Team> teams;
    private String filePath = "/teaminfo.json";

    public TeamParser() {
        this.teams = new ArrayList<>();
        loadTeams();
    }

    private void loadTeams() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            InputStream is = TeamParser.class.getResourceAsStream(filePath);

            if (is == null) {
                throw new FileNotFoundException("File not found: " + filePath);
            }

            JsonNode root = mapper.readTree(is);

            for (JsonNode n: root) {
                String name = n.get("name").asText();
                int ranking = n.get("ranking").asInt();
                String region = n.get("region").asText();

                Team team = new Team(name,ranking,region);
                teams.add(team);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON file: " + filePath,e);
        }
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }
}
