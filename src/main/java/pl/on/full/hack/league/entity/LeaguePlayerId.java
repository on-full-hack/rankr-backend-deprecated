package pl.on.full.hack.league.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
@Entity
public class LeaguePlayerId implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "league_id")
    private Long leagueId;

    private boolean activated;
}
