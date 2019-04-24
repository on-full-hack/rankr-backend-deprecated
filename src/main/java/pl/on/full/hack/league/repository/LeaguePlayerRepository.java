package pl.on.full.hack.league.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.on.full.hack.league.entity.LeaguePlayer;
import pl.on.full.hack.league.entity.LeaguePlayerId;

@Repository
public interface LeaguePlayerRepository extends JpaRepository<LeaguePlayer, LeaguePlayerId> {
}
