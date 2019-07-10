package pl.on.full.hack.match.Templates;

import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.db.Venue;

import java.util.Set;

public class MatchDetails {
    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(long winnerId) {
        this.winnerId = winnerId;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public Set<Long> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Long> players) {
        this.players = players;
    }

    private Venue venue;
    private long winnerId;
    private String discipline;
    private Set<Long> players;
}
