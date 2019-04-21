package pl.on.full.hack.league.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaguePlayerId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "league_id")
    private Long leagueId;

    private boolean activated;
}
