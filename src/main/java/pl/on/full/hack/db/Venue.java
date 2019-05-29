package pl.on.full.hack.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.on.full.hack.match.entity.Match;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "venues")
@Data
@AllArgsConstructor
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Double latitude;

    private Double longitude;

    @OneToMany(mappedBy = "venue")
    private Set<Match> matches = new HashSet<>();
}
