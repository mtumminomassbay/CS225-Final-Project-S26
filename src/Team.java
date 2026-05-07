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
    public String getRanking() {return String.valueOf(ranking);}
    public String getRegion() {return region;}
    public String getCode() {return code;}
    public String getIso2() {return iso2;}
    public String getHomeStadium() {return homeStadium;}
    public String getHeadCoach() {return headCoach;}
    public String getFlagPath() {return flagPath;}
    public String getColor() {return color;}


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
                ", flagPath=" + flagPath +
                ", color=" + color +
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
