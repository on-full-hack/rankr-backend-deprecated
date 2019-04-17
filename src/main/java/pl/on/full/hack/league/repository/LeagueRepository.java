package pl.on.full.hack.league.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.on.full.hack.league.entity.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

}
