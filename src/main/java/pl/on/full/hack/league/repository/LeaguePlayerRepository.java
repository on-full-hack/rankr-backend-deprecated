package pl.on.full.hack.league.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.league.entity.LeaguePlayer;
import pl.on.full.hack.league.entity.LeaguePlayerId;

import java.util.Optional;

@Repository
public interface LeaguePlayerRepository extends JpaRepository<LeaguePlayer, LeaguePlayerId> {

    Optional<LeaguePlayer> findByPlayer(final RankrUser player);
}
