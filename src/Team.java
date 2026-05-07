<<<<<<< HEAD
=======
import javafx.scene.image.Image;

>>>>>>> a4a5c84f31d74e721e6bf9c0d65af2781fabaa4c
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
<<<<<<< HEAD
    private final String color;

    public Team(String team, int ranking, String region, String code, String iso2, String homeStadium, String headCoach, String flagPath, String color) {
=======

    public Team(String team, int ranking, String region, String code, String iso2, String homeStadium, String headCoach, String flagPath) {
>>>>>>> a4a5c84f31d74e721e6bf9c0d65af2781fabaa4c
        this.name = team;
        this.ranking = ranking;
        this.region = region;
        this.code = code;
        this.iso2 = iso2;
        this.homeStadium = homeStadium;
        this.headCoach = headCoach;
        this.flagPath = flagPath;
<<<<<<< HEAD
        this.color = color;
    }

    public String getName() {return name;}
    public String getRanking() {return String.valueOf(ranking);}
=======
    }

    public String getName() {return name;}
    public int getRanking() {return ranking;}
>>>>>>> a4a5c84f31d74e721e6bf9c0d65af2781fabaa4c
    public String getRegion() {return region;}
    public String getCode() {return code;}
    public String getIso2() {return iso2;}
    public String getHomeStadium() {return homeStadium;}
    public String getHeadCoach() {return headCoach;}
    public String getFlagPath() {return flagPath;}
<<<<<<< HEAD
    public String getColor() {return color;}
=======
>>>>>>> a4a5c84f31d74e721e6bf9c0d65af2781fabaa4c


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
<<<<<<< HEAD
                ", color=" + color +
=======
>>>>>>> a4a5c84f31d74e721e6bf9c0d65af2781fabaa4c
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
