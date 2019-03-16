package pl.on.full.hack.auth.entity;

import lombok.Data;
import pl.on.full.hack.db.League;
import pl.on.full.hack.db.Match;
import pl.on.full.hack.db.Rating;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ranr_users")
@Data
public class RankrUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    @ManyToMany(mappedBy = "competitors")
    private Set<League> leagues = new HashSet<>();

    @ManyToMany(mappedBy = "players")
    private Set<Match> matches = new HashSet<>();

    @OneToMany(mappedBy = "league")
    private Set<Rating> rating = new HashSet<>();
}
