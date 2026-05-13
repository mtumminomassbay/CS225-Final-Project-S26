/**
 * Team class.
 *
 * Stores information regarding about a Team
 * Holds information for other classes to call from
 *
 * @author Tristan Burchard
 */

import java.util.Objects;

public class Team {
    private final String name;
    private final int ranking;
    private final String region;
    private final String code;
    private final String iso2;
    private final String homeStadium;
    private final String headCoach;
    private final String flagPath;
    private final String color;

    private Group group = null;
    private String stage;
    private String record;

    public Team(String team, int ranking, String region, String code, String iso2, String homeStadium, String headCoach, String flagPath, String color) {
        this.name = team;
        this.ranking = ranking;
        this.region = region;
        this.code = code;
        this.iso2 = iso2;
        this.homeStadium = homeStadium;
        this.headCoach = headCoach;
        this.flagPath = flagPath;
        this.color = color;
    }

    public String getName() {return name;}
    public int getRanking() {return ranking;}
    public String getRegion() {return region;}
    public String getCode() {return code;}
    public String getIso2() {return iso2;}
    public String getHomeStadium() {return homeStadium;}
    public String getHeadCoach() {return headCoach;}
    public String getFlagPath() {return flagPath;}
    public String getColor() {return color;}

    public Group getGroup() {return group;}
    public String getStage() {return stage;}
    public String getRecord() {return record;}

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", ranking=" + ranking +
                ", region='" + region + '\'' +
                ", code='" + code + '\'' +
                ", iso2='" + iso2 + '\'' +
                ", homeStadium='" + homeStadium + '\'' +
                ", headCoach='" + headCoach + '\'' +
                ", flagPath=" + flagPath + '\''+
                ", color=" + color + '\'' +
                ", group='" + group + '\'' +
                ", stage=" + stage + '\''+
                ", record=" + record + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return ranking == team.ranking && Objects.equals(name, team.name) && Objects.equals(region, team.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ranking, region);
    }
}
