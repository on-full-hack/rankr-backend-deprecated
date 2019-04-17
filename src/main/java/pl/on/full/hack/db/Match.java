package pl.on.full.hack.db;

import lombok.Data;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.league.entity.League;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "matches")
@Data
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToOne()
    @JoinColumn(name = "winner_id")
    private RankrUser winner;

    @ManyToOne()
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @ManyToOne()
    @JoinColumn(name = "league_id")
    private League league;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "match_player",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<RankrUser> players = new HashSet<>();
}
