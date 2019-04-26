package pl.on.full.hack.league.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.on.full.hack.auth.entity.RankrUser;

import javax.persistence.*;

@Entity
@Table(name = "league_player")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaguePlayer {

    @EmbeddedId
    private LeaguePlayerId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private RankrUser player;

    @ManyToOne
    @MapsId("leagueId")
    @JoinColumn(name = "league_id")
    private League league;

    private Long rating;

    private Boolean active;
}
