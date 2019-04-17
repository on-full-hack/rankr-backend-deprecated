package pl.on.full.hack.league.entity;

import lombok.Data;
import pl.on.full.hack.db.Match;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "leagues")
@Data
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "league")
    private Set<Match> matches = new HashSet<>();

    @OneToMany(mappedBy = "league")
    private Set<LeaguePlayer> leaguePlayer = new HashSet<>();
}
