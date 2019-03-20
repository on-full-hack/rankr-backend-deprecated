package pl.on.full.hack.db;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class LeaguePlayerId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "league_id")
    private Long leagueId;
}
