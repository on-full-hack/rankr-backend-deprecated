package pl.on.full.hack.db;

import lombok.Data;

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
