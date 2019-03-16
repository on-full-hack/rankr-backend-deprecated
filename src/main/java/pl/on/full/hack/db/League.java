package pl.on.full.hack.db;

import lombok.Data;
import pl.on.full.hack.auth.entity.RankrUser;

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

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "league_competitor",
            joinColumns = @JoinColumn(name = "league_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<RankrUser> competitors = new HashSet<>();

    @OneToMany(mappedBy = "player")
    private Set<Rating> rating = new HashSet<>();
}
