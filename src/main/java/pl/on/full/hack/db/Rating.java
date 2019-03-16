package pl.on.full.hack.db;

import pl.on.full.hack.auth.entity.RankrUser;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {

    @EmbeddedId
    private RatingPK id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private RankrUser player;

    @ManyToOne
    @MapsId("leagueId")
    @JoinColumn(name = "league_id")
    private League league;
}
