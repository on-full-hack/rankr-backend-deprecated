package pl.on.full.hack.league.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.on.full.hack.league.entity.League;

import java.util.List;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    League findByCodeToJoin(String linkToJoin);
}

