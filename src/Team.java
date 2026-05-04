/**
 * @author Jonathan(soon to be Tristen)
 */

/**
 * Tristan had implemented this already since he handled the json serialization. This file will be replaced by his.
 */

public class Team {
    public Team() {

import java.util.Objects;

public class Team {
    private final String name;
    private final int ranking;
    private final String region;

    public Team(String team, int ranking, String region) {
        this.name = team;
        this.ranking = ranking;
        this.region = region;
    }

    public String getName() {return name;}
    public int getRanking() {return ranking;}
    public String getRegion() {return region;}


    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", ranking=" + ranking +
                ", region='" + region + '\'' +
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
